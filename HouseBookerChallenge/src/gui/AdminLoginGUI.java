package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;

public class AdminLoginGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, GUIOperator gOP) {
		try {
			AdminLoginGUI dialog = new AdminLoginGUI(gOP);
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
	public AdminLoginGUI(GUIOperator gOP) {
		setResizable(false);
		setModal(true);
		setTitle("Modo Administrador: Se necesita autenticaci\u00F3n");
		setBounds(100, 100, 450, 110);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(15, 16, 127, 14);
		contentPanel.add(lblContrasea);
		
		password = new JPasswordField();
		password.setColumns(10);
		password.setBounds(127, 13, 292, 20);
		contentPanel.add(password);
		{
			JButton okButton = new JButton("Iniciar sesi\u00F3n");
			okButton.setBounds(127, 46, 125, 23);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (gOP.superAdminLogin(password.getText()))
						AdminGUI.main(null, gOP, password.getText());
					else
						JOptionPane.showMessageDialog(null,
								"Acceso no autorizado. Credenciales incorrectas.", "Error", JOptionPane.WARNING_MESSAGE);
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancelar");
			cancelButton.setBounds(336, 46, 93, 23);
			contentPanel.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
	}
}
