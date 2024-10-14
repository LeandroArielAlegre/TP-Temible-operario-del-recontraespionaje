package CasosDeTest;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import Modelo.LogicaDeGrafo;

public class LogicaDeGrafoTest {
	
	private LogicaDeGrafo logica;

	@Before
	public void setUp() {
		logica = new LogicaDeGrafo();
	}

	// Test para crear vértice
	@Test
	public void testCrearVerticeExitoso() {
		assertTrue(logica.crearVertice("Espia1"));
	}
	//test para no crear 2 vertices con el mismo nombre
	@Test
	public void testCrearVerticeRepetido() {
		logica.crearVertice("Espia1");
		assertFalse(logica.crearVertice("Espia1"));
	}

	// Tests para crear arista
	@Test
	public void testCrearAristaExitosa() {
		logica.crearVertice("Espia1");
		logica.crearVertice("Espia2");
		assertTrue(logica.crearArista("Espia1", "Espia2", 0.5));
	}
	//test para no crear 2 veces la misma arista
		@Test 
		public void testCrearAristaRepetida() {
			logica.crearVertice("A");
			logica.crearVertice("B");
			logica.crearArista("A", "B", 0.5);

			assertFalse(logica.crearArista("A", "B", 0.5));

		}
	@Test
	public void testCrearAristaVerticesInexistentes() {
		assertFalse(logica.crearArista("EspiaX", "EspiaY", 0.5));
	}

	// Test para verificar existencia de arista
	@Test
	public void testExisteArista() {
		logica.crearVertice("Espia1");
		logica.crearVertice("Espia2");
		logica.crearArista("Espia1", "Espia2", 0.5);
		assertTrue(logica.existeArista("Espia1", "Espia2"));
	}

	@Test
	public void testCrearArbolGeneradorMinimoPrimYVeoSiEsCorrecto() {
		logica.crearVertice("A");
		logica.crearVertice("B");
		logica.crearVertice("C");
		logica.crearArista("A", "B", 0.1);
		logica.crearArista("B", "C", 0.2);
		logica.crearArista("A", "C", 0.3);

		HashMap<String, HashMap<String, Double>> arbol = logica.crearArbolGeneradorMinimoPrim();
		LogicaDeGrafo grafoPrueba = new LogicaDeGrafo();
		grafoPrueba.crearVertice("A");
		grafoPrueba.crearVertice("B");
		grafoPrueba.crearVertice("C");
		grafoPrueba.crearArista("A", "B", 0.1);
		grafoPrueba.crearArista("B", "C", 0.2);

		assertEquals(grafoPrueba.devolverGrafo(), arbol);
	}
	
	@Test
	public void testCrearArbolGeneradorMinimoKruskalYVeoSiEsCorrecto() {
		logica.crearVertice("A");
		logica.crearVertice("B");
		logica.crearVertice("C");
		logica.crearArista("A", "B", 0.1);
		logica.crearArista("B", "C", 0.2);
		logica.crearArista("A", "C", 0.3);

		HashMap<String, HashMap<String, Double>> arbol = logica.crearArbolGeneradorMinimoKruskal();
		LogicaDeGrafo grafoPrueba = new LogicaDeGrafo();//creo un grafo que debe ser igual al arbol generador devuelto
		grafoPrueba.crearVertice("A");
		grafoPrueba.crearVertice("B");
		grafoPrueba.crearVertice("C");
		grafoPrueba.crearArista("A", "B", 0.1);
		grafoPrueba.crearArista("B", "C", 0.2);

		assertEquals(grafoPrueba.devolverGrafo(), arbol);
	}

	// Test para cargar grafo
	@Test
	public void testCargarGrafo() {
		assertFalse(logica.CargarGrafo("archivoInexistente.json"));
	}	
}