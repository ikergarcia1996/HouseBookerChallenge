package gui.paneles;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;
import javax.swing.JProgressBar;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

public class RegistroCliente extends JPanel {
	private JTextField			txtNombre;
	private JTextField			txtApellidos;
	private JFormattedTextField	txtTelfono;
	private JTextField			txtCorreoElectrnico;
	private JPasswordField		pwdContrasea;
	private JPasswordField		pwdConfirmar;

	/**
	 * Create the panel.
	 */
	public RegistroCliente() {

		setLayout(null);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setString("Insuficiente");
		progressBar.setStringPainted(true);
		progressBar.setBounds(170, 192, 120, 20);
		progressBar.setMinimum(0);
		progressBar.setMaximum(12);
		progressBar.setIndeterminate(true);

		add(progressBar);

		JLabel lblRellenarLosDatos = new JLabel("Rellenar los datos de registro:");
		lblRellenarLosDatos.setBounds(10, 11, 280, 14);
		add(lblRellenarLosDatos);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 36, 100, 14);
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(130, 33, 160, 20);
		add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(20, 61, 100, 14);
		add(lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setBounds(130, 58, 160, 20);
		add(txtApellidos);
		txtApellidos.setColumns(10);

		JLabel lblTelfono = new JLabel("Tel\u00E9fono");
		lblTelfono.setBounds(20, 86, 100, 14);
		add(lblTelfono);

		txtTelfono = new JFormattedTextField(createFormatter("### ## ## ##"));
		txtTelfono.setBounds(130, 83, 160, 20);
		add(txtTelfono);
		txtTelfono.setColumns(10);

		JLabel lblDireccinDeCorreo = new JLabel("Direcci\u00F3n de correo electr\u00F3nico:");
		lblDireccinDeCorreo.setBounds(20, 111, 270, 14);
		add(lblDireccinDeCorreo);

		JLabel lblElegirContrasea = new JLabel("Elegir contrase\u00F1a:");
		lblElegirContrasea.setBounds(20, 167, 135, 14);
		add(lblElegirContrasea);

		txtCorreoElectrnico = new JTextField();
		txtCorreoElectrnico.setBounds(20, 136, 270, 20);
		add(txtCorreoElectrnico);
		txtCorreoElectrnico.setColumns(10);

		pwdContrasea = new JPasswordField();
		pwdContrasea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				progressBar.setValue(checkPasswordStrength(String.valueOf(pwdContrasea.getPassword())));

				if (pwdContrasea.getPassword().length == 0)
					progressBar.setIndeterminate(true);
				else {
					progressBar.setIndeterminate(false);
				}

				if (progressBar.getValue() <= 12) progressBar.setString("Muy buena");
				if (progressBar.getValue() < 9) progressBar.setString("Buena");
				if (progressBar.getValue() < 6) progressBar.setString("Débil");
				if (progressBar.getValue() < 3) progressBar.setString("Muy débil");
				if (progressBar.getValue() == 0 || pwdContrasea.getPassword().length <= 5) {
					progressBar.setString("Insuficiente");

				}

			}
		});
		pwdContrasea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pwdContrasea.setText("");
			}
		});
		pwdContrasea.setText("");
		pwdContrasea.setBounds(20, 192, 135, 20);
		add(pwdContrasea);

		JLabel lblConfirmarContrasea = new JLabel("Confirmar contrase\u00F1a:");
		lblConfirmarContrasea.setBounds(20, 223, 270, 14);
		add(lblConfirmarContrasea);

		pwdConfirmar = new JPasswordField();
		pwdConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pwdConfirmar.setText("");
			}
		});
		pwdConfirmar.setText("");
		pwdConfirmar.setBounds(20, 248, 135, 20);
		add(pwdConfirmar);

		JLabel lblFuerzaDeContrasea = new JLabel("Fuerza de contrase\u00F1a:");
		lblFuerzaDeContrasea.setBounds(170, 167, 120, 14);
		add(lblFuerzaDeContrasea);

	}

	protected MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatter;
	}

	private int checkPasswordStrength(String password) {
		int p1, p2, p3, p4, pt;

		String[] partialRegexChecks = { ".*[a-z]{1}.*", // minusculas1
				".*[a-z]{2}.*", // minusculas2
				".*[a-z]{3,}.*", // minusculas3omas
				".*[A-Z]{1}.*", // mayusculas1
				".*[A-Z]{2}.*", // mayusculas2
				".*[A-Z]{3,}.*", // mayusculas3omas
				".*[\\d]{1}.*", // digitos1
				".*[\\d]{2}.*", // digitos2
				".*[\\d]{3,}.*", // digitos3omas
				".*[@#$%]{1}.*", // simbolos1
				".*[@#$%]{2}.*", // simbolos2
				".*[@#$%]{3,}.*" // simbolos3omas
		};
		pt = 0;
		p1 = 0;
		p2 = 0;
		p3 = 0;
		p4 = 0;

		if (password.matches(partialRegexChecks[0])) {
			p1 = 1;
		}
		if (password.matches(partialRegexChecks[1])) {
			p1 = 2;
		}
		if (password.matches(partialRegexChecks[2])) {
			p1 = 3;
		}
		if (password.matches(partialRegexChecks[3])) {
			p2 = 1;
		}
		if (password.matches(partialRegexChecks[4])) {
			p2 = 2;
		}
		if (password.matches(partialRegexChecks[5])) {
			p2 = 3;
		}
		if (password.matches(partialRegexChecks[6])) {
			p3 = 1;
		}
		if (password.matches(partialRegexChecks[7])) {
			p3 = 2;
		}
		if (password.matches(partialRegexChecks[8])) {
			p3 = 3;
		}
		if (password.matches(partialRegexChecks[9])) {
			p4 = 1;
		}
		if (password.matches(partialRegexChecks[10])) {
			p4 = 2;
		}
		if (password.matches(partialRegexChecks[11])) {
			p4 = 3;
		}

		pt = p1 + p2 + p3 + p4;
		return pt;
	}

}
