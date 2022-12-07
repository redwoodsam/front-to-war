package com.samuelaraujo.frontend_war_generator.ui;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CustomErrorStream extends OutputStream {

	private JFrame frame;
	
	public CustomErrorStream(JFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void write(int b) throws IOException {
		JOptionPane.showMessageDialog(frame, String.valueOf((char) b), "Erro", MessageType.ERROR.ordinal());
	}

}
