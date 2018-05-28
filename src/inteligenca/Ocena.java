package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;

public class Ocena {
	public static final int ZMAGA = (1 << 20); // vrednost zmage, veè kot vsaka druga ocena pozicije
	public static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
	public static final int UTEZ = 1;

	
	/**
	 * @param jaz igralec, ki želi oceno
	 * @param igra trentno stanje igre (ne spreminjaj tega objekta!)
	 * @return ocena vrednosti pozicije (èe je igre konec, je ocena zagotovo pravilna)
	 */
	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		switch (igra.stanje()) {
		case ZMAGA_BELI:
			return (jaz == Igralec.BELI ? ZMAGA : ZGUBA);
		case ZMAGA_CRNI:
			return (jaz == Igralec.CRNI ? ZMAGA : ZGUBA);
		case NA_POTEZI_BELI:
		case NA_POTEZI_CRNI:
	   
		}
		assert false;
		return 42; // Java je blesava
	}
	
}