package interfaz;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFrame;

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
	 * Construye la interfaz e inicializa todos sus elementos
	 */
	
	public InterfazCliente() {
		cliente= new Cliente();		
		setLayout( new BorderLayout( ) );		
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "Servidor TCP" );

        setSize( new Dimension( 911, 576 ) );
        setResizable( false );
		
        actualizarLista();
        
        GridBagConstraints gbc = new GridBagConstraints( 0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 3, 5, 3, 5 ), 0, 0 );
        panelListar = new PanelListar( this );
        add( panelListar, gbc );

	}
	
	private void actualizarLista( ) {
        panelListar.actualizarLista( cliente.getListaArchivos());
    }
	
	public void actualizarArchivoActual(String archivoActual) {
		this.archivoActual=archivoActual;
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
