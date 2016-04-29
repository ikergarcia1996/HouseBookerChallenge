package domain;

import java.util.ArrayList;

public class Cliente extends Usuario {
	
	private ArrayList<Offer> ofertasReservadas;
	
	public Cliente(String nombre, String apellidos, String telefono, String correo, String password,
			utilities.ProfileImg perfil) {
		super(nombre, apellidos, telefono, correo, password, perfil);
		this.ofertasReservadas = new ArrayList<Offer>();
	}

	public ArrayList<Offer> getOfertasReservadas() {
		return ofertasReservadas;
	}

	public void setOfertasReservadas(ArrayList<Offer> ofertasReservadas) {
		this.ofertasReservadas = ofertasReservadas;
	}


}
