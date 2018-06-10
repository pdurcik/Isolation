package inteligenca;

import logika.Igra;
import logika.Igralec;


public class Ocena {
	public static final int ZMAGA = (1 << 20); // vrednost zmage, več kot vsaka druga ocena pozicije
	public static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
	public static final int UTEZ = 2;

	
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
		case NA_POTEZI_CRNI:
			//stevilo sosedov in globina crnega igralca
			int stCrniSos = igra.moznePoteze(igra.getPolozajCrni().getX(), igra.getPolozajCrni().getY()).size();
			int globinaCrni = KvaziGlobina.ocenaGlobine(igra, igra.getPolozajCrni());
			
			//stevilo sosedov in globina belega igralca
			int stBeliSos = igra.moznePoteze(igra.getPolozajBeli().getX(), igra.getPolozajBeli().getY()).size();
			int globinaBeli =KvaziGlobina.ocenaGlobine(igra, igra.getPolozajBeli());
			
			//ocene igralcev 
			int ocenaCrni = stCrniSos * globinaCrni - stBeliSos * globinaBeli;
			int ocenaBeli = stBeliSos * globinaBeli - stCrniSos * globinaCrni;
			
			return (jaz == Igralec.CRNI? ocenaCrni : ocenaBeli);
			
		}
		assert false;
		return 0; 
	}
	
}