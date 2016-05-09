package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;

import domain.Mensaje;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewPublicMsgGUI extends JDialog {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(Mensaje mensaje) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPublicMsgGUI dialog = new ViewPublicMsgGUI(mensaje);
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
	 */
	public ViewPublicMsgGUI(Mensaje mensaje) {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 430, 355);
		getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setText(mensaje.getContenido());
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(16, 70, 381, 173);
		getContentPane().add(textArea);
		
		textField = new JTextField();
		textField.setText(mensaje.getAsunto());
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(105, 16, 310, 20);
		getContentPane().add(textField);
		
		JButton button = new JButton("Cerrar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setBounds(326, 293, 89, 23);
		getContentPane().add(button);
		
		textField_1 = new JTextField();
		textField_1.setText(mensaje.getDetalles());
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(105, 252, 310, 20);
		getContentPane().add(textField_1);
		
		JLabel label = new JLabel("Fecha de envio:");
		label.setBounds(15, 255, 80, 14);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Asunto:");
		label_1.setBounds(15, 19, 80, 14);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Mensaje:");
		label_2.setBounds(15, 44, 80, 14);
		getContentPane().add(label_2);

	}

}
