package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import SocialUtils.Twitter.TwitterAPI;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import twitter4j.TwitterException;

import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class TwitterLoginGUI extends JFrame {

	private JPanel contentPane;
	private JFXPanel jfxPanel;
	private TwitterAPI twitterAPI;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TwitterLoginGUI frame = new TwitterLoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TwitterLoginGUI() {
		setTitle("Iniciar sesi\u00F3n con Twitter");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 525);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		// You should execute this part on the Event Dispatch Thread
		// because it modifies a Swing component 
		jfxPanel = new JFXPanel();
		jfxPanel.setBounds(10, 11, 774, 441);
		contentPane.add(jfxPanel);
		
		try {
			twitterAPI = new TwitterAPI();
		} catch (TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Creation of scene and future interactions with JFXPanel
		// should take place on the JavaFX Application Thread
		Platform.runLater(() -> {
		    WebView webView = new WebView();
		    jfxPanel.setScene(new Scene(webView));
		    try {
				webView.getEngine().load(twitterAPI.getAuthURL());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		setContentPane(contentPane);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 455, 774, 14);
		contentPane.add(separator);
		
		textField = new JTextField();
		textField.setBounds(586, 463, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Iniciar sesi\u00F3n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (twitterAPI.authAPI(textField.getText()) != 0){
					JOptionPane.showMessageDialog(null,
							"El PIN introducido no es correcto.", "Error", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"Se ha iniciado sesión correctamente.", "", JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(null,
							twitterAPI.getUserDisplayName()+" "+twitterAPI.getUserName()+" "+twitterAPI.getUserId()+" "+twitterAPI.getUserLocation()+" "+twitterAPI.getUserProfileImgURL(), "DebugInfo", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(682, 462, 102, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblIntroduzcaElPin = new JLabel("Introduzca el PIN obtenido:");
		lblIntroduzcaElPin.setBounds(401, 463, 175, 21);
		contentPane.add(lblIntroduzcaElPin);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(10, 462, 89, 23);
		contentPane.add(btnCancelar);
	}
}
