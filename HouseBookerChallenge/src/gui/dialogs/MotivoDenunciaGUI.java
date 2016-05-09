package gui.dialogs;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;

public class MotivoDenunciaGUI extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MotivoDenunciaGUI dialog = new MotivoDenunciaGUI();
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
	public MotivoDenunciaGUI() {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblEspecifiqueAContinuacion = new JLabel("Especifique a continuacion el motivo de su denuncia:");
		lblEspecifiqueAContinuacion.setBounds(10, 11, 260, 14);
		getContentPane().add(lblEspecifiqueAContinuacion);
		
		JButton btnImagenesConfusas = new JButton("Imagenes confusas");
		btnImagenesConfusas.setBounds(10, 36, 155, 23);
		getContentPane().add(btnImagenesConfusas);
		
		JButton btnSuplantacionDeIdentidad = new JButton("Suplantacion de identidad");
		btnSuplantacionDeIdentidad.setBounds(10, 70, 155, 23);
		getContentPane().add(btnSuplantacionDeIdentidad);
		
		JButton btnLenguajeInadecuado = new JButton("Lenguaje inadecuado");
		btnLenguajeInadecuado.setBounds(10, 104, 155, 23);
		getContentPane().add(btnLenguajeInadecuado);
		
		JButton btnDatosInadecuado = new JButton("Datos inadecuados");
		btnDatosInadecuado.setBounds(10, 138, 155, 23);
		getContentPane().add(btnDatosInadecuado);
		
		JButton btnOtros = new JButton("Otros");
		btnOtros.setBounds(10, 196, 89, 23);
		getContentPane().add(btnOtros);

	}
}
