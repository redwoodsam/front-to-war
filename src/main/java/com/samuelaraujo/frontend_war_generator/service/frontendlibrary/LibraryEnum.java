package com.samuelaraujo.frontend_war_generator.service.frontendlibrary;

import java.util.ArrayList;
import java.util.List;

public enum LibraryEnum {

	REACT("react") {
		@Override
		public FrontEndLibrary getLibrary() {
			return new ReactLibrary();
		}
	},
	VUE("vue") {
		@Override
		public FrontEndLibrary getLibrary() {
			return new VueLibrary();
		}
	},;

	private String description;

	public static LibraryEnum toEnum(String description) {
		for (LibraryEnum library : LibraryEnum.values()) {
			if (library.getDescription().toLowerCase().equals(description.toLowerCase()))
				return library;
		}
		throw new RuntimeException("Nenhuma biblioteca compatível encontrada.");
	}
	
	public static List<String> getAvailableLibraries() {
		List<String> libraries = new ArrayList<>();
		
		for(LibraryEnum library : LibraryEnum.values()) {
			libraries.add(library.getDescription());
		}
		
		return libraries;
	}

	private LibraryEnum(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public abstract FrontEndLibrary getLibrary();

}
