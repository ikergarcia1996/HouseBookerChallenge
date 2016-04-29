package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.filechooser.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import utilities.ImageFilter;
import utilities.ProfileImg;
import utilities.ImageUtils;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;;

public class RegistroGUI extends JDialog {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	public Component[]			Cliente;
	public Component[]			Propietario;
	JFileChooser				fc;

	/**
	 * Launch the application.
	 * 
	 * @param operator
	 */
	public static void main(String[] args, boolean tipo, GUIOperator operator) {
		try {
			RegistroGUI dialog = new RegistroGUI(tipo, operator);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param operator
	 */
	public RegistroGUI(boolean tipo, GUIOperator operator) {
		try {
			setIconImage(ImageIO.read(new File("Images/icon.png")));
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setTitle("Registro");
		setModal(true);
		setResizable(false);
		if (tipo) {
			setBounds(100, 100, 633, 440);
		}
		else {
			setBounds(100, 100, 335, 440);
		}
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SwingUtilities.updateComponentTreeUI(getContentPane());

		getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		{
			JPanel panelCliente = new gui.paneles.RegistroCliente();
			panelCliente.setBounds(0, 0, 300, 280);
			getContentPane().add(panelCliente);
			Cliente = panelCliente.getComponents();
		}
		{
			JPanel panelPropietario = new gui.paneles.RegistroPropietario();
			panelPropietario.setBounds(310, 0, 300, 280);
			if (tipo) {
				getContentPane().add(panelPropietario);
				Propietario = panelPropietario.getComponents();
			}
		}
		{
			/*
			 * JSeparator separator = new JSeparator(); if (tipo)
			 * separator.setBounds(10, 291, 588, 7); else
			 * separator.setBounds(10, 291, 280, 7);
			 * getContentPane().add(separator);
			 */
			JSeparator separator2 = new JSeparator();
			if (tipo)
				separator2.setBounds(10, 367, 588, 7);
			else separator2.setBounds(10, 367, 280, 7);
			getContentPane().add(separator2);
		}
		{
			JButton btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					ElegirTipoGUI.main(null, operator);
				}
			});
			btnCancelar.setBounds(20, 380, 89, 23);
			getContentPane().add(btnCancelar);

			JLabel lblPerfil = new JLabel("");
			lblPerfil.setBorder(new LineBorder(Color.GRAY, 2, true));
			lblPerfil.setBounds(200, 280, 80, 80);
			ProfileImg perfil = new ProfileImg(null);
			lblPerfil.setIcon(new ImageIcon(ImageUtils.decodeToImage(perfil.getProfileImg())));
			getContentPane().add(lblPerfil);

			fc = new JFileChooser();

			JButton btnSeleccionarImagenDe = new JButton("Seleccionar imagen de perfil");
			btnSeleccionarImagenDe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fc.setFileFilter(new ImageFilter());
					int returnVal = fc.showOpenDialog(RegistroGUI.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File img = fc.getSelectedFile();
						BufferedImage image = null;
							try {
								image = ImageIO.read(img);
							}
							catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							image = ImageUtils.resize(image, 80, 80);
							String filestring = img.getPath();
							String filetype=filestring.substring(filestring.lastIndexOf('.')+1, filestring.length());
							perfil.setImage(ImageUtils.encodeToString(image,filetype));
							lblPerfil.setIcon(new ImageIcon(image));

					}

				}
			});
			btnSeleccionarImagenDe.setBounds(20, 291, 170, 23);
			getContentPane().add(btnSeleccionarImagenDe);

			JButton btnContinuar = new JButton("Continuar");
			btnContinuar.setLocation(0, 71);
			if (tipo) {
				btnContinuar.setBounds(508, 380, 89, 23);
			}
			else btnContinuar.setBounds(201, 380, 89, 23);
			getContentPane().add(btnContinuar);
			JRootPane rootPane = getRootPane();
			rootPane.setDefaultButton(btnContinuar);

			JButton btnBorrarImagenDe = new JButton("Borrar imagen de perfil");
			btnBorrarImagenDe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ProfileImg perfil = new ProfileImg(null);
					lblPerfil.setIcon(new ImageIcon(ImageUtils.decodeToImage(perfil.getProfileImg())));
				}
			});
			btnBorrarImagenDe.setBounds(20, 325, 170, 23);
			getContentPane().add(btnBorrarImagenDe);

			btnContinuar.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					String Nombre = ((JTextField) Cliente[3]).getText();
					String Apellidos = ((JTextField) Cliente[5]).getText();
					String Telefono = ((JFormattedTextField) Cliente[7]).getText();
					String Correo = ((JTextField) Cliente[10]).getText();
					String Contrasena = String.valueOf(((JPasswordField) Cliente[11]).getPassword());
					String Confinmar = String.valueOf(((JPasswordField) Cliente[13]).getPassword());

					String DNI = null;
					String Calle = null;
					String Numero = null;
					String Piso = null;
					String Puerta = null;
					String Letra = null;
					String CP = null;
					String Poblacion = null;
					String Provincia = null;
					if (tipo) {
						DNI = ((JFormattedTextField) Propietario[3]).getText();
						Calle = ((JTextField) Propietario[4]).getText();
						Numero = ((JTextField) Propietario[6]).getText();
						Piso = ((JTextField) Propietario[7]).getText();
						Puerta = ((JTextField) Propietario[8]).getText();
						Letra = ((JFormattedTextField) Propietario[9]).getText();
						CP = ((JFormattedTextField) Propietario[14]).getText();
						Poblacion = ((JTextField) Propietario[16]).getText();
						Provincia = ((JTextField) Propietario[18]).getText();
					}
					int error = 0;
					if (tipo) {
						if (Provincia.length() == 0) error = 15;
						if (Poblacion.length() == 0) error = 14;
						CP = CP.replaceAll("\\s", "");
						if (CP.length() == 0) error = 13;
						if (Numero.length() == 0) error = 12;
						if (Calle.length() == 0) error = 11;
						DNI = DNI.replaceAll("\\s", "");
						DNI = DNI.replaceAll("-", "");
						if (DNI.length() == 0) error = 10;
						DNI = DNI.replaceAll("-", "");
						if (!comprobarDNI(DNI)) if (!(error == 10)) error = 9;
					}
					if (Contrasena.length() == 0) error = 8;
					if (!Contrasena.equals(Confinmar)) error = 7;
					if (Correo.length() == 0) error = 6;
					if (!(Correo.matches(
							"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")))
						if (!(error == 6)) error = 5;
					Telefono = Telefono.replaceAll("\\s", "");
					if (Telefono.length() == 0) error = 4;
					if (Telefono.length() <= 8) Telefono = "000000000";
					int tlf = Integer.valueOf(Telefono);
					Component frame = null;
					if (!((tlf <= 999999999 && tlf >= 900000000) || (tlf <= 799999999 && tlf >= 600000000)))
						if (!(error == 4)) error = 3;
					if (Apellidos.length() == 0) error = 2;
					if (Nombre.length() == 0) error = 1;

					if (error != 0) {
						GenerarError(error);

					}
					else {
						CaptchaGUI.main(null);
						WaitGUI dialog = new WaitGUI("Registrando usuario");
						dialog.main(null, dialog);
						int resultado = operator.Registrar(tipo, Nombre, Apellidos, Telefono, Correo, Contrasena, DNI,
								Calle, Numero, Piso, Puerta, Letra, CP, Poblacion, Provincia, perfil);

						if (resultado == -1) {
							dialog.dispose();
							int n = JOptionPane.showConfirmDialog(frame,
									"Este usuario ya ha sido registrado previamente. ¿Desea iniciar sesión ahora?",
									"Error", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
							if (n == 0) {
								dispose();
								LoginGUI.main(null, operator);
							}

						}
						else if (resultado == 0) {
							dialog.dispose();
							JOptionPane.showMessageDialog(frame, "Registro correcto. Ahora puede iniciar sesión.",
									"Registro correcto", JOptionPane.INFORMATION_MESSAGE);
							dispose();
							LoginGUI.main(null, operator);
						} else if (resultado == -2) {
							dialog.dispose();
							JOptionPane.showMessageDialog(frame, "No se puede registrar este usuario por que ese correo o DNI está banneado de poder usar el sistema. Contacte con el administrador para más información.",
									"Error", JOptionPane.INFORMATION_MESSAGE);
						}

					}

				}

				private void GenerarError(int error) {
					String mensaje;
					switch (error) {
						case (15):
							mensaje = "No ha especificado ninguna provincia.";
							break;
						case (14):
							mensaje = "No ha especificado ninguna población.";
							break;
						case (13):
							mensaje = "No ha especificado ningún código postal.";
							break;
						case (12):
							mensaje = "No ha especificado ningún número";
							break;
						case (11):
							mensaje = "No ha especificado ninguna calle.";
							break;
						case (10):
							mensaje = "No ha especificado nungún DNI.";
							break;
						case (9):
							mensaje = "El DNI introducido no es válido";
							break;
						case (8):
							mensaje = "No ha especificado ninguna contraseña.";
							break;
						case (7):
							mensaje = "Las contraseñas no coinciden";
							break;
						case (6):
							mensaje = "No ha especificado ningún correo electrónico.";
							break;
						case (5):
							mensaje = "El correo electrónico introducido no es correcto.";
							break;
						case (4):
							mensaje = "No ha introducido ningún teléfono.";
							break;
						case (3):
							mensaje = "El teléfono introducido no es correcto";
							break;
						case (2):
							mensaje = "No ha especificado ningún apellido.";
							break;
						case (1):
							mensaje = "No ha especificado ningún nombre.";
							break;
						default:
							mensaje = "Error desconocido.";
							break;
					}
					Component frame = null;
					JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);

				}

				private boolean comprobarDNI(String DNI) {

					char[] letraDni = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S',
							'Q', 'V', 'H', 'L', 'C', 'K', 'E' };

					String num = "";
					int ind = 0;
					if (DNI.length() == 8) {
						DNI = "0" + DNI;
					}

					if (DNI.length() != 9) { return false; }

					if (!Character.isLetter(DNI.charAt(8))) { return false; }

					for (int i = 0; i < 8; i++) {

						if (!Character.isDigit(DNI.charAt(i))) { return false; }

						num += DNI.charAt(i);
					}

					ind = Integer.parseInt(num);
					ind %= 23;

					if ((Character.toUpperCase(DNI.charAt(8))) != letraDni[ind]) { return false; }

					return true;
				}
			});

		}
	}
}
