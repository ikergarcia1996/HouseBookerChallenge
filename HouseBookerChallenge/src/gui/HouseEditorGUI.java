package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.RuralHouse;
import domain.Usuario;
import utilities.ImageFilter;
import utilities.ImageUtils;

import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class HouseEditorGUI extends JDialog {

	private final JPanel	contentPanel	= new JPanel();
	private JTextField		ciudad;
	private JTextField		dir;
	private JFileChooser fc;
	private ArrayList<String> imgtemp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Usuario user, boolean editmode, RuralHouse casa, GUIOperator operator) {
		try {
			HouseEditorGUI dialog = new HouseEditorGUI(user, editmode, casa,operator);
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
	public HouseEditorGUI(Usuario user, boolean editmode, RuralHouse casa, GUIOperator operator) {
		setModal(true);
		setResizable(false);
		if (editmode){
			setTitle("Editar casa");
			imgtemp = casa.getImagenes();
		} else {
			setTitle("Crear casa");
			imgtemp = new ArrayList<String>();
		}

		setBounds(100, 100, 780, 640);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		SwingUtilities.updateComponentTreeUI(getContentPane());

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 565, 744, 13);
		contentPanel.add(separator);

		JLabel lblCiudad = new JLabel("Ciudad:");
		lblCiudad.setBounds(26, 28, 46, 14);
		contentPanel.add(lblCiudad);

		ciudad = new JTextField();
		ciudad.setBounds(36, 53, 279, 20);
		contentPanel.add(ciudad);
		ciudad.setColumns(10);

		JLabel lblDescripcin = new JLabel("Direcci\u00F3n:");
		lblDescripcin.setBounds(26, 105, 140, 14);
		contentPanel.add(lblDescripcin);

		dir = new JTextField();
		dir.setBounds(36, 130, 471, 20);
		contentPanel.add(dir);
		dir.setColumns(10);

		JLabel lblDescripcin_1 = new JLabel("Descripci\u00F3n:");
		lblDescripcin_1.setBounds(26, 184, 150, 14);
		contentPanel.add(lblDescripcin_1);
		
		fc = new JFileChooser();

		JEditorPane desc = new JEditorPane();
		desc.setBorder(new LineBorder(Color.LIGHT_GRAY));
		desc.setBounds(36, 209, 679, 253);
		contentPanel.add(desc);
		
				JButton btnSeleccionarImagenDe = new JButton("A�adir una imagen");
				contentPanel.add(btnSeleccionarImagenDe);
				btnSeleccionarImagenDe.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						fc.addChoosableFileFilter(new ImageFilter());
						int returnVal = fc.showOpenDialog(HouseEditorGUI.this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File img = fc.getSelectedFile();
							BufferedImage image = null;
								try {
									image = ImageIO.read(img);
								}
								catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								image = ImageUtils.resize(image, 1080, 1080);
								String filestring = img.getPath();
								String filetype=filestring.substring(filestring.lastIndexOf('.')+1, filestring.length());
								imgtemp.add(ImageUtils.encodeToString(image,filetype));
								JOptionPane.showMessageDialog(null,
										"�Imagen a�adida!", "Operaci�n completada correctamente", JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				
				btnSeleccionarImagenDe.setBounds(36, 473, 262, 50);
				
				JButton button = new JButton("Eliminar todas las im�genes");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						imgtemp.clear();
						JOptionPane.showMessageDialog(null,
								"�Se han borrado todas las im�genes!", "Operaci�n completada correctamente", JOptionPane.WARNING_MESSAGE);
					}
				});
				button.setBounds(453, 473, 262, 50);
				contentPanel.add(button);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Guardar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//A�adir comprobacion de errores. Direccion vacia,descripcion muy corta o vacia, ciudad vacia etc.
						if (editmode) {
							operator.updateHouse(casa, new RuralHouse(casa.getHouseNumber(), desc.getText(), ciudad.getText(), dir.getText(), user, imgtemp));
							dispose();
						}
						else {
							operator.createHouse(desc.getText(), ciudad.getText(), dir.getText(), user, imgtemp);
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		if (editmode) {
			ciudad.setText(casa.getCity());
			dir.setText(casa.getDireccion());
			desc.setText(casa.getDescription());
		}
	}
}
