package CasosDeTest;

import static CasosDeTest.Assert.assertArrayListIguales;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import Modelo.BFS;
import Modelo.Grafo;
import java.util.ArrayList;
import java.util.Arrays;

public class VecinosTest {

    @Test
    public void todosLosVerticesAisladosTest() {
        Grafo grafo = new Grafo();
        grafo.agregarVertice("2");
        grafo.agregarVertice("3");
        grafo.agregarVertice("4");
        grafo.agregarVertice("5");
        
        ArrayList<String> vacio = new ArrayList<>();
        assertFalse(BFS.esConexo(grafo));
        assertArrayListIguales("El vértice 2 debería estar aislado", vacio, grafo.vecinosDeVertice("2"));
        assertArrayListIguales("El vértice 3 debería estar aislado", vacio, grafo.vecinosDeVertice("3"));
        assertArrayListIguales("El vértice 4 debería estar aislado", vacio, grafo.vecinosDeVertice("4"));
        assertArrayListIguales("El vértice 5 debería estar aislado", vacio, grafo.vecinosDeVertice("5"));
    }
    
    @Test
    public void verticeUniversalTest() {
        Grafo grafo = new Grafo();
        grafo.agregarVertice("0");
        grafo.agregarVertice("1");
        grafo.agregarVertice("2");
        grafo.agregarVertice("3");
        grafo.agregarArista("1", "0", 0.0);
        grafo.agregarArista("1", "2", 0.0);
        grafo.agregarArista("1", "3", 0.0);
        
        ArrayList<String> vecinosEsperadosDe1 = new ArrayList<>(Arrays.asList("0", "2", "3"));
        assertEquals(grafo.cantidadDeVertices()-1,vecinosEsperadosDe1.size());//los vecinos de mi universal deben ser n-1 cantidad de vertices
        assertArrayListIguales("El vértice 1 debería ser universal", vecinosEsperadosDe1, grafo.vecinosDeVertice("1"));
    }
    
    @Test
    public void verticeNormalTest() {
        Grafo grafo = new Grafo();
        grafo.agregarVertice("1");
        grafo.agregarVertice("2");
        grafo.agregarVertice("3");
        grafo.agregarVertice("4");
        grafo.agregarArista("1", "3", 1.0);
        grafo.agregarArista("2", "3", 1.0);
        grafo.agregarArista("2", "4", 1.0);
        
        ArrayList<String> vecinosEsperadosDe3 = new ArrayList<>(Arrays.asList("1", "2"));
        assertArrayListIguales("El vértice 3 debería tener los vecinos correctos", vecinosEsperadosDe3, grafo.vecinosDeVertice("3"));
    }
}