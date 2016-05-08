package CloudUtilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

	//Para borrar del directorio donde se esta ejecutand la app:
	//fileRoute.append(System.getProperty("user.dir"));
	
	
	// fl = nombre del fichero a borrar (se borra de la ruta donde esta
	// ejecutandose
	// El programa).
	
	public void removeFile(String route) {
		
		System.out.println("Borrando el fichero " + route);
		try {
			Files.delete(Paths.get(route));
		} catch (IOException e) {
			System.out.println("Error al borrar el archivo");
			e.printStackTrace();

		}
	}
	
	public String GetTempRoute(){
		StringBuilder str = new StringBuilder();		
				Process p = null;
		try {
			p = Runtime.getRuntime().exec("cmd.exe /c echo %TMP%");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		BufferedReader br = new BufferedReader 
		( new InputStreamReader( p.getInputStream() ) ); 
		String tmp = null;
		try {
			tmp = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		str.append(tmp);
		str.append("\\HBC\\");
	return str.toString();
	}
	
	public void createDirectory(String ruta, String nombre) {
		File theDir = new File(ruta+"\\"+nombre);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException e){
		        e.printStackTrace();
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
		
	}
	
}
