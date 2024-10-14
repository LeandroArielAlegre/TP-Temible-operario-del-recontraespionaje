package CasosDeTest;

import java.util.ArrayList;
import java.util.Set;

public class Assert {



    public static boolean arrayListIguales(ArrayList<String> esperado, ArrayList<String> actual) {
        // Verificar que los tamaños sean iguales
        if (esperado.size() != actual.size()) {
            return false;
        }

        // Verificar que todos los elementos de esperado estén en actual
        return esperado.containsAll(actual);
    }
    public static boolean setsIguales(Set<String> esperado, Set<String> actual) {
        // Verificar que los tamaños sean iguales
        if (esperado.size() != actual.size()) {
            return false;
        }

        // Verificar que todos los elementos de esperado estén en actual
        return esperado.containsAll(actual);
    }

    public static void assertArrayListIguales(String mensaje, ArrayList<String> esperado, ArrayList<String> actual) {
        if (!arrayListIguales(esperado, actual)) {
            throw new AssertionError(mensaje + 
                "\nEsperado: " + esperado + 
                "\nActual:   " + actual);
        }
    }
}