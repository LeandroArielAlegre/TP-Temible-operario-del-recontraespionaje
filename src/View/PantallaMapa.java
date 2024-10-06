package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import Modelo.ArchivoJSON;
import Modelo.Sonido;
import Presentador.PresentadorMapa;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
public class PantallaMapa {

	private JFrame frame;
	private JMapViewer mapa;
	private Sonido sonido;
	private Coordinate mapaActual = new Coordinate(-34.521, -58.719);
	private int zoomActual = 12;
	private PresentadorMapa presentadorMapa;

	private Double coordenadaXGlobal;
	private Double coordenadaYGlobal;
	
	private JTextField textfieldNombreVertice;
	private JPanel panelControles;
	private HashMap <String ,Coordinate> hashMapVertices;
	private HashMap<String, HashMap<String,Integer>> hashMapVerticesYVecinos;
	private JButton btnCrearArista;
	private JTextField textFieldVertice2;
	private JTextField textFieldVertice1;
	private JTextField textFieldProbabilidad;
	private JButton btnCargarEspias;
	
	
	
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
		hashMapVertices = new HashMap<>();
		hashMapVerticesYVecinos = new HashMap<>();
    	presentadorMapa = new PresentadorMapa();
		frame.setBounds(400, 200, 833, 676);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//panelcontrol
		panelControles = new JPanel();
		panelControles.setBounds(10, 11, 786, 615);
		panelControles.setLayout(null);
		frame.getContentPane().add(panelControles);
	
		
		//sonido.reproducirSonido("/resources/espiasTheme.wav", "menu");
		
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
		
		//Combobox
		JComboBox<String> comboBoxSeleccionAlgoritmo = new JComboBox<>(new String[] {"Algoritmo De Prim", "Algoritmo de Kruskal"});
		comboBoxSeleccionAlgoritmo.setFocusable(false);
		comboBoxSeleccionAlgoritmo.setBounds(569, 466, 137, 26);
		panelControles.add(comboBoxSeleccionAlgoritmo);
		
		
		
		//BOTON DE ARBOL GENERADOR MINIMO + COMBOBOX
		JButton btnAplicarAlgoritmo = new JButton("Generar Arbol generador minimo");
		btnAplicarAlgoritmo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBoxSeleccionAlgoritmo.getSelectedIndex() == 0) {
					HashMap<String, HashMap<String,Integer>> HashMapnuevoGrafo = presentadorMapa.crearArbolGeneradorMinimoPrim();
					Color color = Color.RED;
					actualizarGrafoEnMapa(HashMapnuevoGrafo, color);
				}else {
					presentadorMapa.crearArbolGeneradorMinimoKruskal();
					mapa.removeAllMapMarkers();
					mapa.removeAllMapPolygons();
				}
				
				
			}
		});
		btnAplicarAlgoritmo.setBounds(538, 493, 202, 34);
		panelControles.add(btnAplicarAlgoritmo);
		
		JButton btnRestablecerGrafo = new JButton("Restablecer Valores");
		btnRestablecerGrafo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Color color = Color.GREEN;
				actualizarGrafoEnMapa(hashMapVerticesYVecinos,color);
			}
		});
		btnRestablecerGrafo.setBounds(548, 529, 177, 26);
		panelControles.add(btnRestablecerGrafo);
		
		JButton btnGuardar = new JButton("Guardar Grafo");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreArchivo =guardarEspias();
				HashMap<String,ArrayList<Double>> nuevoHashMapNombreVerticesYCoordenadas = new HashMap<String,ArrayList<Double>>();
				nuevoHashMapNombreVerticesYCoordenadas = convertirCoordinateEnDosElementosEnUnArrayList();
				if(nombreArchivo != null) {
					
					if(presentadorMapa.guardarGrafo(hashMapVerticesYVecinos, nuevoHashMapNombreVerticesYCoordenadas, nombreArchivo)) {
						JOptionPane.showMessageDialog(null, "Archivo guardado con exito");	
				}
					
				}else {
						JOptionPane.showMessageDialog(null, "Error: No se pudo guardar el archivo");
				}	
				
			}
		});
		btnGuardar.setBounds(547, 552, 178, 25);
		panelControles.add(btnGuardar);
		
		btnCargarEspias = new JButton("Cargar Grafo");
		btnCargarEspias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreArchivo =cargarEspias();
				if(nombreArchivo != null) {
					ArchivoJSON grafoJSON = presentadorMapa.cargarGrafo(nombreArchivo);
					if(grafoJSON != null) {
						
						//Limpio todos los poligonos si existen
						mapa.removeAllMapMarkers();
						mapa.removeAllMapPolygons();
						
						HashMap<String,ArrayList<Double>> auxHashMapCoordenadas= new HashMap<String,ArrayList<Double>>();
						auxHashMapCoordenadas = grafoJSON.getGrafoPosiciones();
						HashMap<String, HashMap<String,Integer>> auxiliarHashMapVecinos = new HashMap<String, HashMap<String,Integer>>();
						auxiliarHashMapVecinos = grafoJSON.getGrafo();
					
						//dibujo
						Color color = Color.GREEN;
						
						dibujarMarcadoresYActualizarVerticesDeModelo(auxHashMapCoordenadas);
						actualizarGrafoEnMapa(grafoJSON.getGrafo(), color);
						actualizarListaDeVecinosEnModelo(auxiliarHashMapVecinos);
						
						//Actualizo el mapa de la view
						hashMapVerticesYVecinos = grafoJSON.getGrafo();
						
					}
						
				
				}else {
					JOptionPane.showMessageDialog(null, "Error: No se pudo cargar el archivo");
				}
				
				
			}

			private void actualizarListaDeVecinosEnModelo(
					HashMap<String, HashMap<String, Integer>> auxiliarHashMapVecinos) {
				for (Entry<String, HashMap<String, Integer>> entry : auxiliarHashMapVecinos.entrySet()) {
						
					HashMap<String,Integer> auxVecinos = new HashMap<String,Integer>();
					String nombreVertice1 = entry.getKey();
					auxVecinos = entry.getValue();
					
					for (Entry<String, Integer> entrada : auxVecinos.entrySet()) {
						String nombreVertice2 = entrada.getKey();
						int probabilidad = entrada.getValue();
						presentadorMapa.crearArista(nombreVertice1, nombreVertice2, probabilidad);
						
				    	}
				    }
			}

			private void dibujarMarcadoresYActualizarVerticesDeModelo(
					HashMap<String, ArrayList<Double>> auxHashMapCoordenadas) {
				for (Entry<String, ArrayList<Double>> entry : auxHashMapCoordenadas.entrySet()) {
						String nombreVertice = entry.getKey();
						
						ArrayList<Double> valor = entry.getValue();
				        Double coordenadaDoubleX = valor.get(0);
				        Double coordenadaDoubleY = valor.get(1);
						
					
						crearVerticeEnMapa(coordenadaDoubleX , coordenadaDoubleY, nombreVertice);
					
				    }
			}
		});
		btnCargarEspias.setBounds(538, 581, 188, 23);
		panelControles.add(btnCargarEspias);
		
		
		
		
		
		mapa.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
			    mapa.setDisplayPosition(mapaActual, zoomActual);
			
			}
			
		});
		
	
		
		
	}

	

	//COLOCA TEXTFIELDS!!!!!!!!!!!!!!!!!
	private void colocarTexfields() {
		//TEXTFIELDS!!!!!!!!!!!!!!!!!!!!!
		
		textFieldProbabilidad = new JTextField();
		textFieldProbabilidad.setBounds(422, 493, 86, 20);
		panelControles.add(textFieldProbabilidad);
		textFieldProbabilidad.setColumns(10);
	
		textfieldNombreVertice = new JTextField();
		
		textfieldNombreVertice.setToolTipText("");
		
		textfieldNombreVertice.setBounds(26, 511, 173, 20);
		
		//mapa.add(textfieldNombreVertice);
		panelControles.add(textfieldNombreVertice);
		textfieldNombreVertice.setColumns(10);
		
		textFieldVertice2 = new JTextField();
		
		textFieldVertice2.setBounds(243, 511, 178, 22);
		//mapa.add(textFieldVertice2);
		panelControles.add(textFieldVertice2);
		textFieldVertice2.setColumns(10);
		
		textFieldVertice1 = new JTextField();
		
		
		textFieldVertice1.setBounds(243, 468, 178, 23);
		//mapa.add(textFieldVertice1);
		panelControles.add(textFieldVertice1);
		textFieldVertice1.setColumns(10);
	}
	
	
	public void actualizarGrafoEnMapa(HashMap<String, HashMap<String,Integer>> HashMapnuevoGrafo, Color color ) {
		//HashMap<String, HashMap<String,Integer>> HashMapnuevoGrafo = presentadorMapa.crearArbolGeneradorMinimoPrim();
		 if(HashMapnuevoGrafo == null) {
			 JOptionPane.showMessageDialog(null, "Error, Grafo Inconexo");
		 }else {
			 for (String vertice : HashMapnuevoGrafo.keySet()) {
		            HashMap<String, Integer> vecinos = HashMapnuevoGrafo.get(vertice);
		              for (Map.Entry<String, Integer> entry : vecinos.entrySet()) {
		            	  cambiarColorArista(vertice, entry.getKey(), color);
		                }
		                
			 }
			 
		 }
		
	}
	
	
	
	
	public void cambiarColorArista(String vertice1, String vertice2, Color color) {
		
		Coordinate coordenada1 = hashMapVertices.get(vertice1);
		Coordinate coordenada2 = hashMapVertices.get(vertice2);
		
		List<Coordinate> aristaEnMapa = new ArrayList<>();
		aristaEnMapa.add(coordenada1); 
		aristaEnMapa.add(coordenada2); 
		aristaEnMapa.add(coordenada1); 
        // Crear el polígono (en este caso una línea)
        MapPolygonImpl arista = new MapPolygonImpl(aristaEnMapa);
        
        arista.setColor(color);
        mapa.addMapPolygon(arista);
		
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
			//Me hago un machete de grafo
			hashMapVerticesYVecinos.put(nombreVertice, new HashMap<String, Integer>());
			
			JOptionPane.showMessageDialog(null, "Se creo el vertice Satisfactoriamente");
			
		}else {
			JOptionPane.showMessageDialog(null, "ERROR: No se puedo crear el Vertice");
		}
		

		
	}
	
	private void crearAristaEnMapa(String nombreVertice1, Coordinate vertice1, String nombreVertice2, Coordinate vertice2, int probabilidad) {
		
		
		
		if(presentadorMapa.crearArista(nombreVertice1, nombreVertice2, probabilidad)) {
			
			//Me guardo la arista
			hashMapVerticesYVecinos.get(nombreVertice1).put(nombreVertice2, probabilidad);
			hashMapVerticesYVecinos.get(nombreVertice2).put(nombreVertice1, probabilidad);
			
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
	
private HashMap<String,ArrayList<Double>> convertirCoordinateEnDosElementosEnUnArrayList(){
	
	HashMap<String,ArrayList<Double>> nuevoHashMap = new HashMap<String,ArrayList<Double>>();
	
	if(hashMapVertices == null) {
		return null;
	}
		
	
	//Crear un nuevo hashmap, parsear coordinate a 2 doubles arraylist
	for (Entry<String, Coordinate> posicion : this.hashMapVertices.entrySet()) {
		String Clave = posicion.getKey();
		Coordinate valor = posicion.getValue();
		Double coordenadaDoubleX = valor.getLat();
		Double coordenadaDoubleY = valor.getLon();
		
		ArrayList<Double> arrayListCoordenadas = new ArrayList<Double>();
		arrayListCoordenadas.add(coordenadaDoubleX);
		arrayListCoordenadas.add(coordenadaDoubleY);
		
		nuevoHashMap.put(Clave, arrayListCoordenadas);
		}
	
	return nuevoHashMap;
}

	
	
private String  guardarEspias() {
			
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 1));
    JLabel label = new JLabel("Coloque el nombre del archivo:");
    panel.add(label);

    JTextField textField = new JTextField(10);
    panel.add(textField);

    int resultado = JOptionPane.showConfirmDialog(null, panel, 
        "Guardar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

    if (resultado == JOptionPane.OK_OPTION) {
        String nombreArchivo = textField.getText().trim();
        if(nombreArchivo.isEmpty()) {
        	return null;
        }
        return nombreArchivo;
       
    } else {
    	return null;
       
    }
}

private String  cargarEspias() {
	
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 1));
    JLabel label = new JLabel("Coloque el nombre del archivo:");
    panel.add(label);

    JTextField textField = new JTextField(10);
    panel.add(textField);

    int resultado = JOptionPane.showConfirmDialog(null, panel, 
        "Guardar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

    if (resultado == JOptionPane.OK_OPTION) {
        String nombreArchivo = textField.getText().trim();
        if(nombreArchivo.isEmpty()) {
        	return null;
        }
        return nombreArchivo;
       
    } else {
    	return null;
       
    }
}
}
