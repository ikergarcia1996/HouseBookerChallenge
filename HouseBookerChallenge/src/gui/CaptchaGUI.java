package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CaptchaGUI extends JDialog {
	private JTextField txtCaptcha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CaptchaGUI dialog = new CaptchaGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	Random				generator	= new Random();
	int					i			= generator.nextInt(10) + 1;
	int					j			= generator.nextInt(10) + 1;
	int					resultado	= i + j;
	private JTextField	txtResultado;

	/**
	 * Create the dialog.
	 */
	public CaptchaGUI() {
		setTitle("Captcha");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 250, 175);
		getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SwingUtilities.updateComponentTreeUI(getContentPane());

		txtCaptcha = new JTextField();
		txtCaptcha.setEditable(false);
		txtCaptcha.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtCaptcha.setHorizontalAlignment(SwingConstants.CENTER);
		txtCaptcha.setText(String.valueOf(i) + " + " + String.valueOf(j) + " = ?");
		txtCaptcha.setBounds(10, 11, 224, 63);
		getContentPane().add(txtCaptcha);
		txtCaptcha.setColumns(10);

		JButton btnContinuar = new JButton("Continuar");
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtResultado.getText().equals(String.valueOf(resultado))) {
					dispose();
				}
				else {
					resultado = update();

					System.out.println(resultado);
				}
				;

			}
		});
		btnContinuar.setBounds(145, 113, 89, 23);
		getContentPane().add(btnContinuar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBounds(10, 113, 89, 23);
		getContentPane().add(btnCancelar);

		txtResultado = new JTextField();
		txtResultado.setBounds(148, 85, 86, 20);
		getContentPane().add(txtResultado);
		txtResultado.setColumns(10);

		JLabel lblResultado = new JLabel("Resultado:");
		lblResultado.setBounds(20, 88, 118, 14);
		getContentPane().add(lblResultado);
	}

	public int update() {
		Random generator = new Random();
		int i = generator.nextInt(10) + 1;
		int j = generator.nextInt(10) + 1;
		resultado = i + j;
		txtCaptcha.setText(String.valueOf(i) + " + " + String.valueOf(j) + " = ?");
		txtResultado.repaint();
		return (resultado);
	}
}
