package cliente;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente{
	//---------------Constantes para el protocolo----------------------
	private static String SYN="SYN";
	private static String SYNACK="SYN,ACK";
	private static String ACK="ACK";
	/**
	 * Conexión al servidor
	 */
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
	 * Lector: lee la información del Socket
	 */
	private BufferedReader lector;
	/**
	 * Escritor: escribe información en el socket
	 */
	private PrintWriter escritor;
	/**
	 *  Escritor auxiliar
	 */
	private static BufferedInputStream bis;

	/**
	 * Constructor de la clase cliente, inicializa los atributos del cliente
	 */

	public Cliente() {		
		try {
			connection = new Socket("localhost", 8081);
			escritor = new PrintWriter(	connection.getOutputStream(), true);
			lector = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			listaArchivos= new ArrayList<>();			
			String fromServer;
			String fromUser;			

			//Inicialización lista archivos
			listaArchivos= new ArrayList<>();
			//---Fase 1 del protocolo, el cliente envía SYN-----

			fromUser = SYN;
			escritor.println(fromUser);			
			LOGGER.log(Level.INFO, "Solicitando conexion");
			fromServer = lector.readLine();
			if(fromServer.equalsIgnoreCase(SYNACK)) {				
				LOGGER.log(Level.INFO, "Conexión extablecida");
				fromUser=ACK;
				escritor.println(fromUser);	
				fromServer = lector.readLine();
				String[] lista = fromServer.split("/");
				System.out.println(fromServer);
				for (int i = 0; i < lista.length; i++) {
					listaArchivos.add(lista[i]);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main (String[] args){
		Cliente cliente = new Cliente();
	} 


	public void cerrarTodo() {					
		try {
			connection.close();
			escritor.close();
			lector.close();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Se ha productido un error "+ e.getMessage());
			e.printStackTrace();
		}
	}


	public void seleccionarYDescargarArchivo(String archivo) {
		try {
			//-----Fase 5: Se escoje un archivo
			escritor.println(archivo);		
			System.out.println("Escrito: "+archivo);
			//----Fase 6: recibir archivo
			byte[] receivedData = new byte[1024];
			bis = new BufferedInputStream(connection.getInputStream());		
			System.out.println("La conexión esta cerrada? "+connection.isClosed());
			DataInputStream dis=new DataInputStream(connection.getInputStream());
			String file=dis.readUTF();			
			file = file.substring(file.indexOf('\\')+1,file.length());
			System.out.println(file.substring(file.indexOf('\\')+1,file.length()));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			
			int in;				
			int numeroPaquetes=Integer.parseInt(lector.readLine());
				
			System.out.println(numeroPaquetes);
			
			while (numeroPaquetes-->0){				
				System.out.println(numeroPaquetes);
				in = bis.read(receivedData);		
				System.err.println(in);
				bos.write(receivedData,0,in);
				if(in!=1024) {
					break;
				}
			}						
			bos.close();
		}
		catch(Exception iOException) {
			//TODO LOG
		}
	}
	
	public ArrayList<String> getListaArchivos() {
		return listaArchivos;
	}

}