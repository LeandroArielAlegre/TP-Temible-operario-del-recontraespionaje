package View;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import Modelo.Sonido;
import Modelo.LogicaDeGrafoEspias;
import Presentador.PresentadorMapa;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class PantallaMapa {

	private JFrame frame;
	private JMapViewer mapa;
	private Sonido sonido;
	private Coordinate mapaActual = new Coordinate(-34.521, -58.719);
	private int zoomActual = 12;
	private PresentadorMapa presentadorMapa;
	private JTextField textFieldParaCoordenadaY;
	private JTextField textFieldParaCoordenadaX;
	private Double coordenadaXGlobal;
	private Double coordenadaYGlobal;
	
	private JTextField textfieldNombreVertice;
	private JPanel panelControles;
	private HashMap <String ,Coordinate> hashMapVertices;
	private JButton btnCrearArista;
	private JTextField textFieldVertice2;
	private JTextField textFieldVertice1;
	private JTextField textFieldProbabilidad;
	
	
	
//a
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
	 * Create the application.a
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
		hashMapVertices = new HashMap<>();
    	presentadorMapa = new PresentadorMapa();
		frame.setBounds(400, 200, 833, 676);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//panelcontrol
		panelControles = new JPanel();
		panelControles.setBounds(10, 11, 786, 615);
		panelControles.setLayout(null);
		frame.getContentPane().add(panelControles);
	
		// LA PREGUNTA
		preguntarFormaDeCargarEspias();
		
		
		
		
		MapMarkerDot marcador1 = new MapMarkerDot("Soy una prueba", this.mapaActual);
		marcador1.getStyle().setBackColor(Color.blue);
		marcador1.getStyle().setColor(Color.blue);
		
		
		
		
		//Colocar coordenadas con un click
		
		
		
		//muestra coordenadas actuales
		//ImprimirCoordenadasActuales();
		
		
		/*
		//sonido.reproducirSonido("/resources/espiasTheme.wav", "menu");
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
		btnIntroducción.setBounds(23, 539, 132, 42);
		//mapa.add(btnIntroducción);
		panelControles.add(btnIntroducción);
		*/
		
		
		
		
		
		JButton btnCrearVertice = new JButton("Crea un vertice");
		btnCrearVertice.setBounds(26, 542, 178, 44);
		//mapa.add(btnCrearVertice);
		panelControles.add(btnCrearVertice);
		btnCrearVertice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(textfieldNombreVertice.getText() != null && textfieldNombreVertice.getText().toString().length() != 0) {
					if(coordenadaXGlobal != null && coordenadaYGlobal != null) {
						
							String nombreVertice = textfieldNombreVertice.getText().toString();
							crearVerticeEnMapa(coordenadaXGlobal, coordenadaYGlobal, nombreVertice);
							textfieldNombreVertice.setText("");
							
						
					
					}
				
				
				//Nota!!!! Se deberia agregar mas verificaciones, para que el usuario no meta cualquier valor
					
				}else {
					JOptionPane.showMessageDialog(null, "ERROR: No se puedo crear el Vertice");
				}
				
			}
		});
		
		//Coloco los textFields en la pantalla
		colocarTexfields();
		
		//BOTON crear arista
		
		btnCrearArista = new JButton("Crea una arista");
		btnCrearArista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldVertice1.getText() != null  
						&& textFieldVertice2.getText() !=null && textFieldProbabilidad.getText() != null) {
					String nombreVertice1 = textFieldVertice1.getText().toString();
					String nombreVertice2 = textFieldVertice2.getText().toString();
					try {
						int probabilidad = Integer.parseInt(textFieldProbabilidad.getText());
						if(hashMapVertices.containsKey(nombreVertice1) && hashMapVertices.containsKey(nombreVertice2) 
								&& probabilidad == 0 || probabilidad ==1 ) {
							
							
							// SI no existe arista no lo agrego
							if(!presentadorMapa.existeArista(nombreVertice1, nombreVertice2)) {
								crearAristaEnMapa(nombreVertice1, hashMapVertices.get(nombreVertice1),nombreVertice2, hashMapVertices.get(nombreVertice2), probabilidad);
								textFieldVertice1.setText("");
								textFieldVertice2.setText("");
								textFieldProbabilidad.setText("");
							}else {
								JOptionPane.showMessageDialog(null, "No es posible crear Arista entre " + nombreVertice1 + " y " + nombreVertice2);
							}
								
						}else {
							JOptionPane.showMessageDialog(null, "Error, ingrese una probabilidad valida  y vertices validos");
						}
						
					}catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para la probabilidad.");
					}
					
				
				}
				

				
			}
		});
		btnCrearArista.setBounds(243, 542, 148, 44);
		//mapa.add(btnCrearArista);
		panelControles.add(btnCrearArista);
		
		
		
		
		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 786, 457);
		panelControles.add(mapa);
		mapa.setFocusable(false);
		mapa.setZoomControlsVisible(false);
		
	
		
		mapa.setDisplayPosition(this.mapaActual, zoomActual);
		mapa.setScrollWrapEnabled(false);  
		
		
		new DefaultMapController(mapa){

			    @Override
			    public void mouseClicked(MouseEvent e) {
			    				    
			  
			    	// Convertimos la posición del clic (en píxeles) a coordenadas geográficas
	                Coordinate coord = (Coordinate) mapa.getPosition(e.getPoint());

	                coordenadaXGlobal = coord.getLat();
	                coordenadaYGlobal = coord.getLon();
	               
			    	
			    	mapa.setDisplayPosition(mapaActual, zoomActual);
			    }
			    
			    public void mouseReleased(MouseEvent e) {
			    	mapa.setDisplayPosition(mapaActual, zoomActual);
			    }
			    
			    public void mousePressed(MouseEvent e) {
			    	mapa.setDisplayPosition(mapaActual, zoomActual);
			    }
			};
		
		
		
		mapa.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
			    mapa.setDisplayPosition(mapaActual, zoomActual);
			
			}
			
		});
		mapa.addMapMarker(marcador1);
	
		
		
	}

	/*
	private void ImprimirCoordenadasActuales() {
		//LABELS
		JLabel labelCoordX = new JLabel("" + mapaActual.getLat());
		labelCoordX.setForeground(new Color(255, 0, 0));
		labelCoordX.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelCoordX.setBounds(23, 462, 109, 33);
		//mapa.add(labelCoordX);
		panelControles.add(labelCoordX);
		
		JLabel labelCoordY = new JLabel(" " + mapaActual.getLon());
		labelCoordY.setForeground(new Color(255, 0, 0));
		labelCoordY.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelCoordY.setBounds(23, 495, 109, 33);
		//mapa.add(labelCoordY);
		panelControles.add(labelCoordY);
	}*/

	//COLOCA TEXTFIELDS!!!!!!!!!!!!!!!!!
	private void colocarTexfields() {
		//TEXTFIELDS!!!!!!!!!!!!!!!!!!!!!
		
		textFieldProbabilidad = new JTextField();
		textFieldProbabilidad.setBounds(422, 493, 86, 20);
		panelControles.add(textFieldProbabilidad);
		textFieldProbabilidad.setColumns(10);
		
		
		textfieldNombreVertice = new JTextField();
		String placeHolderTextFieldNombreVertice ="Ingrese nombre de vertice a ingresar";
		agregarPlaceHolderTexfield(textfieldNombreVertice, placeHolderTextFieldNombreVertice);
		textfieldNombreVertice.setToolTipText("");
		
		textfieldNombreVertice.setBounds(26, 511, 173, 20);
		
		//mapa.add(textfieldNombreVertice);
		panelControles.add(textfieldNombreVertice);
		textfieldNombreVertice.setColumns(10);
		
		
		
		textFieldVertice2 = new JTextField();
		String placeHolderTextFieldVertice2 ="Ingrese nombre de vertice para agregar arista";
		agregarPlaceHolderTexfield(textFieldVertice2, placeHolderTextFieldVertice2);
		textFieldVertice2.setBounds(243, 511, 178, 22);
		//mapa.add(textFieldVertice2);
		panelControles.add(textFieldVertice2);
		textFieldVertice2.setColumns(10);
		
		textFieldVertice1 = new JTextField();
		String placeHolderTextFieldVertice ="Ingrese el segundo nombre de vertice para agregar una arista";
		agregarPlaceHolderTexfield(textFieldVertice1, placeHolderTextFieldVertice);
		textFieldVertice1.setBounds(243, 468, 178, 23);
		//mapa.add(textFieldVertice1);
		panelControles.add(textFieldVertice1);
		textFieldVertice1.setColumns(10);
	}
	
	
	//La View Tiene sus propios metodos para representar marcadores en la pantalla
	private void crearVerticeEnMapa(double CoordenadasX , double CoordenadasY, String nombreVertice) {
		Coordinate vertice = new Coordinate(CoordenadasX, CoordenadasY);
		//Si el presentador me da el OKAY (es decir el presentador consulto con la logica de negocio
		// si, es posible agregar un nuevo vertice en el grafo. devuelve un boolean.
		if (presentadorMapa.crearVertice(nombreVertice) && !hashMapVertices.containsKey(nombreVertice)) {
			
			MapMarkerDot verticeEnMapa = new MapMarkerDot(nombreVertice, vertice);
			verticeEnMapa.getStyle().setBackColor(Color.yellow);
			verticeEnMapa.getStyle().setColor(Color.yellow);

			mapa.addMapMarker(verticeEnMapa);
			
			
			//Guardamos el vertices localmente en un HashMap
			hashMapVertices.put(nombreVertice, vertice);
			JOptionPane.showMessageDialog(null, "Se creo el vertice Satisfactoriamente");
			
		}else {
			JOptionPane.showMessageDialog(null, "ERROR: No se puedo crear el Vertice");
		}
		

		
	}
	
	private void crearAristaEnMapa(String nombreVertice1, Coordinate vertice1, String nombreVertice2, Coordinate vertice2, int probabilidad) {
		
		
		
		if(presentadorMapa.crearArista(nombreVertice1, nombreVertice2, probabilidad)) {
			
			List<Coordinate> aristaEnMapa = new ArrayList<>();
			aristaEnMapa.add(vertice1); 
			aristaEnMapa.add(vertice2); 
			aristaEnMapa.add(vertice1); 
	        // Crear el polígono (en este caso una línea)
	        MapPolygonImpl arista = new MapPolygonImpl(aristaEnMapa);
	        
	        arista.setColor(Color.green);
	        mapa.addMapPolygon(arista);
	        JOptionPane.showMessageDialog(null, "Se creo la Arista Satisfactoriamente");
		}else {
			JOptionPane.showMessageDialog(null, "ERROR: No se puedo crear la Arista");
		}
		
		
		
	}
	
	private void agregarPlaceHolderTexfield(JTextField textField, String placeholder) {
		textField.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (textField.getText().equals(placeholder)) {
		            textField.setText("");
		            textField.setForeground(Color.BLACK);
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        if (textField.getText().isEmpty()) {
		            textField.setText(placeholder);
		            textField.setForeground(Color.GRAY);
		        }
		    }
		});
		
	}
	private void preguntarFormaDeCargarEspias() {
		
		// Creamos un JComboBox con las opciones
        String[] eleccionCargaEspias = { "Manual", "Archivo" };
        JComboBox<String> comboBoxEleccion = new JComboBox<>(eleccionCargaEspias);
        comboBoxEleccion.setSelectedIndex(1);

        // Creamos un JLabel para mostrar el texto
        JLabel label = new JLabel("Seleccione el método de carga:");

        // Creamos un JPanel para contener el JLabel y el JComboBox
        JPanel panelInfo = new JPanel();
        panelInfo.add(label);
        panelInfo.add(comboBoxEleccion);

        // Mostramos el JOptionPane con el panel
        int resultado = JOptionPane.showConfirmDialog(null, panelInfo, 
                "¿De qué manera quiere cargar a los espías?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(resultado == JOptionPane.OK_OPTION) {
        	String seleccion = (String) comboBoxEleccion.getSelectedItem();
        	JOptionPane.showMessageDialog(null, "Usted selecciona la opcion: " + seleccion);
            //System.out.println("Método seleccionado: " + seleccion);
        
        }else if(resultado == JOptionPane.CANCEL_OPTION || resultado == JOptionPane.CLOSED_OPTION) {
        	System.exit(0);
        	//System.out.println("me cierro");
        }
        	
        
	}
}
