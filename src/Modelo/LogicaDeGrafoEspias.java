package Modelo;

import java.util.HashMap;
import java.util.Map;

public class LogicaDeGrafoEspias {
	private Grafo grafoEspias;
	//private Arbol arbolDeEspias;
	private ArbolGenerador arbolGeneradorMinimo;
	

	public LogicaDeGrafoEspias() 
	{
		//arbolDeEspias = new Arbol();
		grafoEspias = new Grafo();
		arbolGeneradorMinimo = new ArbolGenerador(grafoEspias);
	}	
	
	public boolean crearVertice(String vertice) 
	{
		try {
			grafoEspias.agregarVertice(vertice);
			return true;
			
		} catch (IllegalArgumentException e) {
//	        System.out.println("Error: El vértice ya existe. " + e.getMessage());
	        return false;
	    } catch (Exception e) {
//	        System.out.println("Error inesperado: " + e.getMessage());
	        return false;
	    }
	}
	
	public boolean crearArista(String vertice, String vertice2, int probabilidad) 
	{
		try {
			grafoEspias.agregarArista(vertice, vertice2, probabilidad);
			return true;
			
		} catch (IllegalArgumentException e) {
//	        System.out.println("Error: El vértice ya existe. " + e.getMessage());
	        return false;
	    } catch (Exception e) {
//	        System.out.println("Error inesperado: " + e.getMessage());
	        return false;
	    }
		
	}
	
	public boolean existeArista(String nombre1, String nombre2) 
	{
        return grafoEspias.existeArista(nombre1, nombre2); 	
        
    }
	
	public HashMap<String, HashMap<String,Integer>> crearArbolGeneradorMinimoPrim() 
	{
		try {
			Grafo grafoEspiasPrim = this.arbolGeneradorMinimo.crearArbolGeneradoMinimoPrim();
			HashMap<String, HashMap<String,Integer>> arbolGeneradorMinimoPrim = grafoEspiasPrim.devolverGrafo();
			
			return arbolGeneradorMinimoPrim;
			
		} catch (IllegalArgumentException e) {
//	        System.out.println("Error: El vértice ya existe. " + e.getMessage());
	        return null;
	    } catch (Exception e) {
//	        System.out.println("Error inesperado: " + e.getMessage());
	        return null;
	    }
		
	}

	public HashMap<String, HashMap<String,Integer>> crearArbolGeneradorMinimoKruskal() 
	{

			try {
				Grafo grafoEspiasPrim = this.arbolGeneradorMinimo.crearArbolGeneradoMinimoKruskal();
				HashMap<String, HashMap<String,Integer>> arbolGeneradorMinimoPrim = grafoEspiasPrim.devolverGrafo();
				
				return arbolGeneradorMinimoPrim;
				
			} catch (IllegalArgumentException e) {
//		        System.out.println("Error: El vértice ya existe. " + e.getMessage());
		        return null;
		    } catch (Exception e) {
//		        System.out.println("Error inesperado: " + e.getMessage());
		        return null;
		    }
			
		}

}
