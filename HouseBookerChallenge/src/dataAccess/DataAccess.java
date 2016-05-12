package dataAccess;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;
import java.util.UUID;
import java.util.Vector;

import javax.jws.WebMethod;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

import configuration.ConfigXML;
import domain.BannedUser;
import domain.Cliente;
import domain.Mensaje;
//import domain.Booking;
import domain.Offer;
import domain.Propietario;
import domain.RuralHouse;
import domain.TwitterUser;
import domain.Usuario;
import exceptions.OverlappingOfferExists;
import utilities.ProfileImg;

public class DataAccess {
	protected static ObjectContainer db;

	private static DB4oManagerAux theDB4oManagerAux;
	private static EmbeddedConfiguration configuration;
	private static ClientConfiguration configurationCS;

	ConfigXML c;

	public DataAccess() {

		c = ConfigXML.getInstance();

		if (c.isDatabaseLocal()) {
			configuration = Db4oEmbedded.newConfiguration();
			configuration.common().activationDepth(c.getActivationDepth());
			configuration.common().updateDepth(c.getUpdateDepth());
			db = Db4oEmbedded.openFile(configuration, c.getDb4oFilename());
		} else {
			configurationCS = Db4oClientServer.newClientConfiguration();
			configurationCS.common().activationDepth(c.getActivationDepth());
			configurationCS.common().updateDepth(c.getUpdateDepth());
			configurationCS.common().objectClass(RuralHouse.class).cascadeOnDelete(true);
			db = Db4oClientServer.openClient(configurationCS, c.getDatabaseNode(), c.getDatabasePort(), c.getUser(),
					c.getPassword());

		}
		System.out.println("Creating DB4oManager instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());
	}

	class DB4oManagerAux {
		int offerNumber;
		int houseNumber;

		DB4oManagerAux(int offerNumber, int houseNumber) {
			this.offerNumber = offerNumber;
			this.houseNumber = houseNumber;
		}
	}

	private static class PublicMail {
		Stack<Mensaje> buzon;
		
		PublicMail(){
			buzon=new Stack<Mensaje>();
		}
		
	}

	private static class SuperAdmin {

		String password;
		Stack<Mensaje> buzon;

		SuperAdmin(String passwd) {
			password = passwd;
			buzon = new Stack<Mensaje>();

		}

		boolean changePassword(String current, String newpass) {
			if (this.password.equals(current)) {
				this.password = newpass;
				return true;
			} else
				return false;
		}

		Stack<Mensaje> getBuzon() {
			return this.buzon;
		}

	}

	public void initializeDB() {

		System.out.println("Db initialized");
		main.Runer.splash.textArea.append("DB initialized");
		theDB4oManagerAux = new DB4oManagerAux(1, 1);
		SuperAdmin admin = new SuperAdmin("admin123");
		PublicMail mail = new PublicMail();

		db.store(theDB4oManagerAux);
		db.store(admin);
		db.store(mail);

		db.commit();
	}

	public boolean superadminLogin(String password) {
		ObjectSet<SuperAdmin> res = db.queryByExample(SuperAdmin.class);
		ListIterator<SuperAdmin> listIter = res.listIterator();
		if (listIter.hasNext())
			return (res.next().password.equals(password));
		else
			return false;
	}

	public boolean superadminChangepasswd(String old, String newpsw) {
		ObjectSet<SuperAdmin> res = db.queryByExample(SuperAdmin.class);
		ListIterator<SuperAdmin> listIter = res.listIterator();
		boolean done = false;
		SuperAdmin adminacc = res.next();
		if (listIter.hasNext()) {
			done = adminacc.changePassword(old, newpsw);
			db.store(adminacc);
		}
		return done;
	}

	public boolean adminDelete(String password, Object toDelete) {
		ObjectSet<SuperAdmin> res = db.queryByExample(SuperAdmin.class);
		ListIterator<SuperAdmin> listIter = res.listIterator();
		boolean auth = false;
		if (listIter.hasNext())
			auth = res.next().password.equals(password);
		if (auth) {
			db.delete(db.queryByExample(toDelete).next());
			return true;
		}
		return false;
	}

	public boolean banUser(String password, Usuario user) {
		ObjectSet<SuperAdmin> res = db.queryByExample(SuperAdmin.class);
		ListIterator<SuperAdmin> listIter = res.listIterator();
		boolean auth = false;
		if (listIter.hasNext())
			auth = res.next().password.equals(password);
		if (auth) {
			BannedUser usuariobanneado = new BannedUser(user.getCorreo());
			db.store(usuariobanneado);
			return true;
		}
		return false;
	}

	public void unbanUser(String password, Usuario user) {
		ObjectSet<SuperAdmin> res = db.queryByExample(SuperAdmin.class);
		ListIterator<SuperAdmin> listIter = res.listIterator();
		boolean auth = false;
		if (listIter.hasNext())
			auth = res.next().password.equals(password);
		if (auth) {
			db.delete(db.queryByExample(new BannedUser(user.getCorreo())).next());
		}
	}

	public boolean isBanned(Usuario user) {
		ObjectSet<BannedUser> res = db.queryByExample(new BannedUser(user.getCorreo()));
		return !(res.isEmpty());
	}

	public boolean isBannedEmail(String correo) {
		ObjectSet<BannedUser> res = db.queryByExample(new BannedUser(correo));
		return !(res.isEmpty());
	}

	public ArrayList<Boolean> getIsBannedFromList(ArrayList<Usuario> userList) {
		ArrayList<Boolean> isBannedList = new ArrayList<Boolean>();
		for (Usuario u : userList) {
			isBannedList.add(isBanned(u));
		}
		return isBannedList;
	}

	public void createAccount(boolean isPropietario, String nombre, String apellidos, String telefono, String correo,
			String password, String DNI, String DirPostal, ProfileImg perfil) {
		DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
		Date date = new Date();
		if (!isPropietario) {
			Cliente nuevoUsuario = new Cliente(nombre, apellidos, telefono, correo, password, perfil);
			nuevoUsuario.sendMensaje(new Mensaje(nuevoUsuario, "welcome@hbc.com", "Bienvenido", dateFormat.format(date),
					"Hola y bienvenido a HouseBookerChallenge.\n En primer lugar te queremos dar las gracias por empezar a formar parte de esta gran comunidad, y estamos deseando ayudarte en todo lo posible, a si que si tienes alguna pregunta, puedes coontactar con el administrador en el correo HBCHelp@HBC.com."));
			db.store(nuevoUsuario);
		} else {
			Propietario nuevoUsuario = new Propietario(nombre, apellidos, telefono, correo, password, perfil, DNI,
					DirPostal);
			nuevoUsuario.sendMensaje(new Mensaje(nuevoUsuario, "welcome@hbc.com", "Bienvenido", dateFormat.format(date),
					"Hola y bienvenido a HouseBookerChallenge.\n En primer lugar te queremos dar las gracias por empezar a formar parte de esta gran comunidad, y estamos deseando ayudarte en todo lo posible, a si que si tienes alguna pregunta, puedes coontactar con el administrador en el correo HBCHelp@HBC.com."));
			db.store(nuevoUsuario);
		}
		db.commit();
	}

	public ArrayDeque<Usuario> getUsers(String correo) {
		ArrayDeque<Usuario> result = new ArrayDeque<Usuario>();
		result.addAll(db.queryByExample(new Cliente(null, null, null, correo, null, null)));
		result.addAll(db.queryByExample(new Propietario(null, null, null, correo, null, null, null, null)));
		return result;
	}

	public ObjectSet<Usuario> getAuthClients(String correo, String password) {
		return db.queryByExample(new Cliente(null, null, null, correo, password, null));

	}

	public ObjectSet<Usuario> getAuthOwners(String correo, String password) {
		return db.queryByExample(new Propietario(null, null, null, correo, password, null, null, null));
	}

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price, int nPersRoom) {

		try {

			RuralHouse proto = new RuralHouse(ruralHouse.getHouseNumber(), null, null, null, null);
			ObjectSet<RuralHouse> result = db.queryByExample(proto);
			RuralHouse rh = result.next();

			ObjectSet<DB4oManagerAux> res = db.queryByExample(DB4oManagerAux.class);
			ListIterator<DB4oManagerAux> listIter = res.listIterator();
			if (listIter.hasNext())
				theDB4oManagerAux = res.next();

			Offer o = rh.createOffer(theDB4oManagerAux.offerNumber++, firstDay, lastDay, price, nPersRoom);
			// Offer o=rh.createOffer(1,firstDay, lastDay, price);

			db.store(theDB4oManagerAux); // To store the new value for
											// offerNumber
			db.store(rh);
			db.commit();
			return o;
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer");
			return null;
		}
	}

	public RuralHouse createHouse(String desc, String city, String dir, Usuario user, ArrayList<String> imagenes) {
		try {
			ObjectSet<Propietario> result = db.queryByExample(user);
			Propietario us = result.next();

			ObjectSet<DB4oManagerAux> res = db.queryByExample(DB4oManagerAux.class);
			ListIterator<DB4oManagerAux> listIter = res.listIterator();

			if (listIter.hasNext())
				theDB4oManagerAux = res.next();

			RuralHouse rh = new RuralHouse(theDB4oManagerAux.houseNumber, desc, city, dir, us, imagenes);
			us.getCasas().add(rh);
			theDB4oManagerAux.houseNumber++;

			db.store(theDB4oManagerAux); // To store the new value for
											// offerNumber
			db.store(us);
			db.commit();
			return rh;
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer");
			return null;
		}
	}

	public RuralHouse getRuralHouse(int HouseNumber) {
		RuralHouse proto = new RuralHouse(HouseNumber, null, null, null, null);
		ObjectSet<RuralHouse> result = db.queryByExample(proto);
		RuralHouse rh = result.next();
		return rh;
	}

	public ArrayDeque<RuralHouse> getAllRuralHouses() {

		try {
			RuralHouse proto = new RuralHouse(null, null, null, null, null);
			ObjectSet<RuralHouse> result = db.queryByExample(proto);
			ArrayDeque<RuralHouse> ruralHouses = new ArrayDeque<RuralHouse>();
			while (result.hasNext())
				ruralHouses.add(result.next());
			return ruralHouses;
		} finally {
			// db.close();
		}
	}
	// public int GetLastOfferNumber(){}

	public ArrayDeque<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay) {
		ArrayDeque<Offer> offers = new ArrayDeque<Offer>();
		RuralHouse rhn = (RuralHouse) db.queryByExample(new RuralHouse(rh.getHouseNumber(), null, null, null, null))
				.next();
		offers = rhn.getOffers(firstDay, lastDay);
		return offers;
	}

	public void updateHouse(RuralHouse rh, RuralHouse update) {
		ObjectSet<RuralHouse> query = db.queryByExample(
				new RuralHouse(rh.getHouseNumber(), rh.getDescription(), rh.getCity(), rh.getDireccion(), null));
		RuralHouse q = query.next();

		q.setCity(update.getCity());
		q.setDescription(update.getDescription());
		q.setDireccion(update.getDireccion());
		q.setImagenes(update.getImagenes());
		q.setOwner((Propietario) db
				.queryByExample(
						new Propietario(null, null, null, update.getOwner().getCorreo(), null, null, null, null))
				.next());

		db.store(q);
		db.commit();
	}

	public void updateOffer(Offer o1, Offer update) {
		ObjectSet<Offer> query = db.queryByExample(o1);
		Offer q = query.next();

		q.setFirstDay(update.getFirstDay());
		q.setLastDay(update.getLastDay());
		q.setnPersRoom(update.getnPersRoom());
		q.setPrice(update.getPrice());
		if (update.getCliente()!=null){
			q.setCliente((Cliente) db.queryByExample(new Cliente(null, null, null, update.getCliente().getCorreo(), null, null)).next());
			System.out.println("añadiendo cliente a oferta");
			q.setReservaRealizada(true);
		}
		else {q.setCliente(null);
				q.setReservaRealizada(false);
		}
		q.setRuralHouse((RuralHouse) db.queryByExample(new RuralHouse (update.getRuralHouse().getHouseNumber(), null, null, null,null)).next());
		db.store(q);
		db.commit();
	}

		


	public void updateCliente(Usuario u1, Offer of) {
		ObjectSet<Cliente> query = db.queryByExample(u1);
		Cliente q = query.next();

		ObjectSet<Offer> query2 = db.queryByExample(of);
		Offer o = query2.next();

		q.getOfertasReservadas().add(o);
		System.out.println("OFERTAS RESERVADAS POR " + q.getCorreo() + " ->" + q.getOfertasReservadas().toString());

		db.store(q);
		db.commit();
	}

	public boolean existsOverlappingOffer(RuralHouse rh, Date firstDay, Date lastDay) throws OverlappingOfferExists {
		try {

			RuralHouse rhn = (RuralHouse) db.queryByExample(new RuralHouse(rh.getHouseNumber(), null, null, null, null))
					.next();
			if (rhn.overlapsWith(firstDay, lastDay) != null)
				return true;
			else
				return false;
		} finally {
			// db.close();
		}
	}

	// Data una RuralHouse (rh) realiza un queryByExample usandola como modeo
	public ArrayDeque<RuralHouse> SearchRHbyCity(String city) {

		try {
			RuralHouse rh = new RuralHouse(null, null, city, null, null);
			ObjectSet<RuralHouse> result = db.queryByExample(rh);
			ArrayDeque<RuralHouse> ruralHouses = new ArrayDeque<RuralHouse>();
			while (result.hasNext())
				ruralHouses.add(result.next());
			return ruralHouses;
		} finally {
			// db.close();
		}
	}

	public ArrayDeque<Offer> getAllOffers(RuralHouse rh) {
		ArrayDeque<Offer> offers = new ArrayDeque<Offer>();
		RuralHouse rhn = (RuralHouse) db.queryByExample(new RuralHouse(rh.getHouseNumber(), null, null, null, null))
				.next();
		offers = rhn.getOffers();
		return offers;
	}

	public ArrayList<Usuario> getAllUsers() {
		ArrayList<Usuario> users = new ArrayList<Usuario>();
		users.addAll(db.queryByExample(Usuario.class));
		return users;
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public ObjectSet<Usuario> getAuthOwners(Usuario user) {
		return db.queryByExample(user);

	}

	public ObjectSet<Usuario> getAuthClients(Usuario user) {
		return db.queryByExample(user);

	}

	public Propietario getOwner(Usuario user) {
		return (Propietario) db
				.queryByExample(new Propietario(null, null, null, user.getCorreo(), null, null, null, null)).next();

	}

	public int sendMessageTo(String destinatario, String remitente, String asunto, String detalles, String mensaje) {
		// 0 todo correcto
		// 1 usuario no existe
		int ret;
		if (!chekUserExists(destinatario))
			ret = 1;
		else {
			Usuario usdest = (Usuario) db.queryByExample(getUsers(destinatario).getFirst()).next();
			Mensaje m = new Mensaje(usdest, remitente, asunto, detalles, mensaje);
			usdest.sendMensaje(m);
			ret = 0;

			db.store(usdest);
			db.commit();
		}
		return ret;
	}

	private boolean chekUserExists(String destinatario) {

		ArrayDeque<Usuario> result = new ArrayDeque<Usuario>();
		result.addAll(getUsers(destinatario));
		return !result.isEmpty();

	}

	public Usuario updateUserFoto(Usuario user, ProfileImg foto) {
		ObjectSet<Usuario> olduser = db.queryByExample(user);
		Usuario newuser = olduser.next();
		newuser.setProfileImg(foto);
		db.store(newuser);
		db.commit();
		return getDBUser(newuser);
	}

	private Usuario getDBUser(Usuario user) {
		if (user.getClass().equals(Cliente.class))
			return getAuthClients(user).next();
		else
			return getAuthOwners(user).next();
	}

	public Usuario updateUserMail(Usuario user, Stack<Mensaje> mensajes) {
		ObjectSet<Usuario> olduser = db.queryByExample(user);
		Usuario newuser = olduser.next();
		newuser.setMensajes(mensajes);
		db.store(newuser);
		db.commit();
		return getDBUser(newuser);
	}

	public void updateUser(Usuario user) {
		// TODO Auto-generated method stub
		if (user.getClass().equals(Cliente.class)) {
			Cliente upduser = (Cliente) db.queryByExample(new Cliente(null, null, null, user.getCorreo(), null, null))
					.next();
			upduser.setProfileImg(user.getProfileImg());
			db.store(upduser);
			db.commit();
		} else if (user.getClass().equals(Propietario.class)) {
			Propietario upduser = (Propietario) db
					.queryByExample(new Propietario(null, null, null, user.getCorreo(), null, null, null, null)).next();
			upduser.setProfileImg(user.getProfileImg());
			db.store(upduser);
			db.commit();
		}
	}

	public void updateMsg(Mensaje oldmsg, Mensaje mensaje) {
		// TODO Auto-generated method stub
		Mensaje link = (Mensaje) db.queryByExample(oldmsg).next();
		if (mensaje.isUnread())
			link.setUnReaden();
		else
			link.setReaden();
		db.store(link);
		db.commit();
	}

	public Offer getOffer(Offer of) {
		Offer rt = (Offer) db.queryByExample(new Offer(of.getOfferNumber(), of.getFirstDay(), of.getLastDay(),
				of.getPrice(), of.getRuralHouse(), of.getnPersRoom())).next();
		return rt;
	}


	public Stack<Mensaje> getAdminMenssages() {
		ObjectSet<SuperAdmin> res = db.queryByExample(SuperAdmin.class);
		ListIterator<SuperAdmin> listIter = res.listIterator();
		if (listIter.hasNext())
			return (res.next().getBuzon());
		return null;

	}

	public void sendMessageToAdmin(String remite, String asunto, String detalles, String contenido) {
		ObjectSet<SuperAdmin> res = db.queryByExample(SuperAdmin.class);
		ListIterator<SuperAdmin> listIter = res.listIterator();
		SuperAdmin admin = null;
		if (listIter.hasNext()) {
			admin = res.next();
			admin.buzon.push(new Mensaje(remite, asunto, detalles, contenido));
			db.store(admin);
			db.commit();
		}

	}

	public void resetAdmin() {
		db.delete(db.queryByExample(SuperAdmin.class).next());
		SuperAdmin admin = new SuperAdmin("admin123");
		PublicMail mail = new PublicMail();
		db.store(mail);
		db.store(admin);
		db.commit();

	}
	public Mensaje searchPublic(String uuid){
		ObjectSet<Mensaje> res = db.queryByExample(new Mensaje(uuid,null,null,null,true));
		ListIterator<Mensaje> listIter = res.listIterator();
		if (listIter.hasNext())
			return (res.next());
		return null;
	}

	public void sendPublicMesage(String destino, String asunto, String format, String contenido) {
		ObjectSet<PublicMail> res = db.queryByExample(PublicMail.class);
		ListIterator<PublicMail> listIter = res.listIterator();
		PublicMail mail = null;
		if (listIter.hasNext()) mail=res.next();
		mail.buzon.push(new Mensaje(destino, asunto, format, contenido,true));
		db.store(mail);
		db.commit();
			
	}


	public void updateClienteAnular(Usuario u1, Offer of) {
		ObjectSet<Cliente> query = db.queryByExample(u1);
		Cliente q = query.next();
		
		if (q.getOfertasReservadas().remove(db.queryByExample(of).next())) 
			System.out.println("BORRADA"); 
		else System.out.println("NO BORRADA");
		
		
		System.out.println("OFERTAS RESERVADAS POR " + q.getCorreo() + " ->" + q.getOfertasReservadas().toString());

		db.store(q);
		db.commit();
		
	}
	
	public void removeOffer(Offer of){
		Offer d = (Offer) db.queryByExample(of).next();
		System.out.println("Elimindo oferta " + of.toString() );
		RuralHouse rh = (RuralHouse) db.queryByExample(d.getRuralHouse()).next();
		
		Iterator<Offer> itr = rh.offers.iterator();
		Vector<Offer> rho = new Vector<Offer>();
		//while (itr.hasNext()){
			//rho.add(itr.next());
		//}
		if (rho.remove(d)) System.out.println("ELIMINADA OFERTA DE LA CASA");
		else System.out.println("NO SE HA ENCONTRADO LA OFERTA EN LA CASA");
		rh.offers=rho;
		db.delete(d);
		db.store(rh);
		db.commit();
	}
	
	public TwitterUser fetchTwitterUser(long ID){
		ObjectSet<Object> res = db.queryByExample(new TwitterUser(null, ID));
		if (!res.isEmpty())
			return (TwitterUser) res.next();
		else return null;
	}
	
	public TwitterUser fetchTwitterUserE(String email){
		TwitterUser user = (TwitterUser) db.queryByExample(new TwitterUser(email, 0)).next();
		return user;
	}
	
	public void TwittercreateAccount(boolean isPropietario, String nombre, String apellidos, String telefono, String correo,
			String password, String DNI, String DirPostal, ProfileImg perfil, long TwitterID) {
		DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
		Date date = new Date();
		if (!isPropietario) {
			Cliente nuevoUsuario = new Cliente(nombre, apellidos, telefono, correo, password, perfil);
			nuevoUsuario.sendMensaje(new Mensaje(nuevoUsuario, "welcome@hbc.com", "Bienvenido", dateFormat.format(date),
					"Hola y bienvenido a HouseBookerChallenge.\n En primer lugar te queremos dar las gracias por empezar a formar parte de esta gran comunidad, y estamos deseando ayudarte en todo lo posible, a si que si tienes alguna pregunta, puedes coontactar con el administrador en el correo HBCHelp@HBC.com."));
			db.store(nuevoUsuario);
		} else {
			Propietario nuevoUsuario = new Propietario(nombre, apellidos, telefono, correo, password, perfil, DNI,
					DirPostal);
			nuevoUsuario.sendMensaje(new Mensaje(nuevoUsuario, "welcome@hbc.com", "Bienvenido", dateFormat.format(date),
					"Hola y bienvenido a HouseBookerChallenge.\n En primer lugar te queremos dar las gracias por empezar a formar parte de esta gran comunidad, y estamos deseando ayudarte en todo lo posible, a si que si tienes alguna pregunta, puedes coontactar con el administrador en el correo HBCHelp@HBC.com."));
			db.store(nuevoUsuario);
		}
		TwitterUser usr = new TwitterUser(correo, TwitterID);
		db.store(usr);
		db.commit();
	}

	public void rhDelete(RuralHouse rh) {
		// TODO Auto-generated method stub
		ObjectSet<Object> res = db.queryByExample(rh);
		if (!res.isEmpty()) {
			RuralHouse rh1 = (RuralHouse) res.next();
			for (Offer o: rh1.getOffers()) {
				db.delete(db.queryByExample(o));
			}
			db.delete(db.queryByExample(rh1));
		}
	}
	
	public void oDelete(Offer o) {
		// TODO Auto-generated method stub
		ObjectSet<Object> res = db.queryByExample(o);
		if (!res.isEmpty()) {
			Offer o1 = (Offer) res.next();
			ObjectSet<Object> res2 = db.queryByExample(o1.getRuralHouse());
			if (!res2.isEmpty()) {
				db.delete(db.queryByExample(o1));
			}
		}
	}

}
