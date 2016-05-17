package CloudUtilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class JavaDownload {

	// ES NECESARIO PROPORCIONAR UN LINK DE DESCARGA DIRECTA A ESTA FUNCIÓN:
	// SE PUEDE USAR DROPBOX, GOOGLE DRIVE O ONE DRIVE
	// SE PUEDE USAR ESTA WEB APRA CONSEGUIR EL ENLACE DIRECTO:
	// http://www.syncwithtech.org/p/direct-download-link-generator.html
	// NOTA: EN el caso de dropbox con cambiar ?dl=0 a ?dl=1 sirve.

	// Devuelve:
	// 0 -> Todo ok
	// -1 -> Error en la conexión (probablemente no hay internet).
	// -2 -> Otro tipo de error (Sin permisos para escribir, url no válida,
	// apocalípsis zombie...).
	public int Download(String link, String fileName) {
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
				}
				catch (FileNotFoundException e) {
					System.out.println("Error al crear el archivo de destino");
					e.printStackTrace();
					fos.close();
					return -2;
				}
			}
			catch (IOException e1) {
				// AQUI PETA CUANDO NO HAY INTERNET.
				System.out.println("Error al abrir el canal de descarga");
				e1.printStackTrace();
				return -1;
			}

		}
		catch (MalformedURLException e) {
			System.out.println("Error en la URL");
		}
		return -2;

	}

	public void openURLinExplorer(String url) {
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Windows")) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			}
			else if (osName.startsWith("Mac OS X")) {
				// Runtime.getRuntime().exec("open -a safari " + url);
				// Runtime.getRuntime().exec("open " + url + "/index.html");
				Runtime.getRuntime().exec("open " + url);
			}
			else {
				System.out.println("Please open a browser and go to " + url);
			}
		}
		catch (IOException e) {
			System.out.println("Failed to start a browser to open the url " + url);
			e.printStackTrace();
		}
	}

	public static boolean isReachableTime(int time,String servidor) {
		Thread Solve = new Thread() {
			public void run() {
				System.out.println("Pasando por Solve");
				try {
					InetAddress.getByName(servidor);
				}
				catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Terminado Solve");
			}
		};
		Thread Time = new Thread() {
			public void run() {
				System.out.println("Pasando por Time");
				try {
					Thread.sleep(time);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Terminando Time");
			}
		};
		Solve.start();
		Time.start();
		while(Solve.isAlive()){
			if(!Time.isAlive()){Solve.destroy(); return false;}
		}
		Time.interrupt();
		return true;
	}
}