package inteligenca;

import logika.Igra;
import logika.Igralec;


public class Ocena {
	public static final int ZMAGA = (1 << 20); // vrednost zmage, več kot vsaka druga ocena pozicije
	public static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
	public static final int UTEZ = 3;

	
	/**
	 * @param jaz igralec, ki želi oceno
	 * @param igra trentno stanje igre (ne spreminjaj tega objekta!)
	 * @return ocena vrednosti pozicije (če je igre konec, je ocena zagotovo pravilna)
	 */
	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		switch (igra.stanje()) {
		case ZMAGA_BELI:
			return (jaz == Igralec.BELI ? ZMAGA : ZGUBA);
		case ZMAGA_CRNI:
			return (jaz == Igralec.CRNI ? ZMAGA : ZGUBA);
		case NA_POTEZI_BELI:
			return(UTEZ * KvaziGlobina.ocenaGlobine(igra, igra.getPolozajBeli()));
		case NA_POTEZI_CRNI:
			// ocena s pomočjo globine
			//return(jaz == Igralec.CRNI? UTEZ * KvaziGlobina.ocenaGlobine(igra, igra.getPolozajCrni()) : UTEZ * KvaziGlobina.ocenaGlobine(igra, igra.getPolozajBeli()));
			
			// ocena s pomočjo sosedov
			//stevilo sosedov crnega igralca
			int stCrniSos = igra.moznePoteze(igra.getPolozajCrni().getX(), igra.getPolozajCrni().getY()).size();
			
			//stevilo sosedov belega igralca
			int stBeliSos = igra.moznePoteze(igra.getPolozajBeli().getX(), igra.getPolozajBeli().getY()).size();
			//return(jaz == Igralec.CRNI? stCrniSos - UTEZ * stBeliSos: stBeliSos - UTEZ * stCrniSos );
			return(stCrniSos - UTEZ * stBeliSos);
		}
		assert false;
		return 0; 
	}
	
}