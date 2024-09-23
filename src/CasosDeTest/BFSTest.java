package CasosDeTest;

import static org.junit.Assert.*;
import org.junit.Test;

import Modelo.BFS;
import Modelo.Grafo;

public class BFSTest 
{
	@Test(expected=IllegalArgumentException.class)
	public void grafoNullTest() 
	{
		BFS.esConexo(null);
	}

	@Test
	public void grafoVacioTest() 
	{
		assertTrue(BFS.esConexo(new Grafo(0)));
	}
	
	@Test
	public void grafoUnVerticeTest() 
	{
		assertTrue(BFS.esConexo(new Grafo(1)));
	}
	
	@Test
	public void grafoDosVerticesAisladosTest() 
	{
		assertFalse(BFS.esConexo(new Grafo(2)));
	}
	
	@Test
	public void grafoDosVerticesConexoTest() 
	{
		Grafo g = new Grafo(2);
		g.agregarArista(0, 1);
		assertTrue(BFS.esConexo(g));
	}
	
	@Test
	public void grafoInconexoTest() 
	{
		Grafo g = inicializarGrafoInconexo();
		
		assertFalse(BFS.esConexo(g));
	}
	
	@Test
	public void grafoCompletoTest() 
	{
		Grafo g = inicializarGrafoCompleto();
		assertTrue(BFS.esConexo(g));
	}
	
	@Test
	public void alcanzablesGrafoCompletoTest() 
	{
		Grafo g = inicializarGrafoCompleto();
		
		int[] esperado = {0, 1, 2, 3};
		Assert.iguales(esperado, BFS.alcanzables(g, 0));
	}

	
	@Test
	public void alcanzablesInconexoTest() 
	{
		Grafo g = inicializarGrafoInconexo();
		
		int[] esperado = {0, 1, 2, 3, 4};
		Assert.iguales(esperado, BFS.alcanzables(g, 0));
	}
	
	private Grafo inicializarGrafoInconexo() 
	{
		Grafo g = new Grafo(7);
		g.agregarArista(0, 1);
		g.agregarArista(0, 2);
		g.agregarArista(1, 2);
		g.agregarArista(1, 3);
		g.agregarArista(2, 4);
		g.agregarArista(3, 4);
		g.agregarArista(5, 6);
		
		return g;		
	}
	
	private Grafo inicializarGrafoCompleto() 
	{
		Grafo g = new Grafo(4);
		g.agregarArista(0, 1);
		g.agregarArista(1, 2);
		g.agregarArista(2, 3);
		g.agregarArista(0, 3);
		g.agregarArista(0, 2);
		g.agregarArista(1, 3);
		
		return g;		
	}
}
