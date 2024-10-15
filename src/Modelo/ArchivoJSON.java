package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArchivoJSON {

private HashMap<String, HashMap<String,Double>> grafo;
private HashMap<String, ArrayList<Double>> posicionesDeVertices;
	
public ArchivoJSON() {
    this.grafo = new HashMap<>();
    this.posicionesDeVertices = new HashMap<>();
}

public void generarJSON(String archivo) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(this);
    // Ruta relativa al directorio "resource" dentro del proyecto
    String ruta = System.getProperty("user.dir") + "/src/resources/";

    try {
        FileWriter writer = new FileWriter(ruta + archivo);
        writer.write(json);
        writer.close();
    } catch (Exception e) {
        e.printStackTrace();
        throw new IllegalArgumentException("ERROR INESPERADO: " + e.getMessage());
    }
}

public ArchivoJSON leerJSON(String archivo) {
    Gson gson = new Gson();
    ArchivoJSON ret = null;

    // Ruta relativa al directorio "resources" dentro del proyecto
    String ruta = System.getProperty("user.dir") + "/src/resources/";

    try {
        BufferedReader br = new BufferedReader(new FileReader(ruta + archivo));
        ret = gson.fromJson(br, ArchivoJSON.class);
    } catch (Exception e) {
        throw new IllegalArgumentException("ERROR INESPERADO");
    }

    return ret;
}



public HashMap<String, HashMap<String, Double>> getGrafo() {
    return grafo;
}


public void setGrafo(HashMap<String, HashMap<String, Double>> grafo) {
    this.grafo = grafo;
}

public HashMap<String, ArrayList<Double>> obtenerPosicionesDeVerticesEnElMapa() {
    return posicionesDeVertices;
}


public void setGrafoPosiciones(HashMap<String, ArrayList<Double>> grafoPosiciones) {
    this.posicionesDeVertices = grafoPosiciones;
}
}
