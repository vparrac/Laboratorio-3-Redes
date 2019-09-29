package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PanelListar extends JPanel implements ListSelectionListener{

	/**
     * Lista de archivos que estan en el servidor
     */
    private JList listaArchivos;
    /**
     * Conexión a la interfaz principal
     */
    private InterfazCliente ic;
    
    /**
     * Construye el panel listar con todos sus componentes
     * @param ic interfaz principal del cliente
     */
    
    public PanelListar(InterfazCliente ic) {
    	this.ic=ic; 
    	setLayout( new BorderLayout( ) );
    	listaArchivos = new JList( );
    	listaArchivos.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
    	listaArchivos.addListSelectionListener( this );
    	JScrollPane scroll = new JScrollPane( );
        scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBorder( new CompoundBorder( new EmptyBorder( 3, 3, 3, 3 ), new LineBorder( Color.BLACK, 1 ) ) );
        scroll.getViewport( ).add( listaArchivos );
        add( listaArchivos, BorderLayout.CENTER );
	}
    
    
    /**
     * Selecciona un elemento de la lista
     * @param seleccionado La posición del elemento que se debe seleccionar
     */
    public void seleccionar( int seleccionado )
    {
    	listaArchivos.setSelectedIndex( seleccionado );
    	listaArchivos.ensureIndexIsVisible( seleccionado );
    }
    
    /**
     * Retorna el archivo seleccionado de la lista
     * @return Se retornó el string del arvchivo seleccionado
     * Si no hay nada seleccionado retorna null
     */
    public String darVehiculoSeleccionado( )  {
    	String vSeleccionado = null;
        if( listaArchivos.getSelectedValue( ) != null ){
            vSeleccionado = ( String ) listaArchivos.getSelectedValue( );
        }
        return vSeleccionado;
    }    

    /**
     * Actualiza la lista de los archivos que se está mostrando
     * @param lista Es una lista con los achivos que deben mostrarse
     */
    public void actualizarLista( ArrayList lista )
    {
    	listaArchivos.setListData( lista.toArray( ) );
    	listaArchivos.setSelectedIndex( 0 );

    }
       
    /**
     * Cambia la información del archivo que se esta mostrando de acuerdo al
     * nuevo seleccionado
     * @param e El evento de cambio del ítem seleccionado en la lista. evento!=null
     */
    
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if( listaArchivos.getSelectedValue( ) != null )
        {
            String archivo = ( String )listaArchivos.getSelectedValue( );
            ic.actualizarArchivoActual( archivo );
        }	
	}

}
