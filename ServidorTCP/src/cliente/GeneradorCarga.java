package cliente;

import uniandes.gload.core.LoadGenerator;
import uniandes.gload.core.Task;

public class GeneradorCarga {
	private LoadGenerator generator;	
	public GeneradorCarga() {
		Task work = createTask();
		int numberOfTasks=80;
		int gapsBetweenTask=100;
		generator=new LoadGenerator("Cliente - Server Load Test", numberOfTasks, work, gapsBetweenTask);
		generator.generate();
	}	
	private Task createTask() {
		return new ClienteCarga();
	}
	public static void main(String[] args) {
		GeneradorCarga gen = new GeneradorCarga();
		
	}	
}
