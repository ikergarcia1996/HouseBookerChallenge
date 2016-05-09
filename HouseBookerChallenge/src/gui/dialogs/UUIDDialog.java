package gui.dialogs;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.UUID;

import javax.swing.JTextField;

import utilities.SystemClipboard;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UUIDDialog extends JDialog {
	private JTextField txtUuid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args,UUID uuid) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UUIDDialog dialog = new UUIDDialog(uuid);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 * @param uuid 
	 */
	public UUIDDialog(UUID uuid) {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 532, 212);
		setTitle("¡ATENCIÓN!");
		getContentPane().setLayout(null);
		
		JLabel lblElSiguienteIdentificador = new JLabel("<html> El siguiente identificador contiene informacion necesaria para el seguimiento de su consulta. Guarde este identificador para poder localizar su consulta posteriormente. Atenci\u00F3n: este identificador no podr\u00E1 ser recuperado posteriormente. </html>");
		lblElSiguienteIdentificador.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblElSiguienteIdentificador.setBounds(79, 16, 432, 57);
		getContentPane().add(lblElSiguienteIdentificador);
		
		txtUuid = new JTextField();
		txtUuid.setText(uuid.toString());
		txtUuid.setEditable(false);
		txtUuid.setBounds(79, 89, 360, 35);
		getContentPane().add(txtUuid);
		txtUuid.setColumns(10);
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(UUIDDialog.class.getResource("/com/sun/javafx/scene/control/skin/modena/dialog-information.png")));
		lblIcon.setBounds(15, 16, 57, 57);
		getContentPane().add(lblIcon);
		
		JButton btnCopiar = new JButton("Copiar");
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemClipboard cb=new SystemClipboard();
				cb.copy(txtUuid.getText());
			}
		});
		btnCopiar.setBounds(432, 89, 79, 35);
		getContentPane().add(btnCopiar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCerrar.setBounds(396, 144, 115, 23);
		getContentPane().add(btnCerrar);

	}
}
