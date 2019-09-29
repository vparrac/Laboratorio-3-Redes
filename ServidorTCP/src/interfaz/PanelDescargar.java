package interfaz;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PanelDescargar extends JPanel implements ActionListener {
	
	
	/**
     * El comando para el botón descargar
     */
    private final String DESCARGAR = "DESCARGAR";

    /**
     * Conexión a la interfaz principal del cliente
     */
    
    private InterfazCliente interfazCliente;

    /**
     * Botón de descargar
     */
    
    private JButton botonDescargar;
    
    public PanelDescargar(InterfazCliente ic) {
    	this.interfazCliente=ic;
    	setBorder( new TitledBorder( "Puntos de Extensión" ) );
        setLayout( new FlowLayout( ) );
        botonDescargar = new JButton( "Descargar" );
        botonDescargar.setActionCommand( DESCARGAR );
        botonDescargar.addActionListener( this );
        add( botonDescargar );
    }
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String comando = arg0.getActionCommand( );
        if( DESCARGAR.equals( comando ) )
        {
            interfazCliente.descargarArchivoSeleccionado();
        }
      
	}

}
