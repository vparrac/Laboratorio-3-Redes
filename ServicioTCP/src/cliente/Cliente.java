package cliente;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

class Cliente{
	//---------------Constantes para el protocolo----------------------
	private static String SYN="SYN";
	private static String SYNACK="SYN,ACK";
	private static String ACK="ACK";
	/**
	 * Conexión al servidor
	 */
	private static Socket connection;
	/**
	 * Log del cliente
	 */
	private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Control");
	/**
	 * Lista de archivos disponibles en el servidor
	 */
	private static ArrayList<String> listaArchivos;
	/**
	 * Lector: lee la información del Socket
	 */
	private static BufferedReader lector;
	/**
	 * Escritor: escribe información en el socket
	 */
	private static PrintWriter escritor;

	/**
	 * Método principal del cliente, se hace la conexión según el protocolo	 
	 */

	public static void main (String[] args){
		try {			
			//Pide la conexión al servidor
			connection = new Socket("localhost", 8081);
			//Canales de lectura y escritura del socket
			escritor = new PrintWriter(	connection.getOutputStream(), true);
			lector = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//Strings auxiliares
			String fromServer;
			String fromUser;
			
			//---Fase 1 del protocolo, el cliente envía SYN-----
			fromUser = SYN;
			escritor.println(fromUser);			
			LOGGER.log(Level.INFO, "Solicitando conexion");
			
			//---Fase 2: Se lee la respuesta del servidor----
			fromServer = lector.readLine();

			//Si la respuesta del servidor es la esperada según el protocolo
			if(fromServer.equalsIgnoreCase(SYNACK)) {
				//Se es
				LOGGER.log(Level.INFO, "Conexión extablecida");
				
				// -----Fase 3: Se envía el agradecimiento del protocolo-----
				fromUser=ACK;
				escritor.println(fromUser);	
				
				//------Fase 4: Se recibe la lista de archivos del servidor
				fromServer = lector.readLine();
				String[] lista = fromServer.split("/");
				for (int i = 0; i < lista.length; i++) {
					listaArchivos.add(lista[i]);
				}
				//-----Fase 5: Se escoje un archivo
				System.out.println("Escoja el archivo");
				System.out.println(Arrays.toString(lista));
				
				BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
				String seleccionUsuario=br.readLine();
				
				fromUser=seleccionUsuario;
				escritor.println(fromUser);
				
				
				
			}
			
		
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	
	private static void cerrarTodo() {					
		try {
			connection.close();
			escritor.close();
			lector.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void descargarArchivo() {



	}


	public static void escogerArchivo(String archivo) {
		
	}
}