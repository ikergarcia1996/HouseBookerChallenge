package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import domain.Mensaje;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class AdminGUI extends JDialog {

	private JPanel contentPane;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, GUIOperator gOP, String adminpass) {

		try {
			AdminGUI frame = new AdminGUI(gOP, adminpass);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public AdminGUI(GUIOperator gOP, String adminpass) {
		setModal(true);
		setResizable(false);
		setTitle("Modo Administrador");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 580, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnAdministrarUsuarios = new JButton("Administrar usuarios");
		btnAdministrarUsuarios.setBorder(UIManager.getBorder("CheckBox.border"));
		btnAdministrarUsuarios.setContentAreaFilled(false);
		btnAdministrarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdminUserManagerGUI.main(null, gOP, adminpass);
			}
		});
		btnAdministrarUsuarios.setBounds(10, 60, 194, 23);
		contentPane.add(btnAdministrarUsuarios);

		JButton btnEliminarUnaCasa = new JButton("Eliminar una casa");
		btnEliminarUnaCasa.setBorder(UIManager.getBorder("CheckBox.border"));
		btnEliminarUnaCasa.setContentAreaFilled(false);
		btnEliminarUnaCasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminHouseDeleteGUI.main(null, gOP, adminpass);
			}
		});
		btnEliminarUnaCasa.setBounds(10, 99, 194, 23);
		contentPane.add(btnEliminarUnaCasa);

		JButton btnEliminarUnaOferta = new JButton("Eliminar una oferta");
		btnEliminarUnaOferta.setBorder(UIManager.getBorder("CheckBox.border"));
		btnEliminarUnaOferta.setContentAreaFilled(false);
		btnEliminarUnaOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminOfferDeleteGUI.main(null, gOP, adminpass);
			}
		});
		btnEliminarUnaOferta.setBounds(10, 138, 194, 23);
		contentPane.add(btnEliminarUnaOferta);

		JButton btnSalirDelModo = new JButton("Salir del modo administrador");
		btnSalirDelModo.setBorder(UIManager.getBorder("CheckBox.border"));
		btnSalirDelModo.setContentAreaFilled(false);
		btnSalirDelModo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalirDelModo.setBounds(15, 382, 167, 23);
		contentPane.add(btnSalirDelModo);

		passwordField = new JPasswordField();
		passwordField.setBounds(321, 383, 178, 20);
		passwordField.setVisible(false);
		contentPane.add(passwordField);

		JButton btnOk = new JButton("OK");
		btnOk.setBorder(UIManager.getBorder("CheckBox.border"));
		btnOk.setContentAreaFilled(false);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gOP.superAdminChangePass(adminpass, passwordField.getText())) {
					JOptionPane.showMessageDialog(null, "Por motivos de seguridad, por favor, vuelva a iniciar sesión.",
							"Contraseña modificada", JOptionPane.WARNING_MESSAGE);
					AdminLoginGUI.main(null, gOP);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Acceso no autorizado. Credenciales incorrectas.", "Error",
							JOptionPane.WARNING_MESSAGE);
					dispose();
				}
			}
		});
		btnOk.setBounds(511, 382, 48, 23);
		btnOk.setVisible(false);
		contentPane.add(btnOk);

		JButton btnCambiarContrasea = new JButton("Cambiar contrase\u00F1a");
		btnCambiarContrasea.setBorder(UIManager.getBorder("CheckBox.border"));
		btnCambiarContrasea.setContentAreaFilled(false);
		btnCambiarContrasea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnCambiarContrasea.setVisible(false);
				passwordField.setVisible(true);
				btnOk.setVisible(true);
			}
		});
		btnCambiarContrasea.setBounds(419, 382, 140, 23);
		contentPane.add(btnCambiarContrasea);

		JLabel lblTareasAdministrativas = new JLabel("Tareas administrativas");
		lblTareasAdministrativas.setBounds(10, 16, 194, 14);
		contentPane.add(lblTareasAdministrativas);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(219, 16, 2, 389);
		contentPane.add(separator);

		JLabel lblMensajes = new JLabel("Mensajes");
		lblMensajes.setBounds(236, 16, 269, 14);
		contentPane.add(lblMensajes);

		

		String[] columnNames = { "Remitente", "Asunto", "Detalles" };

		ArrayList<Object[]> Data = new ArrayList<Object[]>();
		Stack<Mensaje> mensajes = gOP.getAdminMenssages();
		if (!mensajes.isEmpty())
			for (Mensaje msg : mensajes) {
				Object[] row = new Object[3];
				if (msg.isUnread()) {
					row[0] = msg.getRemite() + " *";
				} else
					row[0] = msg.getRemite();

				if (msg.isUnread()) {
					row[1] = msg.getAsunto() + " *";
				} else
					row[1] = msg.getAsunto();

				if (msg.isUnread()) {
					row[2] = msg.getDetalles() + " *";
				} else
					row[2] = msg.getDetalles();
				Data.add(row);

			}
		int index2 = 0;
		Object[][] tabledata = new Object[Data.size()][3];
		for (Object[] row : Data) {
			tabledata[index2] = row;
			index2++;
		}

		JTable table = new JTable(tabledata, columnNames) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				Object value = getModel().getValueAt(row, col);
				if (value.toString().contains(" *")) { //¡No son dos espacios!
					comp.setBackground(new Color(135, 206, 250));

				} else {
					comp.setBackground(Color.white);
				}
				return comp;
			}
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				ViewMsjGUI.main(null, null, mensajes.get(table.getSelectedRow()), mensajes.get(table.getSelectedRow()),
						gOP);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(236, 46, 323, 321);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
	}
	public class NonEditableModel extends DefaultTableModel {

	    NonEditableModel(Object[][] data, String[] columnNames) {
	        super(data, columnNames);
	    }

	    @Override
	    public boolean isCellEditable(int row, int column) {
	        return false;
	    }
	}
}

