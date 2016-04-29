package gui.paneles;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainNavigationC extends JPanel {

	/**
	 * Create the panel.
	 */
	public MainNavigationC() {
		setLayout(null);

		JLabel lblQuiero = new JLabel("Quiero...");
		lblQuiero.setBounds(10, 11, 44, 14);
		add(lblQuiero);

		JButton btnNewButton = new JButton("Buscar una oferta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(10, 36, 240, 82);
		add(btnNewButton);

	}
}
