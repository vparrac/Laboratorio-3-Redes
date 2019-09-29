package servidor;
import java.util.Arrays;
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
 * @author Valerie Parra Cort�s
 */
public class ServidorThread extends Thread{

	/**
	 * Socket por donde se escucha al cliente
	 */
	private static Socket ss;	
	/**
	 * String de inicio de la conexi�n
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
	 * String que tiene la ruta de la carpeta donde est�n los archivos
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
		LOGGER.log(Level.INFO, "Empezo atenci�n");
		try {
			PrintWriter ac = new PrintWriter(ss.getOutputStream() , true);
			BufferedReader dc = new BufferedReader(new InputStreamReader(ss.getInputStream()));
			BufferedInputStream bis;
			BufferedOutputStream bos;
			DataOutputStream dos;						
			linea = dc.readLine();
			String[] listado ;
			if (linea.equals(SYN)) {
				LOGGER.log(Level.INFO, dlg+" El cliente est� preparado, se proceder� a enviar ");
				
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
				LOGGER.log(Level.WARNING, dlg+" Error estableciendo conexi�n con el cliente");
			}			
			//CronometroThread ct;
			while(true) {
				//ct= new CronometroThread(this.ss);
				//ct.run();
				System.out.println("Pasa por aqui");
				System.out.println("Esta cerrada  ?"+ss.isClosed());
				linea = dc.readLine();
				//ct.destroy();x|
				
				LOGGER.log(Level.WARNING, dlg+"  Leyendo archivo deseado, preparando archivo para el env�o");
				int in;
				
				File localFile = new File( "./data/"+linea );				
				bis = new BufferedInputStream(new FileInputStream(localFile)); 
				bos = new BufferedOutputStream(ss.getOutputStream()); //Canal para enviar archivo
				dos=new DataOutputStream(ss.getOutputStream());  //Escritor del archivo
				System.out.println(localFile.getAbsolutePath());
				dos.writeUTF(localFile.getName());
				System.out.println("Escrito UTF");
				//Envio del archivo
				
				
				long paquetes=localFile.length()/1024;
				
				ac.println(paquetes);
				
				
				byte[] byteArray = new byte[1024];
				int inat =0;
				while ((in = bis.read(byteArray)) != -1){
					bos.write(byteArray,0,in);
					System.out.println(inat++);
					System.err.println(in);
					
				}
				//System.out.println(inant+" IN");
				//bos.write(new byte[inant],0,inant);
				//System.out.println("ByteArray:"+ Arrays.toString(byteArray));
				bos.flush();
				//bos.close();
				
			}

		} catch (IOException e) {
			LOGGER.log(Level.WARNING, dlg+" Error I/O, cerrando conexi�n... ");
			e.printStackTrace();
		}
	}
}