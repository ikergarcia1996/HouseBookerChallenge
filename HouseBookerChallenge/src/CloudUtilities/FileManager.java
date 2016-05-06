package CloudUtilities;

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
		e.printStackTrace();
		
	}
	}
}