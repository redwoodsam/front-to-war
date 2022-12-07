package com.samuelaraujo.frontend_war_generator.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PathUtil {

	public static String templateViewPath = Paths.get(System.getProperty("user.home"), "config-war", "template-view").toString();
	public static String mavenPath = Paths.get(System.getProperty("user.home"), "config-war", "apache-maven-3.6.3", "bin").toString();
	
	public static String executionPath = Paths.get(System.getProperty("user.dir")).toString();
	
	public static void initializeConfigWarPath() {
		String homePath = System.getProperty("user.home");
		
		if(!Files.exists(Paths.get(homePath, "config-war"))) {
			System.out.println("Instalando dependencias, por gentileza aguarde...");
			unzip(homePath);
		}
	}
	
	private static void unzip(String destDir) {
        File destDirectory = new File(destDir);
        // create output directory if it doesn't exist
        if(!destDirectory.exists()) destDirectory.mkdir();
        
        PathUtil util = new PathUtil();
		try(ZipInputStream zipInputStream = new ZipInputStream(util.getFileFromResourceAsStream("config-war.zip"))) {
			ZipEntry entry = zipInputStream.getNextEntry();
			while(entry != null) {
				String filePath = destDir + File.separator + entry.getName();
				
				if(!entry.isDirectory()) {
					extractFile(zipInputStream, filePath);
				} else {
					// If the entry is a directory, make the directory
					File dir = new File(filePath);
					dir.mkdirs();
				}
				zipInputStream.closeEntry();
				entry = zipInputStream.getNextEntry();
			}
			
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
	
	private static void extractFile(ZipInputStream zipInputStream, String filePath) throws IOException {
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[1024];
		int read = 0;
		
		while((read = zipInputStream.read(bytesIn)) != -1) {
			bufferedOutputStream.write(bytesIn, 0, read);
		}
		bufferedOutputStream.close();
	}

	private InputStream getFileFromResourceAsStream(String fileName) {
	
		// The class loader that loaded the class
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		
		// the stream holding the file content
		if (inputStream == null) {
		    throw new IllegalArgumentException("file not found! " + fileName);
		} else {
		    return inputStream;
		}
	}
}
