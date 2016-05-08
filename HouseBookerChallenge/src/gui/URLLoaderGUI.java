package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import CloudUtilities.FileManager;
import CloudUtilities.JavaDownload;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;

public class URLLoaderGUI extends JDialog {
	private JTextField txtUrl;
	private JavaDownload Cloud = new JavaDownload();

	/**
	 * Launch the application.
	 * 
	 * @param HE
	 * 
	 * @return
	 * @return
	 */
	public static void main(String[] args, HouseEditorGUI HE) {
		try {
			URLLoaderGUI dialog = new URLLoaderGUI(HE);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param HE
	 * @param image
	 * 
	 * @param image
	 */
	public URLLoaderGUI(HouseEditorGUI HE) {
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 500, 154);
		getContentPane().setLayout(null);

		JLabel lblIntroduzcaAContinuacion = new JLabel("Introduzca a continuacion la URL de la imagen:");
		lblIntroduzcaAContinuacion.setBounds(10, 11, 224, 14);
		getContentPane().add(lblIntroduzcaAContinuacion);

		txtUrl = new JTextField();
		txtUrl.setBounds(10, 36, 474, 20);
		getContentPane().add(txtUrl);
		txtUrl.setColumns(10);

		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileManager fm=new FileManager();
				URL direccion = null;
				try {
					direccion = new URL(txtUrl.getText());
				} catch (MalformedURLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				Process p = null;
				try {
					p = Runtime.getRuntime().exec("cmd.exe /c echo %TMP%");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String tmp = null;
				try {
					tmp = br.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String direccionS = direccion.toString();
				fm.createDirectory(tmp,"HBC");
				String newdir = tmp + "\\HBC\\"
						+ direccionS.substring(direccionS.lastIndexOf('/') + 1, direccionS.length());
				
				int download = Cloud.Download(direccionS, newdir);
				System.out.println(download);
				HE.result = download;
				if (download == 0) {
					HE.Image = newdir;
					dispose();
				} else {
					direccion = null;
					try {
						direccion = new URL(txtUrl.getText());
					} catch (MalformedURLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					direccionS = direccion.toString();
					
					newdir = "C:\\\\"+ direccionS.substring(direccionS.lastIndexOf('/') + 1, direccionS.length());
					download = Cloud.Download(direccionS, newdir);
					System.out.println(download);
					HE.result = download;
					if (download == 0) {
						HE.Image = newdir;}
						dispose();
				
				}

			}
		});
		btnCargar.setBounds(395, 91, 89, 23);
		getContentPane().add(btnCargar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(10, 91, 89, 23);
		getContentPane().add(btnCancelar);

		JButton btnPrevisualizar = new JButton("Abrir en el navegador");
		btnPrevisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cloud.openURLinExplorer(txtUrl.getText());
			}
		});
		btnPrevisualizar.setBounds(248, 91, 137, 23);
		getContentPane().add(btnPrevisualizar);
	}
}
