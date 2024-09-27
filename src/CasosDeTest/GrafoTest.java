package CasosDeTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;

import Modelo.Grafo;

public class GrafoTest
{
//	@Test(expected = IllegalArgumentException.class)
//	public void verticeNegativoTest()
//	{
//		Grafo grafo = new Grafo(4);
//		grafo.agregarArista(-1, 3);
//	}
	@Test
	public void estaVacioElGrafoTest() {
		Grafo grafo = new Grafo();
		assertTrue(grafo.estaVacioElGrafo());
	}
//	@Test(expected = IllegalArgumentException.class)
//	public void verticeExcedidoTest()
//	{
//		Grafo grafo = new Grafo(4);
//		grafo.agregarArista(2, 4);
//	}

	@Test(expected = IllegalArgumentException.class)
	public void sinLoopsTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista("h", "h");
	}

	@Test
	public void aristaExistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("i");
		grafo.agregarVertice("j");
		grafo.agregarArista("i", "j");
		assertTrue(grafo.existeArista("i", "j"));
	}

	@Test
	public void ordenIrrelevanteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("i");
		grafo.agregarVertice("j");
		grafo.agregarArista("i", "j");
		assertTrue(grafo.existeArista("j", "i"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void aristaInexistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista("i", "j");
		assertFalse(grafo.existeArista("k", "j"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaExistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista("i", "j");
		grafo.agregarArista("i", "j");
//		assertTrue(grafo.existeArista("i", "j"));
	}

//	@Test(expected = IllegalArgumentException.class)
//	public void eliminarAristaConVerticeNegativoTest()
//	{
//		Grafo grafo = new Grafo();
//		grafo.eliminarArista(2, -1);
//	}

//	@Test(expected = IllegalArgumentException.class)
//	public void eliminarAristaConVerticeExcedidoTest()
//	{
//		Grafo grafo = new Grafo(4);
//		grafo.eliminarArista(4, 1);
//	}
//	
	@Test
	public void eliminarAristaExistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("i");
		grafo.agregarVertice("j");
		grafo.agregarArista("i", "j");
		grafo.eliminarArista("i", "j");
		assertFalse(grafo.existeArista("i", "j"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaInexistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.eliminarArista("i", "j");
//		assertFalse(grafo.existeArista(0, 3));
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void gradoDeVerticeNegativoTest()
//	{
//		Grafo grafo = diamanteConVerticeAislado();
//		grafo.gradoDeUnVertice(-1);
//	}

//	@Test(expected = IllegalArgumentException.class)
//	public void gradoDeVerticeExcedidoTest()
//	{
//		Grafo grafo = diamanteConVerticeAislado();
//		grafo.gradoDeUnVertice("5");
//	}
	
	@Test
	public void gradoTest()
	{
		Grafo grafo = diamanteConVerticeAislado();
		assertEquals(2, grafo.gradoDeVertice("0"));
		assertEquals(0, grafo.gradoDeVertice("1"));
		assertEquals(3, grafo.gradoDeVertice("2"));
	}
	
	@Test
	public void vecinosTest()
	{
		Grafo grafo = diamanteConVerticeAislado();
		setsIguales(new String[] {"0", "3", "4"}, grafo.vecinosDeVertice("2"));
	}

	@Test
	public void vecinosVaciosTest()
	{
		Grafo grafo = diamanteConVerticeAislado();
		setsIguales(new String[] {}, grafo.vecinosDeVertice("1"));
	}
	
	@Test
	public void unSoloVecinoTest()
	{
		Grafo grafo = diamanteConVerticeAislado();
		grafo.eliminarArista("0", "3");
		setsIguales(new String[] {"2"}, grafo.vecinosDeVertice("0"));
	}
//	@Test
//	public void  obtenerPesoTest() {
//		Grafo grafo = diamanteConVerticeAislado();
//		assertEquals(1,grafo.obtenerPesoArista("0","2"));
//	}
	private void setsIguales(String[] esperado, ArrayList<String> arrayList) 
	{
		for(String elemento: esperado)
			assertTrue(arrayList.contains(elemento));		
	}
	
	private Grafo diamanteConVerticeAislado()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("0");
		grafo.agregarVertice("1");
		grafo.agregarVertice("2");
		grafo.agregarVertice("3");
		grafo.agregarVertice("4");
		grafo.agregarArista("0", "2");
		grafo.agregarArista("0", "3");
		grafo.agregarArista("2", "3");
		grafo.agregarArista("2", "4");
		grafo.agregarArista("3", "4");
		return grafo;
	}
}