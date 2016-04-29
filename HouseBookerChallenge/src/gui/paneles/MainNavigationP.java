package gui.paneles;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainNavigationP extends JPanel {

	/**
	 * Create the panel.
	 */
	public MainNavigationP() {
		setLayout(null);

		JLabel lblQuiero = new JLabel("Quiero...");
		lblQuiero.setBounds(10, 11, 44, 14);
		add(lblQuiero);

		JButton btnNewButton = new JButton("Registrar una casa");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(10, 36, 240, 82);
		add(btnNewButton);

		JButton btnRealizarUnaOferta = new JButton("Realizar una oferta");
		btnRealizarUnaOferta.setBounds(10, 129, 240, 82);
		add(btnRealizarUnaOferta);

	}
}
