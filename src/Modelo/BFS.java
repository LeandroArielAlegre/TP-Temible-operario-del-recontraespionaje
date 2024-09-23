package Modelo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import Modelo.Grafo;

public class BFS 
{
	private static List<Integer> L;
	private static boolean[] marcados;
	
	public static boolean esConexo(Grafo g) 
	{
		if (g == null)
			throw new IllegalArgumentException("El grafo no puede ser null.");
		
		return g.tamano() == 0 || alcanzables(g, 0).size() == g.tamano();
	}

	public static Set<Integer> alcanzables(Grafo g, int origen) 
	{
		Set<Integer> ret = new HashSet<Integer>();
		inicializarRecorrido(g, origen);
		
		while (!L.isEmpty()) 
		{
			int i = L.get(0);
			marcados[i] = true;
			
			ret.add(i);
			agregarVecinosPendientes(g, i);
			L.remove(0);
		}
		return ret;
	}

	private static void agregarVecinosPendientes(Grafo g, int vertice) 
	{		
		for (int vecino : g.vecinos(vertice))
			if (!marcados[vecino] && !L.contains(vecino))
				L.add(vecino);
	}
	
	private static void inicializarRecorrido(Grafo g, int origen) 
	{
		L = new LinkedList<Integer>();
		marcados = new boolean[g.tamano()];
		L.add(origen);
	}
}