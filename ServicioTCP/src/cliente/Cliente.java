package cliente;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
 
class Cliente{
	
	/**
	 * 
	 */
	private ServerSocket server;
	/**
	 * Socket de conexión con el servidor
	 */
	private static Socket connection;

	private DataOutputStream output;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	/**
	 * Log del servidor
	 */
	 private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Control");
	 
	/**
	 * Lista de archivos
	 */
	private ArrayList<String> listaArchivos;
	
	public void descargarArchivo() {
		
		byte[] receivedData;
		int in;
		String file;

		try{
			//Servidor Socket en el puerto 8081
			server = new ServerSocket( 8081 );
			while ( true ) {
				//Aceptar conexiones
				connection = server.accept();
				//Buffer de 1024 bytes
				receivedData = new byte[1024];
				bis = new BufferedInputStream(connection.getInputStream());
				DataInputStream dis=new DataInputStream(connection.getInputStream());
				//Recibimos el nombre del fichero
				file = dis.readUTF();
				file = file.substring(file.indexOf('\\')+1,file.length());
				//Para guardar fichero recibido
				bos = new BufferedOutputStream(new FileOutputStream(file));
				while ((in = bis.read(receivedData)) != -1){
					bos.write(receivedData,0,in);
				}
				bos.close();
				dis.close();
			}
		}catch (Exception e ) {
			System.err.println(e);
		}
	}
	
	public static void main (String[] args){
		try {
			
			connection = null;
			PrintWriter escritor = null;		
			BufferedReader lector = null;


			connection = new Socket("192.168.0.10", 8081);
			escritor = new PrintWriter(
					connection.getOutputStream(), true);
			lector = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String fromServer;
			String fromUser;
			
			//HOLA
			fromUser = "SYN";
			LOGGER.log(Level.INFO, "Envío de conexión");
			escritor.println(fromUser);

			fromServer = lector.readLine();
			System.out.println("Servidor: " + fromServer);
			LOGGER.log(Level.INFO, "Conexión extablecida");
			if(fromServer == "ERROR"){
				System.out.println("falló");
				escritor.close();
				lector.close();
				stdIn.close();
			}
			
			fromServer = lector.readLine();
			
			System.out.println("Servidor: " + fromServer);
			
			if(fromServer == "ERROR"){
				System.out.println("falló");
				escritor.close();
				lector.close();
				stdIn.close();
			}
			
			fromUser = "ACK";
			System.out.println("Cliente: " + fromUser);
			escritor.println(fromUser);
			
			fromUser = escogerArchivo();
			System.out.println("Cliente: " + fromUser);
			escritor.println(fromUser);
			
			
			escritor.close();
			lector.close();

			stdIn.close();
			connection.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static String escogerArchivo() {
		// TODO Auto-generated method stub
		return null;
	}
}