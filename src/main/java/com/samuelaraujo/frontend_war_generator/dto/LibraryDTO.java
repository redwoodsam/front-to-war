package com.samuelaraujo.frontend_war_generator.dto;

public class LibraryDTO {

	private String name;
	private String version;

	public LibraryDTO(String name, String version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "LibraryDTO [name=" + name + ", version=" + version + "]";
	}

}
