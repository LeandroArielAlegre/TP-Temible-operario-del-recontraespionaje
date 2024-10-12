package Presentador;

import java.util.ArrayList;
import java.util.HashMap;

import Modelo.LogicaDeGrafoEspias;

public class PresentadorMapa {
	private LogicaDeGrafoEspias logicaDeGrafoEspias;
	
	public PresentadorMapa() {
		logicaDeGrafoEspias = new LogicaDeGrafoEspias();
	}

	public boolean crearVertice(String vertice) {
		
		
		if(logicaDeGrafoEspias.crearVertice(vertice)) {
			return true;
		}
		//Devuelve True si pudo incluir el vertice en el grafo 
		//Me da el Okay, entonces le digo a la pantalla que Cree el vertice
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
		return logicaDeGrafoEspias.CargarGrafo(NombreArchivo);
		
		
	}
	public String encuentrosIntermedios() {
		return logicaDeGrafoEspias.encuentrosIntermedios();
	}
	
	public String devolverTiempoDeEjecucionDeAGM() {
		return logicaDeGrafoEspias.devolverTiempoDeEjecucionDeAGM();
	}

	public HashMap<String, ArrayList<Double>> devolverGrafoPosicionesArchivo() {
		try {
			return logicaDeGrafoEspias.devolverGrafoPosicionesArchivo();
			
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	public HashMap<String, HashMap<String, Double>> devolverGrafoArchivo() {
		try {
			return logicaDeGrafoEspias.devolverGrafoArchivo();
			
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	public boolean verificarEntrada(String string, Double coordenadaXGlobal, Double coordenadaYGlobal) {
		return logicaDeGrafoEspias.verificarEntrada(string,coordenadaXGlobal,coordenadaYGlobal);
	}

	public boolean verificarEntrada(String verticeA, String verticeB, String probabilidad) {

		return logicaDeGrafoEspias.verificarEntrada(verticeA,verticeB,probabilidad);
	}

	public Double parsearADouble(String text) {
		// TODO Auto-generated method stub
		
		return logicaDeGrafoEspias.parsearADouble(text);
	}

	public boolean verificarProbabilidad(Double probabilidad) {

		return logicaDeGrafoEspias.verificarProbabilidad(probabilidad);
	}

}
