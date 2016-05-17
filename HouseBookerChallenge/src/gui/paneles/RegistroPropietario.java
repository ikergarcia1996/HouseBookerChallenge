package gui.paneles;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.text.NumberFormatter;
import java.awt.Color;

public class RegistroPropietario extends JPanel {
	private JFormattedTextField	txtDni;
	private JTextField			txtCalle;
	private JFormattedTextField	txtN;
	private JTextField			txtPiso;
	private JTextField			txtPuerta;
	private JFormattedTextField	txtLetra;
	private JFormattedTextField	txtCp;
	private JTextField			txtPoblacin;
	private JTextField			txtProvincia;

	/**
	 * Create the panel.
	 */
	public RegistroPropietario() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		setLayout(null);

		JLabel lblDni = new JLabel("DNI completo:");
		lblDni.setBounds(10, 37, 100, 14);
		add(lblDni);

		JLabel lblDireccinPostal = new JLabel("Direcci\u00F3n postal:");
		lblDireccinPostal.setBounds(10, 62, 280, 14);
		add(lblDireccinPostal);

		JLabel lblCalle = new JLabel("Calle:");
		lblCalle.setBounds(20, 87, 100, 14);
		add(lblCalle);

		txtDni = new JFormattedTextField(createFormatter("########-U"));

		txtDni.setBounds(120, 34, 170, 20);
		add(txtDni);
		txtDni.setColumns(10);

		txtCalle = new JTextField();

		txtCalle.setBounds(120, 84, 170, 20);
		add(txtCalle);
		txtCalle.setColumns(10);

		JLabel lblNumero = new JLabel("N\u00FAmero:");
		lblNumero.setBounds(20, 112, 41, 14);
		add(lblNumero);

		txtN = new JFormattedTextField();

		txtN.setBounds(20, 137, 41, 20);
		add(txtN);
		txtN.setColumns(10);

		txtPiso = new JTextField();

		txtPiso.setBounds(71, 137, 41, 20);
		add(txtPiso);
		txtPiso.setColumns(10);

		txtPuerta = new JTextField();

		txtPuerta.setBounds(122, 137, 41, 20);
		add(txtPuerta);
		txtPuerta.setColumns(10);

		txtLetra = new JFormattedTextField(createFormatter("U"));

		txtLetra.setBounds(173, 137, 40, 20);
		add(txtLetra);
		txtLetra.setColumns(10);

		JLabel lblPiso = new JLabel("Piso:");
		lblPiso.setBounds(71, 112, 46, 14);
		add(lblPiso);

		JLabel lblNewLabel = new JLabel("Puerta:");
		lblNewLabel.setBounds(122, 112, 46, 14);
		add(lblNewLabel);

		JLabel lblLetra = new JLabel("Letra:");
		lblLetra.setBounds(173, 112, 46, 14);
		add(lblLetra);

		JLabel lblCdigoPostal = new JLabel("C\u00F3digo postal:");
		lblCdigoPostal.setBounds(20, 168, 69, 14);
		add(lblCdigoPostal);

		txtCp = new JFormattedTextField(createFormatter("#####"));

		txtCp.setBounds(20, 193, 69, 20);
		add(txtCp);
		txtCp.setColumns(10);

		JLabel lblPoblacin = new JLabel("Poblaci\u00F3n:");
		lblPoblacin.setBounds(132, 168, 69, 14);
		add(lblPoblacin);

		txtPoblacin = new JTextField();

		txtPoblacin.setBounds(132, 193, 86, 20);
		add(txtPoblacin);
		txtPoblacin.setColumns(10);

		JLabel lblProvincia = new JLabel("Provincia:");
		lblProvincia.setBounds(20, 224, 69, 14);
		add(lblProvincia);

		txtProvincia = new JTextField();

		txtProvincia.setBounds(20, 249, 270, 20);
		add(txtProvincia);
		txtProvincia.setColumns(10);

	}

	protected MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		}
		catch (java.text.ParseException exc) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Por favor, compruebe que los datos estén bien escritos.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return formatter;
	}

}
