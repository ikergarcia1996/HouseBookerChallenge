package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import domain.Mensaje;
import domain.Usuario;
import utilities.ImageFilter;
import utilities.ImageUtils;
import utilities.ProfileImg;

import java.awt.Color;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserGUI extends JDialog {

	public static void main(String[] args, Usuario user, GUIOperator operator) {
		try {
			Usuario actualuser = operator.getUser(user.getCorreo());
			UserGUI dialog = new UserGUI(actualuser, operator);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param user
	 */
	public UserGUI(Usuario user, GUIOperator operator) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				operator.updateUser(user);
			}
		});

		setResizable(false);
		setModal(true);
		setBounds(100, 100, 505, 448);
		getContentPane().setLayout(null);

		JLabel lblFoto = new JLabel("Foto");
		lblFoto.setBorder(new LineBorder(new Color(128, 128, 128), 3, true));
		lblFoto.setBounds(10, 11, 80, 80);
		ImageIcon Fperfil = new ImageIcon(utilities.ImageUtils.decodeToImage(user.getProfileImg().getProfileImg()));
		lblFoto.setIcon(Fperfil);
		getContentPane().add(lblFoto);

		JLabel lblNombreDeUsuario = new JLabel(user.getUserName());
		lblNombreDeUsuario.setBounds(100, 11, 190, 14);
		getContentPane().add(lblNombreDeUsuario);

		JFileChooser fc = new JFileChooser();
		JButton btnCambiar = new JButton("Cambiar");
		btnCambiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc.setFileFilter(new ImageFilter());
				int returnVal = fc.showOpenDialog(UserGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File img = fc.getSelectedFile();
					BufferedImage image = null;
					try {
						image = ImageIO.read(img);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					image = ImageUtils.resize(image, 80, 80);
					String filestring = img.getPath();
					String filetype = filestring.substring(filestring.lastIndexOf('.') + 1, filestring.length());
					ProfileImg perfil = new ProfileImg(null);
					perfil.setImage(ImageUtils.encodeToString(image, filetype));
					lblFoto.setIcon(new ImageIcon(image));
					user.setProfileImg(perfil);
					operator.updateUser(user);

				}

			}
		});
		btnCambiar.setBounds(100, 40, 80, 23);
		getContentPane().add(btnCambiar);

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProfileImg perfil = new ProfileImg(null);
				lblFoto.setIcon(new ImageIcon(ImageUtils.decodeToImage(perfil.getProfileImg())));
				user.setProfileImg(perfil);
				operator.updateUser(user);
			}
		});
		btnBorrar.setBounds(100, 68, 80, 23);
		getContentPane().add(btnBorrar);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 102, 479, 1);
		getContentPane().add(separator);

		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(10, 139, 479, 200);
		getContentPane().add(scrollPane);
		scrollPane.setViewportBorder(null);
		getContentPane().add(scrollPane);

		String[] columnNames = { "Remitente", "Asunto", "Detalles" };
		
		int nmensajes = 0;

		ArrayList<Object[]> Data = new ArrayList<Object[]>();
		Stack<Mensaje> mensajes = user.getMensajes();
		for (Mensaje msg : mensajes) {
			Object[] row = new Object[3];
			if (msg.isUnread())
			{
				row[0] = msg.getRemite()+"  " ;
			}
			else
				row[0] = msg.getRemite();
			
			if (msg.isUnread())
			{
				row[1] = msg.getAsunto()+"  " ;
			}
			else
				row[1] = msg.getAsunto();
			
			if (msg.isUnread())
			{
				row[2] = msg.getDetalles()+"  " ;
			}
			else
				row[2] = msg.getDetalles();
			Data.add(row);
			nmensajes++;

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
				if (value.toString().contains("  ")) {
					comp.setBackground(new Color(135,206,250));

				} else {
					comp.setBackground(Color.white);
				}
				return comp;
			}
			public boolean isCellEditable(int row, int column) {
		        return false;
			}
		};
		
		JLabel lblNotificacionesYMensajes = new JLabel("Notificaciones y mensajes");
		lblNotificacionesYMensajes.setBounds(10, 114, 123, 14);
		getContentPane().add(lblNotificacionesYMensajes);
		
		scrollPane.setViewportView(table);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Stack<Mensaje> mensajes = user.getMensajes();
				ViewMsjGUI.main(null, user, mensajes.get(table.getSelectedRow()), mensajes.get(table.getSelectedRow()),
						operator);
			}
		});

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operator.updateUser(user);
				dispose();
			}
		});
		btnCerrar.setBounds(400, 350, 89, 23);
		getContentPane().add(btnCerrar);

		JButton btnRedactarMensaje = new JButton("Redactar mensaje");
		btnRedactarMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operator.updateUser(user);
				RedactarMsgGUI.main(null, user, null, null, null, operator);
				dispose();
				UserGUI.main(null, user, operator);
			}
		});
		btnRedactarMensaje.setBounds(366, 110, 123, 23);
		getContentPane().add(btnRedactarMensaje);

		JButton btnModificarInformacion = new JButton("Modificar informacion");
		btnModificarInformacion.setEnabled(false);
		btnModificarInformacion.setBounds(300, 7, 138, 23);
		getContentPane().add(btnModificarInformacion);
	}

	public class MiRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			/*
			 * if ((String)) { this.setOpaque(true);
			 * this.setBackground(Color.RED); this.setForeground(Color.YELLOW);
			 * } else { // Restaurar los valores por defecto }
			 */
			return this;
		}
	}
}
