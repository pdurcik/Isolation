package test;

import junit.framework.TestCase;
import logika.Igra;
import logika.Stanje;
import logika.Polozaj;

public class TestLogikaIgre extends TestCase {
	
	public void testIgra() {
		Igra igra = new Igra();
		// Na zacetku je na potezi beli
		assertEquals(Stanje.NA_POTEZI_BELI, igra.stanje());
		// Na zacetku je stevilo Aktivnih polj enako N*N-2
		assertEquals(igra.steviloAktivnih(), igra.N*igra.N-2);
		//Polozaj belega igralca je na zacetku nastavljen na (3,3) - le v testu, sicer je random!
		//assertEquals(3, igra.polozajIgralca().getX());
		//Beli igralec ima pri teh koordinatah na zacetku moznih 8 potez
		//assertEquals(8, igra.poteze().size());
		
		// Naredimo eno potezo
		igra.odigraj(igra.poteze().get(0));
		
		//Polozaj crnega igralca je na zacetku nastavljen na (0,0) - le v testu, sicer je random!
		//assertEquals(0, igra.polozajIgralca().getX());
		// Sedaj je na potezi CRNI in imamo aktivno polje manj
		assertEquals(Stanje.NA_POTEZI_CRNI, igra.stanje());
		//Po eni odigrani potezi je �t. aktivnih polj N*N-3
		assertEquals(igra.steviloAktivnih(), igra.N*igra.N - 3);
		
	}

}
