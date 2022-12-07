package com.samuelaraujo.frontend_war_generator.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

import com.samuelaraujo.frontend_war_generator.service.frontendlibrary.FrontEndLibrary;
import com.samuelaraujo.frontend_war_generator.util.PathUtil;

public class FileSystemService {

	private String templateViewPath = PathUtil.templateViewPath;
	private String templateDistPath = Paths.get(templateViewPath, "src", "main", "template", "dist").toString();
	private String templateTargetPath = Paths.get(templateViewPath, "target").toString();
	
	public void moveBuildToTemplateView(FrontEndLibrary library) {
		try {
			File buildPath = new File(library.getDistFolderPath());
			File distTemplatePath = new File(templateDistPath);
			
			if(!buildPath.exists()) throw new RuntimeException("A pasta de build do projeto nao existe.");
			
			if(distTemplatePath.exists()) {
				FileUtils.deleteDirectory(distTemplatePath);
			}
			
			Files.move(buildPath.toPath(), distTemplatePath.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void cleanTargetFolder(FrontEndLibrary library) {
		File targetPath = new File(templateTargetPath);
		Collection<File> targetFolderContents = FileUtils.listFilesAndDirs(
				targetPath,  DirectoryFileFilter.INSTANCE, DirectoryFileFilter.INSTANCE);
		
		targetFolderContents
			.stream()
			.filter(file -> file.getName().startsWith(FrontEndLibrary.getProjectFinalName()))
			.forEach((File file) -> {
				if(FileUtils.isDirectory(file)) {
					try {
						FileUtils.deleteDirectory(file);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				} else {
					file.delete();
				}
			}
		);
	}
	
	public void moveToOutputFolder(FrontEndLibrary library) {
		File targetPath = new File(templateTargetPath);
		
		String[] extensions = {"war"};
		
		Collection<File> targetFolderContents = FileUtils.listFiles(targetPath, extensions, false);
		targetFolderContents
			.stream()
			.filter(file -> file.getName().startsWith(FrontEndLibrary.getProjectFinalName()))
			.forEach((File file) -> {
				try {
					File outputFile = new File(Paths.get(PathUtil.executionPath, file.getName()).toString());
					if(outputFile.exists()) {
						outputFile.delete();
					}
					FileUtils.moveFileToDirectory(file, new File(PathUtil.executionPath), false);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		});
	}
}
