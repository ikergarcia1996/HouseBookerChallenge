package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import domain.Mensaje;

import java.awt.event.ActionListener;
import java.util.UUID;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class AyudaGUI extends JDialog {

	/**
	 * Launch the application.
	 * 
	 * @param operator
	 */
	public static void main(String[] args, GUIOperator operator) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AyudaGUI dialog = new AyudaGUI(operator);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AyudaGUI(GUIOperator operator) {
		setResizable(false);
		setBounds(100, 100, 447, 300);
		setModal(true);
		setTitle("Ayuda");
		GUIRLoader res = new GUIRLoader();
		setIconImage(res.icono);
		getContentPane().setLayout(null);

		JLabel lblSiNecesitasAyuda = new JLabel(
				"Si necesitas ayuda con nuestra aplicacion puedes contactar con el administrador:");
		lblSiNecesitasAyuda.setBounds(10, 10, 419, 20);
		getContentPane().add(lblSiNecesitasAyuda);

		JButton btnNoPuedoAcceder = new JButton("No puedo acceder a mi cuenta");
		btnNoPuedoAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "<html>A continuacion se abrira una ventana para contactar con el administrador. No olvide especificar su nombre de usuario para que pueda ser identificado.</html>",
						"Información", JOptionPane.INFORMATION_MESSAGE);
				RedactarMsgGUI.main(null, null, null, "No puedo acceder a mi cuenta", null, operator);
			}
		});
		btnNoPuedoAcceder.setBounds(10, 46, 175, 23);
		getContentPane().add(btnNoPuedoAcceder);

		JButton btnHeOlvidadoMis = new JButton("He olvidado mis datos de inicio");
		btnHeOlvidadoMis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "<html>Para comprobar su identidad especifica en el mensaje, tu correo electónico, su teléfono, su nombre y sus apellidos.</html>",
						"Información", JOptionPane.INFORMATION_MESSAGE);
				RedactarMsgGUI.main(null, null, null, "He olvidado mis datos de inicio", null, operator);
			}
		});
		btnHeOlvidadoMis.setBounds(10, 85, 175, 23);
		getContentPane().add(btnHeOlvidadoMis);

		JButton btnMenHaRobad = new JButton("Me han robado mi cuenta");
		btnMenHaRobad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "<html>Para comprobar su identidad especifica en el mensaje, tu correo electónico, su teléfono, su nombre y sus apellidos, y describa sus motivos.</html>",
						"Información", JOptionPane.INFORMATION_MESSAGE);
				RedactarMsgGUI.main(null, null, null, "Me han robado mi cuenta", null, operator);
			}
		});
		btnMenHaRobad.setBounds(10, 124, 175, 23);
		getContentPane().add(btnMenHaRobad);

		JButton btnOtro = new JButton("Otro motivo");
		btnOtro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RedactarMsgGUI.main(null, null, null, "Otro motivo", null, operator);
			}
		});
		btnOtro.setBounds(10, 187, 175, 23);
		getContentPane().add(btnOtro);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(200, 46, 2, 164);
		getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 226, 419, 2);
		getContentPane().add(separator_1);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCerrar.setBounds(352, 232, 77, 23);
		getContentPane().add(btnCerrar);

		JButton btnYaHeContactado = new JButton("Ya he contactado con el administrador,\r\ncomprobar respuestas");
		btnYaHeContactado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uuid = JOptionPane.showInputDialog(null,
						"Introduzca aqui el identificador que se le dio cuando realizó su consulta.", "Identificador",
						JOptionPane.INFORMATION_MESSAGE);
				Mensaje mensaje = operator.searchPublic(uuid);

				if (mensaje == null
						|| !Pattern.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", uuid))
					JOptionPane.showMessageDialog(null, "No se ha encontrado ningun mensaje con ese identificador.",
							"Error", JOptionPane.WARNING_MESSAGE);
				else
					ViewPublicMsgGUI.main(mensaje);

			}
		});
		btnYaHeContactado.setBounds(217, 103, 209, 55);
		getContentPane().add(btnYaHeContactado);

	}
}
