package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grafo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Mapa de nombres de vértices a sus índices
    private HashMap<String, Integer> nombresAIndices;
    // Mapa de índices a nombres de vértices
    private HashMap<Integer, String> indicesANombres;
    // Representamos el grafo por su lista de vecinos
    protected HashMap<String, HashMap<String, Double>> grafo;
    protected int contadorVertices;

    public Grafo() {
        grafo = new HashMap<>();
        nombresAIndices = new HashMap<>();
        indicesANombres = new HashMap<>();
        contadorVertices = 0;
    } 
    
    
 
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (String vertice : grafo.keySet()) {
            sb.append("Vértice ").append(vertice).append(": ");
            HashMap<String, Double> vecinos = grafo.get(vertice);
            
            if (vecinos.isEmpty()) {
                sb.append("sin vecinos");
            } else {
                for (Map.Entry<String, Double> entry : vecinos.entrySet()) {
                    sb.append(entry.getKey()).append(" (peso: ").append(entry.getValue()).append("), ");
                }
                sb.setLength(sb.length() - 2); // Eliminar la última coma
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    public HashMap<String, HashMap<String,Double>> devolverGrafo() {
    	return this.grafo;
    }
    public void agregarVertice(String nombre) {
        if (nombresAIndices.containsKey(nombre)) {
            throw new IllegalArgumentException("El vértice ya existe: " + nombre);
        }
        int indice = contadorVertices++;
        nombresAIndices.put(nombre, indice);
        indicesANombres.put(indice, nombre);
        grafo.put(nombre, new HashMap<>());
    }

    public boolean estaVacioElGrafo() {
        return grafo.isEmpty();
    }

    public void agregarArista(String nombre1, String nombre2, Double probabilidad) {
        if (!nombresAIndices.containsKey(nombre1) || !nombresAIndices.containsKey(nombre2)) {
            throw new IllegalArgumentException("Alguno de los vértices ingresados no existen.");
        }

        if (nombre1.equals(nombre2)) {
            throw new IllegalArgumentException("No se admiten loops");
        }

        if (!existeArista(nombre1, nombre2)) {
            //Random randomProbabilidad = new Random();
            //int probabilidadDeEncuentro = randomProbabilidad.nextInt(2);
            grafo.get(nombre1).put(nombre2, probabilidad);
            grafo.get(nombre2).put(nombre1, probabilidad);
        }
    }
//    !formaCircuito(nombre1,nombre2,)
    public void eliminarArista(String nombre1, String nombre2) {
        if (!existeArista(nombre1, nombre2)) {
            throw new IllegalArgumentException("No existe la arista: " + nombre1 + " , " + nombre2);
        }
        grafo.get(nombre1).remove(nombre2);
        grafo.get(nombre2).remove(nombre1);
    }

    public boolean existeArista(String nombre1, String nombre2) {
        return grafo.containsKey(nombre1) && grafo.containsKey(nombre2) && grafo.get(nombre1).containsKey(nombre2);
    }
    
    
    public boolean existeVertice(String vertice) {
    	return grafo.containsKey(vertice);
    }
    
    public int obtenerPesoArista(String nombre1,String nombre2) {
    	if(existeArista(nombre1,nombre2)) {
    		return grafo.get(nombre1).get(nombre2).intValue();
    	}
    	return 0;
    }
    public int tamano() {
        return grafo.size();
    }

    public ArrayList<String> vecinosDeVertice(String nombre) {
    	ArrayList<String> ret= new ArrayList<>();

    	
        if (!grafo.containsKey(nombre)) {
            throw new IllegalArgumentException("El vértice no existe: " + nombre);
        }
        for(String s : grafo.get(nombre).keySet()) {
    		ret.add(s);
    	}
        return ret;
    }
    
    public HashMap<String,Double> vecinosDeVerticeConPeso(String nombre) {
    	HashMap<String,Double> ret= new HashMap<String,Double>();

    	
        if (!grafo.containsKey(nombre)) {
            throw new IllegalArgumentException("El vértice no existe: " + nombre);
        }
        for (Map.Entry<String, Double> entry : grafo.get(nombre).entrySet()) {
            // Agregamos al HashMap de retorno el vecino y su peso asociado
            ret.put(entry.getKey(), entry.getValue());
        }
        return ret;
    }
    

    public int gradoDeVertice(String nombre) {
        if (!grafo.containsKey(nombre)) {
            throw new IllegalArgumentException("El vértice no existe: " + nombre);
        }
        return grafo.get(nombre).size();
    }

    public String obtenerNombre(int indice) {
        String nombre = indicesANombres.get(indice);
        if (nombre == null) {
            throw new IllegalArgumentException("No existe el vértice con índice: " + indice);
        }
        return nombre;
    }
    public int obtenerIndice(String nombre) {
        int indiceDeNombre = nombresAIndices.get(nombre);
        if (nombre == null) {
            throw new IllegalArgumentException("No existe el vértice con nombre: " + nombre);
        }
        return indiceDeNombre;
    }
    public int cantidadDeVertices() {
        return contadorVertices;
    }

    public String dameOrigen() {
        if (!estaVacioElGrafo()) {
            return grafo.keySet().iterator().next(); // devuelve el 1er vertice
        }
        throw new IllegalArgumentException("el grafo esta vacio");
    }
	protected boolean verificarVertice(String vertice)
	{
		return grafo.containsKey(vertice);
	}

	
	public void reiniciarGrafo() {
		nombresAIndices.clear();
		indicesANombres.clear();
		this.contadorVertices = 0;
		grafo.clear();
		
	}
}

//}
//
