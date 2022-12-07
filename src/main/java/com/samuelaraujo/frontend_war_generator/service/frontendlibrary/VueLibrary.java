package com.samuelaraujo.frontend_war_generator.service.frontendlibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VueLibrary extends FrontEndLibrary {

	public VueLibrary(String projectName, String projectPath, String projectFinalName) {
		super(projectName, projectPath, projectFinalName);
	}

	public VueLibrary() {
	}

	@Override
	public void setHomePage(String projectPath, String projectName) {
		String data = "";
		File packageJson = Paths.get(projectPath, "package.json").toFile();

		if (!packageJson.exists())
			throw new RuntimeException(
					"ERRO: Arquivo package.json nao encontrado, verifique se a pasta do projeto esta correta.");

		try {
			data = new String(Files.readAllBytes(packageJson.toPath())).trim();

			if (!data.endsWith("}"))
				throw new RuntimeException("ERRO: Arquivo package.json invalido.");

			setPublicPath(projectPath, projectName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void setPublicPath(String projectPath, String projectName) {
		String data = "";
		File vueConfigJs = Paths.get(projectPath, "vue.config.js").toFile();
		if(Files.exists(vueConfigJs.toPath())) {
			try {
				data = new String(Files.readAllBytes(vueConfigJs.toPath()), StandardCharsets.UTF_8).trim();
				if(data.contains("publicPath")) {
					String publicPathPatternString = "publicPath:\\s?\\n?\\t?.+,?";
					Pattern publicPathPattern = Pattern.compile(publicPathPatternString);
					Matcher publicPathMatcher = publicPathPattern.matcher(data);
					
					if(publicPathMatcher.find()) {
						String newString = "publicPath: \"/" + projectName + "\",";
						data = data.replaceFirst(publicPathPatternString, newString);
						try(FileWriter writer = new FileWriter(vueConfigJs)) {
							writer.write(data);
						}
					}
				}
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
