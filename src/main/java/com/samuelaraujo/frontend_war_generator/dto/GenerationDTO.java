package com.samuelaraujo.frontend_war_generator.dto;

public class GenerationDTO {

	private String projectName;
	private String projectDescription;
	private String projectEnvironment;
	private String projectPath;
	private String projectLibrary;

	public GenerationDTO(String projectName, String projectDescription,
			String projectEnvironment, String projectPath, String projectLibrary) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.projectEnvironment = projectEnvironment;
		this.projectPath = projectPath;
		this.projectLibrary = projectLibrary;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public String getProjectEnvironment() {
		return projectEnvironment;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public String getProjectLibrary() {
		return projectLibrary;
	}

	@Override
	public String toString() {
		return "GenerationDTO [projectName=" + projectName + ", projectDescription=" + projectDescription
				+ ", projectEnvironment=" + projectEnvironment
				+ ", projectPath=" + projectPath + ", projectLibrary=" + projectLibrary + "]";
	}

}
