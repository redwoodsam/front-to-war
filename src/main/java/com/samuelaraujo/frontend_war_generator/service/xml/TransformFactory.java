package com.samuelaraujo.frontend_war_generator.service.xml;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class TransformFactory {

	private static Transformer transformer;
	
	public static Transformer getTransformer() {
		try {
			if(transformer == null) {
				transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "no");
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			}
			
			return transformer;
			
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException(e);
		} catch (TransformerFactoryConfigurationError e) {
			throw new RuntimeException(e);
		}
	}
	
}
