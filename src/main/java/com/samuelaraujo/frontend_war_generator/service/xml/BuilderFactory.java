package com.samuelaraujo.frontend_war_generator.service.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class BuilderFactory {

	private static DocumentBuilderFactory documentBuilderFactory;
	private static DocumentBuilder documentBuilder;
	private static Document document = null;
	
	public static Document getDocument(String pathToXml) {
		if(documentBuilderFactory == null) documentBuilderFactory = DocumentBuilderFactory.newInstance();
		if(documentBuilder == null)
			try {
				documentBuilder = documentBuilderFactory.newDocumentBuilder();
				document = documentBuilder.parse(new File(pathToXml));
			} catch (ParserConfigurationException e) {
				throw new RuntimeException(e);
			} catch (SAXException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		
		return document;
	}
	
	
}
