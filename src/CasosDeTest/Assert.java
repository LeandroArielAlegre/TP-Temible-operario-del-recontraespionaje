package CasosDeTest;

import java.util.ArrayList;
import java.util.Set;

public class Assert {



    public static boolean arrayListIguales(ArrayList<String> esperado, ArrayList<String> actual) {
        if (esperado.size() != actual.size()) {
            return false;
        }
        return esperado.containsAll(actual);
    }
    public static boolean setsIguales(Set<String> esperado, Set<String> actual) {
        if (esperado.size() != actual.size()) {
            return false;
        }
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