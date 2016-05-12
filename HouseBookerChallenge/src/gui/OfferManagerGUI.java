package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
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

import domain.Offer;
import domain.RuralHouse;
import domain.Usuario;

public class OfferManagerGUI extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel				contentPane;
	private JTable				table;
	private String[]			columnNames	= new String[] { "Ciudad", "Primer día", "Último día", "Precio",
			"Num. Pers." };
	// private DefaultListModel lstModel = new DefaultListModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Usuario user, RuralHouse casa, GUIOperator operator) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OfferManagerGUI frame = new OfferManagerGUI(user, casa, operator);
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
	public OfferManagerGUI(Usuario user, RuralHouse casa, GUIOperator operator) {
		setTitle("Administrar ofertas para la casa: " + casa.getCity() + " " + casa.getDireccion());
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// GUIOperator gOP = new GUIOperator();
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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 608, 207);
		contentPane.add(scrollPane);
		scrollPane.setViewportBorder(null);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(table);
		table.setModel(tableModel);

		ArrayList<Offer> offPanel = new ArrayList<Offer>();
		Collection coleccion = operator.getAllOffers(casa);
		for (Object v : coleccion) {
			Vector row = new Vector();
			Offer vo = (Offer) v;
			if (!vo.isReservaRealizada()){
			String firstDaySqlDate = (vo.getFirstDay().getDate() + "/" + (vo.getFirstDay().getMonth() + 1) + "/"
					+ (vo.getFirstDay().getYear() + 1900));
			String lastDaySqlDate = (vo.getLastDay().getDate() + "/" + (vo.getLastDay().getMonth() + 1) + "/"
					+ (vo.getLastDay().getYear() + 1900));
			// lstModel.addElement(v);

			row.add(vo.getRuralHouse().getCity());
			row.add(firstDaySqlDate);
			row.add(lastDaySqlDate);
			row.add(vo.getPrice() + "€");
			row.add(vo.getnPersRoom());
			tableModel.addRow(row);
			offPanel.add(vo);
			}

		}
		scrollPane.repaint();
		scrollPane.revalidate();

		JButton btnAtrs = new JButton("Atr\u00E1s");
		btnAtrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnAtrs.setBounds(628, 195, 101, 23);
		contentPane.add(btnAtrs);

		JButton btnAadirOferta = new JButton("A\u00F1adir oferta");
		btnAadirOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OfferEditorGUI.main(null, operator, false, casa, null);
				dispose();
				OfferManagerGUI.main(null, user, casa, operator);
			}
		});
		btnAadirOferta.setBounds(628, 11, 101, 23);
		contentPane.add(btnAadirOferta);

		JButton btnEditarOferta = new JButton("Editar oferta");
		btnEditarOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					OfferEditorGUI.main(null, operator, true, casa, offPanel.get(table.getSelectedRow()));
					dispose();
					OfferManagerGUI.main(null, user, casa, operator);
				} else JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna oferta, selecciónela en la lista de la izquierda.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnEditarOferta.setBounds(628, 45, 101, 23);
		contentPane.add(btnEditarOferta);

		JButton btnEliminarOferta = new JButton("Eliminar oferta");
		btnEliminarOferta.setVisible(false);
		/*btnEliminarOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1){
					btnEliminarOferta.setEnabled(false);
					int opcode = operator.deleteOfferOwner(user, offPanel.get(table.getSelectedRow()));
					if (opcode == 0){
						
					} else if (opcode == 1) JOptionPane.showMessageDialog(null, "La oferta seleccionada está reservada, no se puede eliminar.",
							"Error", JOptionPane.ERROR_MESSAGE);
					else JOptionPane.showMessageDialog(null, "Error en la operación.",
							"Error", JOptionPane.ERROR_MESSAGE);
					dispose();
					OfferManagerGUI.main(null, user, casa, operator);
				} else JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna oferta, selecciónela en la lista de la izquierda.",
						"Error", JOptionPane.ERROR_MESSAGE);
				
			}
		});*/
		btnEliminarOferta.setBounds(628, 79, 101, 23);
		contentPane.add(btnEliminarOferta);
	}
}
