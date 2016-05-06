package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;

public class URLLoaderGUI extends JDialog {
	private JTextField txtUrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			URLLoaderGUI dialog = new URLLoaderGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public URLLoaderGUI() {
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
				URL direccion=null;
				try {
					direccion = new URL(txtUrl.getText());
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int download = CloudUtilities.JavaDownload.Download(direccion.toString(), "%tmp%\\HBC\\" + direccion.getFile());
				System.out.println(download);
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
				CloudUtilities.JavaDownload.openURLinExplorer(txtUrl.getText());
			}
		});
		btnPrevisualizar.setBounds(248, 91, 137, 23);
		getContentPane().add(btnPrevisualizar);
	}
}
