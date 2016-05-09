package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import businessLogic.FacadeImplementationWS;
import gui.GUIOperator;
import gui.RegistroGUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ElegirTipoGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 * 
	 * @param operator
	 */
	public static void main(String[] args, GUIOperator operator) {
		ElegirTipoGUI dialog = new ElegirTipoGUI(operator);

		dialog.setVisible(true);
	}

	/**
	 * Create the dialog.
	 * 
	 * @param operator
	 */
	public ElegirTipoGUI(GUIOperator operator) {
		setTitle("Elegir tipo de usuario");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 335, 118);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		SwingUtilities.updateComponentTreeUI(getContentPane());

		{
			JButton btnCliente = new JButton("Cliente");
			btnCliente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					RegistroGUI.main(null, false, operator);
				}
			});
			btnCliente.setBounds(220, 11, 89, 23);
			contentPanel.add(btnCliente);
		}
		{
			JButton btnPropietario = new JButton("Propietario");
			btnPropietario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					RegistroGUI.main(null, true, operator);
				}
			});
			btnPropietario.setBounds(220, 45, 89, 23);
			contentPanel.add(btnPropietario);
		}
		{
			JLabel lblElegirTipoDe = new JLabel("Elegir tipo de usuario:");
			lblElegirTipoDe.setBounds(10, 15, 157, 14);
			contentPanel.add(lblElegirTipoDe);
		}
		{
			JButton btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					// MainGUI.main(null);
				}
			});
			btnCancelar.setBounds(10, 45, 89, 23);
			contentPanel.add(btnCancelar);
		}
	}

}
