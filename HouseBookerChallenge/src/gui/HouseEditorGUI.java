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
import javax.swing.JScrollPane;
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

import CloudUtilities.FileManager;

import java.awt.Color;
import javax.swing.JScrollPane;

public class HouseEditorGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField ciudad;
	private JTextField dir;
	private JFileChooser fc;
	private ArrayList<String> imgtemp;
	public int result = -2;
	public String Image;
	public static HouseEditorGUI dialog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Usuario user, boolean editmode, RuralHouse casa, GUIOperator operator) {
		try {
			dialog = new HouseEditorGUI(user, editmode, casa, operator, dialog);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param dialog2
	 */
	public HouseEditorGUI(Usuario user, boolean editmode, RuralHouse casa, GUIOperator operator,
			HouseEditorGUI dialog2) {
		setModal(true);
		setResizable(false);
		if (editmode) {
			setTitle("Editar casa");
			imgtemp = casa.getImagenes();
		} else {
			setTitle("Crear casa");
			imgtemp = new ArrayList<String>();
		}

		setBounds(100, 100, 550, 580);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SwingUtilities.updateComponentTreeUI(getContentPane());

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 509, 527, 2);
		contentPanel.add(separator);

		JLabel lblCiudad = new JLabel("Ciudad:");
		lblCiudad.setBounds(10, 11, 46, 14);
		contentPanel.add(lblCiudad);

		ciudad = new JTextField();
		ciudad.setBounds(66, 8, 220, 20);
		contentPanel.add(ciudad);
		ciudad.setColumns(10);

		JLabel lblDescripcin = new JLabel("Direcci\u00F3n:");
		lblDescripcin.setBounds(10, 36, 54, 14);
		contentPanel.add(lblDescripcin);

		dir = new JTextField();
		dir.setBounds(66, 33, 471, 20);
		contentPanel.add(dir);
		dir.setColumns(10);

		JLabel lblDescripcin_1 = new JLabel("Descripci\u00F3n:");
		lblDescripcin_1.setBounds(10, 61, 150, 14);
		contentPanel.add(lblDescripcin_1);

		fc = new JFileChooser();

		JEditorPane desc = new JEditorPane();
		desc.setBorder(new LineBorder(Color.LIGHT_GRAY));
		desc.setBounds(10, 86, 527, 253);
		contentPanel.add(desc);

		JButton btnSeleccionarImagenDe = new JButton("A\u00F1adir una imagen desde archivo");
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
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					image = ImageUtils.resize(image, 1280, 720);
					String filestring = img.getPath();
					String filetype = filestring.substring(filestring.lastIndexOf('.') + 1, filestring.length());
					imgtemp.add(ImageUtils.encodeToString(image, filetype));
					JOptionPane.showMessageDialog(null, "¡Imagen añadida!", "Operación completada correctamente",
							JOptionPane.INFORMATION_MESSAGE);

				}
			}
		});

		btnSeleccionarImagenDe.setBounds(10, 350, 191, 23);

		JButton button = new JButton("Eliminar todas las imágenes");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				imgtemp.clear();
				JOptionPane.showMessageDialog(null, "¡Se han borrado todas las imágenes!",
						"Operación completada correctamente", JOptionPane.INFORMATION_MESSAGE);
			}

		});
		button.setBounds(374, 350, 163, 23);
		contentPanel.add(button);

		JButton btnCargarImagenDesde = new JButton("Cargar imagen desde URL");
		btnCargarImagenDesde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui.URLLoaderGUI.main(null, dialog);
				if (result == 0) {
					File imagen = new File(Image);
					BufferedImage urlimage = null;
					try {
						urlimage = ImageIO.read(imagen);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					urlimage = ImageUtils.resize(urlimage, 1280, 720);
					imgtemp.add(ImageUtils.encodeToString(urlimage,
							Image.substring(Image.lastIndexOf(".") + 1, Image.length())));
					JOptionPane.showMessageDialog(null, "¡Imagen añadida!", "Operación completada correctamente",
							JOptionPane.INFORMATION_MESSAGE);

				} else
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error al añadir la imagen.", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		JScrollPane panelImagenes = new JScrollPane();
		panelImagenes.setBounds(10, 418, 527, 86);
		panelImagenes.setLayout(null);
		if (editmode) {
			ArrayList<String> listacasas = casa.getImagenes();
			int cantidad = listacasas.size();
			for (int i = 0; i < cantidad; i++) {
				JLabel label = new JLabel();
				label.setBounds(3 + 83 * i, 3, 80, 80);
				label.setIcon(new ImageIcon(
						utilities.ImageUtils.resize(utilities.ImageUtils.decodeToImage(listacasas.get(i)), 80, 80)));
				panelImagenes.add(label);
			}
		}
		contentPanel.add(panelImagenes);
		JButton cancelButton_1 = new JButton("Cancelar");
		cancelButton_1.setBounds(381, 522, 75, 23);
		contentPanel.add(cancelButton_1);
		cancelButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// FileManager fm=new FileManager();
				// fm.removeFile(Image);

			}
		});
		{
			JButton okButton = new JButton("Guardar");
			okButton.setBounds(466, 522, 71, 23);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Añadir comprobacion de errores. Direccion
					// vacia,descripcion muy corta o vacia, ciudad vacia etc.
					if (editmode) {
						operator.updateHouse(casa, new RuralHouse(casa.getHouseNumber(), desc.getText(),
								ciudad.getText(), dir.getText(), user, imgtemp));
						dispose();
					} else {
						operator.createHouse(desc.getText(), ciudad.getText(), dir.getText(), user, imgtemp);
						dispose();
					}
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			btnCargarImagenDesde.setBounds(10, 379, 191, 23);
			contentPanel.add(btnCargarImagenDesde);
			{

				contentPanel.add(panelImagenes);

				{
					JButton okButton = new JButton("Guardar");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// Añadir comprobacion de errores. Direccion
							// vacia,descripcion muy corta o vacia, ciudad vacia
							// etc.
							if (editmode) {
								operator.updateHouse(casa, new RuralHouse(casa.getHouseNumber(), desc.getText(),
										ciudad.getText(), dir.getText(), user, imgtemp));

								dispose();
							} else {
								operator.createHouse(desc.getText(), ciudad.getText(), dir.getText(), user, imgtemp);
								dispose();
							}
						}
					});
					okButton.setActionCommand("OK");
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
				}

				if (editmode) {
					ciudad.setText(casa.getCity());
					dir.setText(casa.getDireccion());
					desc.setText(casa.getDescription());
				}
			}
		}
	}
}
