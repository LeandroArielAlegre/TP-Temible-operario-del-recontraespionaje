package Modelo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BFS {
	private static Set<String> marcados;
    public static boolean esConexo(Grafo g) {
        if (g == null) {
            throw new IllegalArgumentException("El grafo no puede ser null.");
        }

        return g.cantidadDeVertices() == 0 || alcanzables(g, g.dameOrigen()).size() == g.cantidadDeVertices();
    }

    public static Set<String> alcanzables(Grafo g, String origen) {
        marcados = new HashSet<>();
        List<String> listaDePendientes = new LinkedList<>();

        listaDePendientes.add(origen);
        marcados.add(origen);

        while (!noEstaVacio(listaDePendientes)) {
            String i = listaDePendientes.remove(0);

            for (String vecino : g.vecinosDeVertice(i)) {
                if (!marcados.contains(vecino)) {
                    marcados.add(vecino); 
                    listaDePendientes.add(vecino);
                }
            }
        }

        return marcados;
    }

	private static boolean noEstaVacio(List<String> listaDePendientes) {
		return listaDePendientes.isEmpty();
	}
}

//package Modelo;
//
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//import Modelo.Grafo;
//
//public class BFS 
//{
//	private static List<String> L;
////	private static boolean[] marcados;
//	
//	public static boolean esConexo(Grafo g) 
//	{
//		if (g == null)
//			throw new IllegalArgumentException("El grafo no puede ser null.");
//		
//		return g.cantidadDeVertices() == 0 || alcanzables(g, g.dameOrigen()).size() == g.cantidadDeVertices();
//	}
//	 public static List<String> alcanzables(Grafo g, String origen) {
//		    inicializarRecorrido(g, origen);
//	        int index = 0;
//	        String[] vertices = new String[g.cantidadDeVertices()]; // Array to simulate a queue
//	        vertices[index++] = origen; // Add the starting vertex
//
//	        while (index > 0) {
//	            String current = vertices[0]; // Get the first element
//	            System.arraycopy(vertices, 1, vertices, 0, index - 1); // Shift left
//	            index--; // Decrease the index
//
//	            if (!L.contains(current)) {
//	                L.add(current); // Mark the current vertex as visited
//	                
//	                // Add neighbors to the simulated queue
//	                for (String vecino : g.vecinosDeVertice(current)) {
//	                    if (!L.contains(vecino)) {
//	                        vertices[index++] = vecino; // Add to the end of the array
//	                    }
//	                }
//	            }
//	        }
//	        
//	        return L;
//	    }
////	public static Set<String> alcanzables(Grafo g, String origen) 
////	{
////		Set<String> ret = new HashSet<String>();
////		inicializarRecorrido(g, origen);
////		
////		while (!L.isEmpty()) 
////		{
////			String i = L.get(0);
////			marcados[i] = true;
////			
////			ret.add(i);
////			agregarVecinosPendientes(g, i);
////			L.remove(0);
////		}
////		return ret;
////	}
//
////	private static void agregarVecinosPendientes(Grafo g, String vertice) 
////	{		
////		for (String vecino : g.vecinosDeVertice(vertice))
////			if (!marcados[vecino] && !L.contains(vecino))
////				L.add(vecino);
////	}
//	
//	private static void inicializarRecorrido(Grafo g, String origen) 
//	{
//		L = new LinkedList<String>();
////		marcados = new boolean[g.cantidadDeVertices()];
//		L.add(origen);
//	}
//}