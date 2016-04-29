package domain;

import java.util.ArrayList;

public class Propietario extends Usuario {
	
	private String				DNI;
	private String				DirPostal;
	private ArrayList<RuralHouse>	casas;
	
	public Propietario(String nombre, String apellidos, String telefono, String correo, String password,
			utilities.ProfileImg perfil, String DNI, String DirPostal) {
		super(nombre, apellidos, telefono, correo, password, perfil);
		this.casas = new ArrayList<RuralHouse>();
		this.DNI = DNI;
		this.DirPostal = DirPostal;
	}

	public String getDNI() {
		return DNI;
	}

	public String getDirPostal() {
		return DirPostal;
	}

	public ArrayList<RuralHouse> getCasas() {
		return casas;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public void setDirPostal(String dirPostal) {
		DirPostal = dirPostal;
	}

	public void setCasas(ArrayList<RuralHouse> casas) {
		this.casas = casas;
	}
	
}
