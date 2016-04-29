package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Cliente;
import domain.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import domain.Propietario;

public class AdminUserViewGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Usuario user) {
		try {
			AdminUserViewGUI dialog = new AdminUserViewGUI(user);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AdminUserViewGUI(Usuario user) {
		setTitle("Informaci\u00F3n sobre el usuario: ");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JTextPane txtpnNoSeHa = new JTextPane();
			txtpnNoSeHa.setBackground(UIManager.getColor("Panel.background"));
			txtpnNoSeHa.setEditable(false);
			txtpnNoSeHa.setBounds(10, 10, 414, 207);
			txtpnNoSeHa.setText("No se ha podido obtener la informaci\u00F3n sobre el usuario.");
			if (user != null){
				if (user.getClass().equals(Cliente.class))
					txtpnNoSeHa.setText("\n Mostrando información sobre el usuario: "+user.getCorreo()+"\n\n  -Correo: "+user.getCorreo()+
						"\n  -Nombre: "+user.getNombre()+" "+user.getApellido()+"\n  -Teléfono: "+user.getTlf());
				else {
					Propietario prop = (Propietario) user;
					txtpnNoSeHa.setText("\n Mostrando información sobre el usuario: "+user.getCorreo()+"\n\n  -Correo: "+user.getCorreo()+
							"\n  -Nombre: "+prop.getNombre()+" "+prop.getApellido()+"\n  -Teléfono: "+prop.getTlf()+"\n  -DNI: "+prop.getDNI()+"\n  -Dirección: "+prop.getDirPostal()+
							"\n  -Casas: "+prop.getCasas());
				}
			}
			contentPanel.add(txtpnNoSeHa);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
