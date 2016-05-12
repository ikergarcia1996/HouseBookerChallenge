package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.webkit.Utilities;

import domain.Offer;
import domain.RuralHouse;
import domain.Usuario;
import utilities.ImageTypes;
import utilities.ImageUtils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTextArea;
import java.awt.Window.Type;

public class BookOfferGUI extends JDialog {

	private JPanel contenpane;
	private int ifotos = 0;
	private JLabel img;
	JButton siguiente;

	/**
	 * Launch the application.
	 */
	public static void main(Offer offer, Usuario user, GUIOperator operator, boolean verInfo, boolean esPropietario) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookOfferGUI frame = new BookOfferGUI(offer, user, operator, verInfo, esPropietario);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookOfferGUI(Offer offer, Usuario user, GUIOperator operator, boolean verInfo, boolean esPropietario) {

		setModal(true);
		setType(Type.POPUP);
		RuralHouse rh = offer.getRuralHouse();
		setTitle("Casa en" + rh.getCity());

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 939, 696);
		contenpane = new JPanel();
		contenpane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contenpane);
		contenpane.setLayout(null);
		setResizable(false);

		GUIRLoader res = new GUIRLoader();
		setIconImage(res.icono);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		try {
			img = new JLabel(new ImageIcon(utilities.ImageUtils.resize(utilities.ImageUtils.decodeToImage(rh.getImagenes().get(0)), 560, 450)));
			img.setBounds(15, 152, 560, 450);
			contenpane.add(img);

		}

		catch (java.lang.NullPointerException | java.lang.IndexOutOfBoundsException ext) {
			// IMAGEN POR DEFECTO
			JLabel img = new JLabel(new ImageIcon(utilities.ImageUtils.resize(ImageUtils.decodeToImage(ImageTypes.DefaultHouse), 560, 450)));
			img.setBounds(15, 152, 560, 450);
			contenpane.add(img);

		}

		JButton anterior = new JButton("<-- Imagen Anterior");
		anterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ifotos--;
				img.removeAll();
				contenpane.remove(img);
				img = new JLabel(new ImageIcon(utilities.ImageUtils.resize(utilities.ImageUtils.decodeToImage(rh.getImagenes().get(ifotos)), 560, 450)));
				img.setBounds(15, 152, 560, 450);
				contenpane.add(img);
				img.repaint();
				contenpane.repaint();
				contenpane.revalidate();
				img.revalidate();

				if (ifotos == 0)
					anterior.setEnabled(false);
				siguiente.setEnabled(true);
			}
		});
		anterior.setBounds(15, 618, 164, 23);
		contenpane.add(anterior);
		anterior.setEnabled(false);

		siguiente = new JButton("Imagen Siguiente -->");
		siguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ifotos++;
				img.removeAll();
				contenpane.remove(img);
				img = new JLabel(new ImageIcon(utilities.ImageUtils.resize(utilities.ImageUtils.decodeToImage(rh.getImagenes().get(ifotos)), 560, 450)));
				img.setBounds(15, 152, 560, 450);
				contenpane.add(img);
				img.repaint();
				contenpane.repaint();
				contenpane.revalidate();
				img.revalidate();

				if (ifotos + 1 == rh.getImagenes().size())
					siguiente.setEnabled(false);
				anterior.setEnabled(true);
			}
		});
		siguiente.setBounds(398, 618, 177, 23);
		if (rh.getImagenes() != null) {
			if (rh.getImagenes().size() > 1)
				siguiente.setEnabled(true);
			else
				siguiente.setEnabled(false);
		}
		contenpane.add(siguiente);

		JLabel lblImagenesDeLa = new JLabel("Im\u00E1genes de la casa:");
		lblImagenesDeLa.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblImagenesDeLa.setBounds(189, 109, 216, 28);
		contenpane.add(lblImagenesDeLa);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(590, 46, 1, 640);
		contenpane.add(separator);

		JButton contacto = new JButton("Enviar mensaje");
		contacto.setToolTipText("");
		contacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!esPropietario) {
					RedactarMsgGUI.main(null, user, rh.getOwner().getCorreo(),
							"Información sobre la casa en: " + rh.getCity(), "", operator);
				}

				else
					RedactarMsgGUI.main(null, user, offer.getCliente().getCorreo(), "Información sobre tu reserva: ",
							"", operator);
			}
		});
		contacto.setBounds(434, 20, 141, 23);
		contenpane.add(contacto);
		if (!verInfo && !offer.isReservaRealizada()) {
			JButton btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnReservar.setEnabled(false);
					operator.reservar(offer, user);
					btnReservar.setEnabled(false);
					dispose();
				}
			});
			btnReservar.setBounds(622, 602, 295, 55);
			contenpane.add(btnReservar);
		}
		
		if (verInfo && offer.isReservaRealizada() && offer.getCliente().getCorreo().equals(user.getCorreo())){
			JButton btnAnular = new JButton("Anular Reserva");
			btnAnular.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnAnular.setEnabled(false);
					operator.anular(offer, user);
					btnAnular.setEnabled(false);
					dispose();
				}
			});
			btnAnular.setBounds(622, 602, 295, 55);
			contenpane.add(btnAnular);
			
		}

		JLabel lblDatosDeLa = new JLabel("Datos de la oferta");
		lblDatosDeLa.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblDatosDeLa.setBounds(622, 62, 192, 28);
		contenpane.add(lblDatosDeLa);

		JLabel lblPrimerDa = new JLabel("Primer d\u00EDa: " + offer.getFirstDay());
		lblPrimerDa.setBounds(622, 174, 295, 14);
		contenpane.add(lblPrimerDa);

		JLabel lblltimoDa = new JLabel("\u00DAltimo d\u00EDa: " + offer.getLastDay());
		lblltimoDa.setBounds(622, 216, 295, 14);
		contenpane.add(lblltimoDa);

		JLabel lblPrecio = new JLabel("Precio: " + offer.getPrice() + "€");
		lblPrecio.setForeground(Color.GRAY);
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPrecio.setBounds(687, 550, 309, 28);
		contenpane.add(lblPrecio);

		JLabel lblCiudad = new JLabel("Ciudad: " + rh.getCity());
		lblCiudad.setBounds(622, 135, 302, 14);
		contenpane.add(lblCiudad);

		JLabel lblDireccin = new JLabel("Direcci\u00F3n: \n" + rh.getDireccion());
		lblDireccin.setBounds(622, 241, 295, 37);
		contenpane.add(lblDireccin);
		

		JLabel lblNDePersonas = new JLabel("N\u00BA de Personas m\u00E1ximo: " + offer.getnPersRoom());
		lblNDePersonas.setBounds(622, 289, 460, 14);
		contenpane.add(lblNDePersonas);

		JTextArea textArea = new JTextArea(rh.getDescription());
		textArea.setBounds(622, 350, 295, 189);
		contenpane.add(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		
		JLabel lblContacto = new JLabel("Contacto");
		lblContacto.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblContacto.setBounds(63, 23, 160, 14);
		contenpane.add(lblContacto);
		
		if (!esPropietario) {
		//	try {
				Usuario ow = rh.getOwner();
				JLabel lblNombreDelDueo = new JLabel(
						"Nombre del due\u00F1o: " + ow.getNombre() + " " + ow.getApellido());
				lblNombreDelDueo.setBounds(63, 46, 512, 14);
				contenpane.add(lblNombreDelDueo);

				JLabel lblCorreo = new JLabel("Correo: " + ow.getCorreo());
				lblCorreo.setBounds(63, 58, 512, 14);
				contenpane.add(lblCorreo);

				JLabel lblTelfono = new JLabel("Tel\u00E9fono : " + ow.getTlf());
				lblTelfono.setBounds(63, 70, 517, 14);
				contenpane.add(lblTelfono);
			//}

			// TODO LO DEL CATCH SE PUEDE BORRAR CUANDO ACTUALICEMOS TODAS LAS
			// CASAS DE LA BASE DATOS
			// HE AÑADIDO QUE LAS CASAS TENGAN A SU DUEÑO COMO PARAMETRO
			// LAS CASAS QUE HAY AHORA EN LA BASE DE DATOS NO LO TIENEN
			// POR LO QUE LO DE ARRIBA PETA Y SALTA ESTE CATCH
		/*	catch (java.lang.NullPointerException exct) {

				JLabel lblNombreDelDueo = new JLabel("La casa no está actualizada");
				lblNombreDelDueo.setBounds(63, 46, 164, 14);
				contenpane.add(lblNombreDelDueo);

				JLabel lblCorreo = new JLabel("Hace falta añadir su dueño");
				lblCorreo.setBounds(63, 58, 164, 14);
				contenpane.add(lblCorreo);

				JLabel lblTelfono = new JLabel("Para poder mostar sus datos");
				lblTelfono.setBounds(63, 70, 46, 14);
				contenpane.add(lblTelfono);*/
			//}
		} else {
			Usuario cl = offer.getCliente();
			JLabel lblNombreDelDueo = new JLabel("Nombre del cliente: " + cl.getNombre() + " " + cl.getApellido());
			lblNombreDelDueo.setBounds(63, 46, 512, 14);
			contenpane.add(lblNombreDelDueo);

			JLabel lblCorreo = new JLabel("Correo: " + cl.getCorreo());
			lblCorreo.setBounds(63, 58, 512, 14);
			contenpane.add(lblCorreo);

			JLabel lblTelfono = new JLabel("Tel\u00E9fono : " + cl.getTlf());
			lblTelfono.setBounds(63, 70, 512, 14);
			contenpane.add(lblTelfono);

		}

		JLabel lblDescripcinDeLa = new JLabel("Descripci\u00F3n de la casa: ");
		lblDescripcinDeLa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDescripcinDeLa.setBounds(622, 325, 169, 14);
		contenpane.add(lblDescripcinDeLa);

	}
}
