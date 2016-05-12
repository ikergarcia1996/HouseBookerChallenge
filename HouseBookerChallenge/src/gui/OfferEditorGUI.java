package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import domain.Offer;
import domain.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;
import utilities.ImageFilter;
import utilities.ImageTypes;
import utilities.ImageUtils;

import javax.swing.JFormattedTextField;
import java.awt.Color;

public class OfferEditorGUI extends JDialog {

	private final JPanel		contentPanel	= new JPanel();
	private JTextField	precio;
	private JTextField			diaIni;
	private JFormattedTextField			añoIni;
	private JTextField			diaFin;
	private JFormattedTextField			añoFin;
	private JSpinner			noPersonas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, GUIOperator operator, boolean editmode, RuralHouse casa, Offer oferta) {
		try {
			OfferEditorGUI dialog = new OfferEditorGUI(operator, editmode, casa, oferta);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("deprecation")
	public OfferEditorGUI(GUIOperator operator, boolean editmode, RuralHouse casa, Offer oferta) {
		setResizable(false);
		if (editmode)
			setTitle("Editar oferta");
		else setTitle("Crear oferta");
		setBounds(100, 100, 450, 480);
		getContentPane().setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		setModal(true);
		SwingUtilities.updateComponentTreeUI(getContentPane());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 11, 46, 14);
		contentPanel.add(lblPrecio);

		precio = new JTextField();
		precio.setBackground(new Color(211, 228, 213));
		precio.setBounds(10, 31, 142, 20);
		contentPanel.add(precio);
		precio.setColumns(10);

		JComboBox<String> mesIni = new JComboBox<String>();
		contentPanel.add(mesIni);
		mesIni.setBounds(148, 121, 142, 20);
		mesIni.addItem("Enero");
		mesIni.addItem("Febrero");
		mesIni.addItem("Marzo");
		mesIni.addItem("Abril");
		mesIni.addItem("Mayo");
		mesIni.addItem("Junio");
		mesIni.addItem("Julio");
		mesIni.addItem("Agosto");
		mesIni.addItem("Septiembre");
		mesIni.addItem("Octubre");
		mesIni.addItem("Noviembre");
		mesIni.addItem("Diciembre");

		JLabel lblFechaDeInicio = new JLabel("Fecha de inicio:");
		lblFechaDeInicio.setBounds(10, 62, 187, 14);
		contentPanel.add(lblFechaDeInicio);

		JLabel lblMes = new JLabel("Mes:");
		lblMes.setBounds(148, 96, 46, 14);
		contentPanel.add(lblMes);

		JLabel lblDia = new JLabel("Dia:");
		lblDia.setBounds(33, 96, 46, 14);
		contentPanel.add(lblDia);

		diaIni = new JTextField();
		diaIni.setBackground(new Color(211, 228, 213));
		diaIni.setBounds(33, 121, 86, 20);
		contentPanel.add(diaIni);
		diaIni.setColumns(10);

		JLabel lblAo = new JLabel("A\u00F1o:");
		lblAo.setBounds(305, 96, 46, 14);
		contentPanel.add(lblAo);

		añoIni = new JFormattedTextField();
		añoIni.setBackground(new Color(211, 228, 213));
		añoIni.setBounds(300, 121, 86, 20);
		contentPanel.add(añoIni);
		añoIni.setColumns(10);

		diaFin = new JTextField();
		diaFin.setBackground(new Color(211, 228, 213));
		diaFin.setColumns(10);
		diaFin.setBounds(33, 236, 86, 20);
		contentPanel.add(diaFin);

		JLabel label = new JLabel("Dia:");
		label.setBounds(33, 211, 46, 14);
		contentPanel.add(label);

		JLabel label_1 = new JLabel("Mes:");
		label_1.setBounds(148, 211, 46, 14);
		contentPanel.add(label_1);

		JComboBox<String> mesFin = new JComboBox<String>();
		mesFin.setBounds(148, 236, 142, 20);
		mesFin.addItem("Enero");
		mesFin.addItem("Febrero");
		mesFin.addItem("Marzo");
		mesFin.addItem("Abril");
		mesFin.addItem("Mayo");
		mesFin.addItem("Junio");
		mesFin.addItem("Julio");
		mesFin.addItem("Agosto");
		mesFin.addItem("Septiembre");
		mesFin.addItem("Octubre");
		mesFin.addItem("Noviembre");
		mesFin.addItem("Diciembre");
		contentPanel.add(mesFin);

		JLabel label_2 = new JLabel("A\u00F1o:");
		label_2.setBounds(305, 211, 46, 14);
		contentPanel.add(label_2);

		añoFin = new JFormattedTextField();
		añoFin.setBackground(new Color(211, 228, 213));
		añoFin.setColumns(10);
		añoFin.setBounds(300, 236, 86, 20);
		contentPanel.add(añoFin);

		JLabel lblFechaDeFin = new JLabel("Fecha de fin:");
		lblFechaDeFin.setBounds(10, 176, 116, 14);
		contentPanel.add(lblFechaDeFin);

		JLabel lblNDePersonas = new JLabel("N\u00BA de personas de la habitaci\u00F3n:");
		lblNDePersonas.setBounds(10, 289, 187, 14);
		contentPanel.add(lblNDePersonas);

		noPersonas = new JSpinner();
		noPersonas.setBackground(new Color(211, 228, 213));
		SpinnerModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		noPersonas.setModel(model);
		noPersonas.setBounds(20, 314, 86, 20);
		contentPanel.add(noPersonas);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 87, 387, 78);
		contentPanel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(20, 165, 387, 78);
		contentPanel.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(20, 201, 387, 78);
		contentPanel.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(20, 279, 387, 78);
		contentPanel.add(separator_3);
		{
			JButton okButton = new JButton("Guardar");
			okButton.setBorder(UIManager.getBorder("CheckBox.border"));
			okButton.setContentAreaFilled(false);
			okButton.setBounds(340, 406, 89, 29);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (editmode) {
						int mIni = mesIni.getSelectedIndex();
						int mFin = mesFin.getSelectedIndex();
						try {
							Date fechaIni = new Date(Integer.valueOf(añoIni.getText()) - 1900, mIni,
								Integer.valueOf(diaIni.getText()));
							Date fechaFin = new Date(Integer.valueOf(añoFin.getText()) - 1900, mFin,
								Integer.valueOf(diaFin.getText()));
							operator.businessLG.updateOffer(oferta,
									new Offer(0,
											fechaIni,
											fechaFin,
											Float.valueOf(precio.getText()), casa, (Integer) noPersonas.getValue()));
							dispose();
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null,
									"Datos introducidos incorrectos",
									"No se ha podido editar", JOptionPane.WARNING_MESSAGE);
						}
					}
					
					else {
						int mIni = mesIni.getSelectedIndex();
						int mFin = mesFin.getSelectedIndex();
							try {
									Date fechaIni = new Date(Integer.valueOf(añoIni.getText()) - 1900, mIni,
										Integer.valueOf(diaIni.getText()));
									Date fechaFin = new Date(Integer.valueOf(añoFin.getText()) - 1900, mFin,
										Integer.valueOf(diaFin.getText()));
									operator.businessLG.createOffer(casa,
										fechaIni,
										fechaFin,
										Integer.valueOf(precio.getText()), (Integer) noPersonas.getValue());
								dispose();
							}
							catch (NumberFormatException | OverlappingOfferExists | BadDates e) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null,
										"Datos introducidos incorrectos",
										"No se ha podido crear", JOptionPane.WARNING_MESSAGE);
							}
							
					
					}
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancelar");
			cancelButton.setBorder(UIManager.getBorder("CheckBox.border"));
			cancelButton.setContentAreaFilled(false);
			cancelButton.setBounds(232, 406, 93, 29);
			contentPanel.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
		JLabel label2 = new JLabel(new ImageIcon(ImageUtils.decodeToImage(ImageTypes.FONDO)));
		contentPanel.add(label2);
		label2.setBounds(0, 86, 500, 500);
		
					JLabel lblC = new JLabel(new ImageIcon(ImageUtils.decodeToImage(ImageTypes.FONDOCOLOR)));
					contentPanel.add(lblC);
					lblC.setBounds(0, 0, 444, 412);

		// Hay que editar los valores cuando editas
		if (editmode) {
			precio.setText(String.valueOf(oferta.getPrice()));
			diaIni.setText(String.valueOf(oferta.getFirstDay().getDate()));
			mesIni.setSelectedIndex(oferta.getFirstDay().getMonth());
			añoIni.setText(String.valueOf(oferta.getFirstDay().getYear()+1900));
			diaFin.setText(String.valueOf(oferta.getLastDay().getDate()));
			mesFin.setSelectedIndex(oferta.getLastDay().getMonth());
			añoFin.setText(String.valueOf(oferta.getLastDay().getYear()+1900));
			noPersonas.setValue(oferta.getnPersRoom());
		}
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
}
