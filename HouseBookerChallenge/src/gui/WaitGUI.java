package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import utilities.ImageTypes;
import utilities.ImageUtils;

public class WaitGUI extends JDialog {
	Thread thread;
	
	public void main(String[] args, WaitGUI dialog) {
		
				try {
					
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		
		
	

	/**
	 * Create the dialog.
	 * 
	 * @param text
	 */
	public WaitGUI(String text) {
		setAlwaysOnTop(true);
		setUndecorated(true);
		setResizable(false);
		setBounds(100, 100, 220, 70);
		getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		JLabel lblText = new JLabel(text);
		lblText.setBounds(10, 11, 200, 14);
		getContentPane().add(lblText);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setString("Espere por favor...");
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 36, 200, 25);
		getContentPane().add(progressBar);
		
		
		JLabel lblC = new JLabel(new ImageIcon(ImageUtils.decodeToImage(ImageTypes.FONDOCOLOR)));
		lblC.setBounds(0, 0, 237, 85);
		getContentPane().add(lblC);
	}

}
