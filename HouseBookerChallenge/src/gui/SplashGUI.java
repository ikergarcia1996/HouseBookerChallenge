package gui;

import java.awt.Component;
import java.awt.Container;
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

public class SplashGUI extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public TextArea textArea = new TextArea();
	GUIRLoader res = new GUIRLoader();

	/**
	 * Launch the application.
	 */
	public void main(String[] args, SplashGUI dialog) {
		try {

			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
			dialog.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SplashGUI() {
		
		setAlwaysOnTop(true);
		setUndecorated(true);
		setResizable(false);
		setBounds(100, 100, 343, 200);
		
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		SwingUtilities.updateComponentTreeUI(getContentPane());
		
		
		
		
		//			BufferedImage img = ImageIO.read(new File("Images/HouseBookerChallenge.png"));
					
					getContentPane().setLayout(null);
					JLabel lblLogo = new JLabel("logotipo");
					
					lblLogo.setIcon(res.logotipo);
					lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
					lblLogo.setBounds(0, 0, 343, 69);
					this.getContentPane().add(lblLogo);
		
					JProgressBar progressBar = new JProgressBar();
					progressBar.setString("Iniciando...");
					progressBar.setStringPainted(true);
					progressBar.setIndeterminate(true);
					progressBar.setBounds(0, 70, 343, 26);
					getContentPane().add(progressBar);
					textArea.setBackground(SystemColor.text);
					textArea.setEditable(false);
		
					textArea.setBounds(0, 95, 343, 105);
		
					getContentPane().add(textArea);
		
					JLabel lblV = new JLabel("v1.0");
					lblV.setBounds(311, 49, 22, 20);
					getContentPane().add(lblV);

	}
}
