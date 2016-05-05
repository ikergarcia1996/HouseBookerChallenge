package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import gui.GUIOperator;
import gui.MainGUI;
import gui.SplashGUI;

public class Runer {

	public static SplashGUI splash = new SplashGUI();

	public static void run() {
		
		
		splash.main(null, splash);
		GUIOperator operator = new GUIOperator();
		main.Runer.splash.progressBar.setIndeterminate(false);
		main.Runer.splash.progressBar.setValue(1);
		main.Runer.splash.progressBar.setString("Iniciando...");
		main.Runer.splash.textArea.append("\n Launching...");
		MainGUI.main(null, operator);
		splash.dispose();

	}

}
