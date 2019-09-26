package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {	
	private static ServerSocket ss;	
	private static final int PUERTO=8081;
	private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Control");
	public static void main(String[] args) {
		
		try {
			ss = new ServerSocket(PUERTO);
			int idThread = 0;
			
			while(true) {
				Socket sc = ss.accept();
				new ServidorThread(sc, idThread);
			}
			
			
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, " Error I/O, cerrando conexión... ");
			e.printStackTrace();
		}
		
	}


}
