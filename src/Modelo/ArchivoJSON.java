package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArchivoJSON {

private HashMap<String, HashMap<String,Integer>> grafo;
private HashMap<String, ArrayList<Double>> grafoPosiciones;
	
public ArchivoJSON() {
    this.grafo = new HashMap<>();
    this.grafoPosiciones = new HashMap<>();
}

public void generarJSON(String archivo) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(this);

    try {
    	FileWriter writer = new FileWriter(archivo);
        writer.write(json);
        writer.close();
    } catch (Exception e) {
      
        e.printStackTrace();
        throw new IllegalArgumentException("ERROR INESPERADO: " + e.getMessage());
    }
    
}

public ArchivoJSON leerJSON(String archivo)
	 {
	Gson gson = new Gson();
	 ArchivoJSON ret = null;
	
	 try
	 {
	 BufferedReader br = new BufferedReader(new FileReader(archivo));
	 ret = gson.fromJson(br, ArchivoJSON.class);
	 }
catch (Exception e) {
		 throw new IllegalArgumentException("ERROR INESPERADO");
	 }
	 return ret;
	 }



public HashMap<String, HashMap<String, Integer>> getGrafo() {
    return grafo;
}


public void setGrafo(HashMap<String, HashMap<String, Integer>> grafo) {
    this.grafo = grafo;
}

public HashMap<String, ArrayList<Double>> getGrafoPosiciones() {
    return grafoPosiciones;
}


public void setGrafoPosiciones(HashMap<String, ArrayList<Double>> grafoPosiciones) {
    this.grafoPosiciones = grafoPosiciones;
}
}
