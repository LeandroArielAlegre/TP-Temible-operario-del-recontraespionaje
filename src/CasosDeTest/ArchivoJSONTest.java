package CasosDeTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import Modelo.ArchivoJSON;
import Modelo.Grafo;
public class ArchivoJSONTest {
	ArchivoJSON archivo;
	Grafo grafo;
	HashMap<String, ArrayList<Double>> grafoPosiciones;
	@Before
	public void archivoPrueba() {
		archivo = new ArchivoJSON();
		grafo = unGrafo();
		
		archivo.setGrafo(grafo.getGrafo());
		archivo.setGrafoPosiciones(grafoPosiciones);
		archivo.generarJSON("archivoPrueba");
	}
	@Test (expected= IllegalArgumentException.class)
	public void rutaInexistenteTest() {
        archivo.leerJSON("noExiste.txt");
    }
	@Test
	public void rutaExistenteTest() {
        assertNotNull(archivo.leerJSON("archivoPrueba"));       
    }
	@Test
	public void grafoGuardadoIdenticoACargadoTest() {
		assertEquals(archivo.getGrafo(), this.grafo.getGrafo());
              
    }
	@Test
	public void grafoPosicionesGuardadoIdenticoACargadoTest() {
		assertEquals(archivo.getGrafoPosiciones(), this.grafoPosiciones);
              
	}
	private Grafo unGrafo()
	{	
		grafoPosiciones= new HashMap<>();
		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> b = new ArrayList<Double>();
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> d = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();
		a.add(1.0);
		a.add(1.0);
		Grafo grafo = new Grafo();
		grafo.agregarVertice("0");
		grafoPosiciones.put("0", a);
		grafo.agregarVertice("1");
		b.add(2.0);
		b.add(2.0);
		grafoPosiciones.put("1", b);
		grafo.agregarVertice("2");
		c.add(3.0);
		c.add(3.0);
		grafoPosiciones.put("2", c);
		grafo.agregarVertice("3");
		d.add(4.0);
		d.add(4.0);
		grafoPosiciones.put("3", d);
		grafo.agregarVertice("4");
		e.add(5.0);
		e.add(5.0);
		grafoPosiciones.put("4", e);
		grafo.agregarArista("0", "2",1.0);
		grafo.agregarArista("0", "3",1.0);
		grafo.agregarArista("2", "3",1.0);
		grafo.agregarArista("2", "4",1.0);
		grafo.agregarArista("3", "4",1.0);
		return grafo;
	}
}
