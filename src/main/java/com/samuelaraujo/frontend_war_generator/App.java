package com.samuelaraujo.frontend_war_generator;

import java.awt.EventQueue;

import com.samuelaraujo.frontend_war_generator.ui.MainWindow;

public class App 
{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainWindow window = new MainWindow();
				window.start();
			}
		});
	}
}
