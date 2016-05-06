package gui;

import java.awt.Component;

import java.awt.event.*;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import domain.Cliente;
import domain.Mensaje;
import domain.Offer;
import domain.Propietario;
import domain.RuralHouse;
import domain.Usuario;
import gui.paneles.OfferCliente;
import gui.paneles.OfferPropietario;
import utilities.ImageTypes;
import utilities.ImageUtils;

import java.awt.Cursor;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class MainBookerGUI extends JDialog {

	private JPanel contentPane;
	private ArrayList<Offer> ultimasOfertas;

	public static WaitGUI cargando = new WaitGUI("Cargando ofertas recientes");

	/**
	 * Launch the application.
	 * 
	 * @param operator
	 */
	public static void main(String[] args, Usuario user, GUIOperator operator) {

		cargando.setVisible(true);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MainBookerGUI frame = new MainBookerGUI(user, operator);
					cargando.setVisible(false);
					frame.setVisible(true);

				} catch (Exception e) {
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
	public MainBookerGUI(Usuario user, GUIOperator operator) {

		Component frame = null;
		GUIRLoader res = new GUIRLoader();
		setTitle("HouseBookerChallenge");

		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setIconImage(res.icono);
		
		SwingUtilities.updateComponentTreeUI(contentPane);

		ArrayDeque<RuralHouse> tmp = new ArrayDeque<RuralHouse>();

		if (user.getClass().equals(Cliente.class)) {
			tmp = operator.getAllRuralHouses();
		} else {
			for (RuralHouse v : operator.getOwnerHouses(user)) {
				tmp.add(v);
			}
		}

		ultimasOfertas = new ArrayList<Offer>();
		int añadidos = 0;

		while (añadidos < 5 && tmp.size() > 0) {
			Collection<Offer> coleccion;

			coleccion = operator.getAllOffers(tmp.removeLast());

			for (Offer v : coleccion) {
				if (!v.isReservaRealizada()) {
					ultimasOfertas.add(v);
					añadidos++;
				}
			}

		}
		int tamaño = ultimasOfertas.size();
		if (tamaño > 0) {
			Offer ultima0 = ultimasOfertas.get(0);
			if (user.getClass().equals(Cliente.class)) {

				JPanel panel = new OfferCliente(ultima0, user, operator);
				panel.setBounds(180, 96, 440, 130);
				contentPane.add(panel);
			} else {
				JPanel panel = new OfferPropietario(ultima0, user, operator);
				panel.setBounds(180, 96, 440, 130);
				contentPane.add(panel);
			}
		}

		if (tamaño > 1) {
			Offer ultima1 = ultimasOfertas.get(1);
			if (user.getClass().equals(Cliente.class)) {
				JPanel panel_1 = new OfferCliente(ultima1, user, operator);
				panel_1.setBounds(180, 237, 440, 130);
				contentPane.add(panel_1);
			} else {
				JPanel panel_1 = new OfferPropietario(ultimasOfertas.get(1), user, operator);
				panel_1.setBounds(180, 237, 440, 130);
				contentPane.add(panel_1);
			}
		}

		if (tamaño > 2) {
			Offer ultima2 = ultimasOfertas.get(2);
			if (user.getClass().equals(Cliente.class)) {
				JPanel panel_2 = new OfferCliente(ultima2, user, operator);
				panel_2.setBounds(180, 378, 440, 130);
				contentPane.add(panel_2);
			} else {
				JPanel panel_2 = new OfferPropietario(ultima2, user, operator);
				panel_2.setBounds(180, 378, 440, 130);
				contentPane.add(panel_2);
			}
		}
		if (tamaño > 3) {
			Offer ultima3 = ultimasOfertas.get(3);
			if (user.getClass().equals(Cliente.class)) {
				JPanel panel_3 = new OfferCliente(ultima3, user, operator);
				panel_3.setBounds(180, 519, 440, 130);
				contentPane.add(panel_3);
			} else {
				JPanel panel_3 = new OfferPropietario(ultima3, user, operator);
				panel_3.setBounds(180, 519, 440, 130);
				contentPane.add(panel_3);
			}
		}

		JSeparator separator = new JSeparator();
		separator.setBounds(15, 80, 605, 10);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(358, 5, 10, 69);
		contentPane.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(165, 120, 15, 670);
		contentPane.add(separator_2);

		JButton btnBuscarOCasas = new JButton("");
		if (user.getClass().equals(Propietario.class))
			btnBuscarOCasas.setText("Administrar casas");
		else
			btnBuscarOCasas.setText("Buscar ofertas");

		btnBuscarOCasas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (user.getClass().equals(Cliente.class)) {
					BookerGUI.main(null, user, operator);
					dispose();
				} else
					HouseManagerGUI.main(null, user, operator);

			}
		});
		btnBuscarOCasas.setBounds(15, 160, 135, 29);
		contentPane.add(btnBuscarOCasas);

		JLabel lblLogo = new JLabel(res.logotipo);
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLogo.setBounds(5, 5, 343, 69);
		contentPane.add(lblLogo);

		JButton btnVolverAlMenu = new JButton("Cerrar Sesi\u00F3n");
		btnVolverAlMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame,
						"¿Estás seguro de que quieres cerrar serión y salir al menu principal?", "Cerrar sesión",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					operator.logout(user);
					dispose();
				}
			}
		});
		btnVolverAlMenu.setActionCommand("");
		btnVolverAlMenu.setBounds(15, 120, 135, 29);
		contentPane.add(btnVolverAlMenu);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(15, 110, 145, 10);
		contentPane.add(separator_3);

		JLabel lblOfertasAadidasRecientemente = new JLabel("Ofertas a\u00F1adidas recientemente:");
		lblOfertasAadidasRecientemente.setBounds(15, 80, 165, 29);
		contentPane.add(lblOfertasAadidasRecientemente);

		JLabel lblNotification = new JLabel("");
		lblNotification.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Stack<Mensaje> userMessages = new Stack<Mensaje>();
		userMessages = user.getMensajes();
		if (!userMessages.isEmpty() && userMessages.peek().isUnread()) {
			if (userMessages.peek().getRemite().matches("(.*)@hbc.com")) {
				lblNotification.setIcon(new ImageIcon(ImageUtils.decodeToImage(ImageTypes.NEW_NOTIFY)));
			}

			else
				lblNotification.setIcon(new ImageIcon(ImageUtils.decodeToImage(ImageTypes.NEW_MESSAE)));
		}
		lblNotification.setBounds(599, 0, 25, 25);
		contentPane.add(lblNotification);

		JLabel lblPerfil = new JLabel("Perfil");
		lblPerfil.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if (user.getUserName().equals("Invitado")) {
					if (JOptionPane.showConfirmDialog(frame,
							"Estás utilizando esta plataforma como usuario invitado. ¿Quieres registrarte?", "Invitado",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						dispose();
						ElegirTipoGUI.main(null, operator);
					}
				} else {
					UserGUI.main(null, user, operator);
					dispose();
					MainBookerGUI.main(null, user, operator);

				}

			}
		});
		ImageIcon perfil = user.getDecodedProfileImg();
		lblPerfil.setIcon(perfil);
		lblPerfil.setBorder(new LineBorder(Color.GRAY, 2, true));
		lblPerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		lblPerfil.setBounds(544, 0, 80, 80);
		contentPane.add(lblPerfil);
		MainGUI.dialog.dispose();

		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent windowEvent) {
				if (!user.getUserName().equals("Invitado")) {
					if (JOptionPane.showConfirmDialog(frame,
							"¿Estás seguro de que quieres cerrar serión y salir al menu principal?", "Cerrar sesión",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						operator.logout(user);
						dispose();
					} else
						setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				}

				else
					dispose();
			}
		});

		if (!user.getUserName().equals("Invitado")) {

			JButton ver = new JButton("Reservas");
			ver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (user.getClass().equals(Propietario.class)) {
						ReservasEnMisCasasGUI.main(null, operator.getUser(user.getCorreo()), operator);
					} else
						VerMisReservasGUI.main(null, operator.getUser(user.getCorreo()), operator);
				}

			});
			ver.setBounds(15, 200, 135, 29);
			contentPane.add(ver);

		}
	}

}
