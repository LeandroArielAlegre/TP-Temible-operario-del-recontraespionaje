package Modelo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ArbolGenerador extends Grafo {
	private Grafo grafo;
	private String encuentrosIntermedios;
	
	public ArbolGenerador(Grafo grafo) {
		this.grafo = grafo;
		
		
	}    

	public Grafo crearArbolGeneradoMinimoPrim() 
	{	
		if (!BFS.esConexo(grafo)) 
		{		
			throw new IllegalArgumentException("El algoritmo de prim solo funciona sobre grafos conexos");
		}
		
		Grafo agmPrim = new Grafo();
		Set<String> visitados = new HashSet<>();//nodos marcados
		String origen= grafo.dameOrigen(); 
		StringBuilder sb=new StringBuilder();
		
		visitados.add(origen);
		agmPrim.agregarVertice(origen);
		sb.append("Arboles intermedios");
		sb.append("\n");
		
		while (visitados.size() < grafo.cantidadDeVertices()) {
			String mejorOrigen = null;
			String mejorDestino = null;
			Double mejorPeso = Double.MAX_VALUE;

			// Buscar la mejor arista(de menor peso)
			for (String vertice : visitados) {
				for (Map.Entry<String, Double> vecino : grafo.vecinosDeVerticeConPeso(vertice).entrySet()) {
					String vecinoNombre = vecino.getKey();
					
					Double peso = vecino.getValue();

					if (!visitados.contains(vecinoNombre) && peso < mejorPeso) {
						mejorPeso = peso;
						mejorOrigen = vertice;
						mejorDestino = vecinoNombre;
					}
				}
			}

			// Agregar la mejor arista al AGM
			if (mejorOrigen != null && mejorDestino != null) {
				agmPrim.agregarVertice(mejorDestino);
				agmPrim.agregarArista(mejorOrigen, mejorDestino, mejorPeso);
				visitados.add(mejorDestino);
				// Imprimir el estado actual del AGM
				
				
				sb.append("Árbol intermedio (agregando arista " + mejorOrigen + " - " + mejorDestino + " - "+ mejorPeso + "):");
				sb.append("\n");
				
				sb.append("\n");
				
			} else {
				break; // No hay más aristas disponibles
			}
		}
		sb.append("Vertices y sus vecinos");
		sb.append("\n");
		
		sb.append(agmPrim.toString());
		
		this.encuentrosIntermedios=sb.toString();

		
		return agmPrim;
	}
	
	
	public Grafo crearArbolGeneradoMinimoKruskal() {
		if (!BFS.esConexo(grafo)) 
		{		
			throw new IllegalArgumentException("El algoritmo de Kruskal solo funciona sobre grafos conexos");
		}
		
		Grafo agmKruskal = grafo.devolverGrafoInconexo();
		StringBuilder sb=new StringBuilder();
		ArrayList<ArrayList<String>> aristasVisitadas = new ArrayList<>();
		sb.append("Arboles intermedios");
		sb.append("\n");
		//Conjunto de aristas:
		 HashMap<ArrayList<String>, Double> conjuntoDeAristasDeGrafo = new HashMap<ArrayList<String>, Double>();
		 conjuntoDeAristasDeGrafo = grafo.conjuntoDeAristasYSuPeso();
	
		
		//Machete: Iterar sobre todas las aristas y encontrar el de menor peso, lo encuentro 
		// y lo agrego a aristVisitidas. En cada iteración consulto cual es el elemento con menor o igual peso
		//dentro de la lista.
		//Utilizo BFS para consultar los alcanzables de  algunos de los 2 extremos de la arista
		//de manera que no se generen circuitos.
		int i = 0;
		while(i <= grafo.cantidadDeVertices() -1) {
			
			ArrayList<String> aristaDeMenorPeso = devolverAristaConMinimoPeso(conjuntoDeAristasDeGrafo);
			
			Set<String> alcanzablesDeGrafoKruskal = BFS.alcanzables(agmKruskal, aristaDeMenorPeso.get(0));
			if(!alcanzablesDeGrafoKruskal.contains(aristaDeMenorPeso.get(1))) {
				//Agregar a aristasVisitadas
				aristasVisitadas.add(aristaDeMenorPeso);
				//Consulto probabilidad de grafo Original
				Double peso = grafo.obtenerPesoArista(aristaDeMenorPeso.get(0), aristaDeMenorPeso.get(1));
				//Lo agrego al grafo
				
				agmKruskal.agregarArista(aristaDeMenorPeso.get(0), aristaDeMenorPeso.get(1), peso);
				//Quito del ConjuntoDeAristasDeGrafo
				conjuntoDeAristasDeGrafo.remove(aristaDeMenorPeso);
				//Actualizo encuentro intermedio
				sb.append("Árbol intermedio (agregando arista " + aristaDeMenorPeso.get(0) + " - " + aristaDeMenorPeso.get(1) + "peso "
						+ peso +"):");
				sb.append("\n");
//				sb.append("\n");
				
				sb.append("\n");
				
				
			}else {
				//Quito del ConjuntoDeAristasDeGrafo
				conjuntoDeAristasDeGrafo.remove(aristaDeMenorPeso);
			}
			
			
			i+=1;
		}
		sb.append("Vertices y sus vecinos");
		sb.append("\n");
		sb.append(agmKruskal.toString());
		this.encuentrosIntermedios=sb.toString();
		return agmKruskal;
	}

	public ArrayList<String> devolverAristaConMinimoPeso(HashMap<ArrayList<String>, Double> conjuntoDeAristasDeGrafo) {
	    Double minPeso = Double.MAX_VALUE;
	    ArrayList<String> aristaMinima = null;

	    for (Entry<ArrayList<String>, Double> arista : conjuntoDeAristasDeGrafo.entrySet()) {
	        ArrayList<String> aristaActual = arista.getKey();
	        Double pesoActual = arista.getValue();

	        if (pesoActual < minPeso) {
	            minPeso = pesoActual;
	            aristaMinima = aristaActual;
	        }
	    }

	    return aristaMinima;
	}

	
	
	
	

	public String getEncuentrosIntermedios() {
		return encuentrosIntermedios;
	}


}
