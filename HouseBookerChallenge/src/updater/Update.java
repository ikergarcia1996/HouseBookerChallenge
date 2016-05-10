package updater;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Update {

	public static void main(String[] args) {
		Update u = new Update();
		u.removeFile("HouseBookerChallenge.jar");
		u.renameFile("newVersion.jar");
		StringBuilder fileRoute = new StringBuilder();
		fileRoute.append(System.getProperty("user.dir"));
		fileRoute.append("\\HouseBookerChallenge.jar");
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", fileRoute.toString());
		System.exit(0);

	}

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
	
	public void renameFile(String fl){
		StringBuilder fileRoute = new StringBuilder();  
		fileRoute.append(System.getProperty("user.dir"));
		fileRoute.append("\\");
		fileRoute.append(fl);
		Path source = Paths.get(fileRoute.toString());
		try {
			Files.move(source, source.resolveSibling("HouseBookerChallenge.jar"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
