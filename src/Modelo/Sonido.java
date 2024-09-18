package Modelo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sonido {
		
	private Map<String, Clip> hashMapClips = new HashMap<>();
	  private Clip clip; 
	 public void reproducirSonido(String rutaArchivo, String sonido) {
	        try {
	            // Cargar el archivo de sonido
	            AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource(rutaArchivo));
	            clip = AudioSystem.getClip();
	            clip.open(audioStream);
	            clip.start();  // Iniciar el sonido
	           //Si ya existe no lo guardo
	            if(!hashMapClips.containsKey(sonido)) {
	            	hashMapClips.put(sonido, clip);
	            	
	            }
	            
	        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	            e.printStackTrace();
	        }
	    }

	    public void detenerSonido(String sonido){
	    	//Si existe
	    	if(hashMapClips.containsKey(sonido)) {
	    		Clip clip = hashMapClips.get(sonido);  // Obtener el clip del mapa
		        if (clip != null && clip.isRunning()) {
		            clip.stop();  // Detener el sonido
		        }
	    	}else {
	    		 throw new IllegalArgumentException("ERROR: este sonido no esta cargado");
	    	}
	    	
	        
	    }
	    
	    public boolean estaReproduciendo(String sonido) {
	    	if (hashMapClips.containsKey(sonido)) {
	    		Clip clip = hashMapClips.get(sonido); 
	    		return clip.isRunning(); 
	    	}
	    	throw new IllegalArgumentException("ERROR: este sonido no esta cargado");
	    	
	    }
}
