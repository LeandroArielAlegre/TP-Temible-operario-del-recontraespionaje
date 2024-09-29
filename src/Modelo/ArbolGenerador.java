package Modelo;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArbolGenerador extends Grafo {
	private Grafo grafo;
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

	    public static void main(String[] args) {
	        Grafo grafo = new Grafo();
	        grafo.agregarVertice("0");
	        grafo.agregarVertice("2");
	        grafo.agregarVertice("3");
	        grafo.agregarVertice("4");
	        grafo.agregarArista("0", "2", 1);
	        grafo.agregarArista("0", "3", 0);
	        grafo.agregarArista("2", "3", 0);
	        grafo.agregarArista("2", "4", 0);
	        grafo.agregarArista("3", "4", 1);
	        
	        ArbolGenerador arbolGenerador = new ArbolGenerador(grafo);
	        Grafo grafoPrim = arbolGenerador.crearArbolGeneradoMinimoPrim();
	        System.out.println(grafoPrim.toString());
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
		visitados.add(origen);
		agmPrim.agregarVertice(origen);

		while (visitados.size() < grafo.cantidadDeVertices()) {
			String mejorOrigen = null;
			String mejorDestino = null;
			int mejorPeso = 2;//valor que no pertece al intervalo 0-1

			// Buscar la mejor arista(de menor peso)
			for (String vertice : visitados) {
				for (Map.Entry<String, Integer> vecino : grafo.vecinosDeVerticeConPeso(vertice).entrySet()) {
					String vecinoNombre = vecino.getKey();
					
					int peso = vecino.getValue();

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
				System.out.println("Árbol intermedio (agregando arista " + mejorOrigen + " - " + mejorDestino + "):");
				System.out.println(agmPrim.toString());
			} else {
				break; // No hay más aristas disponibles
			}
		}

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

	public void agregarHijo(String padre, String hijo, int probabilidad) {
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
	public void agregarArista(String i, String j, int probabilidad) {
		if (i == j) {
			throw new IllegalArgumentException("No se permiten bucles en un árbol.");
		}
		if (existeArista(i, j)) {
			throw new IllegalArgumentException("La arista ya existe en el árbol.");
		}

		super.agregarArista(i, j, probabilidad);
	}

	// No permitimos eliminar aristas directamente en un árbol
	@Override
	public void eliminarArista(String vertice1, String vertice2) {
		throw new UnsupportedOperationException("No se permite eliminar aristas directamente en un árbol. Ya no seria un arbol");
	}
}
