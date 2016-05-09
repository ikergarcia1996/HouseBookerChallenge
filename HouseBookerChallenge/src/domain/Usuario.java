package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.ImageIcon;

import utilities.ImageUtils;
import utilities.ProfileImg;


public abstract class Usuario {
	private String				nombre;
	private String				apellido;
	private String				tlf;
	private String				correo;
	protected String				pswrd;
	private ProfileImg			ProfileImg;
	private Stack<Mensaje>		mensajes;

	public Usuario(String nombre, String apellidos, String telefono, String correo,
			String password, ProfileImg perfil) {
		
		this.nombre = nombre;
		this.apellido=apellidos;
		this.tlf = telefono;
		this.correo = correo;
		this.pswrd = password;
		this.ProfileImg = perfil;
		this.mensajes= new Stack<Mensaje>();
	}

	public ImageIcon getDecodedProfileImg() {
		return new ImageIcon(ImageUtils.decodeToImage(this.ProfileImg.getProfileImg()));

	}

	public String getUserName() {
		return (this.correo.substring(0, this.correo.lastIndexOf('@')));
	}

	public ArrayDeque<Mensaje> getUnread() {

		Stack<Mensaje> aux = (Stack<Mensaje>) this.mensajes.clone();
		boolean ismore = true;
		ArrayDeque<Mensaje> noleidos = new ArrayDeque<Mensaje>();
		Mensaje msg = null;
		while (ismore) {
			msg = aux.pop();
			if (msg.isUnread())
				noleidos.add(msg);
			else ismore = false;
		}
		return noleidos;
	}

	public Stack<Mensaje> getMensajes() {
		return this.mensajes;
	}
	public boolean sendMensaje(Mensaje m){
		return this.mensajes.add(m);
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the tlf
	 */
	public String getTlf() {
		return tlf;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @return the pswrd
	 */
	
	/**
	 * @return the esPropietario
	 */

	/**
	 * @param profileImg the profileImg to set
	 */
	public void setProfileImg(ProfileImg profileImg) {
		ProfileImg = profileImg;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param tlf the tlf to set
	 */
	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @param pswrd the pswrd to set
	 */
	public void setPswrd(String pswrd) {
		this.pswrd = pswrd;
	}

	/**
	 * @param esPropietario the esPropietario to set
	 */

	/**
	 * @param casas the casas to set
	 */

	/**
	 * @param mensajes the mensajes to set
	 */
	public void setMensajes(Stack<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public utilities.ProfileImg getProfileImg() {
		
		return this.ProfileImg;
	}

	

	

	

	
}
