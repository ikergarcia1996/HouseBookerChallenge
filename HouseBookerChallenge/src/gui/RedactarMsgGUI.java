package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import domain.Usuario;
import gui.dialogs.UUIDDialog;
import utilities.ImageTypes;
import utilities.ImageUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;

public class RedactarMsgGUI extends JDialog {
	private JTextField txtDestinatario;
	private JTextField txtAsunto;

	/**
	 * Launch the application.
	 * 
	 * @param contenido
	 * @param asunto
	 * @param destinatario
	 */
	public static void main(String[] args, Usuario remite, String destinatario, String asunto, String contenido,GUIOperator operator) {
		try {
			RedactarMsgGUI dialog = new RedactarMsgGUI(remite, destinatario, asunto, contenido, operator);
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
	 * @param remite
	 * @param contenido
	 * @param asunto
	 * @param destinatario
	 */
	public RedactarMsgGUI(Usuario remite, String destinatario, String asunto, String contenido, GUIOperator operator) {
		setTitle("Redactar nuevo mensaje");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 425, 370);
		getContentPane().setLayout(null);
		{
			JLabel lblDestinatario = new JLabel("Destinatario: ");
			lblDestinatario.setBounds(10, 11, 80, 14);
			getContentPane().add(lblDestinatario);
		}
		{
			JLabel lblAsunto = new JLabel("Asunto:");
			lblAsunto.setBounds(10, 36, 80, 14);
			getContentPane().add(lblAsunto);
		}

		JTextArea txtMensaje = new JTextArea();
		txtMensaje.setBackground(new Color(211, 228, 213));
		txtMensaje.setLineWrap(true);
		txtMensaje.setWrapStyleWord(true);
		if (contenido != null) {
			txtMensaje.append("");
			txtMensaje.append("");
			txtMensaje.append(contenido);
			txtMensaje.setCaretPosition(0);
		}

		JScrollPane scroll = new JScrollPane(txtMensaje, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scroll.setBounds(10, 86, 400, 200);

		scroll.setAutoscrolls(false);

		getContentPane().add(scroll);

		JLabel lblMensaje = new JLabel("Mensaje:");
		lblMensaje.setBounds(10, 61, 80, 14);
		getContentPane().add(lblMensaje);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 297, 400, 2);
		getContentPane().add(separator);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBorder(UIManager.getBorder("CheckBox.border"));
		btnEnviar.setContentAreaFilled(false);
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
				Date date = new Date();
				System.out.println();
				boolean responder = false;
				if (remite != null) {// Si no es un guest
					if (destinatario!=null && destinatario.equals("admin@hbc.com")){ 
						operator.sendMessageToAdmin(remite.getCorreo(), txtAsunto.getText(), dateFormat.format(date),
								txtMensaje.getText());
						JOptionPane.showMessageDialog(null, "El mensaje se ha enviado correctamente al administrador.",
								"Mensaje enviado", JOptionPane.INFORMATION_MESSAGE);
						dispose();
			
					
					}
					else {
						int result = operator.sendMessageTo(txtDestinatario.getText(), remite.getCorreo(),
								txtAsunto.getText(), dateFormat.format(date), txtMensaje.getText());
						Component frame = null;
						if (result == 0) {
							JOptionPane.showMessageDialog(frame,
									"El mensaje se ha enviado correctamente a " + txtDestinatario.getText(),
									"Mensaje enviado", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else
							JOptionPane.showMessageDialog(frame,
									"El usuaio '" + txtDestinatario.getText()
											+ "' no esiste. Comprueba la informacion introducida.",
									"Error", JOptionPane.WARNING_MESSAGE);

						
					}
					} else if (remite == null) if (destinatario == null) {// si es un guest
						UUID UUID = java.util.UUID.randomUUID();
						operator.sendMessageToAdmin(UUID.toString(), txtAsunto.getText(), dateFormat.format(date),
								txtMensaje.getText());

						JOptionPane.showMessageDialog(null, "El mensaje se ha enviado correctamente al administrador.",
								"Mensaje enviado", JOptionPane.INFORMATION_MESSAGE);

						UUIDDialog.main(null, UUID);
						responder = true;
						dispose();

					}

					else if (remite == null)
						if (destinatario != null) { // Si el admin responde
							operator.sendPublicMesage(destinatario, asunto, dateFormat.format(date),
									txtMensaje.getText());
							JOptionPane.showMessageDialog(null, "Se ha respondido correctamente al mensaje.",
									"Mensaje enviado", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}
			}

		});
		btnEnviar.setBounds(320, 310, 89, 23);
		getContentPane().add(btnEnviar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBorder(UIManager.getBorder("CheckBox.border"));
		btnCancelar.setContentAreaFilled(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBounds(10, 310, 89, 23);
		getContentPane().add(btnCancelar);

		txtDestinatario = new JTextField();
		txtDestinatario.setBackground(new Color(211, 228, 213));
		txtDestinatario.setBounds(100, 8, 310, 20);
		if (destinatario != null)
			txtDestinatario.setText(destinatario);
		getContentPane().add(txtDestinatario);
		txtDestinatario.setColumns(10);

		txtAsunto = new JTextField();
		txtAsunto.setBackground(new Color(211, 228, 213));
		txtAsunto.setBounds(100, 33, 310, 20);
		if (asunto != null)
			txtAsunto.setText(asunto);
		getContentPane().add(txtAsunto);
		txtAsunto.setColumns(10);

		if (remite == null) {
			txtDestinatario.setText("admin@hbc.com");
			txtDestinatario.setEnabled(false);
			txtDestinatario.setEditable(false);
			txtAsunto.setEditable(false);
			txtAsunto.setEnabled(false);
		}
		
		JLabel lblC = new JLabel(new ImageIcon (ImageUtils.decodeToImage(ImageTypes.FONDOCOLOR)));
		lblC.setBounds(0, 0, 437, 366);
		getContentPane().add(lblC);

	}
}
