package CasosDeTest;

import static org.junit.Assert.*;
import org.junit.Test;
import Modelo.BFS;
import Modelo.Grafo;

public class BFSTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void grafoNullTest() {
        BFS.esConexo(null);
    }

    @Test
    public void grafoVacioTest() {
        assertTrue(BFS.esConexo(new Grafo()));
    }

    @Test
    public void grafoUnVerticeTest() {
        Grafo g = new Grafo();
        g.agregarVertice("A");
        assertTrue(BFS.esConexo(g));
    }

    @Test
    public void grafoDosVerticesAisladosTest() {
        Grafo g = new Grafo();
        g.agregarVertice("1");
        g.agregarVertice("2");
        assertFalse(BFS.esConexo(g));
    }

    @Test
    public void grafoDosVerticesConexoTest() {
        Grafo g = new Grafo();
        g.agregarVertice("0");
        g.agregarVertice("1");
        g.agregarArista("0", "1",1.0);
        assertTrue(BFS.esConexo(g));
    }

    @Test
    public void grafoInconexoTest() {
        Grafo g = inicializarGrafoInconexo();
        assertFalse(BFS.esConexo(g));
    }

    @Test
    public void grafoCompletoTest() {
        Grafo g = inicializarGrafoCompleto();
        assertTrue(BFS.esConexo(g));
    }

    @Test
    public void alcanzablesGrafoCompletoTest() {
        Grafo g = inicializarGrafoCompleto();
        String[] esperado = {"0", "1", "2", "3"};
        assertArrayEquals(esperado, BFS.alcanzables(g, "0").toArray());
    }

    @Test
    public void alcanzablesInconexoTest() {
        Grafo g = inicializarGrafoInconexo();
        String[] esperado = {"0", "1", "2", "3", "4"};
        assertArrayEquals(esperado, BFS.alcanzables(g, "0").toArray());
    }

    private Grafo inicializarGrafoInconexo() {
        Grafo g = new Grafo();
        g.agregarVertice("0");
        g.agregarVertice("1");
        g.agregarVertice("2");
        g.agregarVertice("3");
        g.agregarVertice("4");
        g.agregarVertice("5");
        g.agregarVertice("6");
        g.agregarArista("0", "1",1.0);
        g.agregarArista("0", "2",0.0);
        g.agregarArista("1", "2",1.0);
        g.agregarArista("1", "3",1.0);
        g.agregarArista("2", "4",0.0);
        g.agregarArista("3", "4",1.0);
        g.agregarArista("5", "6",1.0);
        
        return g;        
    }

    private Grafo inicializarGrafoCompleto() {
        Grafo g = new Grafo();
        g.agregarVertice("0");
        g.agregarVertice("1");
        g.agregarVertice("2");
        g.agregarVertice("3");
        g.agregarArista("0", "1",1.0);
        g.agregarArista("0", "2",0.0);
        g.agregarArista("0", "3",1.0);
        g.agregarArista("1", "2",1.0);
        g.agregarArista("1", "3",1.0);
        g.agregarArista("2", "3",0.0);
        
        return g;        
    }
}