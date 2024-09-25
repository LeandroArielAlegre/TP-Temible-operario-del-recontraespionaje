package CasosDeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

public class Assert
{
	// Verifica que sean iguales como conjuntos
	public static void iguales(String[] esperado, Set<String> set)
	{
		assertEquals(esperado.length, set.size());
		
		for(int i=0; i<esperado.length; ++i)
			assertTrue( set.contains(esperado[i]) );
	}
}
