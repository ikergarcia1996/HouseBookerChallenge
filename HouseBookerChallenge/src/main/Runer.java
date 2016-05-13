package main;

import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import javax.swing.UIManager;

import CloudUtilities.FileManager;
import CloudUtilities.JavaDownload;
import CloudUtilities.Update;
import CloudUtilities.Update.NewVersion;
import gui.GUIOperator;
import gui.MainGUI;
import gui.SplashGUI;
import gui.WaitGUI;

public class Runer {
	static Update up = new Update();
	static NewVersion nw;
	public static SplashGUI splash = new SplashGUI();
	public static WaitGUI downloading = new WaitGUI("Descargando actualizacion...");

	public static void run() {
		Thread threadTemp;
		Thread thread2;
		splash.main(null, splash);
		GUIOperator operator = new GUIOperator();

		Thread threadUpdate = new Thread() {
			public void run() {
				nw = up.testForUpdates();

			}
		};

		threadTemp = new Thread() {
			public void run() {
				try {
					Thread.sleep(10000);
					Component frame = null;
					if (JOptionPane.showConfirmDialog(frame,
							"La busqueda de actualizaciones esta tardando más de lo esperado ¿Desea omitirla?",
							"Actualización", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						threadUpdate.interrupt();

					}
				} catch (InterruptedException e) {
					System.out.println("Contador Detenido");
				}

			}
		};

		if (operator.WillCheckForUpdates()) {

			System.out.println("0");

			main.Runer.splash.progressBar.setString("Buscando Actualizaciones...");

			FileReader fileReader;
			try {
				fileReader = new FileReader("NewVersion.rh");
				fileReader.close();
				System.out.println("AAAA");
				InicioDespuesDeActualización();
				System.out.println("AAAA");
			//	MainGUI.main(null, operator);
			//	splash.dispose();
				System.out.println("AAAA");
			} catch (IOException e1) {

				threadUpdate.start();
				threadTemp.start();

			}

			while (threadUpdate.isAlive()) {
			}
			threadTemp.interrupt();
			splash.setVisible(false);
			if (nw != null) {
				Component frame = null;
				
				if (JOptionPane.showConfirmDialog(frame,
						"La versión " + nw.LastVersion + " de la aplicación esta disponible ¿Desea actualizar ahora?",
						"Actualización", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					downloading.setVisible(true);
					
					thread2 = new Thread() {
						public void run() {
							utilities.Tetris.main(null);
						}
					};
					Thread thread1 = new Thread() {
						public void run() {
							
							JavaDownload jd = new JavaDownload();
							jd.Download(nw.getLinkLastVersion(), "newVersion.jar");
							jd.Download(nw.getLinkLastVersion(), "updater.jar");

							StringBuilder fileRoute = new StringBuilder();
							fileRoute.append(System.getProperty("user.dir"));
							fileRoute.append("\\updater.jar");

							Component frame2 = null;
							downloading.setVisible(false);
							JOptionPane.showMessageDialog(frame2, "Actualizació lista pulsa OK para continuar",
									"Actualizar", 1);
							
							ProcessBuilder pb = new ProcessBuilder("java", "-jar", fileRoute.toString());
							try {
								thread2.interrupt();
								Process p = pb.start();
								System.exit(0);
							} catch (IOException e) {
								System.out.println("Error al ejecutar el programa");
								e.printStackTrace();
							}

						}
					};

					
					if (JOptionPane.showConfirmDialog(frame,
							"La descarga de la actualizacion puede tardar un tiempo, ¿quieres jugar a tetris mientras esperas?",
							"Actualización", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {thread1.start();thread2.start();}
					else thread1.start();
				}

				else {
					FileManager fl = new FileManager();
					fl.removeFile("NewVersion.rh");
					main.Runer.splash.progressBar.setIndeterminate(false);
					main.Runer.splash.progressBar.setValue(1);
					main.Runer.splash.progressBar.setString("Iniciando...");
					main.Runer.splash.textArea.append("\n Launching...");
					MainGUI.main(null, operator);
					splash.dispose();
				}

			}

			else {
				FileManager fl = new FileManager();
				fl.removeFile("NewVersion.rh");
				MainGUI.main(null, operator);
				splash.dispose();
			}
		} else {
			main.Runer.splash.progressBar.setIndeterminate(false);
			main.Runer.splash.progressBar.setValue(1);
			main.Runer.splash.progressBar.setString("Iniciando...");
			main.Runer.splash.textArea.append("\n Launching...");
			MainGUI.main(null, operator);

		}

		splash.dispose();

	}

	private static void InicioDespuesDeActualización() {
		FileReader fr;
		try {
			fr = new FileReader("NewVersion.rh");
			BufferedReader textReader = new BufferedReader(fr);
			StringBuilder notas = new StringBuilder();
			String con = textReader.readLine();
			int lines = 0;
			while (con != null) {
				lines++;
				if (lines > 4) {
					notas.append(con);
					notas.append("\n");

				}
				con = textReader.readLine();

			}
			textReader.close();

			Component frame3 = null;
			JOptionPane.showMessageDialog(frame3, "Novedades de la actualización: \n" + notas, "Novedades", 1);
			FileManager fl = new FileManager();
			fl.removeFile("newVersion.rh");
			fl.removeFile("updater.jar");
		} catch (IOException e) {
			System.out.println("NO ha habido una actualización desde el ultimo inicio");
		}

	}

}
