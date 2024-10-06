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
    protected HashMap<String, HashMap<String, Integer>> grafo;
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
            HashMap<String, Integer> vecinos = grafo.get(vertice);
            
            if (vecinos.isEmpty()) {
                sb.append("sin vecinos");
            } else {
                for (Map.Entry<String, Integer> entry : vecinos.entrySet()) {
                    sb.append(entry.getKey()).append(" (peso: ").append(entry.getValue()).append("), ");
                }
                sb.setLength(sb.length() - 2); // Eliminar la última coma
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    public HashMap<String, HashMap<String,Integer>> devolverGrafo() {
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

    public void agregarArista(String nombre1, String nombre2, int probabilidad) {
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
    
    public HashMap<String,Integer> vecinosDeVerticeConPeso(String nombre) {
    	HashMap<String,Integer> ret= new HashMap<String,Integer>();

    	
        if (!grafo.containsKey(nombre)) {
            throw new IllegalArgumentException("El vértice no existe: " + nombre);
        }
        for (Map.Entry<String, Integer> entry : grafo.get(nombre).entrySet()) {
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

	
	public void eliminarVerticesYVecinos() {
		grafo = new HashMap<String, HashMap<String, Integer>>();
	}
}



//package Modelo;
//import java.util.HashMap;
//import java.util.Random;
//import java.util.Set;
//import java.util.Map;
//
//public class Grafo {
//    // Mapa de nombres de vértices a sus índices
//    private HashMap<String, Integer> nombresAIndices;
//    // Mapa de índices a nombres de vértices
//    private HashMap<Integer, String> indicesANombres;
//    // Representamos el grafo por su lista de vecinos
//    private HashMap<String, HashMap<Integer, Integer>> grafo;
//    private int contadorVertices;
//
//    public Grafo() {
//        grafo = new HashMap<String, HashMap<Integer, Integer>>();
//        nombresAIndices = new HashMap<String, Integer>();
//        indicesANombres = new HashMap<Integer, String>();
//        contadorVertices = 0;
//    } 
//    public void agregarVertice(String nombre) {
//        if (nombresAIndices.containsKey(nombre)) {
//            throw new IllegalArgumentException("El vértice ya existe: " + nombre);
//        }
//        int indice = contadorVertices++;
//        nombresAIndices.put(nombre, indice);
//        indicesANombres.put(indice, nombre);
//        grafo.put(nombre, new HashMap<Integer, Integer>());
//    }
//
//    public boolean estaVacioElGrafo() {
//        return grafo.isEmpty();
//    }
//
//    public void agregarArista(String nombre1, String nombre2) {
//        int i = obtenerIndice(nombre1);
//        int j = obtenerIndice(nombre2);
//        Random randomProbabilidad = new Random();
//        int probabilidadDeEncuentro = randomProbabilidad.nextInt(2);
//        if (i != j) {
//            if (!existeArista(i, j)) {
//                grafo.get(i).put(j, probabilidadDeEncuentro);
//                grafo.get(j).put(i, probabilidadDeEncuentro);
//            }
//        } else {
//            throw new IllegalArgumentException("No se admiten loops");
//        }
//    }
//
//    public void eliminarArista(String nombre1, String nombre2) {
//        int vertice1 = obtenerIndice(nombre1);
//        int vertice2 = obtenerIndice(nombre2);
//        if (existeArista(vertice1, vertice2)) {
//            grafo.get(vertice1).remove(vertice2);
//            grafo.get(vertice2).remove(vertice1);
//        } else {
//            throw new IllegalArgumentException("No existe la Arista: " + nombre1 + " , " + nombre2);
//        }
//    }
//
//    public boolean existeArista(int i, int j) {
//        return grafo.get(i).containsKey(j);
//    }
//
//    public boolean existeArista(String nombre1, String nombre2) {
//        int i = obtenerIndice(nombre1);
//        int j = obtenerIndice(nombre2);
//        return existeArista(i, j);
//    }
//
//    private int obtenerIndice(String nombre) {
//        Integer indice = nombresAIndices.get(nombre);
//        if (indice == null) {
//            throw new IllegalArgumentException("No existe el vértice: " + nombre);
//        }
//        return indice;
//    }
//
//    public int tamano() {
//        return grafo.size();
//    }
//
//    public Set<String> vecinosDeVertice(String nombre) {
//        int i = obtenerIndice(nombre);
//        Set<Integer> indicesVecinos = grafo.get(i).keySet();
//        Set<String> nombresVecinos = new java.util.HashSet<>();
//        for (int indice : indicesVecinos) {
//            nombresVecinos.add(indicesANombres.get(indice));
//        }
//        return nombresVecinos;
//    }
//
//    public int grado(String nombre) {
//        int i = obtenerIndice(nombre);
//        return grafo.get(i).size();
//    }
//
//    public String obtenerNombre(int indice) {
//        String nombre = indicesANombres.get(indice);
//        if (nombre == null) {
//            throw new IllegalArgumentException("No existe el vértice con índice: " + indice);
//        }
//        return nombre;
//    }
//
//
//	public int gradoDeUnVertice(String vertice)
//	{
//		verificarVertice(vertice);
//		return vecinosDeVertice(vertice).size();
//	}
//	protected boolean verificarVertice(String vertice)
//	{
//		return grafo.containsKey(vertice);
//	}
//
//	public int cantidadDeVertices() {
//		// TODO Auto-generated method stub
//		return contadorVertices;
//	}
//	public String dameOrigen() {
//		// TODO Auto-generated method stub
//		if (!estaVacioElGrafo()) {
//			return grafo.get(0);
//		}
//	}
//	
//}
//
//import java.util.HashMap;
//import java.util.Random;
//import java.util.Set;
//
//public class Grafo
//{
//	// Representamos el grafo por su lista de vecinos
//
//	private HashMap  <Integer, HashMap<Integer,Integer>> grafo;
//
//	// La cantidad de vertices predeterminada
//	public Grafo(int vertices)
//	{
//		grafo = new HashMap <Integer, HashMap<Integer,Integer>>();
//		for(int i = 0; i< vertices; i++) {
//			grafo.put(i,new HashMap<Integer,Integer>());
//		}
//	}
//
//	//GRAFO VACIO
//	public Grafo()
//	{
//		grafo = new HashMap <Integer, HashMap<Integer,Integer>>();
//	}
//
//	public void agregarVertice(int vertice) 
//	{
//
//		if(estaVacioElGrafo()) 
//		{
//			HashMap<Integer,Integer> conjuntoDeVecinos = new HashMap<>();
//			conjuntoDeVecinos.put(vertice, null);
//			grafo.put(vertice ,conjuntoDeVecinos); // Aquí sí es necesario añadirlo al ArrayList		
//		}else {
//			if(verificarVertice(vertice))
//			{
//				throw new IllegalArgumentException("El vértice ya existe.");
//			}else {
//				grafo.put(vertice ,new HashMap<Integer,Integer>());
//			}
//
//		}
//
//	}
//
//	public boolean estaVacioElGrafo() {
//		if(grafo.size() == 0) {
//			return true;
//		}
//		return false;
//	}
//
//	// Agregado de aristas
//	public void agregarArista(int i, int j)
//	{
//		//Se define la probabilidad de encuentro entre espias
//		Random randomProbabilidad = new Random();
//		int probabilidadDeEncuentro = randomProbabilidad.nextInt(2);
//		if(verificarVertice(i) && verificarVertice(j))
//		{
//			if(!existeArista(i,j)) 
//			{ 
//			if(i!=j) {
//				grafo.get(i).put(j,probabilidadDeEncuentro);
//				grafo.get(j).put(i,probabilidadDeEncuentro);
//			}else {
//				throw new IllegalArgumentException("No se admiten loops");
//			
//			}
//			}
//		}else {
//			throw new IllegalArgumentException("No existe alguno de los Vertices indicado" + i + " u " +  j);
//		}
//	}
//
//	// Eliminacion de aristas
//	public void eliminarArista(int vertice1, int vertice2)
//
//	{
//		if(verificarVertice(vertice1) && verificarVertice(vertice2)) 
//		{
//			if(existeArista(vertice1,vertice2)) 
//			{
//				grafo.get(vertice1).remove(vertice2);
//				grafo.get(vertice2).remove(vertice1);
//			}else {
//				throw new IllegalArgumentException("No existe la Arista: " + vertice1 + " , " + vertice2);
//			}
//		}else {
//			throw new IllegalArgumentException("No existe alguno de los Vertices indicado" + vertice1 + " u" +  vertice2);
//		}
//
//	}
//	
//	// Informa si existe la arista especificada
//	public boolean existeArista(int i, int j)
//	{
//		return grafo.get(i).containsKey(j);
//
//	}
//
//	protected boolean verificarVertice(int vertice)
//	{
//		if(grafo.get(vertice) != null && vertice>=0) 
//		{
//			return true;
//		}
//		return false;
//	}
//	
//	// Cantidad de vertices
//	public int cantidadDeVertices()
//	{
//		return grafo.size();
//	}
//
//	public Set<Integer> vecinosDeVertice(int vertice)
//	{
//		if(verificarVertice(vertice)) {
//			Set<Integer> vecinos = grafo.get(vertice).keySet();
//			return vecinos;
//		}else {
//			throw new IllegalArgumentException("No existe el Vertice indicado: " + vertice );
//		}
//	}
//
//	public int gradoDeUnVertice(int vertice)
//	{
//		verificarVertice(vertice);
//		return vecinosDeVertice(vertice).size();
//	}
//	public void darNombreAVertice(int vertice, String nombre) {
//		
//	}
//}
//
