package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class LogicaDeGrafo {
	private Grafo grafoLogica;
	private ArbolGenerador arbolGeneradorMinimo;
	private ArchivoJSON  archivoJSON;
	private String tiempoEjecucionPrim;
	private String tiempoEjecucionKruskal;
	private String recorridoBFS;

	public LogicaDeGrafo() {
		grafoLogica = new Grafo();
		arbolGeneradorMinimo = new ArbolGenerador(grafoLogica);
		archivoJSON = new ArchivoJSON();
	}

	public boolean crearVertice(String vertice) {
		try {
			grafoLogica.agregarVertice(vertice);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public boolean crearArista(String vertice, String vertice2, Double probabilidad) {
		try {
			grafoLogica.agregarArista(vertice, vertice2, probabilidad);
			return true;

		} catch (IllegalArgumentException e) {
			//System.out.println("Error: la Arista ya existe. " + e.getMessage());
			return false;
		} catch (Exception e) {
			//System.out.println("Error inesperado: " + e.getMessage());
			return false;
		}

	}
	public HashMap<String, HashMap<String, Double>> devolverGrafo() {
		
		return this.grafoLogica.getGrafo();
	}
	

	public boolean existeArista(String nombre1, String nombre2) {
		return grafoLogica.existeArista(nombre1, nombre2); 	

	}
	public HashMap<String, HashMap<String,Double>> crearArbolGeneradorMinimoPrim() {
		try {
			//calentamos la java virtual machine : )
		    this.arbolGeneradorMinimo.crearArbolGeneradorMinimoPrim();
			long comenzarTemporizador = System.nanoTime();
			Grafo grafoAGMPrim = this.arbolGeneradorMinimo.crearArbolGeneradorMinimoPrim();
			long finTemporizador = System.nanoTime();
			long duracion = finTemporizador - comenzarTemporizador;
			double milisegundos = duracion / 1_000_000.0;
			this.tiempoEjecucionPrim =String.valueOf(milisegundos);
			this.recorridoBFS=BFS.obtenerEncuentrosIntermedios(grafoAGMPrim);
			
			HashMap<String, HashMap<String,Double>> hashMapVerticesYVecinos = grafoAGMPrim.getGrafo();
			return hashMapVerticesYVecinos;

		} catch (IllegalArgumentException e) {

			return null;
		} catch (Exception e) {

			return null;
		}

	}

	public HashMap<String, HashMap<String,Double>> crearArbolGeneradorMinimoKruskal() {

		try {
			//calentamos la java virtual machine : )
		    this.arbolGeneradorMinimo.crearArbolGeneradorMinimoKruskal();

			long comenzarTemporizador = System.nanoTime();
			Grafo grafoAGMKruskal = this.arbolGeneradorMinimo.crearArbolGeneradorMinimoKruskal();
			long finTemporizador = System.nanoTime();
			long duracion = finTemporizador - comenzarTemporizador;
			double milisegundos = duracion / 1_000_000.0;
			this.tiempoEjecucionKruskal =String.valueOf(milisegundos);
			this.recorridoBFS=BFS.obtenerEncuentrosIntermedios(grafoAGMKruskal);
			HashMap<String, HashMap<String,Double>> hashMapVerticesYVecinos = grafoAGMKruskal.getGrafo();
			return hashMapVerticesYVecinos;

		} catch (IllegalArgumentException e) {

			return null;
		} catch (Exception e) {
			return null;
		}

	}
	
	public String devolverTiempoDeEjecucionDeAGM() {
		StringBuilder sb = new StringBuilder();
		if (!tiempoEjecucionPrim.contains(" ")) {
			sb.append(devolverTiempoDeEjecucionDeAGMPrim());
		}else {
			throw new RuntimeException("Prim aun no fue ejecutado");
		}
		if (!tiempoEjecucionKruskal.contains(" ")) {
			sb.append("-");
			sb.append(devolverTiempoDeEjecucionDeAGMKruskal());
		}else {
			throw new RuntimeException("Kruskal aun no fue ejecutado");
		}
		return sb.toString();
	}
	public String devolverTiempoDeEjecucionDeAGMPrim() {
		String tiempoDeEjecucion = "Tiempo AGM PRIM: " + tiempoEjecucionPrim;
		return tiempoDeEjecucion;
	}
	public String devolverTiempoDeEjecucionDeAGMKruskal() {
		String tiempoDeEjecucion = "Tiempo AGM KRUSKAL: " + tiempoEjecucionKruskal;

		return tiempoDeEjecucion;
	}
	public String recorrerArbolGeneradorMinimoBFS() {
		return recorridoBFS;
	}
	public void limpiarRegistroBFS() {
		recorridoBFS=null;
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
			borrarGrafoActual();
			this.tiempoEjecucionPrim = " ";
			this.tiempoEjecucionKruskal = " ";
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

	public void borrarGrafoActual() {
		grafoLogica.reiniciarGrafo();
	}

	public String encuentrosIntermedios() {
		return arbolGeneradorMinimo.getEncuentrosIntermedios();
	}

}
