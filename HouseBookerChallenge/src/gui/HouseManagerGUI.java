package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.RuralHouse;
import domain.Usuario;
import utilities.ImageTypes;
import utilities.ImageUtils;
import java.awt.Color;

public class HouseManagerGUI extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel				contentPane;

	public WaitGUI actualizando = new WaitGUI("Guardando cambios");
	private JTable				table;
	private String[]			columnNames	= new String[] { "Ciudad", "Dirección", "Descripción" };
	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Usuario user, GUIOperator operator) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HouseManagerGUI frame = new HouseManagerGUI(user, operator);
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
	 */
	public HouseManagerGUI(Usuario user, GUIOperator operator) {
		setTitle("Administrador de casas");
		// GUIOperator gOP = new GUIOperator();
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 742, 254);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		SwingUtilities.updateComponentTreeUI(contentPane);

		table = new JTable();
		table.setBackground(new Color(211, 228, 213));
		table.setOpaque(false);
		DefaultTableModel tableModel = new DefaultTableModel(null, columnNames) {

		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};

		table.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(211, 228, 213));
		scrollPane.setOpaque(false);
		scrollPane.setBounds(10, 11, 605, 203);
		scrollPane.setViewportBorder(null);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(table);
		//tableModel = new DefaultTableModel(null, columnNames);
		table.setModel(tableModel);
		ArrayList<RuralHouse> houseList = operator.getOwnerHouses(user);
		for (RuralHouse rh : houseList) {
			Vector<String> row = new Vector<String>();
			row.add(rh.getCity());
			row.add(rh.getDireccion());
			row.add(rh.getDescription());
			tableModel.addRow(row);
		}
		scrollPane.repaint();
		scrollPane.revalidate();

		JButton btnCrearCasa = new JButton("Registrar casa");
		btnCrearCasa.setBorder(UIManager.getBorder("CheckBox.border"));
		btnCrearCasa.setContentAreaFilled(false);
		btnCrearCasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HouseEditorGUI.main(null, user, false, null, operator);
				actualizando.main(null, actualizando);
				dispose();
				HouseManagerGUI.main(null, user, operator);
				actualizando.dispose();
			}
		});
		btnCrearCasa.setBounds(625, 11, 101, 23);
		contentPane.add(btnCrearCasa);

		JButton btnModificarCasa = new JButton("Modificar casa");
		btnModificarCasa.setBorder(UIManager.getBorder("CheckBox.border"));
		btnModificarCasa.setContentAreaFilled(false);
		btnModificarCasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1){
					HouseEditorGUI.main(null, user, true, houseList.get(table.getSelectedRow()),operator);
					//dispose();
					//HouseManagerGUI.main(null, correo, pass, operator);
					actualizando.main(null, actualizando);
					dispose();
					HouseManagerGUI.main(null, user, operator);
					actualizando.dispose();
				}
				else {
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "Para modificar una casa, selecciónela en la lista de la izquierda.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificarCasa.setBounds(625, 45, 101, 23);
		contentPane.add(btnModificarCasa);

		JButton btnAtrs = new JButton("Atr\u00E1s");
		btnAtrs.setBorder(UIManager.getBorder("CheckBox.border"));
		btnAtrs.setContentAreaFilled(false);
		btnAtrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtrs.setBounds(625, 191, 101, 23);
		contentPane.add(btnAtrs);

		JButton btnAdministrarOfertas = new JButton("Ofertas");
		btnAdministrarOfertas.setBorder(UIManager.getBorder("CheckBox.border"));
		btnAdministrarOfertas.setContentAreaFilled(false);
		btnAdministrarOfertas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1){
					OfferManagerGUI.main(null, houseList.get(table.getSelectedRow()), operator);
				}
			}
		});
		btnAdministrarOfertas.setBounds(625, 123, 101, 23);
		contentPane.add(btnAdministrarOfertas);

		JButton btnRefrescar = new JButton("Refrescar");
		btnRefrescar.setBorder(UIManager.getBorder("CheckBox.border"));
		btnRefrescar.setContentAreaFilled(false);
		btnRefrescar.setVisible(false);
		 btnRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				HouseManagerGUI.main(null, user, operator);
			} 
		});
		btnRefrescar.setBounds(625, 157, 101, 23);
		contentPane.add(btnRefrescar);
		
		JButton btnEliminarCasa_1 = new JButton("Eliminar casa");
		btnEliminarCasa_1.setBorder(UIManager.getBorder("CheckBox.border"));
		btnEliminarCasa_1.setContentAreaFilled(false);
		btnEliminarCasa_1.setVisible(false);
		btnEliminarCasa_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
						"Esta característiaca aun no está disponible, se habilitará con la proxima actualización",
						"Proximamente!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnEliminarCasa_1.setBounds(625, 79, 101, 23);
		contentPane.add(btnEliminarCasa_1);
		
		
		JLabel lblC = new JLabel(new ImageIcon (ImageUtils.decodeToImage(ImageTypes.FONDOCOLOR)));
		lblC.setBounds(0, 0, 794, 298);
		getContentPane().add(lblC);
		
	}
}

