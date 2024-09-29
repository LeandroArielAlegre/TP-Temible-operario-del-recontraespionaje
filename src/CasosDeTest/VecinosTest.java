package CasosDeTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Modelo.Grafo;

public class VecinosTest
{
//	@Test(expected = IllegalArgumentException.class)
//	public void verticeNegativoTest()
//	{
//		Grafo grafo = new Grafo(5);
//		grafo.vecinosDeVertice(-1);
//	}

//	@Test(expected = IllegalArgumentException.class)
//	public void verticeExcedidoTest()
//	{
//		Grafo grafo = new Grafo(5);
//		grafo.vecinosDeVertice(5);
//	}

	@Test
	public void todosAisladosTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("2");
		grafo.agregarVertice("3");
		grafo.agregarVertice("4");
		grafo.agregarVertice("5");
		assertEquals(0, grafo.vecinosDeVertice("2").size());
		assertEquals(0, grafo.vecinosDeVertice("3").size());
		assertEquals(0, grafo.vecinosDeVertice("4").size());
		assertEquals(0, grafo.vecinosDeVertice("5").size());
	}
	
	@Test
	public void verticeUniversalTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("0");
		grafo.agregarVertice("1");
		grafo.agregarVertice("2");
		grafo.agregarVertice("3");
		grafo.agregarArista("1", "0",0);
		grafo.agregarArista("1", "2",0);
		grafo.agregarArista("1", "3",0);
		
		String[] esperado = {"0", "2", "3"};
		Assert.iguales(esperado, grafo.vecinosDeVertice("1"));
	}
	
	@Test
	public void verticeNormalTest()
	{
		Grafo grafo = new Grafo();
		grafo.agregarVertice("1");
		grafo.agregarVertice("2");
		grafo.agregarVertice("3");
		grafo.agregarVertice("4");
		grafo.agregarArista("1", "3",1);
		grafo.agregarArista("2", "3",1);
		grafo.agregarArista("2", "4",1);
		
		String[] esperados = {"1", "2"};
		Assert.iguales(esperados, grafo.vecinosDeVertice("3"));
	}
}

