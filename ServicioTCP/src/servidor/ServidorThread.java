package servidor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Thread que atiende a los clientes
 * @author Valerie Parra Cortés
 */
public class ServidorThread extends Thread{

	/**
	 * Socket por donde se escucha al cliente
	 */
	private static Socket ss;	
	/**
	 * String de inicio de la conexión
	 */
	private String dlg;

	/**
	 * String que notifica que el cliente ya esta preparado para recibir archivos
	 */
	private static String PREPARADO="PREPARADO";
	private static String OK="OK";

	/**
	 * String que tiene la ruta de la carpeta donde están los archivos
	 */

	private static String sCarpAct="./data";



	/**
	 * Crea un nuevo hilo para atender al cliente
	 * @param ss Socket por el cual  se escucha al cliente
	 * @param id de este hilo
	 */
	public ServidorThread(Socket ss, int id) {
		this.ss=ss;
		dlg = new String("Servidor " + id + ": ");
	}

	@Override
	public void run() {		
		String linea;
		System.out.println(dlg + "Empezando atencion.");

		try {
			PrintWriter ac = new PrintWriter(ss.getOutputStream() , true);
			BufferedOutputStream bos = new BufferedOutputStream(ss.getOutputStream());
			BufferedReader dc = new BufferedReader(new InputStreamReader(ss.getInputStream()));
			linea = dc.readLine();
			String[] listado ;
			if (!linea.equals(PREPARADO)) {
				ac.println(OK);
				File carpeta = new File(sCarpAct);
				listado = carpeta.list();
				String listadoArchivos="";
				for (int i = 0; i < listado.length; i++) {
					listadoArchivos+=listado[i]+"/";
				}
				ac.println(listadoArchivos);				
			}else {
				ss.close();
				//TODO: ESCRIBIR EN EL LOG
			}
			linea = dc.readLine();			
			int in;
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(linea));
			byte[] byteArray = new byte[8192];
			while ((in = bis.read(byteArray)) != -1){
				bos.write(byteArray,0,in);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}