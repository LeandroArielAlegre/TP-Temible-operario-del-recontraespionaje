package Modelo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BFS {
	private static Set<String> marcados;
	private static String encuentrosIntermedios;
	
    public static boolean esConexo(Grafo grafo) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo no puede ser null.");
        }

        return grafo.cantidadDeVertices() == 0 || alcanzables(grafo, grafo.dameOrigen()).size() == grafo.cantidadDeVertices();
    }

    public static Set<String> alcanzables(Grafo g, String origen) {
        marcados = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        List<String> listaDePendientes = new LinkedList<>();

        listaDePendientes.add(origen);
        int contadorEncuentros = 0;
        marcados.add(origen);
        sb.append("Orden de los encuentros del grafo con BFS").append("\n");
        sb.append("Nodo origen: " + origen).append("\n");
       

        while (!noEstaVacio(listaDePendientes)) {
            String actual = listaDePendientes.remove(0);

            for (String vecino : g.vecinosDeVertice(actual)) {
                if (!marcados.contains(vecino)) {
                    marcados.add(vecino); 
                    contadorEncuentros++;
                    sb.append("Encuentro: " + contadorEncuentros + " " + actual  + " y "+ vecino).append("\n");
                    listaDePendientes.add(vecino);
                                     
                }                            
            }            
        }
        encuentrosIntermedios = sb.toString();
        return marcados;
    }

	private static boolean noEstaVacio(List<String> listaDePendientes) {
		return listaDePendientes.isEmpty();
	}
	public static String obtenerEncuentrosIntermedios(Grafo grafo) {
		BFS.alcanzables(grafo, grafo.dameOrigen());
		return encuentrosIntermedios;
	}
}


