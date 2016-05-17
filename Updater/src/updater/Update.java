package updater;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


public class Update {

	public static void main(String[] args) {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e1) {
			
		}
		Update u = new Update();
		u.removeFile("HouseBookerChallenge.jar");
		u.renameFile("newVersion.jar");
		u.removeFile("newVersion.jar");
		StringBuilder fileRoute = new StringBuilder();
		fileRoute.append(System.getProperty("user.dir"));
		fileRoute.append("\\HouseBookerChallenge.jar");
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", fileRoute.toString());
		try {
			pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		StringBuilder fileRoute2 = new StringBuilder();  
		fileRoute2.append(System.getProperty("user.dir"));
		fileRoute2.append("\\");
		fileRoute2.append("HouseBookerChallenge.jar");
		Path source2 = Paths.get(fileRoute2.toString());
		try {
			Files.copy(source, source2);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
