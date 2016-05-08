package CloudUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
 
public class FileManager {

	
	//fl = nombre del fichero a borrar (se borra de la ruta donde esta ejecutandose
	//El programa). 
	public void removeFile (String fl){
	StringBuilder fileRoute = new StringBuilder();  
	fileRoute.append(System.getProperty("user.dir"));
	fileRoute.append("\\");
	fileRoute.append(fl);
	System.out.println("Borrando el fichero " + fileRoute.toString());
	try {
		Files.delete(Paths.get(fileRoute.toString()));
	} catch (IOException e) {
		System.out.println("Error al borrar el archivo");
		
	}
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