package Presentador;



import org.openstreetmap.gui.jmapviewer.Coordinate;

import Modelo.LogicaDeMapa;

public class PresentadorMapa {
	private LogicaDeMapa logicaDeMapa;
	
	
	
	public PresentadorMapa() {
		logicaDeMapa = new LogicaDeMapa();
	}

	public boolean crearVertice(Coordinate vertice) {
		
		//logicaDeMapa.crearVertice(Coordinate Vertice);
		//Devuelve True si pudo incluir el vertice en el grafo 
		//Me da el Okay, entonces le digo a la pantalla que Cree el vertice
		//Nota: Tenemos un problema con los tipo de datos, Coordinate es un tipo
		//Double, no optimo para trabajar con algoritmos de grafos.
		//Propuesta: Utilizar BigDecimal para redondear los numeros y castearlos a enteros
		//Guardar coordenadas en un Map.
		
		System.out.println("Creando vertice");
		return true;
		
	}
	
	public boolean crearArista(Coordinate vertice, Coordinate vertice2) {
		//logicaDeMapa.crearArista(Coordinate vertice1 ,Coordinate vertice2);
		
		System.out.println("Creando arista");
		return true;
	}
	

}
