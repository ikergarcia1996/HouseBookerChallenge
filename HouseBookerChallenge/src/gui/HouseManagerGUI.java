package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
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
		btnAtrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtrs.setBounds(625, 191, 101, 23);
		contentPane.add(btnAtrs);

		JButton btnAdministrarOfertas = new JButton("Ofertas");
		btnAdministrarOfertas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1){
					OfferManagerGUI.main(null, user, houseList.get(table.getSelectedRow()), operator);
				} else JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna casa, selecciónela en la lista de la izquierda.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnAdministrarOfertas.setBounds(625, 123, 101, 23);
		contentPane.add(btnAdministrarOfertas);

		JButton btnRefrescar = new JButton("Refrescar");
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
		btnEliminarCasa_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1){
					btnEliminarCasa_1.setEnabled(false);
					int opcode = operator.deleteHouseOwner(user, houseList.get(table.getSelectedRow()));
					if (opcode == 0){
						
					} else if (opcode == 1) JOptionPane.showMessageDialog(null, "La casa seleccionada contiene ofertas reservadas, no se puede eliminar.",
							"Error", JOptionPane.ERROR_MESSAGE);
					else JOptionPane.showMessageDialog(null, "Error en la operación.",
							"Error", JOptionPane.ERROR_MESSAGE);
					dispose();
					HouseManagerGUI.main(null, user, operator);
				} else JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna casa, selecciónela en la lista de la izquierda.",
						"Error", JOptionPane.ERROR_MESSAGE);
				
			}
		});
		btnEliminarCasa_1.setBounds(625, 79, 101, 23);
		contentPane.add(btnEliminarCasa_1);
		
		
	}
}

