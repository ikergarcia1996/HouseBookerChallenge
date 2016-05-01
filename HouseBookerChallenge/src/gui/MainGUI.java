package gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import domain.Cliente;
import domain.Usuario;
import utilities.ProfileImg;

public class MainGUI extends JFrame {


	private JPanel contentPane;
	private int keycoden;
	protected static WaitGUI dialog = new WaitGUI("Cargando Contenido");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, GUIOperator operator) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI(operator);

					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param operator
	 */
	public MainGUI(GUIOperator operator) {
		GUIRLoader res = new GUIRLoader();
		setResizable(false);
		
		setTitle("HouseBookerChallenge");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 365, 400);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if (keycoden > 2) {
					AdminLoginGUI.main(null, operator);
					keycoden = 0;
				} else {
					keycoden++;
				}
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		
		setIconImage(res.icono);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		

		SwingUtilities.updateComponentTreeUI(contentPane);

		JLabel lblLogo = new JLabel(res.logotipo);
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLogo.setBounds(5, 5, 343, 69);
		contentPane.add(lblLogo);

		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setDefaultCapable(false);
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// dispose();
				ElegirTipoGUI.main(null, operator);
			}
		});
		btnRegistrarse.setBounds(234, 279, 114, 23);
		contentPane.add(btnRegistrarse);

		JButton btnIniciar = new JButton("Iniciar sesi\u00F3n");
		btnIniciar.setDefaultCapable(false);
		btnIniciar.setBounds(234, 241, 114, 23);
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// dispose();
				LoginGUI.main(null, operator);

			}
		});
		contentPane.add(btnIniciar);


		JLabel lblAcercaDe = new JLabel("Acerca de...");
		lblAcercaDe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAcercaDe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				AcercaDeGUI.main(null);
			}
		});
		lblAcercaDe.setForeground(Color.BLUE);
		lblAcercaDe.setBounds(15, 319, 221, 14);
		contentPane.add(lblAcercaDe);

		JButton btnSalir = new JButton("Salir");
		btnSalir.setDefaultCapable(false);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component frame = null;
				int n = JOptionPane.showConfirmDialog(frame,
						"¿Estas seguro de que quieres salir de HouseBookerChallenge?",
						"Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (n == 0) {
				System.exit(0);}
			}
		});
		btnSalir.setBounds(259, 337, 89, 23);
		contentPane.add(btnSalir);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(54, 98, 140, -47);
		contentPane.add(scrollPane);

		JButton btnVerOfertas = new JButton("Ver ofertas");
		btnVerOfertas.setDefaultCapable(false);
		btnVerOfertas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				MainBookerGUI.main(null, new Cliente("Invitado", "Easter Egg!", "JAJAJA", "Invitado@hbc.com", "", new ProfileImg(null)), operator);
				
			}
		});
		btnVerOfertas.setBounds(10, 241, 114, 23);
		contentPane.add(btnVerOfertas);

		JSeparator separator = new JSeparator();
		separator.setBounds(15, 307, 333, 8);
		contentPane.add(separator);
		
		JTextArea txtWellcome = new JTextArea();
		txtWellcome.setLineWrap(true);
		txtWellcome.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane (txtWellcome, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(10, 98, 338, 127);

		
		
		txtWellcome.setLineWrap(true);
		txtWellcome.setEditable(false);
		txtWellcome.setText("Bienvenido a HouseBookerChallenge!\r\n\t\"Podras resistirte...\"\r\n\r\nHouseBookerChallenge es una aplicación de reserva y alquiler de casas rurales para todo tipo de usuarios ya sean grandes empresas, como particulares que quieran poner a disposicin de sus clientes casas rurales para su alquiler.");
		txtWellcome.setBounds(5, 84, 340, 128);
		txtWellcome.select(0, 0);
		scroll.setAutoscrolls(false);
		
		contentPane.add(scroll);
		
		
	}
}
