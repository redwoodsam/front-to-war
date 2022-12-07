package com.samuelaraujo.frontend_war_generator.service.frontendlibrary;

public class Environment {

	private String name;
	private String script;

	public Environment(String name, String script) {
		this.name = name;
		this.script = script;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public String toString() {
		return "Environment [name=" + name + ", script=" + script + "]";
	}

}
