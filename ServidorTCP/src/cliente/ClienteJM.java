package cliente;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteJM{
	
	private static String SYN="SYN";
	private static String SYNACK="SYN,ACK";
	private static String ACK="ACK";
	
	private Socket connection;
	/**
	 * Log del cliente
	 */
	private final Logger LOGGER = Logger.getLogger("bitacora.subnivel.Control");
	/**
	 * Lista de archivos disponibles en el servidor
	 */
	private ArrayList<String> listaArchivos;
	/**
	 * Lector: lee la informacion del Socket
	 */
	private BufferedReader lector;
	/**
	 * Escritor: escribe informacion en el socket
	 */
	private PrintWriter escritor;
	/**
	 *  Escritor auxiliar
	 */
	private static BufferedInputStream bis;
	/**
	 * Long de tiempo para
	 */
	public long tiempoTransferencia;
	/**
	 * Canal de escritura para el log
	 */
	private PrintWriter  log;
	
	/**
	 * Constructor de la clase cliente, inicializa los atributos del cliente
	 */
	public ClienteJM() {		
		try {
			long tiempo=System.currentTimeMillis();
			Date fechaActual= new Date(tiempo);
			File nuevoLog= new File("./logs/"+fechaActual.toString()+tiempo);			
			FileWriter fw=new FileWriter(nuevoLog);
			BufferedWriter bw = new BufferedWriter(fw);
			log= new PrintWriter(bw);
			log.println(fechaActual.toString());
			tiempoTransferencia=0;
			connection = new Socket("192.168.43.253", 8081);
			escritor = new PrintWriter(	connection.getOutputStream(), true);
			lector = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			listaArchivos= new ArrayList<>();			
			String fromServer;
			String fromUser;			

					listaArchivos= new ArrayList<>();
			

			fromUser = SYN;
			escritor.println(fromUser);			
			LOGGER.log(Level.INFO, "Solicitando conexion");
			fromServer = lector.readLine();
			if(fromServer.equalsIgnoreCase(SYNACK)) {				
				LOGGER.log(Level.INFO, "Conexion extablecida");
				fromUser=ACK;
				escritor.println(fromUser);	
				fromServer = lector.readLine();
				String[] lista = fromServer.split("/");				
				for (int i = 0; i < lista.length; i++) {
					listaArchivos.add(lista[i]);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void cerrarTodo() {					
		try {
			connection.close();
			escritor.close();
			lector.close();
			log.close();
		} catch (IOException e) {
			log.println("Error cerrando canales ");
			e.printStackTrace();
		}
	}


	public void seleccionarYDescargarArchivo(String archivo) {
		try {
			//-----Fase 5: Se escoje un archivo
			escritor.println(archivo);		
			
			//----Fase 6: recibir archivo
			byte[] receivedData = new byte[1024];
			bis = new BufferedInputStream(connection.getInputStream());		
			
			DataInputStream dis=new DataInputStream(connection.getInputStream());
			String file=dis.readUTF();		
			log.println(file);
			file = file.substring(file.indexOf('\\')+1,file.length());
			
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

			int in;				
			int numeroPaquetes=Integer.parseInt(lector.readLine());
			log.println("Numero de paquetes esperado: "+numeroPaquetes);
			String integridad=lector.readLine();
			long tiempoInicial=Long.parseLong(lector.readLine());			
			MessageDigest messageDigest = MessageDigest.getInstance("SHA");
			int numeroPaquetesRecibidos=0;
			int numeroBytesRecibidos=0;
			while ((in=bis.read(receivedData))!=-1){
				numeroPaquetesRecibidos++;
				messageDigest.update(receivedData);
				bos.write(receivedData, 0, in);
				numeroBytesRecibidos+=in;

			}
			
//			while (numeroPaquetes-->0){
//				in=bis.read(receivedData);
//				numeroPaquetesRecibidos++;
//				messageDigest.update(receivedData);
//				bos.write(receivedData, 0, in);
//				numeroBytesRecibidos+=in;				
//
//			}
			log.println("Numero de paquetes recibido: "+numeroPaquetesRecibidos);
			log.println("Numero de bytes recibidos: "+ numeroBytesRecibidos);
			byte[] resumen = messageDigest.digest();			
			String s="";
			for (int i = 0; i < resumen.length; i++) {
				s += Integer.toHexString((resumen[i] >> 4) & 0xf);
				s += Integer.toHexString(resumen[i] & 0xf);

			}
			if(s.equals(integridad)) {
				log.println("Integridad corroborada, mensaje NO repudiado");
			}
			else {
				log.println("Prueba de integridad fallida, mensaje repudiado");
			}			
			long tiempoFinal= System.currentTimeMillis();
			this.tiempoTransferencia=tiempoFinal-tiempoInicial;
			log.println("Tiempo total de transferencia (milisegundos): "+tiempoTransferencia);
			bos.close();
			cerrarTodo();
		}
		catch(Exception iOException) {
			log.println("Error I/O, transferencia interrumpida");			
		}
	}

	public ArrayList<String> getListaArchivos() {
		return listaArchivos;
	}
	
	public static void main(String[] args) {
		Cliente cliente=new Cliente();
		cliente.seleccionarYDescargarArchivo(cliente.getListaArchivos().get(0));
		cliente.cerrarTodo();
	}
}