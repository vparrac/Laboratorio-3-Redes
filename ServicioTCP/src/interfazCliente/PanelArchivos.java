package interfazCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PanelArchivos extends JPanel implements ActionListener{
	
	private InterfazCliente principal;
	JButton boton100Mib;
	
	public PanelArchivos( InterfazCliente ventanaPrincipal )
    {
        principal = ventanaPrincipal;
        crearPanel( );
    }

	public void crearPanel(){
		setLayout( new BorderLayout() );
		
		JButton boton100Mib = new JButton( );
		boton100Mib.setEnabled( false );
		boton100Mib.setOpaque( false );
		boton100Mib.setBorder( new LineBorder( Color.BLACK ) );
		boton100Mib.setActionCommand( "D100Mib" );
		boton100Mib.addActionListener( this );
		boton100Mib.setText("100 Mb");
		boton100Mib.setEnabled( true );		
		
		JButton boton250Mib = new JButton( );
		boton250Mib.setEnabled( false );
		boton250Mib.setOpaque( false );
		boton250Mib.setBorder( new LineBorder( Color.BLACK ) );
		boton250Mib.setActionCommand( "D250Mib" );
		boton250Mib.addActionListener( this );
		boton250Mib.setText("250 Mb");
		boton250Mib.setEnabled( true );
		
		add(boton100Mib, BorderLayout.NORTH);
		add(boton250Mib, BorderLayout.SOUTH);
			
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando = e.getActionCommand( );

        if( "D100Mib".equals( comando ) )
        {
            System.out.println("D100");
        }
        else if( "D250Mib".equals( comando ) )
        {
            System.out.println("D250");
        }
        
	}

}
