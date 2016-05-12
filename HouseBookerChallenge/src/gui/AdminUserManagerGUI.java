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

import domain.Propietario;
import domain.RuralHouse;
import domain.TwitterUser;
import domain.Usuario;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminUserManagerGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private String[] columnNames = new String[] { "Correo", "¿Es propietario?", "Nombre", "Apellidos",
			"¿Está baneado?" };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, GUIOperator gOP, String adminpass) {
		try {
			AdminUserManagerGUI dialog = new AdminUserManagerGUI(gOP, adminpass);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AdminUserManagerGUI(GUIOperator gOP, String adminpass) {
		setModal(true);
		setTitle("Modo Administrador: Administrar Usuarios");
		setBounds(100, 100, 722, 430);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		DefaultTableModel tableModel = new DefaultTableModel(null, columnNames) {

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		ArrayList<Usuario> userList = gOP.getAllUsers();
		ArrayList<Boolean> banned = gOP.getBannedFromList(userList);
		int i = 0;
		for (Usuario u : userList) {
			Vector<String> row = new Vector<String>();
			row.add(u.getCorreo());
			if (u.getClass().equals(Propietario.class))
				row.add("Sí");
			else
				row.add("No");
			row.add(u.getNombre());
			row.add(u.getApellido());
			if (banned.get(i)) {
				row.add("Sí");
			} else
				row.add("No");
			tableModel.addRow(row);
			i++;
		}

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		table = new JTable();

		table.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane);
		scrollPane.setBounds(10, 11, 686, 338);
		scrollPane.setViewportBorder(null);
		scrollPane.setViewportView(table);
		// tableModel = new DefaultTableModel(null, columnNames);
		table.setModel(tableModel);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					gOP.superAdminDelete(adminpass, userList.get(table.getSelectedRow()));
					TwitterUser usr = gOP.getTwitterUser(userList.get(table.getSelectedRow()).getCorreo());
					if (usr != null) gOP.superAdminDelete(adminpass, usr);
					dispose();
				} else
					showError();
			}
		});
		btnEliminar.setBounds(196, 360, 89, 23);
		contentPanel.add(btnEliminar);

		JButton btnBannear = new JButton("Bannear");
		btnBannear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					if (!banned.get(table.getSelectedRow())) {
						gOP.banUser(adminpass, userList.get(table.getSelectedRow()));
						dispose();
					} else
						JOptionPane.showMessageDialog(null, "Ese usuario ya está banneado.", "Error",
								JOptionPane.WARNING_MESSAGE);
				} else
					showError();
			}
		});
		btnBannear.setBounds(295, 360, 89, 23);
		contentPanel.add(btnBannear);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(607, 360, 89, 23);
		contentPanel.add(btnSalir);

		JButton btnVerInformacinDetallada = new JButton("Ver informaci\u00F3n detallada");
		btnVerInformacinDetallada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					AdminUserViewGUI.main(null, userList.get(table.getSelectedRow()));
				} else
					showError();
			}
		});
		btnVerInformacinDetallada.setBounds(10, 360, 153, 23);
		contentPanel.add(btnVerInformacinDetallada);

		JButton button = new JButton("Desbannear");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					if (banned.get(table.getSelectedRow())) {
						gOP.unbanUser(adminpass, userList.get(table.getSelectedRow()));
						dispose();
					} else
						JOptionPane.showMessageDialog(null, "Ese usuario no está banneado.", "Error",
								JOptionPane.WARNING_MESSAGE);
				} else
					showError();
			}
		});
		button.setBounds(394, 360, 104, 23);
		contentPanel.add(button);
		scrollPane.repaint();
		scrollPane.revalidate();
	}

	private static void showError() {
		JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún usuario.", "Error",
				JOptionPane.WARNING_MESSAGE);
	}
}
