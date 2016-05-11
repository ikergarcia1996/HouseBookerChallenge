package domain;

public class TwitterUser {
	
	private String correo;
	private long TwitterID;
	
	public TwitterUser(String c, long ID){
		correo = c;
		TwitterID = ID;
	}
	
	public String getCorreo(){
		return this.correo;
	}

}
