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
					"Est�s utilizando esta plataforma como usuario invitado. Para poder utilizar esta caracter�stica debes crear una cuenta.",
					"Sesi�n como invitado", JOptionPane.WARNING_MESSAGE);

		} else {

			if (businessLG.reservar(offer, user) == -1) {
				JOptionPane.showMessageDialog(frame, "Error al realizar la reserva", "Error",
						JOptionPane.WARNING_MESSAGE);
			} else {
				// Enviar mensaje al due�o
				DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
				Date date = new Date();
				sendMessageTo(offer.getRuralHouse().getOwner().getCorreo(), "notify@hbc.com",
						"Alguien ha reservado tu casa!", dateFormat.format(date),
						"En hora buena!\nEl usuario " + user.getUserName()
								+ " ha reservado una de tus ofertas. Accede a la secci�n de 'Ofertas reservadas' y estate al corriente de todo lo que sucede. Puedes ponerte en contacto con el usuario dirigi�ndote a �l mediante la direcci�n el correo "
								+ user.getCorreo() + " o mediante su n�mero de tel�fono " + user.getTlf()
								+ " .\n \n \n Por favor, no responda a este mensaje ya que ha sido generado autom�ticamente y nadie leer� su respuesta. ");

				JOptionPane.showMessageDialog(frame, "�Reserva realizada, disfruta de tus vacaciones!",
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

	public BufferedImage decodeToImage(String imageString) {
		return imgUtil.decodeToImage(imageString);

	}

	public BufferedImage resize(BufferedImage img, int newW, int newH) {
		return imgUtil.resize(img, newW, newH);
	}

	public ImageIcon resizeIcon(ImageIcon icon, int newW, int newH) {
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newimg);
		return newIcon;
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

}
