package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;


//import Modelo.Sonido;
import Presentador.PresentadorMapa;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JSeparator;
import java.awt.ComponentOrientation;

public class PantallaMapa {

	private JFrame frame;
	private JMapViewer mapa;
//	private Sonido sonido;
	private Coordinate mapaActual = new Coordinate(-34.521, -58.719);
	private int zoomActual = 12;
	private PresentadorMapa presentadorMapa;

	private Double coordenadaXGlobal;
	private Double coordenadaYGlobal;

	private JTextField textfieldNombreVertice;
	private JPanel panelControles;
	private HashMap <String ,Coordinate> hashMapVertices;
	private HashMap<String, HashMap<String,Double>> hashMapVerticesYVecinos;
	private JButton btnCrearArista;
	private JTextField textFieldVerticeB;
	private JTextField textFieldVerticeA;
	private JTextField textFieldProbabilidad;
	private JButton btnCargarEspias;
	URL urlEspia = getClass().getClassLoader().getResource("resources/espia.png");
	ImageIcon iconEspia = new ImageIcon(urlEspia);
	//URL url = getClass().getClassLoader().getResource("resources/mensaje.png");
	URL urlCarta = getClass().getClassLoader().getResource("resources/carta.gif");
	ImageIcon iconMensaje = new ImageIcon(urlCarta);
	
	



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
		frame.getContentPane().setBackground(new Color(128, 0, 0));
//		sonido = new Sonido();
		hashMapVertices = new HashMap<>();
		hashMapVerticesYVecinos = new HashMap<>();
		presentadorMapa = new PresentadorMapa();
		frame.setBounds(400, 200, 833, 676);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//panelcontrol
		panelControles = new JPanel();
		panelControles.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelControles.setBackground(new Color(164, 0, 4));
		panelControles.setBounds(10, 11, 786, 615);
		panelControles.setLayout(null);
		frame.getContentPane().add(panelControles);
	
		

		//sonido.reproducirSonido("/resources/espiasTheme.wav", "menu");

		JButton btnCrearVertice = new JButton("Crear vértice ");
		btnCrearVertice.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnCrearVertice.setHorizontalTextPosition(SwingConstants.LEFT);
		btnCrearVertice.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCrearVertice.setFocusable(false);
		btnCrearVertice.setBounds(20, 560, 86, 44);
		//mapa.add(btnCrearVertice);
		panelControles.add(btnCrearVertice);
		btnCrearVertice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(textfieldNombreVertice.getText() != null && textfieldNombreVertice.getText().toString().length() != 0) 
				{
					if(coordenadaXGlobal != null && coordenadaYGlobal != null) 
					{

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

		btnCrearArista = new JButton("Crear arista");
		btnCrearArista.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnCrearArista.setHorizontalTextPosition(SwingConstants.LEFT);
		btnCrearArista.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCrearArista.setFocusable(false);
		btnCrearArista.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(textFieldVerticeA.getText() != null  
						&& textFieldVerticeB.getText() !=null 
						&& textFieldProbabilidad.getText() != null) 
				{
					String nombreVertice1 = textFieldVerticeA.getText().toString();
					String nombreVertice2 = textFieldVerticeB.getText().toString();
					try {
						Double probabilidad = Double.parseDouble(textFieldProbabilidad.getText());
						if(hashMapVertices.containsKey(nombreVertice1) && hashMapVertices.containsKey(nombreVertice2) 
								&& probabilidad >= 0 || probabilidad <=1 ) 
						{
							// SI no existe arista no lo agrego
							if(!presentadorMapa.existeArista(nombreVertice1, nombreVertice2)) 
							{
								crearAristaEnMapa(nombreVertice1, hashMapVertices.get(nombreVertice1),nombreVertice2, hashMapVertices.get(nombreVertice2), probabilidad);
								textFieldVerticeA.setText("");
								textFieldVerticeB.setText("");
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
		btnCrearArista.setBounds(230, 560, 86, 44);
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
		
		JLabel lblEncuentrosIntermedios = new JLabel("");
		lblEncuentrosIntermedios.setVerticalAlignment(SwingConstants.TOP);
		lblEncuentrosIntermedios.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblEncuentrosIntermedios.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblEncuentrosIntermedios.setFocusable(false);
		lblEncuentrosIntermedios.setHorizontalAlignment(SwingConstants.CENTER);
		lblEncuentrosIntermedios.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEncuentrosIntermedios.setOpaque(true);
		lblEncuentrosIntermedios.setVisible(false);
		lblEncuentrosIntermedios.setBounds(10, 11, 233, 435);
		panelControles.add(lblEncuentrosIntermedios);
		
		

		//Combobox
		JComboBox<String> comboBoxSeleccionAlgoritmo = new JComboBox<>(new String[] {"Algoritmo De Prim", "Algoritmo de Kruskal"});
		comboBoxSeleccionAlgoritmo.setBorder(new LineBorder(new Color(0, 0, 0)));
		comboBoxSeleccionAlgoritmo.setFont(new Font("Tahoma", Font.BOLD, 11));
		comboBoxSeleccionAlgoritmo.setFocusable(false);
		comboBoxSeleccionAlgoritmo.setBounds(588, 469, 137, 20);
		panelControles.add(comboBoxSeleccionAlgoritmo);



		//BOTON DE ARBOL GENERADOR MINIMO + COMBOBOX
		JButton btnAplicarAlgoritmo = new JButton("Generar Arbol generador mínimo");
		btnAplicarAlgoritmo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAplicarAlgoritmo.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnAplicarAlgoritmo.setHorizontalAlignment(SwingConstants.LEFT);
		btnAplicarAlgoritmo.setFocusable(false);
		btnAplicarAlgoritmo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//GUARDO MI INSTANCIA DE GRAFO ACTUAL PARA NO PERDERLA
//				hashMapVerticesYVecinosAux=new HashMap<>();
//				hashMapVerticesYVecinosAux=hashMapVerticesYVecinos;
				
				if(comboBoxSeleccionAlgoritmo.getSelectedIndex() == 0) {
					HashMap<String, HashMap<String,Double>> HashMapNuevoGrafoConPrim = presentadorMapa.crearArbolGeneradorMinimoPrim();
					Color color = Color.RED;
					actualizarGrafoEnMapa(HashMapNuevoGrafoConPrim, color);
					colocarImagenCartaEnAGM(HashMapNuevoGrafoConPrim);
					if(presentadorMapa.encuentrosIntermedios() != null ) {
						lblEncuentrosIntermedios.setText("<html>" + presentadorMapa.encuentrosIntermedios().replace("\n", "<br>") + "</html>");
						lblEncuentrosIntermedios.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Error: No se puede cargar los encuentros intermedios");
						lblEncuentrosIntermedios.setVisible(false);
					}
					
//					
					
				}else {
					presentadorMapa.crearArbolGeneradorMinimoKruskal();
					mapa.removeAllMapMarkers();
					mapa.removeAllMapPolygons();
				}


			}
		});
		btnAplicarAlgoritmo.setBounds(561, 493, 189, 20);
		panelControles.add(btnAplicarAlgoritmo);

		JButton btnRestablecerGrafo = new JButton("Restablecer Valores");
		btnRestablecerGrafo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRestablecerGrafo.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnRestablecerGrafo.setFocusable(false);
		btnRestablecerGrafo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Color color = Color.GREEN;
				actualizarGrafoEnMapa(hashMapVerticesYVecinos,color);
				limpiarJLabels(mapa,urlCarta);
				lblEncuentrosIntermedios.setText(null);
				lblEncuentrosIntermedios.setVisible(false);

			}
		});
		btnRestablecerGrafo.setBounds(561, 529, 189, 20);
		panelControles.add(btnRestablecerGrafo);

		JButton btnGuardar = new JButton("Guardar Grafo");
		btnGuardar.setHorizontalAlignment(SwingConstants.LEFT);
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGuardar.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnGuardar.setFocusable(false);
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
		btnGuardar.setBounds(517, 584, 86, 20);
		panelControles.add(btnGuardar);

		btnCargarEspias = new JButton("Cargar Grafo");
		btnCargarEspias.setHorizontalAlignment(SwingConstants.LEFT);
		btnCargarEspias.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCargarEspias.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnCargarEspias.setFocusable(false);
		btnCargarEspias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreArchivo =cargarEspias();
				if(nombreArchivo != null) {
					if(presentadorMapa.cargarGrafo(nombreArchivo)) {

						//Limpio todos los poligonos si existen
						mapa.removeAllMapMarkers();
						mapa.removeAllMapPolygons();
						Color color = Color.GREEN;
						
						HashMap<String,ArrayList<Double>> auxHashMapCoordenadas= new HashMap<String,ArrayList<Double>>();
						auxHashMapCoordenadas = presentadorMapa.devolverGrafoPosicionesArchivo();

						HashMap<String, HashMap<String,Double>> auxiliarHashMapVecinos = new HashMap<String, HashMap<String,Double>>();
						auxiliarHashMapVecinos =presentadorMapa.devolverGrafoArchivo();
						
						//Limpio las hashmap locales
						hashMapVerticesYVecinos.clear();
						hashMapVertices.clear();
						
						//Borro las imagenes residuales, si existieran
						limpiarJLabels(mapa,urlEspia);
						limpiarJLabels(mapa,urlCarta);

						//Dibujo los vertices
						actualizarMarcadores(auxHashMapCoordenadas);
						//Dibujo las aristas
						actualizarGrafoEnMapa(auxiliarHashMapVecinos, color);		
						
						//Actualizo el hashmap de la view
						hashMapVerticesYVecinos = auxiliarHashMapVecinos;
					
					}
				}else {
					JOptionPane.showMessageDialog(null, "Error: No se pudo cargar el archivo");
				}
			}
		});
		btnCargarEspias.setBounds(690, 584, 86, 20);
		panelControles.add(btnCargarEspias);
		
		JLabel lblindicativoNombreDeVértice = new JLabel("Nombre de Vértice ");
		lblindicativoNombreDeVértice.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblindicativoNombreDeVértice.setBounds(10, 472, 115, 14);
		panelControles.add(lblindicativoNombreDeVértice);
		
		JLabel lblindicativoVérticeOrigen = new JLabel("Vértice origen");
		lblindicativoVérticeOrigen.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblindicativoVérticeOrigen.setBounds(230, 472, 79, 14);
		panelControles.add(lblindicativoVérticeOrigen);
		
		JLabel lblindicativoVerticeDestino = new JLabel("Vértice destino");
		lblindicativoVerticeDestino.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblindicativoVerticeDestino.setBounds(230, 514, 86, 14);
		panelControles.add(lblindicativoVerticeDestino);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(128, 128, 128));
		separator.setOpaque(true);
		separator.setBounds(135, 457, 85, 157);
		panelControles.add(separator);
		
		JLabel lblPeso = new JLabel("Peso");
		lblPeso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPeso.setBounds(354, 472, 27, 14);
		panelControles.add(lblPeso);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOpaque(true);
		separator_1.setBackground(Color.GRAY);
		separator_1.setBounds(422, 457, 85, 157);
		panelControles.add(separator_1);

		mapa.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				mapa.setDisplayPosition(mapaActual, zoomActual);
			}
		});
	}

	private void actualizarMarcadores(
			HashMap<String, ArrayList<Double>> auxHashMapCoordenadas) {
		for (Entry<String, ArrayList<Double>> entry : auxHashMapCoordenadas.entrySet()) {
			String nombreVertice = entry.getKey();
			ArrayList<Double> valor = entry.getValue();
			Double coordenadaDoubleX = valor.get(0);
			Double coordenadaDoubleY = valor.get(1);
			colocarMarcadoresEnMapa(coordenadaDoubleX , coordenadaDoubleY, nombreVertice);
		}
	}
	private void colocarMarcadoresEnMapa(double CoordenadasX , double CoordenadasY, String nombreVertice) {
		Coordinate coordenadaVertice = new Coordinate(CoordenadasX, CoordenadasY);
		MapMarkerDot verticeEnMapa = new MapMarkerDot(nombreVertice, coordenadaVertice);
		
		colocarImagenEnPosicionDeMarcador(coordenadaVertice,urlEspia);
		
		verticeEnMapa.getStyle().setBackColor(Color.yellow);
		verticeEnMapa.getStyle().setColor(Color.yellow);
		mapa.addMapMarker(verticeEnMapa);
		//Guardamos el vertices localmente en un HashMap
		hashMapVertices.put(nombreVertice, coordenadaVertice);
		//Me hago un machete de grafo
		
		if(!hashMapVerticesYVecinos.containsKey(nombreVertice)) {
			hashMapVerticesYVecinos.put(nombreVertice, new HashMap<String, Double>());
		}
		
		

	}

	public void colocarImagenCartaEnAGM(HashMap<String, HashMap<String,Double>> HashMapNuevoGrafoAGM) {
		for (Entry<String, HashMap<String, Double>> entry : HashMapNuevoGrafoAGM.entrySet()) {
			String claveVertice1 = entry.getKey();
			HashMap<String, Double> valor = entry.getValue();
			for (Entry<String, Double> entrada : valor.entrySet()) {
				String claveVertice2 = entrada.getKey();
				Coordinate coordenadaVertice1 = hashMapVertices.get(claveVertice1);
				Coordinate coordenadaVertice2 =hashMapVertices.get(claveVertice2);
				colocarImagenEnPosicionDeArista(coordenadaVertice1,coordenadaVertice2, urlCarta);
			}
		}     
		
		
	}
	
	private void colocarImagenEnPosicionDeArista(Coordinate coordenadaVertice1, Coordinate coordenadaVertice2, URL urlImagen) {
		Point posicionEnPantalla1 = mapa.getMapPosition(coordenadaVertice1);
		Point posicionEnPantalla2 = mapa.getMapPosition(coordenadaVertice2);
		int posicionImagenX = (posicionEnPantalla1.x + posicionEnPantalla2.x) / 2;
		int posicionImagenY = ( posicionEnPantalla1.y + posicionEnPantalla2.y) / 2;
		Point posicionImagenEnPantalla = new Point(posicionImagenX, posicionImagenY);
		ImageIcon imagenEscaladaALabel = new ImageIcon(iconMensaje.getImage());
		JLabel jlabelImagen = new JLabel(imagenEscaladaALabel);
		jlabelImagen.setBounds(posicionImagenEnPantalla.x, posicionImagenEnPantalla.y, 30, 30);
		jlabelImagen.putClientProperty("imageURL", urlImagen);
		mapa.add(jlabelImagen);
	}
	private void colocarImagenEnPosicionDeMarcador(Coordinate coordenadaVertice, URL urlEspia) {
		Point posicionEnPantalla = mapa.getMapPosition(coordenadaVertice);
		JLabel jlabelImagen = new JLabel();
		jlabelImagen.setBounds(posicionEnPantalla.x, posicionEnPantalla.y, 30, 30);
		ImageIcon imagenEscaladaALabel = new ImageIcon(iconEspia.getImage().getScaledInstance(jlabelImagen.getWidth(), jlabelImagen.getHeight(), Image.SCALE_SMOOTH));
		jlabelImagen.setIcon(imagenEscaladaALabel);
		jlabelImagen.putClientProperty("imageURL", urlEspia);
		mapa.add(jlabelImagen);
	}
	public void actualizarGrafoEnMapa(HashMap<String, HashMap<String,Double>> HashMapnuevoGrafo, Color color ) {
		//HashMap<String, HashMap<String,Integer>> HashMapnuevoGrafo = presentadorMapa.crearArbolGeneradorMinimoPrim();
		//VER!"!"!"!"!"!
		if(HashMapnuevoGrafo == null) {
			JOptionPane.showMessageDialog(null, "Error, Grafo Inconexo");
		}else {
			for (String vertice : HashMapnuevoGrafo.keySet()) {
				HashMap<String, Double> vecinos = HashMapnuevoGrafo.get(vertice);
				for (Map.Entry<String, Double> entry : vecinos.entrySet()) {
					
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

		//colocarImagenEnPosicionDeArista(coordenada1, coordenada2);
		arista.setColor(color);
		mapa.addMapPolygon(arista);
		System.out.println(hashMapVerticesYVecinos);
	}
	private static void limpiarJLabels(JMapViewer mapa, URL urlAImagenAEliminar) {
	    Component[] componentes = mapa.getComponents();
	    
	    for (Component componente : componentes) {
	        if (componente instanceof JLabel) {
	            JLabel label = (JLabel) componente;
	            URL urlAlmacenada = (URL) label.getClientProperty("imageURL");
	            if (urlAlmacenada != null && urlAlmacenada.equals(urlAImagenAEliminar)) {
	                mapa.remove(componente);
	            }
	        }
	    }

	    mapa.revalidate();
	    mapa.repaint();
	}
	//COLOCA TEXTFIELDS!!!!!!!!!!!!!!!!!
	private void colocarTexfields() {
		
		textFieldProbabilidad = new JTextField();
		textFieldProbabilidad.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldProbabilidad.setBounds(326, 493, 86, 20);
		panelControles.add(textFieldProbabilidad);
		textFieldProbabilidad.setColumns(10);

		textfieldNombreVertice = new JTextField();
		textfieldNombreVertice.setBorder(new LineBorder(new Color(0, 0, 0)));

		textfieldNombreVertice.setToolTipText("");

		textfieldNombreVertice.setBounds(20, 493, 86, 20);

		//mapa.add(textfieldNombreVertice);
		panelControles.add(textfieldNombreVertice);
		textfieldNombreVertice.setColumns(10);

		textFieldVerticeB = new JTextField();
		textFieldVerticeB.setBorder(new LineBorder(new Color(0, 0, 0)));

		textFieldVerticeB.setBounds(230, 529, 86, 20);
		//mapa.add(textFieldVertice2);
		panelControles.add(textFieldVerticeB);
		textFieldVerticeB.setColumns(10);

		textFieldVerticeA = new JTextField();
		textFieldVerticeA.setBorder(new LineBorder(new Color(0, 0, 0)));


		textFieldVerticeA.setBounds(230, 493, 86, 20);
		//mapa.add(textFieldVertice1);
		panelControles.add(textFieldVerticeA);
		textFieldVerticeA.setColumns(10);
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
			
			colocarImagenEnPosicionDeMarcador(vertice,urlEspia);
			mapa.addMapMarker(verticeEnMapa);


			//Guardamos el vertices localmente en un HashMap
			hashMapVertices.put(nombreVertice, vertice);
			//Me hago un machete de grafo
			hashMapVerticesYVecinos.put(nombreVertice, new HashMap<String, Double>());

			JOptionPane.showMessageDialog(null, "Se creo el vertice Satisfactoriamente");

		}else {
			JOptionPane.showMessageDialog(null, "ERROR: No se puedo crear el Vertice");
		}



	}

	private void crearAristaEnMapa(String nombreVertice1, Coordinate vertice1, String nombreVertice2, Coordinate vertice2, Double probabilidad) {



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

			//colocarImagenEnPosicionDeArista(vertice1, vertice2);
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
