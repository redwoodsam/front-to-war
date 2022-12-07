package com.samuelaraujo.frontend_war_generator.service.frontendlibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReactLibrary extends FrontEndLibrary {

	public ReactLibrary(String projectName, String projectPath, String projectFinalName) {
		super(projectName, projectPath, projectFinalName);
	}
	
	public ReactLibrary() {}

	@Override
	public void setHomePage(String projectPath, String projectName) {
		String data = "";
		File packageJson = Paths.get(projectPath, "package.json").toFile();
		
		if(!packageJson.exists()) throw new RuntimeException(
				"ERRO: Arquivo package.json nao encontrado, verifique se a pasta do projeto esta correta.");
		
		try {
			data = new String(Files.readAllBytes(packageJson.toPath())).trim();
			
			if(!data.endsWith("}")) throw new RuntimeException(
					"ERRO: Arquivo package.json invalido");
			
			if(!data.contains("\"homepage\"")) {
				String modifyingString = data.substring(0, data.length() - 2);
				String modifiedString = modifyingString + String.format(", \n  \"homepage\": \"/%s\"\n}", projectName);
				try (FileWriter writer = new FileWriter(packageJson)) {
					writer.write(modifiedString);
				};
			} else {
				String homePagePatternString = "\\\"homepage\\\"\\s?:\\s?\\\".+\\\"";
				Pattern homePagePattern = Pattern.compile(homePagePatternString);
				Matcher homePageMatcher = homePagePattern.matcher(data);
				
				if(homePageMatcher.find()) {
					
					String newString = String.format("\"homepage\": \"%s\"", projectName); 
					
					data = data.replaceFirst(homePagePatternString, newString);
					
					try (FileWriter writer = new FileWriter(packageJson)) {
						writer.write(data);
					};
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
