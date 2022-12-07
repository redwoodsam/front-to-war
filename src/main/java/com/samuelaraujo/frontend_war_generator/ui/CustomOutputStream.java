package com.samuelaraujo.frontend_war_generator.ui;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class CustomOutputStream extends OutputStream {
	
	private JTextArea textArea;
	
	public CustomOutputStream(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int b) throws IOException {
		// Redirects data to the text area
		textArea.setText(textArea.getText() + String.valueOf((char) b));
		// Scrolls the text area to the end of the data
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

}
