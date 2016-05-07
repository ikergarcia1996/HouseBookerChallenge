package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class AdminGUI extends JDialog {

	private JPanel contentPane;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, GUIOperator gOP, String adminpass) {
		
				try {
					AdminGUI frame = new AdminGUI(gOP, adminpass);
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
	}

	/**
	 * Create the frame.
	 */
	public AdminGUI(GUIOperator gOP, String adminpass) {
		setModal(true);
		setResizable(false);
		setTitle("Modo Administrador");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 580, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAdministrarUsuarios = new JButton("Administrar usuarios");
		btnAdministrarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdminUserManagerGUI.main(null, gOP, adminpass);
			}
		});
		btnAdministrarUsuarios.setBounds(10, 60, 194, 23);
		contentPane.add(btnAdministrarUsuarios);
		
		JButton btnEliminarUnaCasa = new JButton("Eliminar una casa");
		btnEliminarUnaCasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminHouseDeleteGUI.main(null, gOP, adminpass);
			}
		});
		btnEliminarUnaCasa.setBounds(10, 99, 194, 23);
		contentPane.add(btnEliminarUnaCasa);
		
		JButton btnEliminarUnaOferta = new JButton("Eliminar una oferta");
		btnEliminarUnaOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminOfferDeleteGUI.main(null, gOP, adminpass);
			}
		});
		btnEliminarUnaOferta.setBounds(10, 138, 194, 23);
		contentPane.add(btnEliminarUnaOferta);
		
		JButton btnSalirDelModo = new JButton("Salir del modo administrador");
		btnSalirDelModo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalirDelModo.setBounds(15, 382, 167, 23);
		contentPane.add(btnSalirDelModo);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(321, 383, 178, 20);
		passwordField.setVisible(false);
		contentPane.add(passwordField);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gOP.superAdminChangePass(adminpass, passwordField.getText())){
					JOptionPane.showMessageDialog(null,
							"Por motivos de seguridad, por favor, vuelva a iniciar sesión.", "Contraseña modificada", JOptionPane.WARNING_MESSAGE);
					AdminLoginGUI.main(null, gOP);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"Acceso no autorizado. Credenciales incorrectas.", "Error", JOptionPane.WARNING_MESSAGE);
					dispose();
				}
			}
		});
		btnOk.setBounds(511, 382, 48, 23);
		btnOk.setVisible(false);
		contentPane.add(btnOk);
		
		JButton btnCambiarContrasea = new JButton("Cambiar contrase\u00F1a");
		btnCambiarContrasea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnCambiarContrasea.setVisible(false);
				passwordField.setVisible(true);
				btnOk.setVisible(true);
			}
		});
		btnCambiarContrasea.setBounds(419, 382, 140, 23);
		contentPane.add(btnCambiarContrasea);
		
		JLabel lblTareasAdministrativas = new JLabel("Tareas administrativas");
		lblTareasAdministrativas.setBounds(10, 16, 269, 14);
		contentPane.add(lblTareasAdministrativas);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(219, 16, 2, 389);
		contentPane.add(separator);
	}
}
