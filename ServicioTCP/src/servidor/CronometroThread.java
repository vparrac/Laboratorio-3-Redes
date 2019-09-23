package servidor;

import java.io.IOException;
import java.net.Socket;
/**
 * Thread que cierra la conexión si se excede el tiempo de espera
 * @author Valerie
 *
 */
public class CronometroThread extends Thread{

	/**
	 * Socket al que se le cerrará la conexión si se agota el tiempo de espera
	 */
	private static Socket ss;	
	/**
	 * Tiempo máximo que esperá el Thread para cerrar la conexión
	 */
	private static int TIEMPO_MAX_MILISEGUNDO=8000;
	
	
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
