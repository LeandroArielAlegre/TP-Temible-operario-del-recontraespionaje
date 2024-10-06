package Modelo;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArbolGenerador extends Grafo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Grafo grafo;
	private String encuentrosIntermedios;
	private int cantidadDeNodos;
	private int cantidadDeAristas;
	private int longitud;
	private String raiz;
	public ArbolGenerador(Grafo grafo) {
		this.grafo = grafo;
		cantidadDeNodos=0;
		cantidadDeAristas=0;
		longitud=0;
	}

	    

	public Grafo crearArbolGeneradoMinimoPrim() 
	{	
		if (!BFS.esConexo(grafo)) 
		{		
			throw new IllegalArgumentException("El algoritmo de prim funciona sobre grafos conexos");
		}
		
		Grafo agmPrim = new Grafo();
		Set<String> visitados = new HashSet<>();//nodos marcados
		String origen= grafo.dameOrigen(); 
		StringBuilder sb=new StringBuilder();
		
		visitados.add(origen);
		agmPrim.agregarVertice(origen);

		while (visitados.size() < grafo.cantidadDeVertices()) {
			String mejorOrigen = null;
			String mejorDestino = null;
			Double mejorPeso = 2.0;//valor que no pertece al intervalo 0-1

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
				
				
				sb.append("Árbol intermedio (agregando arista " + mejorOrigen + " - " + mejorDestino + "):");
				sb.append("\n");
//				sb.append("\n");
				sb.append(agmPrim.toString());
				sb.append("\n");
//				sb.append("\n");
			} else {
				break; // No hay más aristas disponibles
			}
		}
		this.encuentrosIntermedios=sb.toString();
		
		return agmPrim;
	}

	public void agregarVertice(String vertice) {
		if (!verificarVertice(vertice)) {
			throw new IllegalArgumentException("El vértice padre no existe en el árbol.");
		}
		if (estaVacioElGrafo()) {
			agregarVertice(vertice);
			this.raiz= vertice;
			cantidadDeNodos++;
		}
		agregarVertice(vertice);
		cantidadDeNodos++;
		//        agregarArista(padre, hijo);
	}

	public void agregarHijo(String padre, String hijo, Double probabilidad) {
		if (!verificarVertice(padre)) {
			throw new IllegalArgumentException("El vértice padre no existe en el árbol.");
		}
		agregarVertice(hijo);
		cantidadDeNodos++;
		agregarArista(padre, hijo, probabilidad);
		cantidadDeAristas++;
	}

	public ArrayList<String> obtenerHijos(String padre) {
		if (!verificarVertice(padre)) {
			throw new IllegalArgumentException("El vértice padre no existe en el árbol.");
		}
		return new ArrayList<String>(vecinosDeVertice(padre));
	}

	public String obtenerRaiz() {
		return raiz;
	}

	public boolean esHoja(String vertice) {
		if (noEsArbolTrivial()) {
			return (gradoDeVertice(vertice) == 1 && vertice != raiz);
		}
		throw new IllegalArgumentException("El conjunto de vertices definido es trivial y no tiene al menos 2 hojas");
	}
	//    public String[] obtenerHojas() {
	//    	
	//    }
	private boolean noEsArbolTrivial() {
		if (cantidadDeNodos>=2) {
			return true;
		}
		return false;
	}

	@Override
	public void agregarArista(String i, String j, Double probabilidad) {
		if (i == j) {
			throw new IllegalArgumentException("No se permiten bucles en un árbol.");
		}
		if (existeArista(i, j)) {
			throw new IllegalArgumentException("La arista ya existe en el árbol.");
		}

		super.agregarArista(i, j, probabilidad);
	}

	public String getEncuentrosIntermedios() {
		return encuentrosIntermedios;
	}



	// No permitimos eliminar aristas directamente en un árbol
	@Override
	public void eliminarArista(String vertice1, String vertice2) {
		throw new UnsupportedOperationException("No se permite eliminar aristas directamente en un árbol. Ya no seria un arbol");
	}
}
