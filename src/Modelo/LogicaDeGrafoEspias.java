package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogicaDeGrafoEspias {
	private Grafo grafoEspias;
	//private Arbol arbolDeEspias;
	private ArbolGenerador arbolGeneradorMinimo;
	private ArchivoJSON  archivoJSON;
	

	public LogicaDeGrafoEspias() {
		//arbolDeEspias = new Arbol();
		grafoEspias = new Grafo();
		arbolGeneradorMinimo = new ArbolGenerador(grafoEspias);
		archivoJSON = new ArchivoJSON();
	}


	
	
	public boolean crearVertice(String vertice) {
		try {
			grafoEspias.agregarVertice(vertice);
			
			return true;
			
		} catch (IllegalArgumentException e) {
	        System.out.println("Error: El vértice ya existe. " + e.getMessage());
	        return false;
	    } catch (Exception e) {
	        System.out.println("Error inesperado: " + e.getMessage());
	        return false;
	    }
	}
	
	public boolean crearArista(String vertice, String vertice2, int probabilidad) {
		try {
			grafoEspias.agregarArista(vertice, vertice2, probabilidad);
			return true;
			
		} catch (IllegalArgumentException e) {
	        System.out.println("Error: la Arista ya existe. " + e.getMessage());
	        return false;
	    } catch (Exception e) {
	        System.out.println("Error inesperado: " + e.getMessage());
	        return false;
	    }
		
	}
	
	public boolean existeArista(String nombre1, String nombre2) {
        return grafoEspias.existeArista(nombre1, nombre2); 	
        
    }
	
	
	public HashMap<String, HashMap<String,Integer>> crearArbolGeneradorMinimoPrim() {
		try {
			Grafo grafoEspiasPrim = this.arbolGeneradorMinimo.crearArbolGeneradoMinimoPrim();
			HashMap<String, HashMap<String,Integer>> hashMapVerticesYVecinos = grafoEspiasPrim.devolverGrafo();
			
			return hashMapVerticesYVecinos;
			
		} catch (IllegalArgumentException e) {
	        System.out.println("Error:  " + e.getMessage());
	        return null;
	    } catch (Exception e) {
	        System.out.println("Error inesperado: " + e.getMessage());
	        return null;
	    }
		
	}

	public void crearArbolGeneradorMinimoKruskal() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean guardarGrafo(HashMap<String, HashMap<String,Integer>> grafo, HashMap<String, ArrayList<Double>> grafoPosiciones, String NombreArchivo) {
		try {
	        archivoJSON.setGrafo(grafo);
	        archivoJSON.setGrafoPosiciones(grafoPosiciones);
	        archivoJSON.generarJSON(NombreArchivo);
	        
		} catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	        return false;
	    } catch (Exception e) {
	        System.out.println("Error inesperado: " + e.getMessage());
	        return false;
	    }
		return false;
		
	}
	
	public ArchivoJSON CargarGrafo(String NombreArchivo) {
		try {
		
	        ArchivoJSON archivoNuevo = archivoJSON.leerJSON(NombreArchivo);

	        
	        return archivoNuevo;
			
	        
		} catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	        return null;
	    } catch (Exception e) {
	        System.out.println("Error inesperado: " + e.getMessage());
	        return null;
	    }
		
		
	}
	

}
