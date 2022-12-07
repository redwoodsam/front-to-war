package com.samuelaraujo.frontend_war_generator.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import com.samuelaraujo.frontend_war_generator.service.frontendlibrary.Environment;
import com.samuelaraujo.frontend_war_generator.util.PathUtil;

public class CommandLineService {
	
	private String mavenPath = PathUtil.mavenPath;
	private String templateViewPath = PathUtil.templateViewPath;
	private boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

	public void npmInstall(String projectPath) {
		try {
			ProcessBuilder builder = null;
			if(isWindows) {
				builder = new ProcessBuilder("cmd.exe", "/c", String.format("cd %s && npm install", projectPath));
			} else {
				builder = new ProcessBuilder("sh", "-c", String.format("cd %s && npm install", projectPath));
			}
			
			Process process = builder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			while(true) {
				line = reader.readLine();
				if (line == null) break;
				System.out.println(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void npmBuild(String projectPath, Environment environment) {
		try {
			ProcessBuilder builder = null;
			if(isWindows) {
				builder = new ProcessBuilder("cmd.exe", "/c", String.format("cd %s && npm run %s", projectPath, environment.getScript()));
			} else {
				builder = new ProcessBuilder("sh", "-c", String.format("cd %s && npm run %s", projectPath, environment.getScript()));
			}
			
			Process process = builder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			while(true) {
				line = reader.readLine();
				if (line == null) break;
				System.out.println(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void mvnCleanPackage() {
		try {
			ProcessBuilder builder = null;
			if(isWindows) {
				builder = new ProcessBuilder("cmd.exe", "/c", 
						String.format("cd %s && %s clean package", templateViewPath, Paths.get(mavenPath, "mvn").toString()));
				System.out.println(String.format("cd %s && %s clean package", templateViewPath, Paths.get(mavenPath, "mvn").toString()));
			} else {
				builder = new ProcessBuilder("sh", "-c", 
						String.format("cd %s && %s clean package", templateViewPath, Paths.get(mavenPath, "mvn").toString()));
			}
			
			Process process = builder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			while(true) {
				line = reader.readLine();
				if (line == null) break;
				System.out.println(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
