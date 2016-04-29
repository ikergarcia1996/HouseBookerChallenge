package domain;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)

public class RuralHouse implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer				houseNumber;
	private String				description;
	private String				city;
	private String				direccion;
	public Vector<Offer>		offers;
	private ArrayList<String> imagenes;
	private Usuario owner;

	public ArrayList<String> getImagenes() {
		return imagenes;
	}

	public void setImagenes(ArrayList<String> imagenes) {
		this.imagenes = imagenes;
	}

	public RuralHouse() {
		super();
	}

	public RuralHouse(Integer houseNumber, String description, String city, String direccion, Usuario owner) {
		this.houseNumber = houseNumber;
		this.description = description;
		this.city = city;
		this.direccion = direccion;
		offers = new Vector<Offer>();
		this.imagenes = new ArrayList<String>();
		this.owner=owner;
	}
	
	public RuralHouse(Integer houseNumber, String description, String city, String direccion, Usuario owner, ArrayList<String> imagenes) {
		this.houseNumber = houseNumber;
		this.description = description;
		this.city = city;
		this.direccion = direccion;
		offers = new Vector<Offer>();
		this.imagenes = imagenes;
		this.owner=owner;
	}

	public Usuario getOwner() {
		return owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String toString() {
		return this.houseNumber + ": " + this.city;
	}

	/**
	 * This method creates an offer with a house number, first day, last day and
	 * price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */
	public Offer createOffer(int offerNumber, Date firstDay, Date lastDay, float price, int nPersRoom) {
		Offer off = new Offer(offerNumber, firstDay, lastDay, price, this, nPersRoom);
		offers.add(off);
		return off;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + houseNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		RuralHouse other = (RuralHouse) obj;
		if (houseNumber != other.houseNumber) return false;
		return true;
	}

	/**
	 * This method obtains available offers for a concrete house in a certain
	 * period
	 * 
	 * @param houseNumber,
	 *            the house number where the offers must be obtained
	 * @param firstDay,
	 *            first day in a period range
	 * @param lastDay,
	 *            last day in a period range
	 * @return a vector of offers(Offer class) available in this period
	 */
	public ArrayDeque<Offer> getOffers(Date firstDay, Date lastDay) {

		ArrayDeque<Offer> availableOffers = new ArrayDeque<Offer>();
		Iterator<Offer> e = offers.iterator();
		Offer offer;
		while (e.hasNext()) {
			offer = e.next();
			if ((offer.getFirstDay().compareTo(firstDay) <= 0) && (offer.getLastDay().compareTo(lastDay) >= 0))
				availableOffers.add(offer);
		}
		return availableOffers;

	}

	/**
	 * This method obtains the first offer that overlaps with the provided dates
	 * 
	 * @param firstDay,
	 *            first day in a period range
	 * @param lastDay,
	 *            last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there
	 *         is no overlapping offer
	 */

	public Offer overlapsWith(Date firstDay, Date lastDay) {

		Iterator<Offer> e = offers.iterator();
		Offer offer = null;
		while (e.hasNext()) {
			offer = e.next();
			if ((offer.getFirstDay().compareTo(lastDay) < 0) && (offer.getLastDay().compareTo(firstDay) > 0))
				return offer;
		}
		return null;

	}

	public ArrayDeque<Offer> getOffers() {

		ArrayDeque<Offer> availableOffers = new ArrayDeque<Offer>();
		Iterator<Offer> e = offers.iterator();
		Offer offer;
		while (e.hasNext()) {
			offer = e.next();
			
				availableOffers.add(offer);
		}
		return availableOffers;

	}


}

