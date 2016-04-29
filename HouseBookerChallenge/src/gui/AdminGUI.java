package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class AdminGUI extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, GUIOperator gOP, String adminpass) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI(gOP, adminpass);
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminGUI(GUIOperator gOP, String adminpass) {
		setResizable(false);
		setTitle("Modo Administrador");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 580, 355);
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
		btnAdministrarUsuarios.setBounds(10, 11, 554, 82);
		contentPane.add(btnAdministrarUsuarios);
		
		JButton btnEliminarUnaCasa = new JButton("Eliminar una casa...");
		btnEliminarUnaCasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminHouseDeleteGUI.main(null, gOP, adminpass);
			}
		});
		btnEliminarUnaCasa.setBounds(10, 104, 554, 82);
		contentPane.add(btnEliminarUnaCasa);
		
		JButton btnEliminarUnaOferta = new JButton("Eliminar una oferta...");
		btnEliminarUnaOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminOfferDeleteGUI.main(null, gOP, adminpass);
			}
		});
		btnEliminarUnaOferta.setBounds(10, 197, 554, 82);
		contentPane.add(btnEliminarUnaOferta);
		
		JButton btnSalirDelModo = new JButton("Salir del modo administrador");
		btnSalirDelModo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalirDelModo.setBounds(10, 290, 167, 23);
		contentPane.add(btnSalirDelModo);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(326, 291, 178, 20);
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
		btnOk.setBounds(516, 290, 48, 23);
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
		btnCambiarContrasea.setBounds(424, 290, 140, 23);
		contentPane.add(btnCambiarContrasea);
	}
}
