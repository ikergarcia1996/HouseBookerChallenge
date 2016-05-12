
package gui;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import businessLogic.FacadeImplementationWS;
import businessLogic.FacadeImplementationWS.loginresult;
import domain.Mensaje;
import domain.Offer;
import domain.RuralHouse;
import domain.TwitterUser;
import domain.Usuario;
import utilities.ImageUtils;
import utilities.ProfileImg;

public class GUIOperator {

	public FacadeImplementationWS businessLG = new FacadeImplementationWS();
	public ImageUtils imgUtil = new ImageUtils();

	int Registrar(boolean tipo, String Nombre, String Apellidos, String Telefono, String Correo, String Contrasena,
			String DNI, String Calle, String Numero, String Piso, String Puerta, String Letra, String CP,
			String Poblacion, String Provincia, ProfileImg perfil) {

		String Direccion = Calle + ", " + Numero + ", " + Piso + ", " + Puerta + ", " + Letra + ", " + CP + ", "
				+ Poblacion + ", " + Provincia;

		return businessLG.createAccount(tipo, Nombre, Apellidos, Telefono, Correo, Contrasena, DNI, Direccion, perfil);
	}

	loginresult login(String Usuario, String pass) {
		return businessLG.login(Usuario, pass);

	}

	public ArrayList<RuralHouse> getOwnerHouses(Usuario user) {
		return (businessLG.getOwnerHouses(user));
	}

	public ArrayDeque<RuralHouse> getAllRuralHouses() {

		return (businessLG.getAllRuralHouses());
	}

	public ArrayDeque<Offer> getOffers(RuralHouse rhtemp, Date date, Date datefin) {
		return (businessLG.getOffers(rhtemp, date, datefin));
	}

	public ArrayDeque<RuralHouse> getRuralHousesCity(String city) {
		return (businessLG.getRuralHousesCity(city));
	}

	public RuralHouse getHouse(int v) {
		return (businessLG.getHouse(v));
	}

	public ArrayDeque<Offer> getAllOffers(RuralHouse rh) {
		return businessLG.getAllOffers(rh);
	}

	public int reservar(Offer offer, Usuario user) {
		Component frame = null;
		if (user.getUserName().equals("Invitado")) {
			JOptionPane.showMessageDialog(frame,
					"Estás utilizando esta plataforma como usuario invitado. Para poder utilizar esta característica debes crear una cuenta.",
					"Sesión como invitado", JOptionPane.WARNING_MESSAGE);

		} else {

			if (businessLG.reservar(offer, user) == -1) {
				JOptionPane.showMessageDialog(frame, "Error al realizar la reserva", "Error",
						JOptionPane.WARNING_MESSAGE);
			} else {
				// Enviar mensaje al dueño
				DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
				Date date = new Date();
				sendMessageTo(offer.getRuralHouse().getOwner().getCorreo(), "notify@hbc.com",
						"Alguien ha reservado tu casa!", dateFormat.format(date),
						"En hora buena!\nEl usuario " + user.getUserName()
								+ " ha reservado una de tus ofertas. Accede a la sección de 'Ofertas reservadas' y estate al corriente de todo lo que sucede. Puedes ponerte en contacto con el usuario dirigiéndote a él mediante la dirección el correo "
								+ user.getCorreo() + " o mediante su número de teléfono " + user.getTlf()
								+ " .\n \n \n Por favor, no responda a este mensaje ya que ha sido generado automáticamente y nadie leerá su respuesta. ");

				JOptionPane.showMessageDialog(frame, "¡Reserva realizada, disfruta de tus vacaciones!",
						"Todo ha salido bien", JOptionPane.WARNING_MESSAGE);

			}

		}
		return -1;
	}

	public void updateHouse(RuralHouse casa, RuralHouse ruralHouse) {
		businessLG.updateHouse(casa, ruralHouse);

	}

	public void createHouse(String desc, String ciudad, String dir, Usuario user, ArrayList<String> imagenes) {
		businessLG.createHouse(desc, ciudad, dir, user, imagenes);

	}

	public Usuario getUser(String correo) {
		return businessLG.getUser(correo);
	}

	public ArrayList<Usuario> getAllUsers() {
		return businessLG.getAllUsers();
	}

	public boolean superAdminLogin(String password) {
		return businessLG.superAdminLogin(password);
	}

	public boolean superAdminChangePass(String old, String newpass) {
		return businessLG.superAdminChangePswd(old, newpass);
	}

	public boolean superAdminDelete(String password, Object ToDelete) {
		return businessLG.superAdminDelete(password, ToDelete);
	}

	public int sendMessageTo(String destinatario, String remitente, String asunto, String detalles, String mensaje) {
		return businessLG.sendMessageTo(destinatario, remitente, asunto, detalles, mensaje);
	}

	public void updateUser(Usuario user) {
		// TODO Auto-generated method stub
		businessLG.updateUser(user);
	}

	public void logout(Usuario user) {
		user = null;
	}

	public ArrayList<Boolean> getBannedFromList(ArrayList<Usuario> userList) {
		// TODO Auto-generated method stub
		return businessLG.getBannedFromList(userList);
	}

	public void banUser(String adminpass, Usuario usuario) {
		// TODO Auto-generated method stub
		businessLG.banUser(adminpass, usuario);
	}

	public void unbanUser(String adminpass, Usuario usuario) {
		// TODO Auto-generated method stub
		businessLG.unbanUser(adminpass, usuario);
	}

	public void updateMsg(Mensaje oldmsg, Mensaje mensaje) {
		// TODO Auto-generated method stub
		businessLG.updateMsg(oldmsg, mensaje);
	}

	public Offer getOffer(Offer of) {
		return businessLG.getOffer(of);
	}

	public Stack<Mensaje> getAdminMenssages() {
		// TODO Auto-generated method stub
		return businessLG.getAdminMenssages();
	}

	public void sendMessageToAdmin(String remite, String asunto, String detalles, String contenido) {
		// TODO Auto-generated method stub
		businessLG.sendMessageToAdmin(remite, asunto, detalles, contenido);
		
	}

	public void resetAdmin() {
		businessLG.resetAdmin();
		
	}

	public Mensaje searchPublic(String uuid) {
		return businessLG.searchPublic(uuid);
		
	}

	public void sendPublicMesage(String destino, String asunto, String format, String contenido) {
		businessLG.sendPublicMesage(destino,asunto,format,contenido);
	}

	public boolean WillCheckForUpdates() {
		// TODO Auto-generated method stub
		return businessLG.WillCheckForUpdates();
	}



	public void anular(Offer offer, Usuario user) {
		java.util.Date fecha = new Date();
		System.out.println (fecha);
		
		if (fecha.compareTo(offer.getFirstDay())<10){
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Solo se pueden anular reservas con más de 10 días de antelación", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		
		else{
		
		Component frame=null;
		if (businessLG.AnularReserva(offer, user) == -1) {
			JOptionPane.showMessageDialog(frame, "Error al anula la reserva", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else {
			// Enviar mensaje al dueño
			DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
			Date date = new Date();
			sendMessageTo(offer.getRuralHouse().getOwner().getCorreo(), "notify@hbc.com",
					"Han anulado una reserva en tu casa", dateFormat.format(date),
					"El usuario " + user.getUserName()
							+ " ha anulado la reserva entre los días "+ offer.getFirstDay() + " " + offer.getLastDay() + " en tu casa de " + offer.getRuralHouse().getCity() + " .\n \n \n Por favor, no responda a este mensaje ya que ha sido generado automáticamente y nadie leerá su respuesta. ");

			JOptionPane.showMessageDialog(frame, "Reserva cancela, esperamos verte pronto de nuevo",
					"Todo ha salido bien", JOptionPane.WARNING_MESSAGE);

		}
		}
		
	}

	loginresult twitterlogin(long twitterID) {
		return businessLG.twitterlogin(twitterID);

	}
	
	int TwitterRegistrar(boolean tipo, String Nombre, String Apellidos, String Telefono, String Correo, String Contrasena,
			String DNI, String Calle, String Numero, String Piso, String Puerta, String Letra, String CP,
			String Poblacion, String Provincia, ProfileImg perfil, long ID) {

		String Direccion = Calle + ", " + Numero + ", " + Piso + ", " + Puerta + ", " + Letra + ", " + CP + ", "
				+ Poblacion + ", " + Provincia;

		return businessLG.TwittercreateAccount(tipo, Nombre, Apellidos, Telefono, Correo, Contrasena, DNI, Direccion, perfil, ID);
	}

	public TwitterUser getTwitterUser(String correo) {
		// TODO Auto-generated method stub
		return businessLG.getTwitterUser(correo);
	}

}

