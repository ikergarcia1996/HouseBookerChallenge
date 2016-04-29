package domain;

import java.io.*;
import java.util.Comparator;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)

public class Offer implements Serializable {

	private Integer		offerNumber;
	private Date		firstDay;	// Dates are stored as java.util.Date
									// objects instead of java.sql.Date objects
	private Date		lastDay;	// because, they are not well stored in db4o
									// as java.util.Date objects
	private float		price;		// This is coherent because objects of
									// java.sql.Date are objects of
									// java.util.Date
	@XmlIDREF
	private int			nPersRoom;	// numero de personas que entran en la
									// habitación

	private RuralHouse	ruralHouse;
	
	private boolean reservaRealizada=false; //true = reservado
	private Usuario cliente=null;

	public boolean isReservaRealizada() {
		return reservaRealizada;
	}

	public void setReservaRealizada(boolean reservaRealizada) {
		this.reservaRealizada = reservaRealizada;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Offer() {
	}

	public Offer(int offerNumber, Date firstDay, Date lastDay, float price, RuralHouse ruralHouse, int nPersRoom) {
		this.firstDay = firstDay;
		this.lastDay = lastDay;
		this.price = price;
		this.ruralHouse = ruralHouse;
		this.offerNumber = offerNumber;
		this.nPersRoom = nPersRoom;
	}

	/**
	 * Get the house number of the offer
	 * 
	 * @return the house number
	 */
	public RuralHouse getRuralHouse() {
		return this.ruralHouse;
	}

	/**
	 * Set the house number to a offer
	 * 
	 * @param house
	 *            number
	 */
	public void setRuralHouse(RuralHouse ruralHouse) {
		this.ruralHouse = ruralHouse;
	}

	/**
	 * Get the offer number
	 * 
	 * @return offer number
	 */
	public int getOfferNumber() {
		return this.offerNumber;
	}

	/**
	 * Get the first day of the offer
	 * 
	 * @return the first day
	 */
	public Date getFirstDay() {
		return this.firstDay;
	}

	/**
	 * Set the first day of the offer
	 * 
	 * @param firstDay
	 *            The first day
	 */
	public void setFirstDay(Date firstDay) {
		this.firstDay = firstDay;
	}

	/**
	 * Get the last day of the offer
	 * 
	 * @return the last day
	 */
	public Date getLastDay() {
		return this.lastDay;
	}

	/**
	 * Set the last day of the offer
	 * 
	 * @param lastDay
	 *            The last day
	 */
	public void setLastDay(Date lastDay) {
		this.lastDay = lastDay;
	}

	/**
	 * Get the price
	 * 
	 * @return price
	 */
	public float getPrice() {
		return this.price;
	}

	/**
	 * Set the price
	 * 
	 * @param price
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * Get the persons per room
	 * 
	 * @return persons per room
	 */
	public int getnPersRoom() {
		return nPersRoom;
	}

	/**
	 * Set the persons per room
	 * 
	 * @param Num
	 *            persons
	 * 
	 */
	public void setnPersRoom(int nPersRoom) {
		this.nPersRoom = nPersRoom;
	}

	public String toString() {
		return offerNumber + ";" + firstDay.toString() + ";" + lastDay.toString() + ";" + price;
	}
	
	public static Comparator<Offer> StrPrice = new Comparator <Offer>(){

		@Override
		public int compare(Offer o1, Offer o2) {
			if (o1.price>o2.price) return 1;
			else return -1;
		}

		
	};
	
	public static Comparator<Offer> StrPriceI = new Comparator <Offer>(){

			@Override
			public int compare(Offer o1, Offer o2) {
				if (o1.price>o2.price) return -1;
				else return 1;
			}

		
	};
	
	public static Comparator<Offer> StrCity = new Comparator <Offer>(){

		@Override
		public int compare(Offer o1, Offer o2) {
			String rh0 = o1.getRuralHouse().getCity();
			String rh1 = o2.getRuralHouse().getCity();
			return rh0.compareTo(rh1);
		}

		
		

		
	};
}