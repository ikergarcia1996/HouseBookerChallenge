package gui.paneles;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import domain.Offer;
import domain.Usuario;
import gui.BookOfferGUI;
import gui.GUIOperator;
import utilities.ImageTypes;
import utilities.ImageUtils;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Base64;
import javax.swing.JFormattedTextField;
import java.awt.SystemColor;
import javax.swing.JSeparator;

public class OfferCliente extends JPanel {
	
	/**
	 * Create the panel.
	 * 
	 * @param i
	 * @param operator
	 */
	public OfferCliente(Offer offer, Usuario user, GUIOperator operator) {
		setLayout(null);

		JLabel lblCiudad = new JLabel("Ciudad: " + offer.getRuralHouse().getCity());
		lblCiudad.setBounds(128, 11, 312, 14);
		add(lblCiudad);

		JButton btnConsultar = new JButton("M\u00E1s info / Reservar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				BookOfferGUI.main(operator.getOffer(offer), user, operator, false, false);
				
			}
		});
		btnConsultar.setBounds(300, 79, 140, 42);
		add(btnConsultar);

		JLabel lblNciudad = new JLabel("");
		lblNciudad.setBounds(183, 11, 46, 14);
		add(lblNciudad);

		JLabel lblPrecio = new JLabel("Precio: " + offer.getPrice() + " €");
		lblPrecio.setForeground(SystemColor.controlDkShadow);
		lblPrecio.setBounds(128, 107, 162, 14);
		add(lblPrecio);

		JLabel labelPrecionum = new JLabel("");
		labelPrecionum.setBounds(169, 102, 46, 14);
		add(labelPrecionum);
		
		JLabel lblNumeroDePersonas = new JLabel("Numero de personas: "+ offer.getnPersRoom());
		lblNumeroDePersonas.setBounds(128, 79, 162, 14);
		add(lblNumeroDePersonas);
		
		ImageIcon imagen;
		try {
			imagen = new ImageIcon (ImageUtils.decodeToImage(offer.getRuralHouse().getImagenes().get(0)));
		}
		catch (java.lang.NullPointerException | java.lang.IndexOutOfBoundsException ext) {
			//si no hay imagenes se asigna la imagen default
			imagen = new ImageIcon(ImageUtils.decodeToImage(ImageTypes.DefaultHouse));
			
		}
		
	
		
		JLabel lblImagen = new JLabel(operator.resizeIcon(imagen, 108, 108));
		
		lblImagen.setBounds(10, 11, 108, 108);
		add(lblImagen);
		
		JLabel lblNewLabel = new JLabel("Descripción: " + offer.getRuralHouse().getDescription());
		lblNewLabel.setBounds(128, 36, 312, 42);
		add(lblNewLabel);
		

	}
}
