package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ArbolGeneradorMinimo {
	private Grafo grafo;
	
	public ArbolGeneradorMinimo(Grafo grafo) {
		this.grafo = grafo;
		
	}
	public static void main(String[]args) {
		Grafo grafo = new Grafo();
		grafo.agregarVertice("0");
		grafo.agregarVertice("2");
		grafo.agregarVertice("3");
		grafo.agregarVertice("4");
		grafo.agregarArista("0", "2",1);
		grafo.agregarArista("0", "3",0);
		grafo.agregarArista("2", "3",0);
		grafo.agregarArista("2", "4",0);
		grafo.agregarArista("3", "4",1);
		ArbolGeneradorMinimo arbolGeneradorMinimo = new ArbolGeneradorMinimo(grafo);
		
		Grafo grafoPrim = new Grafo();
		grafoPrim = arbolGeneradorMinimo.crearArbolGeneradoMinimoPrim();
		System.out.println(grafoPrim.toString());
	}
	
	
	//NOTA: CONSIDERAR TESTEAR ARDUAMENTE EL SIGUIENTE ALGORITMO
	
	public Grafo crearArbolGeneradoMinimoPrim() {
		
		//Nota: En primera instancia, el grafo debe ser Conexo, para lo cual, utilizaremos BFS para comprobarlo
		if(BFS.esConexo(grafo)) {
			
		//Nuevo grafo	
		Grafo grafoPrim = new Grafo();
		//VT
		grafoPrim.agregarVertice(this.grafo.dameOrigen());
		String verticeActual = this.grafo.dameOrigen();
		
		ArrayList <String> listaMarcados = new ArrayList<String>();
		listaMarcados.add(this.grafo.dameOrigen());
	
		int cantidadDeVertices = grafo.tamano();
		int contadorIteraciones = 0;
		while(contadorIteraciones <= cantidadDeVertices -1) {
			
			//Traigo los vecinos con el peso de mi vertice
			HashMap<String, Integer> hashMapAristasDeUnVertice = grafo.vecinosDeVerticeConPeso(verticeActual);
			
			// COnjunto auxiliar para decidir con que vertice me quedo en caso de haber mas de un valor distinto de 1
			ArrayList <String> listaCandidatosAux = new ArrayList<String>();
			
			for (Entry<String, Integer> entry : hashMapAristasDeUnVertice.entrySet()) {
				
				String vertice = entry.getKey();   
			    int peso = entry.getValue(); 
			  //Si no esta en la lista de marcados... 
			    if(!listaMarcados.contains(vertice)) {
			    	//Si el peso es menor a 1
			    	if(peso < 1) {
			    		listaCandidatosAux.add(vertice);
			    		
			    	}
			    	
			    }
			   	
			}
			
			if(listaCandidatosAux.size() > 0) {
				// En caso de haber mas de un candidato a ser marcado
				for (String candidatoVertice : listaCandidatosAux) {
					//Necesito saber si el vertice que voy a seleccionar no est
					//Tendria que preguntarles vertice, tus vecinos estan marcados? en caso de que haya uno, no lo agrego
					
					if(!estaEnListaMarcado(candidatoVertice, listaMarcados)) {
						//Si no esta lo eligo para la proxima iteracione del while
						
						//En caso de no existir los vertices en el grafoPrim, los agrego.
						if(!grafo.existeVertice(verticeActual)) {
							grafoPrim.agregarVertice(verticeActual);
						}
						if(!grafo.existeVertice(candidatoVertice)) {
							grafoPrim.agregarVertice(candidatoVertice);
						}
						
						//Agrego la arista al nuevo grafo
						grafoPrim.agregarArista(verticeActual, candidatoVertice, 0);
						
						//Me posiciono en ese vertice para la siguiente iteracion
						verticeActual = candidatoVertice;
					}
				}
			//Si no hay candidatos entonces ya tenemos el arbol generador minimo
			}else {
				contadorIteraciones = cantidadDeVertices -1;
				
			}
			
			
			contadorIteraciones +=1;
		}
		return grafoPrim;	
			
		}
		throw new IllegalArgumentException("El grafo no es conexo");
		
		
	}
	
	private boolean estaEnListaMarcado(String candidatoVertice, ArrayList <String> listaMarcados ) {
		boolean ret = false;
		HashMap<String, Integer> hashMapVecinosDeVerticeCandidato = grafo.vecinosDeVerticeConPeso(candidatoVertice);
		
		for (Entry<String, Integer> entry : hashMapVecinosDeVerticeCandidato.entrySet()) {
			String vecino = entry.getKey();
			if(listaMarcados.contains(vecino)) {
				ret = true;
			}
		}
		
		return ret;
		
	}
	
	
	
	
	public Grafo crearArbolGeneradoMinimoKruskal() {
		
		
		return grafo;
	}
	
	
	
	public void imprimirGrafo(Grafo grafo) {
		
		//a
		
		
	}

}
