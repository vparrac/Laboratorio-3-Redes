package servidor;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
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
	private static String SYN="SYN";
	/**
	 *  String para el SYNACK
	 */
	private static String SYNACK="SYN,ACK";
	
	private static String ACK="ACK";
	
	/**
	 * Log del servidor
	 */
	 private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Control");
	/**
	 * String que tiene la ruta de la carpeta donde están los archivos
	 */

	private static String sCarpAct="./data";

	/**
	 * Crea un nuevo hilo para atender al cliente
	 * @param ss Socket por el cual  se escucha al cliente
	 * @param id de este hilo para el log
	 */
	public ServidorThread(Socket ss, int id) {
		this.ss=ss;		
		dlg = new String("Servidor " + id + ": ");
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {		
		String linea; //String que se lee
		LOGGER.log(Level.INFO, "Empezo atención");
		try {
			PrintWriter ac = new PrintWriter(ss.getOutputStream() , true);
			BufferedReader dc = new BufferedReader(new InputStreamReader(ss.getInputStream()));
			BufferedInputStream bis;
			BufferedOutputStream bos;
			DataOutputStream dos;						
			linea = dc.readLine();
			String[] listado ;
			if (linea.equals(SYN)) {
				LOGGER.log(Level.INFO, dlg+" El cliente está preparado, se procederá a enviar ");
				
				ac.println(SYNACK);
				LOGGER.log(Level.INFO, dlg+"Agradecimiento enviado");
				
				linea = dc.readLine();
				if(linea.equals(ACK)) {
					File carpeta = new File(sCarpAct);
					listado = carpeta.list();
					String listadoArchivos="";
					for (int i = 0; i < listado.length; i++) {
						listadoArchivos+=listado[i]+"/";
					}
					ac.println(listadoArchivos);				
					LOGGER.log(Level.INFO, dlg+"Enviando lista de archivos al cliente");
				}				
				
			}else {
				ss.close();
				LOGGER.log(Level.WARNING, dlg+" Error estableciendo conexión con el cliente");
			}			
			CronometroThread ct;
			while(true) {
				ct= new CronometroThread(this.ss);
				ct.run();
				linea = dc.readLine();
				ct.destroy();
				
				LOGGER.log(Level.WARNING, dlg+"  Leyendo archivo deseado, preparando archivo para el envío");
				int in;
				
				File localFile = new File( linea );				
				bis = new BufferedInputStream(new FileInputStream(localFile)); 
				bos = new BufferedOutputStream(ss.getOutputStream()); //Canal para enviar archivo
				dos=new DataOutputStream(ss.getOutputStream());  //Escritor del archivo
				dos.writeUTF(localFile.getName());
				//Envio del archivo
				byte[] byteArray = new byte[8192];
				while ((in = bis.read(byteArray)) != -1){
					bos.write(byteArray,0,in);
				}
				bos.close();
				
			}

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, dlg+" Error I/O, cerrando conexión... ");
		}
	}
}