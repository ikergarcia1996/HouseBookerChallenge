package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import SocialUtils.Twitter.TwitterAPI;
import businessLogic.FacadeImplementationWS.loginresult;
import domain.Usuario;
import gui.dialogs.ElegirTipoGUI;
import gui.dialogs.TwitterElegirTipoGUI;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import twitter4j.TwitterException;
import utilities.ImageTypes;
import utilities.ImageUtils;

import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.Color;
import javax.swing.UIManager;

public class TwitterLoginGUI extends JFrame {

	private JPanel contentPane;
	private static JFXPanel jfxPanel;
	private TwitterAPI twitterAPI;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, GUIOperator gOP) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TwitterLoginGUI frame = new TwitterLoginGUI(gOP);
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
	public TwitterLoginGUI(GUIOperator gOP) {
		setTitle("Iniciar sesi\u00F3n con Twitter");
		setIconImage(ImageUtils.decodeToImage(ImageTypes.TWITTER));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		textField.setBackground(new Color(211,228,213));
		textField.setBounds(586, 463, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Iniciar sesi\u00F3n");
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorder(UIManager.getBorder("CheckBox.border"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textField.getText().isEmpty()){
				if (twitterAPI.authAPI(textField.getText()) != 0){
					JOptionPane.showMessageDialog(null,
							"El PIN introducido no es correcto.", "Error", JOptionPane.WARNING_MESSAGE);
					dispose();
				} else {
					btnNewButton.setEnabled(false);
					int resultado = 0;
					Usuario user = null;
					
						loginresult out = gOP.twitterlogin(twitterAPI.getUserId());
						resultado = out.getResult();
						user = out.getUser();

					if (resultado == -1) {
						Component frame = null;
						int n = JOptionPane.showConfirmDialog(frame,
								"El usuario introducido no esta registrado. ¿Desea registrarlo ahora?", "Error",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if (n == 0) {
							dispose();
							TwitterElegirTipoGUI.main(null, gOP, twitterAPI);
						} else {
							dispose();
						}
					} else if (resultado == 0) {
						Component frame = null;
						JOptionPane.showMessageDialog(frame, "El usuario o contraseña introducidos no son correctos.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else if (resultado == 1) {// cliente
						MainGUI.dialog.main(null, MainGUI.dialog);
						dispose();
						MainBookerGUI.main(null, user, gOP);

					} else if (resultado == 2) {// propietario
						MainGUI.dialog.main(null, MainGUI.dialog);
						dispose();
						MainBookerGUI.main(null, user, gOP);
					} else if (resultado == -2) {
						JOptionPane.showMessageDialog(null,
								"Este usuario está banneado del sistema. Contacte con el administrador para más información.",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null,
								"Ha ocurrido un error desconocido. Contacte con el administrador para informar de este problema.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					btnNewButton.setEnabled(true);
				}
				}
			}
		});
		btnNewButton.setBounds(682, 462, 102, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblIntroduzcaElPin = new JLabel("Introduzca el PIN obtenido:");
		lblIntroduzcaElPin.setBounds(433, 463, 143, 21);
		contentPane.add(lblIntroduzcaElPin);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setContentAreaFilled(false);
		btnCancelar.setBorder(UIManager.getBorder("CheckBox.border"));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(10, 462, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel btnnoCargaLa = new JLabel("\u00BFNo carga la p\u00E1gina? Click aqu\u00ED");
		btnnoCargaLa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Desktop.isDesktopSupported()){
					try {
						Desktop.getDesktop().browse(new URL(twitterAPI.getAuthURL()).toURI());
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TwitterException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnnoCargaLa.setForeground(Color.BLUE);
		btnnoCargaLa.setBounds(109, 463, 154, 23);
		contentPane.add(btnnoCargaLa);
		
		JLabel lblC = new JLabel(new ImageIcon(ImageUtils.decodeToImage(ImageTypes.FONDOCOLOR)));
		lblC.setBounds(0, 0, 794, 496);
		getContentPane().add(lblC);
	}
}
