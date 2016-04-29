package testing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDayChooser;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.util.spi.CalendarDataProvider;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JCalendar;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class Calendar extends JFrame {

	private JPanel		contentPane;
	private JTextField	txtDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calendar frame = new Calendar();
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
	public Calendar() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 215, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SwingUtilities.updateComponentTreeUI(getContentPane());

		txtDate = new JTextField();
		txtDate.setEditable(false);
		txtDate.setText("Date");
		txtDate.setBounds(10, 202, 189, 80);
		contentPane.add(txtDate);
		txtDate.setColumns(10);

		JCalendar calendar = new JCalendar();
		calendar.setBounds(10, 11, 190, 146);
		contentPane.add(calendar);

		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//txtDate.setText(String.valueOf(calendar.getDayChooser().getDay()) + " de "
				//		+ String.valueOf(calendar.getMonthChooser().getMonth()) + " del año "
				//		+ String.valueOf(calendar.getYearChooser().getYear()));
				txtDate.setText(calendar.getCalendar().getTime().toLocaleString());
			}
		});
		btnPrint.setBounds(110, 168, 89, 23);
		contentPane.add(btnPrint);
		
	}
}
