package CasosDeTest;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import Modelo.LogicaDeGrafoEspias;

public class LogicaDeGrafoEspiasTest {
	
	private LogicaDeGrafoEspias logica;

	@Before
	public void setUp() {
		logica = new LogicaDeGrafoEspias();
	}

	// Test para crear vértice
	@Test
	public void testCrearVerticeExitoso() {
		assertTrue(logica.crearVertice("Espia1"));
	}

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

	// Test para crear árbol generador mínimo de Prim
	@Test
	public void testCrearArbolGeneradorMinimoPrim() {
		logica.crearVertice("A");
		logica.crearVertice("B");
		logica.crearVertice("C");
		logica.crearArista("A", "B", 0.1);
		logica.crearArista("B", "C", 0.2);
		logica.crearArista("A", "C", 0.3);

		HashMap<String, HashMap<String, Double>> arbol = logica.crearArbolGeneradorMinimoPrim();
		assertNotNull(arbol);
		assertFalse(arbol.isEmpty());
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
		LogicaDeGrafoEspias grafoPrueba = new LogicaDeGrafoEspias();
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
		LogicaDeGrafoEspias grafoPrueba = new LogicaDeGrafoEspias();
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

	@Test 
	public void testCreoDosVerticesIgualesEsperaFalse() {
		logica.crearVertice("A");
		assertFalse(logica.crearVertice("A"));

	}
	
	@Test 
	public void testCreoDosAristasIgualesEsperaFalse() {
		logica.crearVertice("A");
		logica.crearVertice("B");
		logica.crearArista("A", "B", 0.5);

		assertFalse(logica.crearArista("A", "B", 0.5));

	}
	

}