package com.samuelaraujo.frontend_war_generator.service.xml;

import java.io.File;
import java.nio.file.Paths;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.samuelaraujo.frontend_war_generator.util.PathUtil;

public class XmlService {
	
	private String diretorioTemplateView = PathUtil.templateViewPath;
	
	public void setPomProjectName(String name, String finalName, String description) {
		Document pomDocument = BuilderFactory.getDocument(Paths.get(diretorioTemplateView, "pom.xml").toString());
		Node projectName = pomDocument.getElementsByTagName("name").item(0);
		Node projectFinalName = pomDocument.getElementsByTagName("finalName").item(0);
		Node projectDescription = pomDocument.getElementsByTagName("description").item(0);
		
		projectName.setTextContent(name);
		projectFinalName.setTextContent(finalName);
		projectDescription.setTextContent(description);
		
		exportFile(pomDocument, Paths.get(diretorioTemplateView.toString(), "pom.xml").toString());
	}
	
	private void exportFile(Document document, String pathToFile) {
		Transformer transformer = TransformFactory.getTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult result = new StreamResult(new File(pathToFile));
		
		try {
			transformer.transform(domSource, result);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}
	
}
