package CasosDeTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import Modelo.ArbolGenerador;
import Modelo.Grafo;
public class ArbolGeneradorTest {
	private ArbolGenerador arbolGenerador;
	
	@Before
	public void inicializarTest(){
		Grafo grafo = crearGrafoEjemplo();	
		arbolGenerador=new ArbolGenerador(grafo);
	}
	 @Test
	    public void testCrearArbolGeneradorMinimoPrim() {

	        Grafo grafoAGMPrim = this.arbolGenerador.crearArbolGeneradorMinimoPrim();
	        
	        HashMap<String, HashMap<String, Double>> arbolEsperado = arbolGeneradorEsperado();
	        
	        assertEquals(grafoAGMPrim.getGrafo().size(), arbolEsperado.size());
	        

	        for (Map.Entry<String, HashMap<String, Double>> entry : grafoAGMPrim.getGrafo().entrySet()) {
	            String nodo = entry.getKey();
	            HashMap<String, Double> vecinos = entry.getValue();
	            assertTrue(arbolEsperado.containsKey(nodo));
	            HashMap<String, Double> vecinosEsperados = arbolEsperado.get(nodo);
	            assertEquals(vecinos, vecinosEsperados);
	        }
	    }
	
	 @Test
	    public void testCrearArbolGeneradorMinimokruskal() {

	        Grafo grafoAGMPrim = this.arbolGenerador.crearArbolGeneradorMinimoKruskal();
	        
	        HashMap<String, HashMap<String, Double>> arbolEsperado = arbolGeneradorEsperado();
	        assertEquals(grafoAGMPrim.getGrafo().size(), arbolEsperado.size());
	        

	        for (Map.Entry<String, HashMap<String, Double>> entry : grafoAGMPrim.getGrafo().entrySet()) {
	            String nodo = entry.getKey();
	            HashMap<String, Double> vecinos = entry.getValue();
	            assertTrue(arbolEsperado.containsKey(nodo));
	            HashMap<String, Double> vecinosEsperados = arbolEsperado.get(nodo);
	            assertEquals(vecinos, vecinosEsperados);
	        }
	    }
	    

	 private Grafo crearGrafoEjemplo() {
		 Grafo grafo = new Grafo();
	        grafo.agregarVertice("A");
	        grafo.agregarVertice("B");
	        grafo.agregarVertice("C");
	        grafo.agregarArista("A", "B", 0.5);
	        grafo.agregarArista("B", "C", 0.7);
	        grafo.agregarArista("A", "C", 0.9);
	        return grafo;
	    }
	 private HashMap<String, HashMap<String, Double>> arbolGeneradorEsperado() {
			HashMap<String, HashMap<String, Double>> arbolEsperado = new HashMap<>();
	    
			arbolEsperado.put("A", new HashMap<>());
			arbolEsperado.get("A").put("B", 0.5);
			arbolEsperado.put("B", new HashMap<>());
			arbolEsperado.get("B").put("A", 0.5);
			arbolEsperado.get("B").put("C", 0.7);
			arbolEsperado.put("C", new HashMap<>());
			arbolEsperado.get("C").put("B", 0.7);
			return arbolEsperado;
		}
}
