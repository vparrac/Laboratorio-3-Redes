package interfaz;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PanelDescargar extends JPanel implements ActionListener {
	
	
	/**
     * El comando para el bot�n descargar
     */
    private final String DESCARGAR = "DESCARGAR";

    /**
     * Conexi�n a la interfaz principal del cliente
     */
    
    private InterfazCliente interfazCliente;

    /**
     * Bot�n de descargar
     */
    
    private JButton botonDescargar;
    
    public PanelDescargar(InterfazCliente ic) {
    	this.interfazCliente=ic;
    	setBorder( new TitledBorder( "Puntos de Extensi�n" ) );
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
