package domain;

public class Mensaje {
	private Usuario Destinatario;
	private String Remite;
	private String Asunto;
	private String Detalles;
	private String Contenido;
	private boolean isUnread=true;
	
	public Mensaje(Usuario destinatario, String remite,String asunto,String detalles,String contenido){
		this.Destinatario = destinatario;
		this.Remite=remite;
		this.Asunto=asunto;
		this.Detalles=detalles;
		this.Contenido=contenido;
	}
	public Mensaje() {
		super();
	}
	/**
	 * @return the remite
	 */
	public String getRemite() {
		return Remite;
	}
	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return Asunto;
	}
	/**
	 * @return the detalles
	 */
	public String getDetalles() {
		return Detalles;
	}
	/**
	 * @return the contenido
	 */
	public String getContenido() {
		return Contenido;
	}
	
	public boolean isUnread() {
		return this.isUnread;
	}
	public void setReaden() {
		this.isUnread=false;
		
	}
	public void setUnReaden() {
		this.isUnread=true;
		
	}
	public void setDetalles(String detalles) {
		Detalles = detalles;
	}

}
