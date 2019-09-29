package servidor;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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
					System.out.println(listadoArchivos);
					LOGGER.log(Level.INFO, dlg+"Enviando lista de archivos al cliente");
				}				

			}else {
				ss.close();
				LOGGER.log(Level.WARNING, dlg+" Error estableciendo conexión con el cliente");
			}			
			//CronometroThread ct;

			//ct= new CronometroThread(this.ss);
			//ct.run();
		
			linea = dc.readLine();
			//ct.destroy();x
			LOGGER.log(Level.WARNING, dlg+"  Leyendo archivo deseado, preparando archivo para el envío");
			int in;
			File localFile = new File( "./data/"+linea );				
			bis = new BufferedInputStream(new FileInputStream(localFile)); 
			bos = new BufferedOutputStream(ss.getOutputStream()); //Canal para enviar archivo
			dos=new DataOutputStream(ss.getOutputStream());  //Escritor del archivo
			System.out.println(localFile.getAbsolutePath());
			dos.writeUTF(localFile.getName());
			System.out.println("Escrito UTF");
			//Envio del archivo
			
			double tamano=localFile.length();
			double buffer=1024;
			double tam=tamano/buffer;			
			long paquetes=(long) Math.ceil(tam);
			MessageDigest messageDigest = MessageDigest.getInstance("SHA");				
			byte[] byteArray = new byte[1024];				
			while ((in = bis.read(byteArray)) != -1){					
				messageDigest.update(byteArray);					
			}
			byte[] resumen = messageDigest.digest();
			String s="";
			for (int i = 0; i < resumen.length; i++)
			{
				s += Integer.toHexString((resumen[i] >> 4) & 0xf);
				s += Integer.toHexString(resumen[i] & 0xf);
			}
			ac.println(paquetes); 
			ac.println(s);
			ac.println(System.currentTimeMillis());
			bis = new BufferedInputStream(new FileInputStream(localFile));
			int inat =0;
			while ((in = bis.read(byteArray)) != -1){
				bos.write(byteArray,0,in);
				messageDigest.update(byteArray);
				System.out.println(in);
			}			
			bos.close();
			LOGGER.log(Level.INFO, dlg+" Se envio el archivo");
		
		} catch (IOException | NoSuchAlgorithmException e) {
			LOGGER.log(Level.WARNING, dlg+" Error I/O, cerrando conexión... ");
			e.printStackTrace();
		}
	}
}