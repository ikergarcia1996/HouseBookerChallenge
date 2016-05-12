package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import main.Launcher;
import utilities.ImageTypes;
import utilities.ImageUtils;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class AcercaDeGUI extends JDialog {
	GUIRLoader res = new GUIRLoader();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AcercaDeGUI dialog = new AcercaDeGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AcercaDeGUI() {
		setIconImage(res.icono);

		setTitle("Acerca de HouseBookerChallenge");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 370, 249);
		getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		setIconImage(res.icono);

		SwingUtilities.updateComponentTreeUI(getContentPane());

		JLabel lblLogo = new JLabel(res.logotipo);
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLogo.setBounds(5, 5, 343, 69);
		getContentPane().add(lblLogo);

		/*
		 * try { BufferedImage img = ImageIO.read(new
		 * File("Images/HouseBookerChallenge.png")); ImageIcon icon = new
		 * ImageIcon(img); JLabel lblLogo = new JLabel(icon);
		 * lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		 * lblLogo.setBounds(5, 5, 343, 69); contentPane.add(lblLogo); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */

		JLabel lblAplicacionCreadaPor = new JLabel("Aplicacion creada por:");
		lblAplicacionCreadaPor.setBounds(5, 85, 424, 14);
		getContentPane().add(lblAplicacionCreadaPor);

		JLabel lblChristianMartn = new JLabel("Christian Mart\u00EDn");
		lblChristianMartn.setBounds(15, 110, 110, 14);
		getContentPane().add(lblChristianMartn);

		JLabel lblEritzYerga = new JLabel("Eritz Yerga");
		lblEritzYerga.setBounds(135, 110, 110, 14);
		getContentPane().add(lblEritzYerga);

		JLabel lblIkerGarca = new JLabel("Iker Garc\u00EDa");
		lblIkerGarca.setBounds(249, 110, 110, 14);
		getContentPane().add(lblIkerGarca);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnAceptar.setBounds(260, 141, 89, 23);
		getContentPane().add(btnAceptar);

		JLabel lblVersinDelPrograma = new JLabel("Versi\u00F3n del programa: "+Launcher.VERSION);
		lblVersinDelPrograma.setBounds(5, 150, 240, 14);
		getContentPane().add(lblVersinDelPrograma);
		
		JLabel lblT = new JLabel("T");
		lblT.setIcon(new ImageIcon(ImageUtils.decodeToImage(ImageTypes.POWBYTWI)));
		lblT.setBounds(5, 175, 138, 30);
		getContentPane().add(lblT);
	}
}
