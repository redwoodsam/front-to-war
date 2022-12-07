package com.samuelaraujo.frontend_war_generator.service;

import com.samuelaraujo.frontend_war_generator.dto.GenerationDTO;
import com.samuelaraujo.frontend_war_generator.dto.ProjectDetailsDTO;
import com.samuelaraujo.frontend_war_generator.service.frontendlibrary.Environment;
import com.samuelaraujo.frontend_war_generator.service.frontendlibrary.FrontEndLibrary;
import com.samuelaraujo.frontend_war_generator.service.frontendlibrary.LibraryEnum;
import com.samuelaraujo.frontend_war_generator.service.frontendlibrary.ReactLibrary;
import com.samuelaraujo.frontend_war_generator.service.xml.XmlService;
import com.samuelaraujo.frontend_war_generator.util.PathUtil;

public class MainService {

	public static ProjectDetailsDTO getProjectDetails(String projectPath) {
		ReactLibrary lib = new ReactLibrary();
		return lib.getProjectDetails(projectPath);
	}
	
	public static void run(GenerationDTO generationDTO) {
		try {
			System.out.println("Iniciando build, por favor aguarde...");
			PathUtil.initializeConfigWarPath();
			XmlService xmlService = new XmlService();
			xmlService.setPomProjectName(
					generationDTO.getProjectName(), generationDTO.getProjectName(), generationDTO.getProjectDescription());
			
			CommandLineService cliService = new CommandLineService();
			FileSystemService fsService = new FileSystemService();
			
			FrontEndLibrary lib = LibraryEnum.toEnum(generationDTO.getProjectLibrary()).getLibrary();
			lib.setProjectName(generationDTO.getProjectName());
			lib.setProjectPath(generationDTO.getProjectPath());
			FrontEndLibrary.setProjectFinalName(generationDTO.getProjectName());
			
			Environment selectedEnvironment = lib.getEnvironments()
					.stream()
					.filter(env -> env.getName().equals(generationDTO.getProjectEnvironment()))
					.findFirst().orElseThrow(() -> new RuntimeException("Erro ao selecionar ambiente de geracao."));
			
			lib.setHomePage(generationDTO.getProjectPath(), generationDTO.getProjectName());
			
			cliService.npmInstall(generationDTO.getProjectPath());
			cliService.npmBuild(generationDTO.getProjectPath(), selectedEnvironment);
			
			fsService.moveBuildToTemplateView(lib);
			cliService.mvnCleanPackage();
			
			fsService.moveToOutputFolder(lib);
			fsService.cleanTargetFolder(lib);
			
		} catch(RuntimeException e) {
			throw e;
		}
	}
}
