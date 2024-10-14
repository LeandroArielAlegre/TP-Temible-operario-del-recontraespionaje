package CasosDeTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import Modelo.Grafo;

public class GrafoTest
{

	@Test
	public void estaVacioElGrafoTest() {
		Grafo grafo = new Grafo();
		assertTrue(grafo.estaVacioElGrafo());
	}

	@Test(expected = IllegalArgumentException.class)
	public void sinLoopsTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista("h", "h",0.0);
	}

	@Test
	public void aristaExistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("i");
		grafo.agregarVertice("j");
		grafo.agregarArista("i", "j",1.0);
		assertTrue(grafo.existeArista("i", "j"));
	}

	@Test
	public void ordenIrrelevanteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("i");
		grafo.agregarVertice("j");
		grafo.agregarArista("i", "j",1.0);
		assertTrue(grafo.existeArista("j", "i"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void aristaInexistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista("i", "j",1.0);
		assertFalse(grafo.existeArista("k", "j"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaExistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarArista("i", "j",1.0);
		grafo.agregarArista("i", "j",1.0);
	}

	@Test
	public void eliminarAristaExistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("i");
		grafo.agregarVertice("j");
		grafo.agregarArista("i", "j",1.0);
		grafo.eliminarArista("i", "j");
		assertFalse(grafo.existeArista("i", "j"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaInexistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.eliminarArista("i", "j");
	}
	
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
	@Test
	public void  obtenerPesoTest() {
		Grafo grafo = diamanteConVerticeAislado();
		assertEquals(1,grafo.obtenerPesoArista("0","2").intValue());
	}
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
		grafo.agregarArista("0", "2",1.0);
		grafo.agregarArista("0", "3",1.0);
		grafo.agregarArista("2", "3",1.0);
		grafo.agregarArista("2", "4",1.0);
		grafo.agregarArista("3", "4",1.0);
		return grafo;
	}
}