package interfazCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PanelDescargas extends JPanel{
	
	private InterfazCliente principal;
	private JLabel tiempo;

	public PanelDescargas( InterfazCliente ventanaPrincipal )
    {
        principal = ventanaPrincipal;
        crearPanel( );
    }
	
	public void crearPanel(){
		setLayout( new BorderLayout() );
		
		tiempo = new JLabel("No hay descargas");
		tiempo.setHorizontalAlignment(JLabel.CENTER);
	    tiempo.setVerticalAlignment(JLabel.CENTER);
		
		JLabel espacio = new JLabel(" ");
		
		add(espacio, BorderLayout.WEST);
		add(tiempo, BorderLayout.CENTER);
			
	}
	
}
