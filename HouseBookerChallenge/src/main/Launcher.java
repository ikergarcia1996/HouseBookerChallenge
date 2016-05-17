package main;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.GUIRLoader;
import gui.MainBookerGUI;
import gui.SplashGUI;

public class Launcher {
	public static final float VERSION=4;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		GUIRLoader res = new GUIRLoader();
		main.Runer.run();
	}
}
