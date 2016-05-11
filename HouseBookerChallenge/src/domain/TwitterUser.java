package domain;

public class TwitterUser {
	
	private String correo;
	private String TwitterID;
	
	public TwitterUser(String c, String ID){
		correo = c;
		TwitterID = ID;
	}
	
	public String getCorreo(){
		return this.correo;
	}

}
