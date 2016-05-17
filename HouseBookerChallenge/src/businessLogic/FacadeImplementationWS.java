package businessLogic;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.mail.MessagingException;

import com.db4o.ObjectSet;

import businessLogic.FacadeImplementationWS.loginresult;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Cliente;
import domain.Mensaje;
//import domain.Booking;
import domain.Offer;
import domain.RuralHouse;
import domain.TwitterUser;
import domain.Usuario;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;
import utilities.ProfileImg;

//Service Implementation
@WebService(endpointInterface = "businessLogic.ApplicationFacadeInterfaceWS")
public class FacadeImplementationWS implements ApplicationFacadeInterfaceWS {

	/**
	 * 
	 */

	// Vector<Owner> owners;
	// Vector<RuralHouse> ruralHouses;
	// DataAccessInterface dB4oManager;

	public FacadeImplementationWS() {

		System.out.println("Executing FacadeImplementationWS");
		main.Runer.splash.textArea.append("Executing FacadeImplementationWS");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize") && (c.isDatabaseLocal())) {
			main.Runer.splash.textArea.append("File deleted");
			System.out.println("File deleted");
			new File(c.getDb4oFilename()).delete();
		}
		DataAccess dB4oManager = new DataAccess();
		if (c.getDataBaseOpenMode().equals("initialize")) {
			dB4oManager.initializeDB();
		}
		else {// check if it is opened
				// Vector<Owner> owns=dataAccess.getOwners();
		}
		dB4oManager.close();

	}

	public int createAccount(boolean isPropietario, String nombre, String apellidos, String telefono, String correo,
			String password, String DNI, String DirPostal, ProfileImg perfil) {
		/*
		 * -1 -> Usuario ya existe 1 -> Error de datos //retirado para aumentar
		 * eficiencia. se hace en registroGUI
		 * 
		 * 0 -> Todo correcto
		 */
		DataAccess dB4oManager = new DataAccess();
		int result = 0;
		if (dB4oManager.isBannedEmail(correo)) {
			dB4oManager.close();
			return -2;
		}
		if (!dB4oManager.getUsers(correo).isEmpty())
			result = -1;
		else {
			if (result == 0) dB4oManager.createAccount(isPropietario, nombre, apellidos, telefono, correo, password,
					DNI, DirPostal, perfil);
		}
		dB4oManager.close();
		return result;
	}

	public loginresult login(String correo, String password) {
		/*
		 * Devuelve: 1 -> Cliente 2 -> Propietario
		 * 
		 * -1 -> Esa cuenta no existe 0 -> Datos introducidos incorrectos
		 */
		DataAccess dB4oManager = new DataAccess();
		Usuario user;

		ObjectSet<Usuario> owner = dB4oManager.getAuthOwners(correo, password);
		ObjectSet<Usuario> client = dB4oManager.getAuthClients(correo, password);
		if (dB4oManager.isBannedEmail(correo)){
			dB4oManager.close();
			return (new loginresult(-2, null));
		}
		
		if (!owner.isEmpty()) {
			user = owner.next();
			user.getUserName();
			dB4oManager.close();
			return (new loginresult(2, user));
		}

		else if (!client.isEmpty()) {
			user = client.next();
			dB4oManager.close();
			return (new loginresult(1, user));
		}
		else {
			ArrayDeque<Usuario> users = dB4oManager.getUsers(correo);
			if (users.isEmpty()) {
				dB4oManager.close();
				user = null;
				return (new loginresult(-1, null));
			}
			else {
				dB4oManager.close();
				return (new loginresult(0,null));
			}

		}
	}

	public ArrayList<RuralHouse> getOwnerHouses(Usuario user) {
		DataAccess dB4oManager = new DataAccess();
		ArrayList<RuralHouse> result = dB4oManager.getOwner(user).getCasas();
		dB4oManager.close();
		return result;
	}

	public RuralHouse getHouse(int HouseNumber) {
		DataAccess dB4oManager = new DataAccess();
		RuralHouse rh = dB4oManager.getRuralHouse(HouseNumber);
		dB4oManager.close();
		return rh;
	}

	/**
	 * This method creates an offer with a house number, first day, last day and
	 * price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return the created offer, or null, or an exception
	 */
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price, int nPersRoom)
			throws OverlappingOfferExists, BadDates {
		System.out.println("Executing createOffer");
		System.out.println(firstDay);
		System.out.println(lastDay);

		DataAccess dB4oManager = new DataAccess();
		Offer o = null;
		if (firstDay.compareTo(lastDay) >= 0) throw new BadDates();

		boolean b = dB4oManager.existsOverlappingOffer(ruralHouse, firstDay, lastDay); // The
																						// ruralHouse
																						// object
																						// in
																						// the
																						// client
																						// may
																						// not
																						// be
																						// updated
		if (!b) o = dB4oManager.createOffer(ruralHouse, firstDay, lastDay, price, nPersRoom);

		dB4oManager.close();

		return o;
	}

	public RuralHouse createHouse(String desc, String city, String dir, Usuario user, ArrayList<String> imagenes) {
		DataAccess dB4oManager = new DataAccess();
		RuralHouse rh = dB4oManager.createHouse(desc, city, dir, user, imagenes);
		dB4oManager.close();
		return rh;
	}

	public ArrayDeque<RuralHouse> getAllRuralHouses() {
		System.out.println("Start: getAllRuralHouses");

		DataAccess dB4oManager = new DataAccess();

		ArrayDeque<RuralHouse> ruralHouses = dB4oManager.getAllRuralHouses();
		dB4oManager.close();
		System.out.println("End: getAllRuralHouses");

		return ruralHouses;

	}

	/**
	 * This method obtains the offers of a ruralHouse in the provided dates
	 * 
	 * @param ruralHouse,
	 *            the ruralHouse to inspect
	 * @param firstDay,
	 *            first day in a period range
	 * @param lastDay,
	 *            last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there
	 *         is no overlapping offer
	 */

	public ArrayDeque<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay) {

		DataAccess dB4oManager = new DataAccess();
		ArrayDeque<Offer> offers = dB4oManager.getOffers(rh, firstDay, lastDay);
		dB4oManager.close();

		return offers;
	}

	public void updateHouse(RuralHouse rh, RuralHouse newUpd) {
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.updateHouse(rh, newUpd);
		dB4oManager.close();
	}

	public void updateOffer(Offer o1, Offer newUpd) {
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.updateOffer(o1, newUpd);
		dB4oManager.close();
	}

	/**
	 * This method obtains the offers of a ruralHouse in the provided dates
	 * 
	 * @param ruralHouse,
	 *            the ruralHouse to inspect
	 * @param firstDay,
	 *            first day in a period range
	 * @param lastDay,
	 *            last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there
	 *         is no overlapping offer
	 */

	public ArrayDeque<Offer> getAllOffers(RuralHouse rh) {

		DataAccess dB4oManager = new DataAccess();
		ArrayDeque<Offer> offers = new ArrayDeque<Offer>();
		offers = dB4oManager.getAllOffers(rh);
		dB4oManager.close();

		return offers;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess();

		dB4oManager.close();

	}

	public void initializeBD() {

		DataAccess dB4oManager = new DataAccess();
		dB4oManager.initializeDB();
		dB4oManager.close();

	}

	public int sendMessageTo(String destinatario, String remitente, String asunto, String detalles, String mensaje) {
		DataAccess dB4oManager = new DataAccess();
		int result = dB4oManager.sendMessageTo(destinatario, remitente, asunto, detalles, mensaje);
		dB4oManager.close();
		
		
		return result;

	}

	public class loginresult {
		private int		result;
		private Usuario	usuario;

		public loginresult(int result, Usuario user) {
			this.result = result;
			this.usuario = user;
		}

		public int getResult() {
			return this.result;
		}

		public Usuario getUser() {
			return this.usuario;
		}
	}


	public Usuario updateUserFoto(Usuario user,ProfileImg foto) {
		DataAccess dB4oManager = new DataAccess();
		Usuario newuser =dB4oManager.updateUserFoto(user,foto);
		dB4oManager.close();
		return newuser;
	}

	public Usuario updateUserMail(Usuario user, Stack<Mensaje> mensajes) {
		DataAccess dB4oManager = new DataAccess();
		Usuario newuser =dB4oManager.updateUserMail(user,mensajes);
		dB4oManager.close();
		return newuser;
	}
	
	
	

	@Override
	public ArrayDeque<RuralHouse> getRuralHousesCity(String city) {
		
		DataAccess dB4oManager = new DataAccess();
		ArrayDeque<RuralHouse> rh = dB4oManager.SearchRHbyCity(city);
		dB4oManager.close();
		return rh;
	}
	
	public int reservar(Offer of, Usuario cliente){
		DataAccess dB4oManager = new DataAccess();
		
		Offer mod = dB4oManager.getOffer(of);
		mod.setReservaRealizada(true);
		//Solo necesito pasarme el correo, lo hago así para no modificar la función updateOffer ya que se usa en otos sitos
		mod.setCliente(new Cliente(null, null, null, cliente.getCorreo(), null, null));
		
		

		
		
		dB4oManager.updateOffer(of, mod);
		dB4oManager.updateCliente(cliente, mod);
		dB4oManager.close();
		return 0;
		
	}
	
	public int AnularReserva(Offer of, Usuario cliente){
		DataAccess dB4oManager = new DataAccess();	
		dB4oManager.updateClienteAnular(cliente, of);
		System.out.println("AAA");
		Offer update = new Offer (of.getOfferNumber(), of.getFirstDay(),of.getLastDay(), of.getPrice(), of.getRuralHouse(), of.getnPersRoom());
		System.out.println("BB: " + of.isReservaRealizada());

		dB4oManager.updateOffer(of, update);
	//	dB4oManager.removeOffer(of);
	//	System.out.println("BBB");
	//	dB4oManager.createOffer(of.getRuralHouse(), of.getFirstDay(), of.getLastDay(), of.getPrice(), of.getnPersRoom());
	//	System.out.println("CCC");
		dB4oManager.close();
		return 0;
		
	}
	
	public Usuario getUser(String correo){
		DataAccess dB4oManager = new DataAccess();
		Usuario u = (Usuario) dB4oManager.getUsers(correo).getFirst();
		dB4oManager.close();
		return u;
	}
	
	public ArrayList<Usuario> getAllUsers() {

		DataAccess dB4oManager = new DataAccess();
		ArrayList<Usuario> users = dB4oManager.getAllUsers();
		dB4oManager.close();

		return users;
	}
	
	public boolean superAdminLogin(String password){
		DataAccess dB4oManager = new DataAccess();
		boolean res = dB4oManager.superadminLogin(password);
		dB4oManager.close();
		return res;
	}
	
	public boolean superAdminChangePswd(String old, String newpsw){
		DataAccess dB4oManager = new DataAccess();
		boolean res = dB4oManager.superadminChangepasswd(old, newpsw);
		dB4oManager.close();
		return res;
	}
	
	public boolean superAdminDelete(String password, Object ToDelete){
		DataAccess dB4oManager = new DataAccess();
		boolean res = dB4oManager.adminDelete(password, ToDelete);
		dB4oManager.close();
		return res;
	}

	public ArrayList<Boolean> getBannedFromList(ArrayList<Usuario> userList) {
		// TODO Auto-generated method stub
		DataAccess dB4oManager = new DataAccess();
		ArrayList<Boolean> res = dB4oManager.getIsBannedFromList(userList);
		dB4oManager.close();
		return res;
	}

	public void banUser(String adminpass, Usuario usuario) {
		// TODO Auto-generated method stub
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.banUser(adminpass, usuario);
		dB4oManager.close();
	}

	public void unbanUser(String adminpass, Usuario usuario) {
		// TODO Auto-generated method stub
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.unbanUser(adminpass, usuario);
		dB4oManager.close();
	}

	public void updateUser(Usuario user) {
		// TODO Auto-generated method stub
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.updateUser(user);
		dB4oManager.close();
	}

	public void updateMsg(Mensaje oldmsg, Mensaje mensaje) {
		// TODO Auto-generated method stub
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.updateMsg(oldmsg, mensaje);
		dB4oManager.close();
	}
	
	public Offer getOffer (Offer of){
		DataAccess dB4oManager = new DataAccess();
		Offer rt = dB4oManager.getOffer(of);
		dB4oManager.close();
		return rt;
	}

	public Stack<Mensaje> getAdminMenssages() {
		DataAccess dB4oManager = new DataAccess();
		Stack<Mensaje> buzon = dB4oManager.getAdminMenssages();
		dB4oManager.close();
		return buzon;
	}

	public void sendMessageToAdmin(String remite, String asunto, String detalles, String contenido) {
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.sendMessageToAdmin(remite, asunto, detalles, contenido);
		dB4oManager.close();
	}

	public void resetAdmin() {
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.resetAdmin();
		dB4oManager.close();
		
	}

	public Mensaje searchPublic(String uuid) {
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.resetAdmin();
		Mensaje mensaje= dB4oManager.searchPublic(uuid);
		dB4oManager.close();
		return mensaje;
	}

	public void sendPublicMesage(String destino, String asunto, String format, String contenido) {	
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.sendPublicMesage(destino,asunto,format,contenido);
		dB4oManager.close();
	}

	public boolean WillCheckForUpdates() {
		ConfigXML c = ConfigXML.getInstance();
		return c.WillCheckForUpdates();
	}

	public loginresult twitterlogin(long twitterID) {
		/*
		 * Devuelve: 1 -> Cliente 2 -> Propietario
		 * 
		 * -1 -> Esa cuenta no existe 0 -> Datos introducidos incorrectos
		 */
		DataAccess dB4oManager = new DataAccess();
		TwitterUser getUser = dB4oManager.fetchTwitterUser(twitterID);
		dB4oManager.close();
		if (getUser == null) return new loginresult(-1, null);
		else return login(getUser.getCorreo(), String.valueOf(twitterID));
	}
	
	public int TwittercreateAccount(boolean isPropietario, String nombre, String apellidos, String telefono, String correo,
			String password, String DNI, String DirPostal, ProfileImg perfil, long ID) {
		/*
		 * -1 -> Usuario ya existe 1 -> Error de datos //retirado para aumentar
		 * eficiencia. se hace en registroGUI
		 * 
		 * 0 -> Todo correcto
		 */
		DataAccess dB4oManager = new DataAccess();
		int result = 0;
		if (dB4oManager.isBannedEmail(correo)) {
			dB4oManager.close();
			return -2;
		}
		if (!dB4oManager.getUsers(correo).isEmpty())
			result = -1;
		else {
			if (result == 0) dB4oManager.TwittercreateAccount(isPropietario, nombre, apellidos, telefono, correo, password,
					DNI, DirPostal, perfil, ID);
		}
		dB4oManager.close();
		return result;
	}

	public TwitterUser getTwitterUser(String correo) {
		// TODO Auto-generated method stub
		DataAccess dB4oManager = new DataAccess();
		TwitterUser getUser = dB4oManager.fetchTwitterUserE(correo);
		dB4oManager.close();
		return getUser;
	}

	/*public int deleteHouseOwner(Usuario user, RuralHouse ruralHouse) {
		DataAccess dB4oManager = new DataAccess();
		RuralHouse rh = dB4oManager.getRuralHouse(ruralHouse.getHouseNumber());
		if (rh.getOwner().getCorreo().equals(user.getCorreo())){
			boolean reserve = false;
			for (Offer o: rh.getOffers())
				reserve = o.isReservaRealizada();
			if (reserve) {
				dB4oManager.close();
				return 1;
			} else {
				dB4oManager.rhDelete(rh);
				dB4oManager.close();
				return 0;
			}
		} else {
			dB4oManager.close();
			return -1;
		}
	}
	
	public int deleteOfferOwner(Usuario user, Offer offer) {
		DataAccess dB4oManager = new DataAccess();
		Offer o = dB4oManager.getOffer(offer);
		if (o.getRuralHouse().getOwner().getCorreo().equals(user.getCorreo())){
			if (o.isReservaRealizada()) {
				dB4oManager.close();
				return 1;
			} else {
				dB4oManager.oDelete(o);
				dB4oManager.close();
				return 0;
			}
		} else {
			dB4oManager.close();
			return -1;
		}
	}*/

}
