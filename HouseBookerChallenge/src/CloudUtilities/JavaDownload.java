package CloudUtilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import CloudUtilities.Update.NewVersion;

public class JavaDownload {

	//ES NECESARIO PROPORCIONAR UN LINK DE DESCARGA DIRECTA A ESTA FUNCIÓN:
	//SE PUEDE USAR DROPBOX, GOOGLE DRIVE O ONE DRIVE
	//SE PUEDE USAR ESTA WEB APRA CONSEGUIR EL ENLACE DIRECTO: http://www.syncwithtech.org/p/direct-download-link-generator.html
	//NOTA: EN el caso de dropbox con cambiar ?dl=0 a ?dl=1 sirve.
	 
	//Devuelve:
	//0 -> Todo ok
	//-1 -> Error en la conexión (probablemente no hay internet).
	//-2 -> Otro tipo de error (Sin permisos para escribir, url no válida, apocalípsis zombie...). 
	public int Download (String link, String fileName){
		System.out.println("Iniciando descarga del archivo: " + fileName + " desde la url: " + link);
		URL url;
		FileOutputStream fos = null;
		try {
			url = new URL(link);
			ReadableByteChannel rbc;
			try {
				rbc = Channels.newChannel(url.openStream());
				
				try {
					fos = new FileOutputStream(fileName);
					try {
						fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
						fos.close();	
						return 0;
					
					}
					
					catch (IOException e) {
						System.out.println("Error en la transferencia");
						e.printStackTrace();
						fos.close();
						return -1;
					}	
				} catch (FileNotFoundException e) {
					System.out.println("Error al crear el archivo de destino");
					e.printStackTrace();
					fos.close();
					return -2;
				}
			} catch (IOException e1) {
				//AQUI PETA CUANDO NO HAY INTERNET.
				System.out.println("Error al abrir el canal de descarga");
				e1.printStackTrace();
				return -1;
			}
			
			
		} catch (MalformedURLException e) {
			System.out.println("Error en la URL");
		}
		return -2;
	
		
	}
	 public void openURLinExplorer(String url) {
	        String osName = System.getProperty("os.name");
	        try {
	            if (osName.startsWith("Windows")) {
	                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
	            } else if (osName.startsWith("Mac OS X")) {
	                // Runtime.getRuntime().exec("open -a safari " + url);
	                // Runtime.getRuntime().exec("open " + url + "/index.html");
	                Runtime.getRuntime().exec("open " + url);
	            } else {
	                System.out.println("Please open a browser and go to "+ url);
	            }
	        } catch (IOException e) {
	            System.out.println("Failed to start a browser to open the url " + url);
	            e.printStackTrace();
	        }
	    }
	 public String getFileName(URL extUrl) {
			//URL: "http://photosaaaaa.net/photos-ak-snc1/v315/224/13/659629384/s659629384_752969_4472.jpg"
			String filename = "";
			//PATH: /photos-ak-snc1/v315/224/13/659629384/s659629384_752969_4472.jpg
			String path = extUrl.getPath();
			//Checks for both forward and/or backslash 
			//NOTE:**While backslashes are not supported in URL's 
			//most browsers will autoreplace them with forward slashes
			//So technically if you're parsing an html page you could run into 
			//a backslash , so i'm accounting for them here;
			String[] pathContents = path.split("[\\\\/]");
			if(pathContents != null){
				int pathContentsLength = pathContents.length;
				System.out.println("Path Contents Length: " + pathContentsLength);
				for (int i = 0; i < pathContents.length; i++) {
					System.out.println("Path " + i + ": " + pathContents[i]);
				}
				//lastPart: s659629384_752969_4472.jpg
				String lastPart = pathContents[pathContentsLength-1];
				String[] lastPartContents = lastPart.split("\\.");
				if(lastPartContents != null && lastPartContents.length > 1){
					int lastPartContentLength = lastPartContents.length;
					System.out.println("Last Part Length: " + lastPartContentLength);
					//filenames can contain . , so we assume everything before
					//the last . is the name, everything after the last . is the 
					//extension
					String name = "";
					for (int i = 0; i < lastPartContentLength; i++) {
						System.out.println("Last Part " + i + ": "+ lastPartContents[i]);
						if(i < (lastPartContents.length -1)){
							name += lastPartContents[i] ;
							if(i < (lastPartContentLength -2)){
								name += ".";
							}
						}
					}
					String extension = lastPartContents[lastPartContentLength -1];
					filename = name + "." +extension;
					System.out.println("Name: " + name);
					System.out.println("Extension: " + extension);
					System.out.println("Filename: " + filename);
				}
			}
			return filename;
		}
}
