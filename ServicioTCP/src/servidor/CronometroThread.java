package servidor;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;
/**
 * Thread que cierra la conexi�n si se excede el tiempo de espera
 * @author Valerie
 */
public class CronometroThread extends Thread{

	/**
	 * Socket al que se le cerrar� la conexi�n si se agota el tiempo de espera
	 */
	private static Socket ss;	
	/**
	 * Tiempo m�ximo que esper� el Thread para cerrar la conexi�n
	 */
	private static int TIEMPO_MAX_MILISEGUNDO=5000;
	 private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Control");
	/**
	 * Constructor del cronometro
	 * @param ss Socker para escuchar
	 */
	public CronometroThread(Socket ss) {
		this.ss=ss;
	}
	
	@Override
	public void run() {
		try {
			this.sleep(TIEMPO_MAX_MILISEGUNDO);
			this.ss.close();
			
		} catch (InterruptedException e) {
			// TODO PONERLO EN EL LOg
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}	
}