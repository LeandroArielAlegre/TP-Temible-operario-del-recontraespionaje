package Presentador;



import org.openstreetmap.gui.jmapviewer.Coordinate;

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
	
	public boolean crearArista(String vertice, String vertice2, int probabilidad) {
		
		if(!logicaDeGrafoEspias.existeArista(vertice, vertice2)) {
			return logicaDeGrafoEspias.crearArista(vertice, vertice2, probabilidad);
		}
		
		return false;
	}

	
	public boolean existeArista(String vertice, String vertice2) {
		return logicaDeGrafoEspias.existeArista(vertice, vertice2);
	    }

	public boolean crearArbolGeneradorMinimoPrim() {
		logicaDeGrafoEspias.crearArbolGeneradorMinimoPrim();
		return true;
		
	}

	public boolean crearArbolGeneradorMinimoKruskal() {
		logicaDeGrafoEspias.crearArbolGeneradorMinimoKruskal();
		return true;
		
	}
	

}
