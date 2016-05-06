package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.Cliente;
import domain.Offer;
import domain.Propietario;
import domain.Usuario;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window.Type;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDayChooser;

public class VerMisReservasGUI extends JDialog {

	private JPanel contentPane;
	private JTable				table;
	DefaultTableModel tableModel;
	private String[]			columnNames	= new String[] { "Ciudad", "Fecha Incio","Fecha Fin", "Precio" };


	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Usuario user, GUIOperator operator) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerMisReservasGUI frame = new VerMisReservasGUI((Cliente) user, operator);
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VerMisReservasGUI(Cliente user, GUIOperator operator) {
		setModal(true);
		setType(Type.POPUP);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 736, 704);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Ver mis reservas");
		setResizable(false);
		table = new JTable();
		tableModel = new DefaultTableModel(null, columnNames) {

		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 92, 700, 245);
		contentPane.add(scrollPane);
		scrollPane.setViewportBorder(null);
		scrollPane.setViewportView(table);
		table.setModel(tableModel);
		scrollPane.repaint();
		scrollPane.revalidate();
		
		JButton btnNewButton = new JButton("M\u00E1s informaci\u00F3n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (user.getOfertasReservadas()!=null)
				BookOfferGUI.main(user.getOfertasReservadas().get(table.getSelectedRow()), user, operator, true, false);
			}
		});
		btnNewButton.setBounds(10, 611, 187, 32);
		contentPane.add(btnNewButton);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(523, 611, 187, 32);
		contentPane.add(btnSalir);
		
		JLabel lblVerMisOfertas = new JLabel("Ver mis reservas");
		lblVerMisOfertas.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblVerMisOfertas.setBounds(262, 11, 172, 42);
		contentPane.add(lblVerMisOfertas);
		
		JLabel lblAquPodrsVer = new JLabel("Aqu\u00ED podr\u00E1s ver las reservas que has realizado, puedes ampliar la informaci\u00F3n o ponerte en conctacto con el due\u00F1o clicando en \"M\u00E1s informaci\u00F3n\".");
		lblAquPodrsVer.setBounds(10, 56, 734, 14);
		contentPane.add(lblAquPodrsVer);
		
		
		
		
		table.setModel(tableModel);
		if (user.getOfertasReservadas()!=null){
		for (Offer of: user.getOfertasReservadas()) {
			Vector<String> row = new Vector<String>();
			row.add(of.getRuralHouse().getCity());
			row.add(of.getFirstDay().toString());
			row.add(of.getLastDay().toString());
			row.add(Float.toString(of.getPrice()));
			tableModel.addRow(row);
			System.out.println("Reserva: " + of.toString());
		

		}
		scrollPane.repaint();
		scrollPane.revalidate();
		contentPane.repaint();
		}
		}
	}

