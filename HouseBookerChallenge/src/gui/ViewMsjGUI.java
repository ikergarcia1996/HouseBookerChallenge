package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Mensaje;
import domain.Usuario;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewMsjGUI extends JDialog {

	private JPanel contentPane;
	private JTextField textRemite;
	private JTextField textAsunto;
	private JTextField txtFecha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, Usuario user, Mensaje oldmsg, Mensaje mensaje, GUIOperator operator) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewMsjGUI frame = new ViewMsjGUI(user, oldmsg, mensaje, operator);
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
	 * @param operator 
	 * @param mensaje 
	 * @param user 
	 * @param mensajes 
	 */
	public ViewMsjGUI(Usuario user, Mensaje oldmsg, Mensaje mensaje, GUIOperator operator) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				operator.updateMsg(oldmsg, mensaje);
			}
		});
		mensaje.setReaden();
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 594, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRemitente = new JLabel("Remitente:");
		lblRemitente.setBounds(10, 14, 80, 14);
		contentPane.add(lblRemitente);
		
		JLabel label_1 = new JLabel("Asunto:");
		label_1.setBounds(10, 39, 80, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Mensaje:");
		label_2.setBounds(10, 64, 80, 14);
		contentPane.add(label_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 300, 400, 2);
		contentPane.add(separator);
		
		textRemite = new JTextField();
		textRemite.setEditable(false);
		textRemite.setColumns(10);
		textRemite.setBounds(100, 11, 310, 20);
		textRemite.setText(mensaje.getRemite());
		contentPane.add(textRemite);
		
		textAsunto = new JTextField();
		textAsunto.setEditable(false);
		textAsunto.setColumns(10);
		textAsunto.setBounds(100, 36, 310, 20);
		textAsunto.setText(mensaje.getAsunto());
		contentPane.add(textAsunto);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operator.updateMsg(oldmsg, mensaje);
				dispose();
			}
		});
		btnCerrar.setBounds(321, 313, 89, 23);
		contentPane.add(btnCerrar);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(420, 11, 2, 291);
		contentPane.add(separator_1);
		
		JLabel lblAcciones = new JLabel("Acciones:");
		lblAcciones.setBounds(432, 11, 122, 14);
		contentPane.add(lblAcciones);
		
		JButton btnMantenerComoNo = new JButton("Mantener como no leido");
		btnMantenerComoNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mensaje.setUnReaden();
			}
		});
		btnMantenerComoNo.setBounds(432, 35, 147, 23);
		contentPane.add(btnMantenerComoNo);
		
		JButton btnResponder = new JButton("Responder");
		btnResponder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RedactarMsgGUI.main(null, user ,mensaje.getRemite(),"Re: "+ mensaje.getAsunto(),"En respuesta a: "+ mensaje.getContenido(), operator);
			}
		});
		btnResponder.setBounds(432, 70, 147, 23);
		contentPane.add(btnResponder);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operator.updateMsg(oldmsg, mensaje);
				dispose();
			}
		});
		btnEliminar.setBounds(432, 120, 147, 23);
		//contentPane.add(btnEliminar);
		
		JButton btnExportarAArchivo = new JButton("Exportar a archivo");
		btnExportarAArchivo.setEnabled(false);
		btnExportarAArchivo.setBounds(432, 266, 147, 23);
		contentPane.add(btnExportarAArchivo);
		
		JButton btnDenunciarAAdministrador = new JButton("Denunciar");
		btnDenunciarAAdministrador.setEnabled(false);
		btnDenunciarAAdministrador.setBounds(432, 232, 147, 23);
		contentPane.add(btnDenunciarAAdministrador);
		
		JTextArea txtMensaje = new JTextArea();
		txtMensaje.setLineWrap(true);
		txtMensaje.setWrapStyleWord(true);
		txtMensaje.setText(mensaje.getContenido());
		txtMensaje.setEditable(false);
		
		JScrollPane scroll = new JScrollPane (txtMensaje, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scroll.setAutoscrolls(false);
		scroll.setBounds(10, 89, 400, 175);
		contentPane.add(scroll);
		
		JLabel lblFechaDeEnvio = new JLabel("Fecha de envio:");
		lblFechaDeEnvio.setBounds(10, 275, 80, 14);
		contentPane.add(lblFechaDeEnvio);
		
		txtFecha = new JTextField();
		txtFecha.setEditable(false);
		txtFecha.setBounds(100, 272, 310, 20);
		contentPane.add(txtFecha);
		txtFecha.setText(mensaje.getDetalles());
		txtFecha.setColumns(10);
	}
}
