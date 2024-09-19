package View;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import Modelo.Sonido;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.ActionEvent;

public class PantallaMapa {

	private JFrame frame;
	private JMapViewer mapa;
	private Sonido sonido;
	private int zoomDefault = 12;
	private Coordinate coordinate = new Coordinate(-34.521, -58.719);
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaMapa window = new PantallaMapa();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PantallaMapa() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		sonido = new Sonido();
		frame.setBounds(400, 200, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mapa = new JMapViewer();
		mapa.setFocusable(false);
		mapa.setZoomControlsVisible(false);
		
	
		
		mapa.setDisplayPosition(this.coordinate, 12);
		mapa.setScrollWrapEnabled(false);  
		MapMarkerDot marcador1 = new MapMarkerDot("Aquí", this.coordinate);
		marcador1.getStyle().setBackColor(Color.blue);
		marcador1.getStyle().setColor(Color.blue);
		mapa.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
			    int newZoom = mapa.getZoom();
			    if (zoomDefault < newZoom) {
			    	mapa.setZoom(zoomDefault);
			    }
			    
			    if (zoomDefault >
				 newZoom) {
			    	mapa.setZoom(zoomDefault);
			    }
			    mapa.setDisplayPosition(coordinate, 12);
			
			}
			
		});
	
		
		Coordinate coordinate2 = new Coordinate(-34.521, -58.709);
		MapMarkerDot marcador2 = new MapMarkerDot("Otro", coordinate2);
		marcador2.getStyle().setBackColor(Color.yellow);
		marcador2.getStyle().setColor(Color.yellow);
		mapa.addMapMarker(marcador2);
		mapa.addMapMarker(marcador1);
	
		frame.getContentPane().add(mapa);
		
		
		
		sonido.reproducirSonido("/resources/espiasTheme.wav", "menu");
		JButton btnIntroducción = new JButton("Cliqueame ");
		btnIntroducción.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Tenemos una conjunto de espias en territorio enemigo, y necesitamos enviarles un mensaje\r\n"
						+ "a todos ellos. La comunicaci´on se realiza de manera personal entre cada par de esp´ıas: se\r\n"
						+ "encuentran en un punto preacordado y se pasan el mensaje en un papel que se autodestruye\r\n"
						+ "luego de unos segundos. No todos los pares de esp´ıas pueden encontrarse, y representamos\r\n"
						+ "esta situaci´on por medio de un grafo G = (V, E) con un v´ertice por cada esp´ıa y una arista\r\n"
						+ "por cada par de esp´ıas que pueden encontrarse. Para cada arista ij ∈ E, tenemos adem´as la\r\n"
						+ "probabilidad pij ∈ [0, 1] de que el enemigo intercepte a los esp´ıas durante el encuentro y se\r\n"
						+ "arruine el operativo.\r\n"
						+ "Para enviarles el mismo mensaje a toda nuestra red de espias, buscamos un ´arbol generador de\r\n"
						+ "G y los mensajes se transmiten a lo largo de las aristas de este ´arbol generador. Un cuello de\r\n"
						+ "botella de un ´arbol generador es su arista de mayor peso. En nuestro caso, buscamos un ´arbol\r\n"
						+ "generador con el cuello de botella m´as peque˜no posible (es decir, buscamos minimizar el riesgo\r\n"
						+ "del encuentro m´as peligroso). Este problema se denomina ´arbol generador con m´ınimo cuello\r\n"
						+ "de botella. Todo ´arbol generador m´ınimo es tambi´en un ´arbol generador con m´ınimo cuello\r\n"
						+ "de botella, de modo que podemos aplicar los Algoritmos de Prim o Kruskal para resolver este\r\n"
						+ "problema.");
			}
		});
		btnIntroducción.setBounds(23, 650, 173, 100);
		mapa.add(btnIntroducción);
	}
}
