package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Grafo
{
	// Representamos el grafo por su matriz de vecinos
	//private boolean[][] A;
	private ArrayList  <HashSet <Integer>> arrayListVecinos;
	//private int contador ;
	
	// La cantidad de vertices esta predeterminada desde el constructor
	public Grafo(int vertices)
	{
		arrayListVecinos = new ArrayList<HashSet <Integer>>();
		//A = new boolean[vertices][vertices];
		for(int i = 0; i< vertices; i++) {
			arrayListVecinos.add(new HashSet<Integer>());
		}
	}
	
	
	
	/*
	//GRAFO VACIO
		public Grafo()
		{
			arrayListVecinos = new ArrayList<HashSet <Integer>>();	
		
	}
	
	public void agregarVertice(int vertice) {
		
		
		if(estaGrafoVacio()) {
			HashSet<Integer> nuevoSet = new HashSet<>();
		    nuevoSet.add(vertice);
		    arrayListVecinos.add(nuevoSet); // Aquí sí es necesario añadirlo al ArrayList
		}else {
			if(verificarVertice(vertice)){
				throw new IllegalArgumentException("El vértice ya existe.");
			}else {
				arrayListVecinos.add(new HashSet<Integer>(vertice));
			}
		
		}
			
		
			
	
		
			
			
		
	}
		
	
	public boolean estaGrafoVacio() {
		if(arrayListVecinos.size() == 0) {
			return true;
		}
		return false;
	}
	
	*/
	// Agregado de aristas
		public void agregarArista(int i, int j)
	
		{
			
			if( verificarVertice(i) && verificarVertice(j)) {
				if(!existeArista(i,j)) {
					arrayListVecinos.get(i).add(j);
					arrayListVecinos.get(j).add(i);
				}else {
					throw new IllegalArgumentException("No existe la Arista: " + i + " , " + j);
				}
				
			}else {
				throw new IllegalArgumentException("No existe alguno de los Vertices indicado" + i + " u " +  j);
			}
			
			
				
		}
	
	
		// Eliminacion de aristas
		public void eliminarArista(int i, int j)
		
		{
			if(verificarVertice(i) && verificarVertice(j)) {
				if(existeArista(i,j)) {
					arrayListVecinos.get(i).add(j);
					arrayListVecinos.get(j).add(i);
				}else {
					throw new IllegalArgumentException("No existe la Arista: " + i + " , " + j);
				}
			}else {
				throw new IllegalArgumentException("No existe alguno de los Vertices indicado" + i + " u" +  j);
			}
		
		
		}
		// Informa si existe la arista especificada
		public boolean existeArista(int i, int j)
		{
		return arrayListVecinos.get(i).contains(j);
			
		}
	
		private boolean verificarVertice(int i)
		{
			if(arrayListVecinos.get(i) != null) {
				return true;
			}
			return false;
			 
		}
		// Cantidad de vertices
		public int tamano()
		{
			return arrayListVecinos.size();
		}
	
	
	
	//Vecinos (arraylistVeciones.get(i).vecinos(ArrayListVecinos.get(i);
		public HashSet <Integer> vecinos(int i )
		{
			if(verificarVertice(i)) {
				HashSet<Integer> ret = arrayListVecinos.get(i);
				return ret;
				
			}else {
				throw new IllegalArgumentException("No existe el Vertice indicado: " + i );
			}
			
		}
	
		
		public int grado(int i)
		{
			verificarVertice(i);
			return vecinos(i).size();
		}
	
	
	
	
	
	
	
	
/*
	// Eliminacion de aristas
	public void eliminarArista(int i, int j)
	{
		verificarVertice(i);
		verificarVertice(j);
		//verificarDistintos(i, j);

		A[i][j] = false;
		A[j][i] = false;
	}*/
/*
	// Informa si existe la arista especificada
	public boolean existeArista(int i, int j)
	{
		verificarVertice(i);
		verificarVertice(j);
		//verificarDistintos(i, j);

		return A[i][j];
	}*/

		/*
	// Cantidad de vertices
	public int tamano()
	{
		return A.length;
	}
	*/
		
		/*
	// Vecinos de un vertice
	public Set<Integer> vecinos(int i)
	{
		verificarVertice(i);
		
		Set<Integer> ret = new HashSet<Integer>();
		for(int j = 0; j < this.tamano(); ++j) if( i != j )
		{
			if( this.existeArista(i,j) )
				ret.add(j);
		}
		
		return ret;
	}*/
	
	/*
	public int grado(int i)
	{
		verificarVertice(i);
		return vecinos(i).size();
	}*/
	
	/*
	// Verifica que sea un vertice valido
	private void verificarVertice(int i)
	{
		if( i < 0 )
			throw new IllegalArgumentException("El vertice no puede ser negativo: " + i);
		
		if( i >= A.length )
			throw new IllegalArgumentException("Los vertices deben estar entre 0 y |V|-1: " + i);
	}*/

	/*
	// Verifica que i y j sean distintos
	private void verificarDistintos(int i, int j)
	{
		if( i == j )
			throw new IllegalArgumentException("No se permiten loops: (" + i + ", " + j + ")");
	}
	*/
}

