package interfazCliente;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import cliente.Cliente;

public class InterfazCliente extends JFrame{

	private PanelArchivos panelArchivos;
	private PanelDescargas panelDescargas;
	private Cliente cliente;
	
	public InterfazCliente() {
		getContentPane( ).setLayout( new BorderLayout( ) );
        setSize( 400, 300 );
        setResizable( false );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "Cliente" );
        
        panelArchivos = new PanelArchivos( this );
        getContentPane( ).add( panelArchivos, BorderLayout.NORTH );
        
        panelDescargas = new PanelDescargas(this);
        getContentPane( ).add( panelDescargas, BorderLayout.CENTER );

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfazCliente interfaz = new InterfazCliente();
		interfaz.setVisible(true);
	}

}
