package interfaz;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cliente.Cliente;

/**
 * Interfaz del cliete
 * @author Valerie Parra Cortés
 *
 */
public class InterfazCliente extends JFrame {
	/**
	 * Referencia principal al mundo
	 */
	private Cliente cliente;
	/**
	 * Panel donde se listan los archivos
	 */	
	private PanelListar panelListar;
	
	/**
	 * String con el  nombre del archivo
	 */	
	private String archivoActual;
		
	/**
	 * Panel con el botón de descarga
	 */
	private PanelDescargar panelDescargar;
	
	
	/**
	 * Construye la interfaz e inicializa todos sus elementos
	 */
	
	
	
	
	public InterfazCliente() {
		cliente= new Cliente();		
		setLayout( new BorderLayout() );		
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "Servidor TCP" );

        setSize( new Dimension( 911, 576 ) );
        setResizable( false );    
        
        panelListar = new PanelListar( this );
        add( panelListar, BorderLayout.CENTER  );
        
        
        panelDescargar= new PanelDescargar(this);
        add( panelDescargar, BorderLayout.PAGE_END  );     
          
        actualizarLista();       

	}
	/**
	 * Actualiza la lista
	 */
	
	private void actualizarLista( ) {
        panelListar.actualizarLista( cliente.getListaArchivos());
    }
	
	/**
	 * Guarda el archivo actual
	 * @param archivoActual
	 */
	public void actualizarArchivoActual(String archivoActual) {
		this.archivoActual=archivoActual;
	}

	
	public void descargarArchivoSeleccionado() {
		System.out.println("Descargar archivo seleccionado");
		System.out.println(this.archivoActual);
		cliente.seleccionarYDescargarArchivo(this.archivoActual);
		if(!cliente.tiempoAgotado){
		JOptionPane.showMessageDialog(
			    null, 
			    "El archivo se ha recibido con éxito! \n el tiempo total de transferencia [milisegundos] "+cliente.tiempoTransferencia, 
			    "Archivo recibido!",
			    JOptionPane.INFORMATION_MESSAGE
			    );		
		}else {
			JOptionPane.showMessageDialog(
				    null, 
				    "Se ha agotado el tiempo de espera del servidor",
				    "Tiempo espera agotado",
				    JOptionPane.ERROR_MESSAGE
				    ); 			
		}
		dispose();
	} 
	
	/**   
     * Ejecuta la aplicación
     * @param args Son los parámetros de ejecución de la aplicación. No deben usarse.
     */
    public static void main( String[] args )
    {
        InterfazCliente iec = new InterfazCliente( );
        iec.setVisible( true );
    }
    
    
	
}
