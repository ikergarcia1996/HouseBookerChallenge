package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import domain.Mensaje;
import utilities.ImageTypes;
import utilities.ImageUtils;

import java.awt.event.ActionListener;
import java.util.UUID;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

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
		btnNoPuedoAcceder.setBorder(UIManager.getBorder("CheckBox.border"));
		btnNoPuedoAcceder.setContentAreaFilled(false);
		btnNoPuedoAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "<html>A continuacion se abrira una ventana para contactar con el administrador. No olvide especificar su nombre de usuario para que pueda ser identificado.</html>",
						"Información", JOptionPane.INFORMATION_MESSAGE);
				RedactarMsgGUI.main(null, null, null, "No puedo acceder a mi cuenta", null, operator);
			}
		});
		btnNoPuedoAcceder.setBounds(10, 46, 209, 23);
		getContentPane().add(btnNoPuedoAcceder);

		JButton btnHeOlvidadoMis = new JButton("He olvidado mis datos de inicio");
		btnHeOlvidadoMis.setBorder(UIManager.getBorder("CheckBox.border"));
		btnHeOlvidadoMis.setContentAreaFilled(false);
		btnHeOlvidadoMis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "<html>Para comprobar su identidad especifica en el mensaje, tu correo electónico, su teléfono, su nombre y sus apellidos.</html>",
						"Información", JOptionPane.INFORMATION_MESSAGE);
				RedactarMsgGUI.main(null, null, null, "He olvidado mis datos de inicio", null, operator);
			}
		});
		btnHeOlvidadoMis.setBounds(10, 85, 209, 23);
		getContentPane().add(btnHeOlvidadoMis);

		JButton btnMenHaRobad = new JButton("Me han robado mi cuenta");
		btnMenHaRobad.setBorder(UIManager.getBorder("CheckBox.border"));
		btnMenHaRobad.setContentAreaFilled(false);
		btnMenHaRobad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "<html>Para comprobar su identidad especifica en el mensaje, tu correo electónico, su teléfono, su nombre y sus apellidos, y describa sus motivos.</html>",
						"Información", JOptionPane.INFORMATION_MESSAGE);
				RedactarMsgGUI.main(null, null, null, "Me han robado mi cuenta", null, operator);
			}
		});
		btnMenHaRobad.setBounds(10, 124, 209, 23);
		getContentPane().add(btnMenHaRobad);

		JButton btnOtro = new JButton("Otro motivo");
		btnOtro.setBorder(UIManager.getBorder("CheckBox.border"));
		btnOtro.setContentAreaFilled(false);
		btnOtro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RedactarMsgGUI.main(null, null, null, "Otro motivo", null, operator);
			}
		});
		btnOtro.setBounds(10, 187, 209, 23);
		getContentPane().add(btnOtro);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(234, 46, 2, 164);
		getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 226, 419, 2);
		getContentPane().add(separator_1);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBorder(UIManager.getBorder("CheckBox.border"));
		btnCerrar.setContentAreaFilled(false);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCerrar.setBounds(352, 232, 77, 23);
		getContentPane().add(btnCerrar);

		JButton btnYaHeContactado = new JButton("Comprobar respuesta");
		btnYaHeContactado.setBorder(UIManager.getBorder("CheckBox.border"));
		btnYaHeContactado.setContentAreaFilled(false);
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
		btnYaHeContactado.setBounds(251, 103, 175, 55);
		getContentPane().add(btnYaHeContactado);
		
		JLabel label = new JLabel(new ImageIcon (ImageUtils.decodeToImage(ImageTypes.FONDO)));
		 label.setBounds(0, -104, 441, 432);
		 getContentPane().add(label);

	}
}
