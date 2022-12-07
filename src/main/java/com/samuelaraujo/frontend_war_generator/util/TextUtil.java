package com.samuelaraujo.frontend_war_generator.util;

public class TextUtil {

	public static String capitalize(String text) {
		String beginningLetter = text.substring(0, 1);
		String textBody = text.substring(1);
		return beginningLetter.toUpperCase() + textBody;
	}
	
}
