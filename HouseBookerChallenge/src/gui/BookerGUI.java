package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import domain.Offer;
import domain.RuralHouse;
import domain.Usuario;
import gui.paneles.OfferCliente;

import java.awt.Window.Type;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollBar;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import com.toedter.calendar.JCalendar;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.PanelUI;
import javax.swing.event.ChangeEvent;

public class BookerGUI extends JDialog {

	private JPanel contentPane;
	private JTextField textCiudad;
	private JFormattedTextField dia;
	private JFormattedTextField ano;
	private JCheckBox ciudad;
	private Component frame = null;
	JSpinner sPrecioMax;
	JLabel labelError;
	JSpinner numnoches;
	JSpinner tipoHabitacionL;
	private Collection coleccion;
	JLabel labelError2;
	ArrayList<Offer> offPanel;
	JPanel p1;
	JPanel p2;
	JPanel p3;
	JPanel p4;

	JButton anteriores;
	JButton siguientes;
	public static WaitGUI wait = new WaitGUI("Buscando ofertas");
	public static WaitGUI cargando=new WaitGUI("Cargando contenido, por favor espere.");

	private ArrayList<Offer> ofertasEncontradas = new ArrayList<Offer>();
	int indice = 0;

	public class ExcepcionBusqueda extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ExcepcionBusqueda(String msg) {
			super(msg);
		}
	}

	private void mostrarResult(Usuario user, GUIOperator operator) {
		System.out.println("MOSTRANDO RESULTADOS: ");
		if (indice < 4)
			anteriores.setEnabled(false);
		else
			anteriores.setEnabled(true);
		if (indice + 4 < ofertasEncontradas.size())
			siguientes.setEnabled(true);
		else
			siguientes.setEnabled(false);
		// Borrar paneles
		if (p1 != null)
			contentPane.remove(p1);
		if (p2 != null)
			contentPane.remove(p2);
		if (p3 != null)
			contentPane.remove(p3);
		if (p4 != null)
			contentPane.remove(p4);

		int i = ofertasEncontradas.size() - indice;

		if (i > 0) {

			p1 = new OfferCliente(ofertasEncontradas.get(indice), user, operator);
			p1.setBounds(170, 59, 440, 130);
			contentPane.add(p1);
			System.out.println(ofertasEncontradas.get(indice).toString() + " "
					+ ofertasEncontradas.get(indice).getRuralHouse().toString());
			p1.repaint();

		}

		if (i > 1) {

			p2 = new OfferCliente(ofertasEncontradas.get(indice + 1), user, operator);
			p2.setBounds(170, 201, 440, 130);
			contentPane.add(p2);
			System.out.println(ofertasEncontradas.get(indice + 1).toString() + " "
					+ ofertasEncontradas.get(indice + 1).getRuralHouse().toString());
			p2.repaint();

		}

		if (i > 2) {
			p3 = new OfferCliente(ofertasEncontradas.get(indice + 2), user, operator);
			p3.setBounds(170, 342, 440, 130);
			contentPane.add(p3);
			System.out.println(ofertasEncontradas.get(indice + 2).toString() + " "
					+ ofertasEncontradas.get(indice + 2).getRuralHouse().toString());
			p3.repaint();
		}

		if (i > 3) {
			p4 = new OfferCliente(ofertasEncontradas.get(indice + 3), user, operator);
			System.out.println(ofertasEncontradas.get(indice + 3).toString() + " "
					+ ofertasEncontradas.get(indice + 3).getRuralHouse().toString());
			p4.setBounds(170, 486, 440, 130);
			contentPane.add(p4);
			p4.repaint();
		}

		/* Debug */System.out.println("indice lista ofertas: " + indice);

		contentPane.repaint();

	}

	// private FacadeImplementationWS busqueda = new FacadeImplementationWS();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Usuario user, GUIOperator operator) {
		cargando.setVisible(true);
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					BookerGUI frame = new BookerGUI(user, operator);
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
	 * @param user
	 * 
	 * @param operator
	 */
	public BookerGUI(Usuario user, GUIOperator operator) {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MainBookerGUI.main(null, user, operator);
				dispose();

			}
		});
		GUIRLoader res = new GUIRLoader();
		setModal(true);
		setResizable(false);

		contentPane = new JPanel();
		
		
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setIconImage(res.icono);
		setResizable(false);

		setBounds(100, 100, 627, 654);
		setTitle("Buscador de ofertas");
		SwingUtilities.updateComponentTreeUI(getContentPane());

		JButton btnAtras = new JButton("Volver atr\u00E1s");
		btnAtras.setBounds(10, 11, 107, 35);
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainBookerGUI.main(null, user, operator);
				dispose();

			}
		});
		getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
//UIManager.getLookAndFeelDefaults().put("Panel.background", null);
//UIManager.getLookAndFeelDefaults().put("Background", null);
		try {
			String clase = "javax.swing.plaf.metal.MetalLookAndFeel";
			UIManager.setLookAndFeel(clase);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JCalendar calendar = new JCalendar();
		calendar.getMonthChooser().getComboBox().setBackground(Color.WHITE);
		calendar.getMonthChooser().getSpinner().setForeground(Color.WHITE);
		
		
		calendar.putClientProperty("JCalendar.background", null);
		calendar.getMonthChooser().getSpinner().setBackground(Color.WHITE);
		calendar.getDayChooser().setBackground(UIManager.getColor("Button.background"));
		calendar.getDayChooser().getDayPanel().setBorder(null);
		calendar.setMaxDayCharacters(2);
		calendar.setBounds(3, 170, 175, 115);
		Component botones[] = calendar.getDayChooser().getDayPanel().getComponents();
		for(int i=7;i<49;i++){
			((JComponent) botones[i]).setBorder(null);
		}
		contentPane.add(calendar);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		

		getContentPane().add(btnAtras);

		JLabel lblFiltrosDisponibles = new JLabel("Filtros Disponibles");
		lblFiltrosDisponibles.setBounds(20, 70, 107, 25);
		getContentPane().add(lblFiltrosDisponibles);

		ciudad = new JCheckBox("Ciudad");
		ciudad.setBounds(20, 100, 97, 23);
		ciudad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ciudad.isSelected())
					textCiudad.setEnabled(true);
				else
					textCiudad.setEnabled(false);
			}
		});
		getContentPane().add(ciudad);

		textCiudad = new JTextField();
		textCiudad.setBounds(20, 128, 107, 20);
		getContentPane().add(textCiudad);
		textCiudad.setColumns(10);
		textCiudad.setEnabled(false);

		JLabel lblFechaInicio = new JLabel("Fecha Inicio");
		lblFechaInicio.setBounds(48, 176, 57, 14);
		getContentPane().add(lblFechaInicio);

		dia = new JFormattedTextField();
		dia.setVisible(false);
		dia.setBounds(20, 201, 32, 20);
		getContentPane().add(dia);
		dia.setColumns(10);

		JLabel lblDe = new JLabel("de");
		lblDe.setVisible(false);
		lblDe.setBounds(62, 204, 30, 14);
		getContentPane().add(lblDe);

		JComboBox<String> mes = new JComboBox<String>();
		mes.setVisible(false);
		mes.setBounds(92, 201, 74, 20);
		mes.addItem("Enero");
		mes.addItem("Febrero");
		mes.addItem("Marzo");
		mes.addItem("Abril");
		mes.addItem("Mayo");
		mes.addItem("Junio");
		mes.addItem("Julio");
		mes.addItem("Agosto");
		mes.addItem("Septiembre");
		mes.addItem("Octubre");
		mes.addItem("Noviembre");
		mes.addItem("Diciembre");
		getContentPane().add(mes);

		JLabel lblDel = new JLabel("del");
		lblDel.setVisible(false);
		lblDel.setBounds(30, 232, 46, 14);
		getContentPane().add(lblDel);

		ano = new JFormattedTextField();
		ano.setVisible(false);
		ano.setBounds(60, 229, 57, 20);
		getContentPane().add(ano);
		ano.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 93, 156, 2);
		getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 163, 156, 2);
		getContentPane().add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 312, 156, 2);
		getContentPane().add(separator_2);

		JCheckBox precioMax = new JCheckBox("Precio M\u00E1ximo");
		precioMax.setBounds(20, 321, 97, 23);
		precioMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (precioMax.isSelected()) {
					sPrecioMax.setEnabled(true);
				} else
					sPrecioMax.setEnabled(false);
			}
		});
		getContentPane().add(precioMax);

		sPrecioMax = new JSpinner();
		sPrecioMax.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		sPrecioMax.setBounds(20, 351, 136, 20);
		getContentPane().add(sPrecioMax);
		sPrecioMax.setEnabled(false);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 392, 156, 2);
		getContentPane().add(separator_3);

		JCheckBox tipoHabitacion = new JCheckBox("N\u00BA de personas");
		tipoHabitacion.setBounds(20, 406, 128, 23);
		tipoHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tipoHabitacion.isSelected())
					tipoHabitacionL.setEnabled(true);
				else
					tipoHabitacionL.setEnabled(false);
			}
		});
		getContentPane().add(tipoHabitacion);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 486, 156, 2);
		getContentPane().add(separator_4);

		JLabel lblNDeNoches = new JLabel("N\u00BA de noches");
		lblNDeNoches.setBounds(23, 290, 69, 14);
		getContentPane().add(lblNDeNoches);
		
		Color bonito = new Color (0,184,245);
		numnoches = new JSpinner();
		numnoches.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Component components[] = calendar.getDayChooser().getDayPanel().getComponents();
				int i = 7;
				int selected = 7;
				boolean encontrado = false;
				Color def=new Color(238,238,238);
				Color defsel=new Color(160,160,160);
				int dia=calendar.getDayChooser().getDay();
				
				for (int s = 7; s < 49; s++) components[s].setBackground(def);
				
				calendar.getDayChooser().setDay(dia);
				

				while (!encontrado && i < 49) {
					if (components[i].getBackground().equals(defsel)) {
						selected = i;
						encontrado = true;
					}
					i++;
				}
				 
				
				for (int j = selected+1; j < selected + (Integer) numnoches.getValue()&&j<49; j++) {
					components[j].setBackground(bonito);
				}
				
			}

		});
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				Component components[] = calendar.getDayChooser().getDayPanel().getComponents();
				int i = 7;
				int selected = 7;
				boolean encontrado = false;
				Color def=new Color(238,238,238);
				Color defsel=new Color(160,160,160);
				int dia=calendar.getDayChooser().getDay();
				
				for (int s = 7; s < 49; s++) components[s].setBackground(def);
				
				calendar.getDayChooser().setDay(dia);
				

				while (!encontrado && i < 49) {
					if (components[i].getBackground().equals(defsel)) {
						selected = i;
						encontrado = true;
					}
					i++;
				}
				 
				
				for (int j = selected+1; j < selected + (Integer) numnoches.getValue()&&j<49; j++) {
					components[j].setBackground(bonito);
				}
				
			}

		});
		SpinnerModel nochesmodel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		numnoches.setModel(nochesmodel);
		numnoches.setBounds(102, 287, 47, 20);
		getContentPane().add(numnoches);

		labelError = new JLabel(" ");
		labelError.setBounds(287, 44, 222, 14);
		getContentPane().add(labelError);

		JLabel lblNewLabelResult = new JLabel("");
		lblNewLabelResult.setBounds(10, 536, 204, 14);
		getContentPane().add(lblNewLabelResult);

		SpinnerModel tipomodel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		tipoHabitacionL = new JSpinner();
		tipoHabitacionL.setModel(tipomodel);
		tipoHabitacionL.setBounds(20, 442, 136, 20);
		getContentPane().add(tipoHabitacionL);
		tipoHabitacionL.setEnabled(false);


		
		labelError2 = new JLabel(" ");
		labelError2.setBounds(10, 473, 146, 14);
		getContentPane().add(labelError2);

		JButton btnAplicarFiltros = new JButton("Aplicar filtros y buscar");
		btnAplicarFiltros.setBounds(10, 513, 156, 37);
		btnAplicarFiltros.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				btnAplicarFiltros.setEnabled(false);
				// BOTON APLICAR FILTROS

				ofertasEncontradas.clear();

				String city = null;
				ArrayDeque<RuralHouse> result = null;

				try {
					// 1º PASO CIUDAD
					if (ciudad.isSelected()) {
						// SI EL FILTRO CIUDAD ESTA SELECCIONADO
						city = textCiudad.getText();

						// SI EL RECUADRO ESTA VACIO <-- error
						if (city.isEmpty()) {

							JOptionPane.showMessageDialog(frame,
									"La ciudad introducida es inválida, revise los datos introducidos, si no quieres buscar ofertas por ciudad desmarca la casilla",
									"No se ha podido realizar la busqueda", JOptionPane.WARNING_MESSAGE);
							throw new ExcepcionBusqueda("Ciudad");
							// SI NO ESTA VACIO <-- busqueda
						} else {
							result = operator.getRuralHousesCity(city);
							System.out.println("Busqueda de casas en la ciudad:" + city);

						}

						// SI EL FILTRO CIUDAD NO ESTA SELECCIONADO
					}

					else {
						System.out.println("Cargando todas las casas");
						result = operator.getAllRuralHouses();
					}

					// 2º PASO: SELECCION DE OFERTAS

					// CREACION DE FECHA
					int m = mes.getSelectedIndex();
					// String day = dia.getText();
					// String year = ano.getText();
					String day = "just for testing";
					String year = "just for testing";
					int dayI;
					int yearI;
					int numDias = (Integer) numnoches.getValue();

					// DIA VACIO <-- error
					if (day.isEmpty()) {
						// buscando.dispose();
						JOptionPane.showMessageDialog(frame, "No has introducido el día de incio de la reserva",
								"No se ha podido realizar la busqueda", JOptionPane.WARNING_MESSAGE);
						throw new ExcepcionBusqueda("Dia vacio");
					}
					// AÑO VACIO <--error
					else if (year.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "No has introducido el año de incio de la reserva",
								"No se ha podido realizar la busqueda", JOptionPane.WARNING_MESSAGE);
						throw new ExcepcionBusqueda("Año vacio");
					}

					// 0 Días <--error

					else if (numDias == 0) { // redundante -> quitar
						JOptionPane.showMessageDialog(frame, "No puedes alquilar una casa durante 0 días",
								"No se ha podido realizar la busqueda", JOptionPane.WARNING_MESSAGE);
						throw new ExcepcionBusqueda("dias = 0");
					}

					else if (numDias < 0) { // redundante -> quitar
						JOptionPane.showMessageDialog(frame,
								"Por desgracia la opicón de invetir el tiempo no está implementada por ahora en la aplicación",
								"No se ha podido realizar la busqueda", JOptionPane.WARNING_MESSAGE);
						throw new ExcepcionBusqueda("dias = 0");
					}

					// TIPO DE HABITACION
					int PersHabit = 0;

					if (tipoHabitacion.isSelected()) {
						PersHabit = (int) tipoHabitacionL.getValue();
						if (PersHabit == 0) {
							JOptionPane.showMessageDialog(frame, "No puedes buscar habitaciones para 0 personas",
									"No se ha podido realizar la busqueda", JOptionPane.WARNING_MESSAGE);
							throw new ExcepcionBusqueda("Tipo habitacion");
						} else if (PersHabit < 0) {
							JOptionPane.showMessageDialog(frame,
									"No puedes buscar habitaciones para un número de personas negativo",
									"No se ha podido realizar la busqueda", JOptionPane.WARNING_MESSAGE);
							throw new ExcepcionBusqueda("Tipo habitacion");
						}

					}

					// dayI = Integer.valueOf(day);
					// yearI = Integer.valueOf(year);

					// @SuppressWarnings("deprecation")
					// Date date = new Date(yearI - 1900, m, dayI);
					// System.out.println(date);
					Date date = new Date(calendar.getYearChooser().getYear() - 1900,
							calendar.getMonthChooser().getMonth(), calendar.getDayChooser().getDay());
					dayI = calendar.getDayChooser().getDay();
					yearI = calendar.getYearChooser().getYear();
					System.out.println(date);
					@SuppressWarnings("deprecation")
					Date datefin = (Date) date.clone();
					datefin.setDate(dayI + numDias);

					// BUSQUEDA DE OFERTAS
					Iterator<RuralHouse> itr;
					if (result != null) {
						itr = result.iterator();
					} else
						itr = null;

					// Precio maximo
					float premax = Integer.MAX_VALUE; // Siempre hago la
														// comprobación de
														// si es menor que
														// premax, en caso
														// de que no este
														// seleccionado, su
														// valor será el
														// maximo de un
														// Integer, por lo
														// que todas las
														// casas tendrán un
														// precio
														// menor.

					if (precioMax.isSelected()) {
						premax = (float) sPrecioMax.getValue();
						if (premax == 0) { // DEJAMOS BUSCAR CASAS GRATIS
							labelError.setText("AVISO: Has puesto 0€ como precio máximo");

						}

						else if (premax < 0) {
							JOptionPane.showMessageDialog(frame, "No puedes buscar habitaciones con un precio negativo",
									"No se ha podido realizar la busqueda", JOptionPane.WARNING_MESSAGE);
							throw new ExcepcionBusqueda("Precio Negativo");

						}

						else
							labelError.setText("");
					}
					// System.out.println(date);
					// System.out.println(datefin);
					// tableModel = new DefaultTableModel(null, columnNames);
					offPanel = new ArrayList<Offer>();
					if (itr != null) {
						while (itr.hasNext()) {

							RuralHouse rhtemp = itr.next();

							System.out.println(rhtemp);

							ArrayDeque<Offer> ofertas = operator.getOffers(rhtemp, date, datefin);

							System.out.println(ofertas.toString());

							for (Offer vo : ofertas) {

								System.out.println(vo.toString());
								if (vo.getPrice() <= premax && vo.getnPersRoom() >= PersHabit
										&& !vo.isReservaRealizada()) {
									ofertasEncontradas.add(vo);

									// -------------SUSTITUIDO POR
									// PANELES-----------------
									// CARGAR AL JLIST LO ENCONTRADO

									/*
									 * System.out.println("Offer retrieved: " +
									 * vo.toString()); Vector row = new
									 * Vector(); row.add(rhtemp.getCity());
									 * 
									 * String firstDaySqlDate =
									 * (vo.getFirstDay().getDate() + "/" +
									 * (vo.getFirstDay().getMonth() + 1) + "/" +
									 * (vo.getFirstDay().getYear() + 1900));
									 * String lastDaySqlDate =
									 * (vo.getLastDay().getDate() + "/" +
									 * (vo.getLastDay().getMonth() + 1) + "/" +
									 * (vo.getLastDay().getYear() + 1900));
									 * 
									 * row.add(firstDaySqlDate);
									 * row.add(lastDaySqlDate);
									 * row.add(vo.getPrice() + "€");
									 * row.add(vo.getnPersRoom());
									 * 
									 * tableModel.addRow(row); offPanel.add(vo);
									 * scrollPane.revalidate();
									 * scrollPane.repaint();
									 */

								}
							}

						}
					}

					// Si escribes null en ciudad te carga toda las ofertas
					// Sirve para hacer pruebas de forma sencilla.
					System.out.println(city);
					if (city != null) {
						if (city.equals("null")) {
							ArrayDeque<RuralHouse> tmp1 = new ArrayDeque<RuralHouse>();
							tmp1 = operator.getAllRuralHouses();
							Iterator<RuralHouse> itr2 = tmp1.iterator();
							while (itr2.hasNext())
								ofertasEncontradas.addAll(operator.getAllOffers((RuralHouse) itr2.next()));
						}
					}

					mostrarResult(user, operator);
				}

				catch (java.lang.NumberFormatException exc) {
					JOptionPane.showMessageDialog(frame,
							"Ha ocurrido un error, por favor, revise los datos introducidos",
							"No se ha podido realizar la busqueda", JOptionPane.ERROR_MESSAGE);
				} catch (ExcepcionBusqueda exc) {
					System.out.println(exc);
				}

				btnAplicarFiltros.setEnabled(true);
			}
		});
		getContentPane().add(btnAplicarFiltros);

		JLabel lblOrdenarPor = new JLabel("Ordenar por:");
		lblOrdenarPor.setBounds(351, 0, 84, 25);
		getContentPane().add(lblOrdenarPor);

		JButton btnPrecio = new JButton("Precio\u2191");
		btnPrecio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Collections.sort(ofertasEncontradas, Offer.StrPrice);
				indice = 0;
				mostrarResult(user, operator);
			}
		});
		btnPrecio.setBounds(355, 23, 69, 22);
		getContentPane().add(btnPrecio);

		JButton btnNombre = new JButton("Precio\u2193");
		btnNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Collections.sort(ofertasEncontradas, Offer.StrPriceI);
				indice = 0;
				mostrarResult(user, operator);

			}
		});
		btnNombre.setBounds(287, 23, 69, 22);
		getContentPane().add(btnNombre);

		JButton btnCiudad = new JButton("Ciudad");
		btnCiudad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Collections.sort(ofertasEncontradas, Offer.StrCity);
				indice = 0;
				mostrarResult(user, operator);
			}
		});
		btnCiudad.setBounds(423, 23, 69, 22);
		getContentPane().add(btnCiudad);

		anteriores = new JButton("<- Anteriores");
		anteriores.setEnabled(false);
		anteriores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				indice -= 4;
				mostrarResult(user, operator);
			}
		});
		anteriores.setForeground(new Color(0, 0, 102));
		anteriores.setBounds(191, 21, 97, 25);
		getContentPane().add(anteriores);

		siguientes = new JButton("Siguientes ->");
		siguientes.setEnabled(false);
		siguientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				indice += 4;
				mostrarResult(user, operator);
			}
		});
		siguientes.setForeground(new Color(0, 0, 102));
		siguientes.setBounds(491, 21, 97, 25);
		getContentPane().add(siguientes);

	}

	protected MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatter;
	}
}
