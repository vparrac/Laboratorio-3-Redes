package cliente;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;

import uniandes.gload.core.Task;

public class ClienteCarga extends Task {

	private ClienteJM cliente;
	
	private String error;
	
	@Override
	public void fail() {		
		long tiempo= System.currentTimeMillis();
		Date fechaActual= new Date(tiempo);
		BufferedWriter bw = null;
	    FileWriter fw = null;
	    try {
	        String data = "Se produjo algun error "+error;
	        File file = new File("./logs/"+fechaActual.toString()+tiempo);	        
	        synchronized (file) {
	        	if (!file.exists()) {
		            file.createNewFile();
		        }
		        // flag true, indica adjuntar información al archivo.
		        fw = new FileWriter(file.getAbsoluteFile(), true);
		        bw = new BufferedWriter(fw);
		        bw.write(data);	  
			}
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (bw != null)
	                bw.close();
	            if (fw != null)
	                fw.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
		
	}

	@Override
	public void success() {
		System.out.println(Task.OK_MESSAGE);		
	}

	@Override
	public void execute() {
		try {
			System.out.println("Iniciando conexión");		
			this.cliente=new ClienteJM();			
			this.cliente.seleccionarYDescargarArchivo(cliente.getListaArchivos().get(1));
		}
		catch (Exception e) {
			this.error=e.getMessage();
			e.printStackTrace();
			System.out.println(error);
			fail();
		}
		
	}
	

}
