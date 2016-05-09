package CloudUtilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Update {

	public final int CurrentVersion = 3; //ACTUALIZAR AL SACAR UNA NUEVA VERSI�N. 
	
	//Comprueba si hay una nueva versi�n, si la hay devuelve un objeto de la clase NewVersion (mirar m�s abajo) en caso contrario, devuelve null
	  
	
	public NewVersion testForUpdates (){ 
		JavaDownload jv = new JavaDownload();
		int result = jv.Download("https://www.dropbox.com/s/aorbo9tsuuddxv5/nev.txt?dl=1", "NewVersion.rh");
		if (result==-1){
			return null;
			
		}
		
		if (result==-2){
			return null;
		}
		
		try {
			FileReader fr = new FileReader("NewVersion.rh");
			BufferedReader textReader = new BufferedReader(fr);
			try {
				int LastVersion = Integer.valueOf(textReader.readLine());
				
				
			
				if (LastVersion > CurrentVersion){
					System.out.println("Nueva versi�n encontrada");
					
					String url = textReader.readLine();
					String urlupdate = textReader.readLine();
					int comando = Integer.valueOf(textReader.readLine());
					
					StringBuilder notas = new StringBuilder();
					String con = textReader.readLine();
					while (con!=null){
						notas.append(con);
						notas.append("\n");
						
						con = textReader.readLine();
					}
					textReader.close();
					
					//Borrar el fichero descargado, en proceso. 
					//FileManager fl = new FileManager();
					//fl.removeFile("NewVersion.rh");
					NewVersion nw = new NewVersion(LastVersion, url, urlupdate, comando, notas.toString());
					return nw;
					
				}
				else {
					System.out.println("No existen nuevas versiones");
					textReader.close();
					return null;}
				
				
				
			} catch (NumberFormatException | IOException e) {
				
				System.out.println("Error al leer el archivo de actualizaci�n");
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			
			System.out.println("No existe el fichero de actualizaci�n o no se ha podido abrir. Probablemente debido a un error en la descarga");
		}
		return null;
	
		
		
		
		
	}
	
	
	
	public class NewVersion{ //Esta clase sirve para devolver al que llama toda la informaci�n de la nueva versi�n
		//documento de texto en dropbox para actualizar (poner cada cosa en una l�nea, las notas pueden tener extensi�n infinita)
		
		//N� de Versi�n
		//URL nueva versi�n
		//Comandos (Es un int, sirve para enviar a la aplicaci�n alguna indicaci�n, por ejemplo si es "1" la actualizaci�n es obligatoria,
					//a�adido por si en un futuro nos sirviera de algo xD). En caso normal escribir 0.
		//Notas (aqu� se pueden escribir las novedades de la versi�n o cualquier otra cosa que se quiera mostrar en la interfaz).
		
		public int LastVersion; 
		public String LinkLastVersion;
		public String LinkUpdater;
		public int Comandos;
		public String Notas;
		
		public NewVersion(int lastVersion, String linkLastVersion,String LinkUpdater, int comandos, String notas) {
	
			LastVersion = lastVersion;
			LinkLastVersion = linkLastVersion;
			LinkUpdater= LinkUpdater;
			Comandos = comandos;
			Notas = notas;
		}
		
		public int getLastVersion() {
			return LastVersion;
		}
		public String getLinkLastVersion() {
			return LinkLastVersion;
		}
		public int getComandos() {
			return Comandos;
		}
		public String getNotas() {
			return Notas;
		}
		
		
	}
}