package Presentador;

import java.util.ArrayList;
import java.util.HashMap;

import Modelo.LogicaDeGrafo;

public class PresentadorMapa {
	private LogicaDeGrafo logicaDeGrafoEspias;
	
	public PresentadorMapa() {
		logicaDeGrafoEspias = new LogicaDeGrafo();
	}

	public boolean crearVertice(String vertice) {
		
		
		if(logicaDeGrafoEspias.crearVertice(vertice)) {
			return true;
		}
		return false;
		
	}
	
	public boolean crearArista(String vertice, String vertice2, Double probabilidad) {
		
		if(!logicaDeGrafoEspias.existeArista(vertice, vertice2)) {
			return logicaDeGrafoEspias.crearArista(vertice, vertice2, probabilidad);
		}
		
		return false;
	}
	public boolean existeArista(String vertice, String vertice2) {
		return logicaDeGrafoEspias.existeArista(vertice, vertice2);
	    }

	public HashMap<String, HashMap<String,Double>> crearArbolGeneradorMinimoPrim() {
		
		HashMap<String, HashMap<String,Double>> hashMapVerticesYVecinos = logicaDeGrafoEspias.crearArbolGeneradorMinimoPrim();
		return hashMapVerticesYVecinos;
		
	}
	
	
	public HashMap<String, HashMap<String,Double>> crearArbolGeneradorMinimoKruskal() {
		HashMap<String, HashMap<String,Double>> hashMapVerticesYVecinos = logicaDeGrafoEspias.crearArbolGeneradorMinimoKruskal();
		return hashMapVerticesYVecinos;
		
	}
	
	public boolean guardarGrafo(HashMap<String, HashMap<String,Double>> grafo, HashMap<String, ArrayList<Double>> grafoPosiciones, String NombreArchivo) {
		if(logicaDeGrafoEspias.guardarGrafo(grafo, grafoPosiciones, NombreArchivo)) {
			return true;
		}
		return false;
		
	}
	
	public boolean cargarGrafo(String NombreArchivo) {
		return logicaDeGrafoEspias.cargarGrafo(NombreArchivo);
		
		
	}
	public HashMap<String, ArrayList<Double>> devolverPosicionDeVerticeEnElMapaArchivo() {
		try {
			return logicaDeGrafoEspias.devolverPosicionDeVerticeEnElMapaArchivo();
			
		} catch (IllegalArgumentException e) {
			//System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	public HashMap<String, HashMap<String, Double>> devolverGrafoArchivo() {
		try {
			return logicaDeGrafoEspias.devolverGrafoArchivo();
			
		} catch (IllegalArgumentException e) {
			//System.out.println("Error: " + e.getMessage());
			return null;
		}
	}
	
	public HashMap<String, HashMap<String, Double>> devolverGrafo() {
		try {
			return logicaDeGrafoEspias.devolverGrafo();
			
		} catch (IllegalArgumentException e) {
			//System.out.println("Error: " + e.getMessage());
			return null;
		}

	
	}
	public String encuentrosIntermedios() {
		return logicaDeGrafoEspias.encuentrosIntermedios();
	}
	
	public String recorrerArbolGeneradorMinimoBFS() {
		try {
			return logicaDeGrafoEspias.recorrerArbolGeneradorMinimoBFS();
			
		} catch (IllegalArgumentException e) {
			//System.out.println("Error: " + e.getMessage());
			return null;
		}
		
	}
	
	public String devolverTiempoDeEjecucionDeAGM() {
		return logicaDeGrafoEspias.devolverTiempoDeEjecucionDeAGM();
	}
	public String devolverTiempoDeEjecucionDeAGMPrim() {
		return logicaDeGrafoEspias.devolverTiempoDeEjecucionDeAGMPrim();
	}
	public String devolverTiempoDeEjecucionDeAGMKruskal() {
		return logicaDeGrafoEspias.devolverTiempoDeEjecucionDeAGMKruskal();
	}
	
	public void borrarGrafoActual() {
		logicaDeGrafoEspias.borrarGrafoActual();;
	}
	public void limpiarRegistroBFS() {
		logicaDeGrafoEspias.limpiarRegistroBFS();
	}
}
