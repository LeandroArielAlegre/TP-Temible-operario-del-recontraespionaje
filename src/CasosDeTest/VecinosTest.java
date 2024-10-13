package CasosDeTest;

import static CasosDeTest.Assert.assertSetEquals;
import org.junit.Test;
import Modelo.Grafo;
import java.util.ArrayList;
import java.util.Arrays;

public class VecinosTest {

    @Test
    public void todosAisladosTest() {
        Grafo grafo = new Grafo();
        grafo.agregarVertice("2");
        grafo.agregarVertice("3");
        grafo.agregarVertice("4");
        grafo.agregarVertice("5");
        
        ArrayList<String> vacio = new ArrayList<>();
        assertSetEquals("El vértice 2 debería estar aislado", vacio, grafo.vecinosDeVertice("2"));
        assertSetEquals("El vértice 3 debería estar aislado", vacio, grafo.vecinosDeVertice("3"));
        assertSetEquals("El vértice 4 debería estar aislado", vacio, grafo.vecinosDeVertice("4"));
        assertSetEquals("El vértice 5 debería estar aislado", vacio, grafo.vecinosDeVertice("5"));
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
        
        ArrayList<String> esperado = new ArrayList<>(Arrays.asList("0", "2", "3"));
        assertSetEquals("El vértice 1 debería ser universal", esperado, grafo.vecinosDeVertice("1"));
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
        
        ArrayList<String> esperados = new ArrayList<>(Arrays.asList("1", "2"));
        assertSetEquals("El vértice 3 debería tener los vecinos correctos", esperados, grafo.vecinosDeVertice("3"));
    }
}