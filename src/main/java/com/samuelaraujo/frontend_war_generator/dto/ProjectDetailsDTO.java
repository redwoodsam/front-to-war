package com.samuelaraujo.frontend_war_generator.dto;

import java.util.List;

import com.samuelaraujo.frontend_war_generator.service.frontendlibrary.Environment;

public class ProjectDetailsDTO {

	private String projectName;
	private String projectDescription;
	private String projectFinalName;

	private LibraryDTO library;
	private List<Environment> environments;

	public ProjectDetailsDTO(String projectName, String projectDescription, String projectFinalName, LibraryDTO library,
			List<Environment> environments) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.projectFinalName = projectFinalName;
		this.library = library;
		this.environments = environments;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public String getProjectFinalName() {
		return projectFinalName;
	}

	public LibraryDTO getLibrary() {
		return library;
	}

	public List<Environment> getEnvironments() {
		return environments;
	}

	@Override
	public String toString() {
		return "ProjectDetailsDTO [projectName=" + projectName + ", projectDescription=" + projectDescription
				+ ", projectFinalName=" + projectFinalName + ", library=" + library + ", environments=" + environments
				+ "]";
	}

}
