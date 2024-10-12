package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class LogicaDeGrafoEspias {
	private Grafo grafoEspias;
	private ArbolGenerador arbolGeneradorMinimo;
	private ArchivoJSON  archivoJSON;
	private String tiempoEjecucionPrim;
	private String tiempoEjecucionKruskal;

	public LogicaDeGrafoEspias() {
		grafoEspias = new Grafo();
		arbolGeneradorMinimo = new ArbolGenerador(grafoEspias);
		archivoJSON = new ArchivoJSON();
	}

	public String encuentrosIntermedios() {
		return arbolGeneradorMinimo.getEncuentrosIntermedios();
	}


	public boolean crearVertice(String vertice) {
		try {
			grafoEspias.agregarVertice(vertice);
			return true;

		} catch (Exception e) {
			//System.out.println("Error inesperado: " + e.getMessage());
			return false;
		}
	}

	public boolean crearArista(String vertice, String vertice2, Double probabilidad) {
		try {
			grafoEspias.agregarArista(vertice, vertice2, probabilidad);
			return true;

		} catch (IllegalArgumentException e) {
			//System.out.println("Error: la Arista ya existe. " + e.getMessage());
			return false;
		} catch (Exception e) {
			//System.out.println("Error inesperado: " + e.getMessage());
			return false;
		}

	}

	public boolean existeArista(String nombre1, String nombre2) {
		return grafoEspias.existeArista(nombre1, nombre2); 	

	}


	public HashMap<String, HashMap<String,Double>> crearArbolGeneradorMinimoPrim() {
		try {
			long comenzarTemporizador = System.nanoTime();
			Grafo grafoEspiasPrim = this.arbolGeneradorMinimo.crearArbolGeneradoMinimoPrim();
			HashMap<String, HashMap<String,Double>> hashMapVerticesYVecinos = grafoEspiasPrim.getGrafo();
			long finTemporizador = System.nanoTime();
			long duracion = finTemporizador - comenzarTemporizador;

			// Convertir de nanosegundos a milisegundos
			double milisegundos = duracion / 1_000_000.0;

			//System.out.println("Tiempo de ejecución Arbol Generador Prim: " + milisegundos + " milisegundos");
			this.tiempoEjecucionPrim =String.valueOf(milisegundos);
			return hashMapVerticesYVecinos;

		} catch (IllegalArgumentException e) {
			//System.out.println("Error:  " + e.getMessage());
			return null;
		} catch (Exception e) {
			//System.out.println("Error inesperado: " + e.getMessage());
			return null;
		}

	}

	public HashMap<String, HashMap<String,Double>> crearArbolGeneradorMinimoKruskal() {

		try {
			long comenzarTemporizador = System.nanoTime();
			Grafo grafoEspiasKruskal = this.arbolGeneradorMinimo.crearArbolGeneradoMinimoKruskal();
			HashMap<String, HashMap<String,Double>> hashMapVerticesYVecinos = grafoEspiasKruskal.getGrafo();
			long finTemporizador = System.nanoTime();
			long duracion = finTemporizador - comenzarTemporizador;
			double milisegundos = duracion / 1_000_000.0;
		
			this.tiempoEjecucionKruskal =String.valueOf(milisegundos);
			return hashMapVerticesYVecinos;

		} catch (IllegalArgumentException e) {

			return null;
		} catch (Exception e) {
			return null;
		}



	}

	public boolean guardarGrafo(HashMap<String, HashMap<String,Double>> grafo, HashMap<String, ArrayList<Double>> grafoPosiciones, String NombreArchivo) {
		try {
			archivoJSON.setGrafo(grafo);
			archivoJSON.setGrafoPosiciones(grafoPosiciones);
			archivoJSON.generarJSON(NombreArchivo);

		} catch (IllegalArgumentException e) {
			return false;
		} catch (Exception e) {

			return false;
		}
		return true;

	}

	public boolean CargarGrafo(String NombreArchivo) 
	{
		try {
			this.archivoJSON = archivoJSON.leerJSON(NombreArchivo);
			//Limpio el grafo anterior (SUJETO A CAMBIOS)
			grafoEspias.reiniciarGrafo();
			this.tiempoEjecucionPrim = " ";
			this.tiempoEjecucionKruskal = " ";
			//Sintesis, actualizo el modelo antes de dibujar el nuevo grafo.
			HashMap<String, HashMap<String,Double>> auxiliarHashMapVecinos = new HashMap<String, HashMap<String,Double>>();
			auxiliarHashMapVecinos = archivoJSON.getGrafo();
			crearVerticesDesdeArchivo(auxiliarHashMapVecinos);
			//ARISTAS
			crearAristasDesdeArchivo(auxiliarHashMapVecinos);
			return true;
		} catch (IllegalArgumentException e) {
			//System.out.println("Error: " + e.getMessage());
			return false;
		} catch (Exception e) {
			//System.out.println("Error inesperado: " + e.getMessage());
			return false;
		}
	}

	public HashMap<String, HashMap<String,Double>> devolverGrafoArchivo(){

		return this.archivoJSON.getGrafo();
	}

	public HashMap<String,ArrayList<Double>> devolverGrafoPosicionesArchivo(){

		return this.archivoJSON.getGrafoPosiciones();
	}


	private void crearVerticesDesdeArchivo( HashMap<String, HashMap<String,Double>> auxiliarHashMapVecinos) {

		for (Entry<String, HashMap<String, Double>> entry : auxiliarHashMapVecinos.entrySet()) {
			String nombreVertice = entry.getKey();
			crearVertice(nombreVertice);
		}

	}
	private void crearAristasDesdeArchivo( HashMap<String, HashMap<String,Double>> auxiliarHashMapVecinos) {
		for (Entry<String, HashMap<String, Double>> entry : auxiliarHashMapVecinos.entrySet()) {
			HashMap<String,Double> auxVecinos = new HashMap<String,Double>();
			String nombreVertice1 = entry.getKey();
			auxVecinos = entry.getValue();
			for (Entry<String, Double> entrada : auxVecinos.entrySet()) {
				String nombreVertice2 = entrada.getKey();
				Double probabilidad = entrada.getValue();
				crearArista(nombreVertice1, nombreVertice2, probabilidad);
			}
		}

	}


	public String devolverTiempoDeEjecucionDeAGM() {
		String tiempoDeEjecucion = "Tiempo AGM PRIM: " + tiempoEjecucionPrim
				+ "-" + "Tiempo AGM KRUSKAL: " + tiempoEjecucionKruskal;

		return tiempoDeEjecucion;
	}

	public Grafo devolverGrafo() {
		return this.grafoEspias;
	}

	public boolean verificarEntrada(String string, Double coordenadaXGlobal, Double coordenadaYGlobal) {

		if(string != null && string.length() != 0) 
		{
			if(coordenadaXGlobal != null && coordenadaYGlobal != null) 
			{

				return true;

			}				
		}
		return false;
	}

	public boolean verificarEntrada(String verticeA, String verticeB, String probabilidad) {
		if(verticeA!= null && verticeB!=null && probabilidad!= null) 
		{
			return true;
		}
		return false;
	}

	public Double parsearADouble(String text) {
		// TODO Auto-generated method stub
		Double probabilidad=Double.parseDouble(text);
		return probabilidad;
	}

	public boolean verificarProbabilidad(Double probabilidad) {
		
		return probabilidad >= 0 && probabilidad <=1 ;
	}
}
