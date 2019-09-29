package interfaz;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
