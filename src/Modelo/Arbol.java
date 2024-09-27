package Modelo;

import java.util.ArrayList;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Arbol extends Grafo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String raiz;
    private int cantidadDeNodos;
    private int cantidadDeAristas;
    private int longitud;
//    private String[] conjuntoDeHojas;
    
    public Arbol() {
        super();  // Llamo al constructor de Grafo
//        this.raiz = raiz;
//        agregarVertice(raiz);
        cantidadDeNodos=0;
        cantidadDeAristas=0;
        longitud=0;
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
    public void agregarHijo(String padre, String hijo) {
        if (!verificarVertice(padre)) {
            throw new IllegalArgumentException("El vértice padre no existe en el árbol.");
        }
        agregarVertice(hijo);
        cantidadDeNodos++;
        agregarArista(padre, hijo);
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
    public void agregarArista(String i, String j) {
        if (i == j) {
            throw new IllegalArgumentException("No se permiten bucles en un árbol.");
        }
        if (existeArista(i, j)) {
            throw new IllegalArgumentException("La arista ya existe en el árbol.");
        }
        
        super.agregarArista(i, j);
    }

    // No permitimos eliminar aristas directamente en un árbol
    @Override
    public void eliminarArista(String vertice1, String vertice2) {
        throw new UnsupportedOperationException("No se permite eliminar aristas directamente en un árbol. Ya no seria un arbol");
    }
}