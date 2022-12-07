package com.samuelaraujo.frontend_war_generator.service.exception;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
        handleException(e);
	}

	private void handleException(Throwable e) {
		System.out.println(e.getMessage());
	}
	
	public static void registerExceptionHandler() {
		Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
		System.setProperty("sun.awt.exception.handler", GlobalExceptionHandler.class.getName());
	}

}
