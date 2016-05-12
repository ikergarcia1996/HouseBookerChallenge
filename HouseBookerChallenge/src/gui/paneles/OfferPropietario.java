package gui.paneles;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import domain.Offer;
import domain.RuralHouse;
import domain.Usuario;
import gui.GUIOperator;
import gui.HouseEditorGUI;
import gui.OfferEditorGUI;
import gui.OfferManagerGUI;
import utilities.ImageTypes;
import utilities.ImageUtils;

import java.awt.Label;
import java.awt.Button;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class OfferPropietario extends JPanel {

	/**
	 * Create the panel.
	 * @param pass 
	 * @param user 
	 * 
	 * @param i
	 * @param operator
	 */
	public OfferPropietario(Offer offer, Usuario user, GUIOperator operator) {
		setOpaque(false);
		setLayout(null);

		JLabel lblNombre = new JLabel("Ciudad: " + offer.getRuralHouse().getCity());
		lblNombre.setBounds(128, 11, 362, 14);
		add(lblNombre);

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n -" + offer.getRuralHouse().getDescription());
		lblDescripcin.setBounds(128, 36, 362, 83);
		add(lblDescripcin);

		JButton btnConsultar = new JButton("Modificar");
		btnConsultar.setContentAreaFilled(false);
		btnConsultar.setBorder(UIManager.getBorder("CheckBox.border"));
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OfferEditorGUI.main(null, operator, true, offer.getRuralHouse(), offer);
			}
		});
		btnConsultar.setBounds(10, 11, 108, 108);
		add(btnConsultar);


	}
}
