package businessLogic;

import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayDeque;
import java.util.Date;

//import domain.Booking;
import domain.Offer;
import domain.RuralHouse;
import domain.Usuario;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;
import utilities.ProfileImg;

import javax.jws.WebMethod;
import javax.jws.WebService;

import businessLogic.FacadeImplementationWS.loginresult;

@WebService
public interface ApplicationFacadeInterfaceWS {

	/**
	 * This method creates an offer with a house number, first day, last day and
	 * price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */

	int createAccount(boolean isPropietario, String nombre, String apellidos, String telefono, String correo,
			String password, String DNI, String DirPostal, ProfileImg perfil);


	@WebMethod
	Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price, int nPersRoom)
			throws OverlappingOfferExists, BadDates;
	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */

	/**
	 * This method retrieves the existing rural houses
	 * 
	 * @return a Set of rural houses
	 */
	@WebMethod
	public ArrayDeque<RuralHouse> getAllRuralHouses();

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

	@WebMethod
	public ArrayDeque<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay);
	
	@WebMethod
	public ArrayDeque<Offer> getAllOffers(RuralHouse rh);

	@WebMethod
	public ArrayDeque<RuralHouse> getRuralHousesCity(String city);

	/**
	 * This method obtains the RuralHouses in the city provided
	 * 
	 * @param city,
	 *            the city of the rural house
	 * 
	 * @return a Set of rural houses
	 */

	@WebMethod
	public void initializeBD();

}