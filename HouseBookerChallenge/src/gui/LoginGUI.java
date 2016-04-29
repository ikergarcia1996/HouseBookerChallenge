package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import businessLogic.FacadeImplementationWS.loginresult;
import domain.Usuario;
import utilities.ImageTypes;
import utilities.ImageUtils;

import java.awt.event.KeyEvent;
import com.jgoodies.forms.factories.DefaultComponentFactory;

public class LoginGUI extends JDialog {
	private JTextField txtUsuario;
	private JPasswordField pwdContrasea;

	/**
	 * Launch the application.
	 * 
	 * @param operator
	 */
	public static void main(String[] args, GUIOperator operator) {
		try {
			LoginGUI dialog = new LoginGUI(operator);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param operator
	 */
	public LoginGUI(GUIOperator operator) {
		try {
			setIconImage(ImageIO.read(new File("Images/icon.png")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setTitle("Iniciar sesi\u00F3n");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 458, 165);
		getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SwingUtilities.updateComponentTreeUI(getContentPane());
		
		
		JLabel lblCaps = new JLabel("");
		if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
		lblCaps.setIcon(new ImageIcon(ImageUtils.decodeToImage(ImageTypes.WARNING)));
		lblCaps.setBounds(275, 42, 14, 14);
		getContentPane().add(lblCaps);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)){
					lblCaps.setIcon(new ImageIcon(ImageUtils.decodeToImage(ImageTypes.WARNING)));
					pwdContrasea.setBounds(104, 39, 168, 20);
					}
				else{
					lblCaps.setIcon(null);
					pwdContrasea.setBounds(104, 39, 189, 20);
					}
				return false;
			}
		});

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(303, 0, 2, 123);
		getContentPane().add(separator);

		JLabel lblNotienes = new JLabel("\u00BFNo tienes cuenta?");
		lblNotienes.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotienes.setBounds(315, 17, 119, 37);
		getContentPane().add(lblNotienes);

		JButton btnRegistrarse = new JButton("Registrarse ahora");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ElegirTipoGUI.main(null, operator);
			}
		});
		btnRegistrarse.setBounds(315, 77, 119, 23);
		getContentPane().add(btnRegistrarse);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(10, 17, 84, 14);
		getContentPane().add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(10, 42, 84, 14);
		getContentPane().add(lblContrasea);

		
		
	
	
		txtUsuario = new JTextField();
		txtUsuario.setText("");
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(104, 14, 189, 20);
		getContentPane().add(txtUsuario);

		pwdContrasea = new JPasswordField();
		pwdContrasea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pwdContrasea.setText("");
			}
		});
		pwdContrasea.addKeyListener(new KeyAdapter() {
		});
		pwdContrasea.setText("");
		if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) pwdContrasea.setBounds(104, 39, 168, 20);
		
		else pwdContrasea.setBounds(104, 39, 189, 20);
		getContentPane().add(pwdContrasea);

		JLabel label_3 = new JLabel("Recuperar usuario...");
		label_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Esta característica no está disponible aún. Avise al administrador.", "Proximamente!",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		label_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label_3.setForeground(Color.BLUE);
		label_3.setBounds(10, 109, 100, 14);
		getContentPane().add(label_3);

		JButton btnIniciarSesion = new JButton("Iniciar sesi\u00F3n");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String usuario = txtUsuario.getText();
				char[] password = pwdContrasea.getPassword();
				String pass = String.valueOf(password);
				int resultado = 0;
				Usuario user = null;
				if (!usuario.matches(
						"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
					resultado = 0;
				} else {

					loginresult out = operator.login(usuario, pass);
					resultado = out.getResult();
					user = out.getUser();
				}

				if (resultado == -1) {
					Component frame = null;
					int n = JOptionPane.showConfirmDialog(frame,
							"El usuario introducido no esta registrado. ¿Desea registrarlo ahora?", "Error",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (n == 0) {
						dispose();
						ElegirTipoGUI.main(null, operator);
					}
				} else if (resultado == 0) {
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "El usuario o contraseña introducidos no son correctos.",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else if (resultado == 1) {// cliente
					MainGUI.dialog.main(null, MainGUI.dialog);
					dispose();
					MainBookerGUI.main(null, user, operator);

				} else if (resultado == 2) {// propietario
					MainGUI.dialog.main(null, MainGUI.dialog);
					dispose();
					MainBookerGUI.main(null, user, operator);
				}  else if (resultado == -2){
					JOptionPane.showMessageDialog(null, "Este usuario está banneado del sistema. Contacte con el administrador para más información.",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
				}
			}

		});
		btnIniciarSesion.setBounds(174, 77, 119, 23);

		getContentPane().add(btnIniciarSesion);
		JRootPane rootPane = getRootPane();
		rootPane.setDefaultButton(btnIniciarSesion);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				// MainGUI.main(null);

			}
		});
		btnCancelar.setBounds(10, 77, 89, 23);
		getContentPane().add(btnCancelar);
	}
}
