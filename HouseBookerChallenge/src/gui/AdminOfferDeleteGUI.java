package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.Offer;
import domain.RuralHouse;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminOfferDeleteGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable				table;
	private String[]			columnNames	= new String[] { "Nº Casa", "Ciudad", "Dirección", "Descripción", "Primer día", "Último día", "Precio",
			"Num. Pers." };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, GUIOperator gOP, String adminpass) {
		try {
			AdminOfferDeleteGUI dialog = new AdminOfferDeleteGUI(gOP, adminpass);
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
	public AdminOfferDeleteGUI(GUIOperator gOP, String adminpass) {
		setModal(true);
		setTitle("Modo Administrador: Eliminar una oferta");
		setBounds(100, 100, 700, 577);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		ArrayDeque<RuralHouse> houseList = gOP.getAllRuralHouses();
		ArrayList<Offer> offerList = new ArrayList<Offer>();
		for (RuralHouse rh : houseList) {
			for (Offer vo: rh.getOffers()){
				Vector<String> row = new Vector<String>();
				row.add(rh.getHouseNumber().toString());
				row.add(rh.getCity());
				row.add(rh.getDireccion());
				row.add(rh.getDescription());
				String firstDaySqlDate = (vo.getFirstDay().getDate() + "/" + (vo.getFirstDay().getMonth() + 1) + "/"
						+ (vo.getFirstDay().getYear() + 1900));
				String lastDaySqlDate = (vo.getLastDay().getDate() + "/" + (vo.getLastDay().getMonth() + 1) + "/"
						+ (vo.getLastDay().getYear() + 1900));

				row.add(vo.getRuralHouse().getCity());
				row.add(firstDaySqlDate);
				row.add(lastDaySqlDate);
				row.add(vo.getPrice() + "€");
				row.add(String.valueOf(vo.getnPersRoom()));
				offerList.add(vo);
				tableModel.addRow(row);
			}
		}
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.setLayout(null);
		
		table = new JTable();
		
				table.setModel(tableModel);
				JScrollPane scrollPane = new JScrollPane();
				contentPanel.add(scrollPane);
				scrollPane.setBounds(10, 38, 664, 456);
				scrollPane.setViewportBorder(null);
				scrollPane.setViewportView(table);
				//tableModel = new DefaultTableModel(null, columnNames);
				table.setModel(tableModel);
				
				JLabel lblSeleccioneUnaCasa = new JLabel("Seleccione una oferta para eliminarla:");
				lblSeleccioneUnaCasa.setBounds(10, 13, 310, 14);
				contentPanel.add(lblSeleccioneUnaCasa);
				scrollPane.repaint();
				scrollPane.revalidate();
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (table.getSelectedRow() != -1) {
						gOP.superAdminDelete(adminpass, offerList.get(table.getSelectedRow()));
						dispose();
						} else JOptionPane.showMessageDialog(null,
								"No se ha seleccionado ninguna oferta.", "Error", JOptionPane.WARNING_MESSAGE);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
