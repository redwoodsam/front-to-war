package com.samuelaraujo.frontend_war_generator.service.frontendlibrary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.samuelaraujo.frontend_war_generator.dto.LibraryDTO;
import com.samuelaraujo.frontend_war_generator.dto.ProjectDetailsDTO;
import com.samuelaraujo.frontend_war_generator.service.exception.NotFoundException;
import com.samuelaraujo.frontend_war_generator.util.TextUtil;

public abstract class FrontEndLibrary {

	protected static String projectName;
	protected static String projectPath;
	protected static String projectFinalName;
	protected static Pattern patternBuild = Pattern.compile("\\\"build(..+)?\\\"\\s?:\\s?\\\".+\\\"");
	protected static Pattern patternProjectName = Pattern.compile("\\\"name\\\"\\s?:\\s?\\\".+\\\"");

	public FrontEndLibrary(String projectName, String projectPath, String projectFinalName) {
		FrontEndLibrary.projectName = projectName;
		FrontEndLibrary.projectPath = projectPath;
		FrontEndLibrary.projectFinalName = projectFinalName;
	}

	public FrontEndLibrary() {
	}

	public String getDistFolderPath() {
		Path buildPath = Paths.get(projectPath, "build");
		Path distPath = Paths.get(projectPath, "dist");

		boolean existsBuild = Files.exists(Paths.get(projectPath, "build"));
		boolean existsDist = Files.exists(Paths.get(projectPath, "dist"));

		if (existsBuild) {
			return buildPath.toString();
		} else if (existsDist) {
			return distPath.toString();
		} else {
			throw new RuntimeException("ERRO: A pasta de build da aplicacao nao foi encontrada.");
		}
	}

	public List<Environment> getEnvironments() {
		String data = "";
		File packageJson = Paths.get(projectPath, "package.json").toFile();
		List<String> initialLines = new ArrayList<>();
		List<Environment> environments = new ArrayList<>();

		if (!packageJson.exists())
			throw new RuntimeException(
					"ERRO: Arquivo package.json nao encontrado, verifique se a pasta do projeto esta correta.");

		try {
			data = new String(Files.readAllBytes(packageJson.toPath())).trim();

			Matcher matcher = patternBuild.matcher(data);
			while (matcher.find()) {
				initialLines.add(matcher.group());
			}

			Pattern scriptPattern = Pattern.compile("\".+\":");

			initialLines.stream().forEach(line -> {
				String[] cmds = line.split("\"build(.)?");
				Matcher scriptMatcher = scriptPattern.matcher(line);

				String environmentString = "";
				String scriptString = "";

				for (String i : cmds) {
					if (!i.equals("\n") && i.length() > 1) {
						String environment = i.substring(0, i.indexOf("\""));
						if (environment.trim().equals(":")) {
							environmentString = "default";
						} else {
							environmentString = environment;
						}
					}
				}

				while (scriptMatcher.find()) {
					String script = scriptMatcher.group();
					scriptString = script.substring(1, script.length() - 2);
				}

				environments.add(new Environment(environmentString, scriptString));
			});

			return environments;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Environment> getEnvironments(String projectPath) {
		String data = "";
		File packageJson = Paths.get(projectPath, "package.json").toFile();
		List<String> initialLines = new ArrayList<>();
		List<Environment> environments = new ArrayList<>();

		if (!packageJson.exists())
			throw new RuntimeException(
					"ERRO: Arquivo package.json nao encontrado, verifique se a pasta do projeto esta correta.");

		try {
			data = new String(Files.readAllBytes(packageJson.toPath())).trim();

			Matcher matcher = patternBuild.matcher(data);
			while (matcher.find()) {
				initialLines.add(matcher.group());
			}

			Pattern scriptPattern = Pattern.compile("\".+\":");

			initialLines.stream().forEach(line -> {
				String[] cmds = line.split("\"build(.)?");
				Matcher scriptMatcher = scriptPattern.matcher(line);

				String environmentString = "";
				String scriptString = "";

				for (String i : cmds) {
					if (!i.equals("\n") && i.length() > 1) {
						String environment = i.substring(0, i.indexOf("\""));
						if (environment.trim().equals(":")) {
							environmentString = "default";
						} else {
							environmentString = environment;
						}
					}
				}

				while (scriptMatcher.find()) {
					String script = scriptMatcher.group();
					scriptString = script.substring(1, script.length() - 2);
				}

				environments.add(new Environment(environmentString, scriptString));
			});

			return environments;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ProjectDetailsDTO getProjectDetails(String projectPath) {
		String data = "";
		File packageJson = Paths.get(projectPath, "package.json").toFile();

		if (!packageJson.exists())
			throw new NotFoundException(
					"ERRO: Arquivo package.json nao encontrado, verifique se a pasta do projeto esta correta.");

		try {
			data = new String(Files.readAllBytes(packageJson.toPath())).trim();
			String projectName = getProjectName(data);
			LibraryDTO projectLibrary = getProjectLibrary(data);
			List<Environment> environments = getEnvironments(projectPath);

			return new ProjectDetailsDTO(projectName, projectName, projectName, projectLibrary, environments);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private String getProjectName(String data) {
		String projectName = "";

		Matcher matcher = patternProjectName.matcher(data);
		if(matcher.find()) {
			projectName = matcher.group();
		}
		
		Pattern namePattern = Pattern.compile("\\:\\s?\\\"\\s?.+\\\"\\.?");
		Matcher nameMatcher = namePattern.matcher(projectName);

		while (nameMatcher.find()) {
			String nameStringRaw = nameMatcher.group();
			String trim = nameStringRaw.substring(1, nameStringRaw.length()).trim();
			String result = trim.substring(1, trim.length() - 1);

			projectName = result;
		}

		return projectName;
	}

	private LibraryDTO getProjectLibrary(String data) {
		String projectLibraryLine = "";
		String projectLibraryName = "";
		String projectLibraryVersion = "";

		List<String> availableLibraries = LibraryEnum.getAvailableLibraries();

		for (String lib : availableLibraries) {
			String regex = "\\\"" + lib + "\\\"\\s?:\\s?\\\".+\\\"";

			Pattern libraryPattern = Pattern.compile(regex);
			Matcher matcher = libraryPattern.matcher(data);

			while (matcher.find()) {
				projectLibraryLine = matcher.group();
			}
		}

		String[] libraryInfo = projectLibraryLine.split(":");

		// Extract Library name
		String libraryNameRaw = libraryInfo[0].trim();
		String libraryNameWithoutQuotes = libraryNameRaw.substring(1, libraryNameRaw.length() - 1);
		projectLibraryName = TextUtil.capitalize(libraryNameWithoutQuotes);

		// Extract Library version
		String libraryVersionRaw = libraryInfo[1].trim();
		String libraryVersionWithoutQuotes = libraryVersionRaw.substring(1, libraryVersionRaw.length() - 1);

		if (libraryVersionWithoutQuotes.startsWith("^")) {
			String libraryVersionWithoutCaret = libraryVersionWithoutQuotes.substring(1,
					libraryVersionWithoutQuotes.length());
			projectLibraryVersion = libraryVersionWithoutCaret;
		} else {
			projectLibraryVersion = libraryVersionWithoutQuotes;
		}

		if (projectLibraryName.isEmpty() || projectLibraryVersion.isEmpty())
			throw new RuntimeException("Erro ao obter biblioteca do projeto");

		return new LibraryDTO(projectLibraryName, projectLibraryVersion);
	}

	public abstract void setHomePage(String projectPath, String projectName);

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		FrontEndLibrary.projectName = projectName;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		FrontEndLibrary.projectPath = projectPath;
	}

	public static String getProjectFinalName() {
		return projectFinalName;
	}

	public static void setProjectFinalName(String projectFinalName) {
		FrontEndLibrary.projectFinalName = projectFinalName;
	}

}
