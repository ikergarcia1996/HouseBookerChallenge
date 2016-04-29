package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.RuralHouse;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminHouseDeleteGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable				table;
	private String[]			columnNames	= new String[] { "Nº Casa" ,"Ciudad", "Dirección", "Descripción" };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, GUIOperator gOP, String adminpass) {
		try {
			AdminHouseDeleteGUI dialog = new AdminHouseDeleteGUI(gOP, adminpass);
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
	public AdminHouseDeleteGUI(GUIOperator gOP, String adminpass) {
		setTitle("Modo Administrador: Eliminar una casa");
		setBounds(100, 100, 640, 459);
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
		for (RuralHouse rh : houseList) {
			Vector<String> row = new Vector<String>();
			row.add(rh.getHouseNumber().toString());
			row.add(rh.getCity());
			row.add(rh.getDireccion());
			row.add(rh.getDescription());
			tableModel.addRow(row);
		}
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		table = new JTable();
		
				table.setModel(tableModel);
				JScrollPane scrollPane = new JScrollPane();
				contentPanel.add(scrollPane);
				scrollPane.setBounds(10, 38, 605, 338);
				scrollPane.setViewportBorder(null);
				scrollPane.setViewportView(table);
				//tableModel = new DefaultTableModel(null, columnNames);
				table.setModel(tableModel);
				
				JLabel lblSeleccioneUnaCasa = new JLabel("Seleccione una casa para eliminarla:");
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
					public void actionPerformed(ActionEvent e) {
						if (table.getSelectedRow() != -1){
							RuralHouse house = null;
							int i = 0;
							for (RuralHouse rh : houseList) {
								if (i == table.getSelectedRow()) house = rh;
								i++;
							}
							if (house != null) gOP.superAdminDelete(adminpass, house);
							dispose();
						} else JOptionPane.showMessageDialog(null,
								"No se ha seleccionado ninguna casa.", "Error", JOptionPane.WARNING_MESSAGE);
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
