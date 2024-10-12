package Presentador;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PresentadorMapaTest {

    private PresentadorMapa presentador;

    @Before
    public void setUp() {
        presentador = new PresentadorMapa();
    }

    @Test
    public void testCrearVertice() {
        assertTrue("Debería crear el vértice A", presentador.crearVertice("A"));
        assertTrue("Debería crear el vértice B", presentador.crearVertice("B"));
        assertFalse("No debería crear el vértice A de nuevo", presentador.crearVertice("A"));
    }

    @Test
    public void testCrearArista() {
        presentador.crearVertice("A");
        presentador.crearVertice("B");
        presentador.crearVertice("C");

        assertTrue("Debería crear la arista entre A y B", presentador.crearArista("A", "B", 0.5));
        assertTrue("Debería crear la arista entre B y C", presentador.crearArista("B", "C", 0.7));
        assertFalse("No debería crear la arista entre A y B de nuevo", presentador.crearArista("A", "B", 0.6));
    }

    @Test
    public void testExisteArista() {
        presentador.crearVertice("A");
        presentador.crearVertice("B");
        presentador.crearArista("A", "B", 0.5);

        assertTrue("Debería existir la arista entre A y B", presentador.existeArista("A", "B"));
        assertTrue("Debería existir la arista entre B y A", presentador.existeArista("B", "A"));
        assertFalse("No debería existir la arista entre A y C", presentador.existeArista("A", "C"));
    }

    @Test
    public void testCrearArbolGeneradorMinimoPrim() {
        crearGrafoEjemplo();

        HashMap<String, HashMap<String, Double>> arbol = presentador.crearArbolGeneradorMinimoPrim();
        HashMap<String, HashMap<String, Double>> arbolEsperado = new HashMap<>();
     
        arbolEsperado.put("A", new HashMap<>());
        arbolEsperado.get("A").put("B", 0.5);
        arbolEsperado.put("B", new HashMap<>());
        arbolEsperado.get("B").put("A", 0.5);
        arbolEsperado.get("B").put("C", 0.7);
        arbolEsperado.put("C", new HashMap<>());
        arbolEsperado.get("C").put("B", 0.7);
       
        assertEquals(arbol.size(), arbolEsperado.size());
        

        for (Map.Entry<String, HashMap<String, Double>> entry : arbol.entrySet()) {
            String nodo = entry.getKey();
            HashMap<String, Double> vecinos = entry.getValue();
            assertTrue(arbolEsperado.containsKey(nodo));
            HashMap<String, Double> vecinosEsperados = arbolEsperado.get(nodo);
            assertEquals(vecinos, vecinosEsperados);
        }
    }
    
    @Test
    public void testCrearArbolGeneradorMinimoKruskalYVeoSiEsCorrecto() {
    	
        crearGrafoEjemplo();

        HashMap<String, HashMap<String, Double>> arbol = presentador.crearArbolGeneradorMinimoKruskal();
        HashMap<String, HashMap<String, Double>> arbolEsperado = new HashMap<>();
     
        arbolEsperado.put("A", new HashMap<>());
        arbolEsperado.get("A").put("B", 0.5);
        arbolEsperado.put("B", new HashMap<>());
        arbolEsperado.get("B").put("A", 0.5);
        arbolEsperado.get("B").put("C", 0.7);
        arbolEsperado.put("C", new HashMap<>());
        arbolEsperado.get("C").put("B", 0.7);
       
        assertEquals(arbol.size(), arbolEsperado.size());
        

        for (Map.Entry<String, HashMap<String, Double>> entry : arbol.entrySet()) {
            String nodo = entry.getKey();
            HashMap<String, Double> vecinos = entry.getValue();
            assertTrue(arbolEsperado.containsKey(nodo));
            HashMap<String, Double> vecinosEsperados = arbolEsperado.get(nodo);
            assertEquals(vecinos, vecinosEsperados);
        }
    }

    @Test
    public void testGuardarYCargarGrafo() {
        crearGrafoEjemplo();

        HashMap<String, HashMap<String, Double>> grafo = presentador.crearArbolGeneradorMinimoPrim();
        HashMap<String, ArrayList<Double>> posiciones = new HashMap<>();
        ArrayList<Double> coordenadaA = new ArrayList<Double>();
        ArrayList<Double> coordenadaB = new ArrayList<Double>();
        ArrayList<Double> coordenadaC = new ArrayList<Double>();
        coordenadaA.add(1.0); 
        coordenadaA.add(1.0); 
        coordenadaB.add(2.0); 
        coordenadaB.add(2.0); 
        coordenadaC.add(3.0); 
        coordenadaC.add(3.0); 
        
        posiciones.put("A", coordenadaA);
        posiciones.put("B", coordenadaB);
        posiciones.put("C", coordenadaC);

        assertTrue("Debería guardar el grafo correctamente", presentador.guardarGrafo(grafo, posiciones, "test_grafo"));
        assertTrue("Debería cargar el grafo correctamente", presentador.cargarGrafo("test_grafo"));
    }

    private void crearGrafoEjemplo() {
        presentador.crearVertice("A");
        presentador.crearVertice("B");
        presentador.crearVertice("C");
        presentador.crearArista("A", "B", 0.5);
        presentador.crearArista("B", "C", 0.7);
        presentador.crearArista("A", "C", 0.9);
    }
}