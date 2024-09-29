package CasosDeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Assert
{
	// Verifica que sean iguales como conjuntos
	public static boolean iguales(String[] esperado, ArrayList<String> arrayList)
	{
		assertEquals(esperado.length, arrayList.size());

		boolean ret=true;
		Iterator <String>it= arrayList.iterator(); 
		int indice=0;
		while (it.hasNext()) {
			 String i= it.next();
			 if (i!=esperado[indice]) {
				 return false;
//			 }else {
//				 ret=false;
//			 }
//			 indice++;
			}
//		for (String i : esperado) {
//			for (String j: hashSet) {
//				if (i.equals(j)) {
//					ret=true;
//				}
//			}

		}
		return ret;
	}
}
//	}
