package CasosDeTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Modelo.Grafo;

public class EdicionDeAristasTest
{
//	@Test(expected = IllegalArgumentException.class)
//	public void primerVerticeNegativoTest()
//	{
//		Grafo grafo = new Grafo();
//		
//		grafo.agregarArista(-1, 3);
//	}

//	@Test(expected = IllegalArgumentException.class)
//	public void primerVerticeExcedidoTest()
//	{
//		Grafo grafo = new Grafo(5);
//		grafo.agregarArista(5, 2);
//	}

//	@Test(expected = IllegalArgumentException.class)
//	public void segundoVerticeNegativoTest()
//	{
//		Grafo grafo = new Grafo(5);
//		grafo.agregarArista(2, -1);
//	}

//	@Test(expected = IllegalArgumentException.class)
//	public void segundoVerticeExcedidoTest()
//	{
//		Grafo grafo = new Grafo(5);
//		grafo.agregarArista(2, 5);
//	}
	
	@Test(expected = IllegalArgumentException.class)
	public void agregarLoopTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("2");
		grafo.agregarArista("2","2",0.0);
	}

	@Test
	public void aristaExistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("2");
		grafo.agregarVertice("3");
		grafo.agregarArista("2", "3",1.0);
		assertTrue( grafo.existeArista("2", "3") );
	}

	@Test
	public void aristaOpuestaTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("2");
		grafo.agregarVertice("3");
		grafo.agregarArista("2", "3",0.0);
		assertTrue( grafo.existeArista("3","2") );
	}

	@Test
	public void aristaInexistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("2");
		grafo.agregarVertice("3");
		grafo.agregarVertice("1");
		grafo.agregarVertice("4");
		grafo.agregarArista("2", "3",1.0);
		assertFalse( grafo.existeArista("1", "4") );
	}

	@Test (expected= IllegalArgumentException.class)
	public void agregarAristaDosVecesTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("2");
		grafo.agregarVertice("3");
		grafo.agregarArista("2", "3",1.0);
		grafo.agregarArista("2", "3",0.0);
	}
	
	@Test
	public void eliminarAristaExistenteTest()
	{
		Grafo grafo = new Grafo();
		
		grafo.agregarVertice("2");
		grafo.agregarVertice("4");
		grafo.agregarArista("2", "4",0.0);		
		grafo.eliminarArista("2", "4");
		
		assertFalse( grafo.existeArista("2", "4") );
	}

	@Test (expected = IllegalArgumentException.class)
	public void eliminarAristaInexistenteTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("2");
		grafo.agregarVertice("3");
		grafo.agregarVertice("4");
		grafo.eliminarArista("2", "4");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void eliminarAristaDosVecesTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("2");
		grafo.agregarVertice("4");
		grafo.agregarArista("2", "4",1.0);
		
		grafo.eliminarArista("2", "4");
		grafo.eliminarArista("2", "4");
	}
}

