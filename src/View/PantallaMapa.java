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
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
public class PantallaMapa {

	private JFrame frame;
	private JMapViewer mapa;
	private Sonido sonido;
	private Coordinate mapaActual = new Coordinate(-34.521, -58.719);
	private int zoomActual = 12;
	private PresentadorMapa presentadorMapa;

	private Double coordenadaXIngresada;
	private Double coordenadaYIngresada;
	
	private JTextField textfieldNombreVertice;
	private JPanel panelControlesUsuario;
	private HashMap <String ,Coordinate> hashMapVertices;
	private HashMap<String, HashMap<String,Integer>> hashMapVerticesYVecinos;
	private JButton btnCrearArista;
	private JTextField textFieldVertice2;
	private JTextField textFieldVertice1;
	private JTextField textFieldProbabilidad;

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try {
					PantallaMapa window = new PantallaMapa();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public PantallaMapa() 
	{
		initialize();
	}

	private void initialize() 
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(131, 162, 197));
		sonido = new Sonido();
		hashMapVertices = new HashMap<>();
		hashMapVerticesYVecinos = new HashMap<>();
    	presentadorMapa = new PresentadorMapa();
		frame.setBounds(400, 200, 833, 676);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//panelcontrol
		panelControlesUsuario = new JPanel();
		panelControlesUsuario.setBackground(new Color(192, 192, 192));
		panelControlesUsuario.setBounds(10, 11, 786, 615);
		panelControlesUsuario.setLayout(null);
		frame.getContentPane().add(panelControlesUsuario);
	
		// LA PREGUNTA
		preguntarFormaDeCargarEspias();
				
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
		btnCrearVertice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCrearVertice.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		btnCrearVertice.setBounds(10, 560, 187, 44);
		//mapa.add(btnCrearVertice);
		panelControlesUsuario.add(btnCrearVertice);
		btnCrearVertice.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				
				if(textfieldNombreVertice.getText() != null && 
						textfieldNombreVertice.getText().toString().length() != 0) 
				{
					if(coordenadaXIngresada != null && coordenadaYIngresada != null) 
					{						
							String nombreVertice = textfieldNombreVertice.getText().toString();
							crearVerticeEnMapa(coordenadaXIngresada, coordenadaYIngresada, nombreVertice);
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
		btnCrearArista.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCrearArista.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(textFieldVertice1.getText() != null  
						&& textFieldVertice2.getText() !=null && textFieldProbabilidad.getText() != null) 
				{
					String nombreVertice1 = textFieldVertice1.getText().toString();
					String nombreVertice2 = textFieldVertice2.getText().toString();
					try {
						int probabilidad = Integer.parseInt(textFieldProbabilidad.getText());
						if(hashMapVertices.containsKey(nombreVertice1) && hashMapVertices.containsKey(nombreVertice2) 
								&& probabilidad == 0 || probabilidad ==1 ) 
						{													
							// SI no existe arista la agrego
							if(!presentadorMapa.existeArista(nombreVertice1, nombreVertice2)) 
							{
								crearAristaEnMapa(nombreVertice1, hashMapVertices.get(nombreVertice1),nombreVertice2, hashMapVertices.get(nombreVertice2), probabilidad);
								textFieldVertice1.setText("");
								textFieldVertice2.setText("");
								textFieldProbabilidad.setText("");
							}else {
								JOptionPane.showMessageDialog(null, "No es posible crear Arista entre " + nombreVertice1 + " y " + nombreVertice2);
							}
								
						}else {
							JOptionPane.showMessageDialog(null, "Error, ingrese una probabilidad valida  y vertices existentes");
						}
						
					}catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para la probabilidad.");
					}									
				}								
			}
		});
		btnCrearArista.setBounds(277, 560, 148, 44);
		//mapa.add(btnCrearArista);
		panelControlesUsuario.add(btnCrearArista);
		
		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 786, 457);
		panelControlesUsuario.add(mapa);
		mapa.setFocusable(false);
		mapa.setZoomControlsVisible(false);	
		
		mapa.setDisplayPosition(this.mapaActual, zoomActual);
		mapa.setScrollWrapEnabled(false);  
				
		new DefaultMapController(mapa)
		{
			    @Override
			    public void mouseClicked(MouseEvent e)
			    {			    				    			  
			    	// Convertimos la posición del clic (en píxeles) a coordenadas geográficas
	                Coordinate coord = (Coordinate) mapa.getPosition(e.getPoint());

	                coordenadaXIngresada = coord.getLat();
	                coordenadaYIngresada = coord.getLon();
	               			    	
			    	mapa.setDisplayPosition(mapaActual, zoomActual);
			    }
			    
			    public void mouseReleased(MouseEvent e) 
			    {
			    	mapa.setDisplayPosition(mapaActual, zoomActual);
			    }
			    
			    public void mousePressed(MouseEvent e) 
			    {
			    	mapa.setDisplayPosition(mapaActual, zoomActual);
			    }
			};
		
		//Combobox
		JComboBox<String> comboBoxSeleccionAlgoritmo = new JComboBox<>(new String[] {"Algoritmo De Prim", "Algoritmo de Kruskal"});
		comboBoxSeleccionAlgoritmo.setFocusable(false);
		comboBoxSeleccionAlgoritmo.setBounds(572, 484, 187, 26);
		panelControlesUsuario.add(comboBoxSeleccionAlgoritmo);
				
		//BOTON DE ARBOL GENERADOR MINIMO + COMBOBOX
		JButton btnAplicarAlgoritmo = new JButton("Generar Arbol generador minimo");
		btnAplicarAlgoritmo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				if(comboBoxSeleccionAlgoritmo.getSelectedIndex() == 0) 
				{
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
		btnAplicarAlgoritmo.setBounds(572, 514, 187, 36);
		panelControlesUsuario.add(btnAplicarAlgoritmo);
		
		JButton btnRestablecerGrafo = new JButton("Restablecer Grafo");
		btnRestablecerGrafo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{				
				Color color = Color.GREEN;
				actualizarGrafoEnMapa(hashMapVerticesYVecinos,color);
			}
		});
		btnRestablecerGrafo.setBounds(572, 560, 187, 44);
		panelControlesUsuario.add(btnRestablecerGrafo);				 				
		
		JLabel lblGuia1 = new JLabel("Ingrese nombre para el vertice");
		lblGuia1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblGuia1.setBounds(10, 485, 187, 33);
		panelControlesUsuario.add(lblGuia1);
		
		JLabel vertice1 = new JLabel("Vertice1");
		vertice1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		vertice1.setBounds(277, 501, 49, 14);
		panelControlesUsuario.add(vertice1);
		
		JLabel vertice2 = new JLabel("Vertice2");
		vertice2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		vertice2.setBounds(277, 528, 49, 14);
		panelControlesUsuario.add(vertice2);
		
		JLabel pesoArista = new JLabel("Peso");
		pesoArista.setBorder(new LineBorder(new Color(192, 192, 192)));
		pesoArista.setFont(new Font("Tahoma", Font.PLAIN, 13));
		pesoArista.setBounds(277, 473, 49, 14);
		panelControlesUsuario.add(pesoArista);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(118, 171, 237));
		separator.setOpaque(true);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(214, 456, 53, 159);
		panelControlesUsuario.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setOpaque(true);
		separator_1.setBackground(new Color(118, 171, 237));
		separator_1.setBounds(470, 456, 53, 159);
		panelControlesUsuario.add(separator_1);
		
		mapa.addMouseWheelListener(new MouseWheelListener()
		{
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) 
			{
			    mapa.setDisplayPosition(mapaActual, zoomActual);			
			}			
		});		
	}	

	//COLOCA TEXTFIELDS!!!!!!!!!!!!!!!!!
	private void colocarTexfields() {
		//TEXTFIELDS!!!!!!!!!!!!!!!!!!!!!
		
		textFieldProbabilidad = new JTextField();
		textFieldProbabilidad.setBorder(new LineBorder(new Color(171, 173, 179), 2));
		textFieldProbabilidad.setBounds(339, 473, 86, 22);
		panelControlesUsuario.add(textFieldProbabilidad);
		textFieldProbabilidad.setColumns(10);
		
		
		textfieldNombreVertice = new JTextField();
		textfieldNombreVertice.setBorder(new LineBorder(new Color(171, 173, 179), 2));
		String placeHolderTextFieldNombreVertice ="Ingrese nombre de vertice a ingresar";
		agregarPlaceHolderTexfield(textfieldNombreVertice, placeHolderTextFieldNombreVertice);
		textfieldNombreVertice.setToolTipText("");
		
		textfieldNombreVertice.setBounds(10, 529, 187, 20);
		
		//mapa.add(textfieldNombreVertice);
		panelControlesUsuario.add(textfieldNombreVertice);
		textfieldNombreVertice.setColumns(10);
		
		
		
		textFieldVertice2 = new JTextField();
		textFieldVertice2.setBorder(new LineBorder(new Color(171, 173, 179), 2));
		String placeHolderTextFieldVertice2 ="Ingrese nombre de vertice para agregar arista";
		agregarPlaceHolderTexfield(textFieldVertice2, placeHolderTextFieldVertice2);
		textFieldVertice2.setBounds(339, 528, 86, 22);
		//mapa.add(textFieldVertice2);
		panelControlesUsuario.add(textFieldVertice2);
		textFieldVertice2.setColumns(10);
		
		textFieldVertice1 = new JTextField();
		textFieldVertice1.setBorder(new LineBorder(new Color(171, 173, 179), 2));
		String placeHolderTextFieldVertice ="Ingrese el segundo nombre de vertice para agregar una arista";
		agregarPlaceHolderTexfield(textFieldVertice1, placeHolderTextFieldVertice);
		textFieldVertice1.setBounds(339, 501, 86, 22);
		//mapa.add(textFieldVertice1);
		panelControlesUsuario.add(textFieldVertice1);
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
	
	/*
	public void actualizarPantallaNuevoGrafoPrim() {
		HashMap<String, HashMap<String,Integer>> HashMapnuevoGrafo = presentadorMapa.crearArbolGeneradorMinimoPrim();
		 if(HashMapnuevoGrafo == null) {
			 JOptionPane.showMessageDialog(null, "Error, Grafo Inconexo");
		 }else {
			 Color color = Color.RED;
			 for (String vertice : HashMapnuevoGrafo.keySet()) {
		            HashMap<String, Integer> vecinos = HashMapnuevoGrafo.get(vertice);
		              for (Map.Entry<String, Integer> entry : vecinos.entrySet()) {
		            	  cambiarColorArista(vertice, entry.getKey(), color);
		                }
		                
			 }
			 
		 }
		
	     
	}
	
	public void restablecerPantallaGrafo() {
		if(hashMapVerticesYVecinos == null) {
			 JOptionPane.showMessageDialog(null, "Error, Grafo Inconexo");
		 }else {
			 Color color = Color.GREEN;
			 for (String vertice : hashMapVerticesYVecinos.keySet()) {
		            HashMap<String, Integer> vecinos = hashMapVerticesYVecinos.get(vertice);
		              for (Map.Entry<String, Integer> entry : vecinos.entrySet()) {
		            	  cambiarColorArista(vertice, entry.getKey(), color);
		                }
		                
			 }
			 
		 }
		
	}*/
		
	
	
	
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
