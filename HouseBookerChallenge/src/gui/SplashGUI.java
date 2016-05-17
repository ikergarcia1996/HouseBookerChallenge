package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.Launcher;
import utilities.ImageTypes;
import utilities.ImageUtils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Color;

public class SplashGUI extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TextArea textArea = new TextArea();
	GUIRLoader res = new GUIRLoader();
	public JProgressBar progressBar;

	/**
	 * Launch the application.
	 */
	public void main(String[] args, SplashGUI dialog) {

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		dialog.setVisible(true);

	}

	/**
	 * Create the dialog.
	 */
	public SplashGUI() {
				
		setBounds(100, 100, 343, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		setUndecorated(true);
		setResizable(false);

		
		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(50, 205, 50));
		progressBar.setMaximum(1);
		progressBar.setString("Cargando...");
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setBounds(0, 70, 343, 26);
		
		getContentPane().add(progressBar);


		// BufferedImage img = ImageIO.read(new
		// File("Images/HouseBookerChallenge.png"));

		getContentPane().setLayout(null);
		JLabel lblLogo = new JLabel("logotipo");

		lblLogo.setIcon(res.logotipo);
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLogo.setBounds(0, 0, 343, 69);
		this.getContentPane().add(lblLogo);

		
		
		textArea.setBackground(new Color(191,208,193));
		textArea.setEditable(false);
		textArea.setBounds(0, 95, 343, 105);
		getContentPane().add(textArea);

		JLabel lblV = new JLabel("v"+Launcher.VERSION);
		lblV.setBounds(311, 49, 22, 20);
		getContentPane().add(lblV);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(ERROR);
			}
		});
		btnSalir.setBounds(228, 201, 115, 19);
		getContentPane().add(btnSalir);
		
		JLabel lblC = new JLabel(new ImageIcon(ImageUtils.decodeToImage(ImageTypes.FONDOCOLOR)));
		lblC.setBounds(0, 0, 343, 200);
		getContentPane().add(lblC);

	}
}
