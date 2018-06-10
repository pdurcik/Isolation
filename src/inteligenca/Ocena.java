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
			//stevilo sosedov in globina belega igralca
			int stBeliSos = igra.moznePoteze(igra.getPolozajBeli().getX(), igra.getPolozajBeli().getY()).size();
			//stevilo sosedov in globina crnega igralca
			int stCrniSos = igra.moznePoteze(igra.getPolozajCrni().getX(), igra.getPolozajCrni().getY()).size();	
			
			//ocene igralcev 
			int ocenaCrni = stCrniSos  - UTEZ * stBeliSos;
			int ocenaBeli = stBeliSos  - UTEZ*stCrniSos;
			
			
			return (jaz == Igralec.CRNI? ocenaCrni: ocenaBeli);
		case NA_POTEZI_CRNI:
			//stevilo sosedov in globina belega igralca
			int stBeliSos2 = igra.moznePoteze(igra.getPolozajBeli().getX(), igra.getPolozajBeli().getY()).size();
			
			//stevilo sosedov in globina crnega igralca
			int stCrniSos2 = igra.moznePoteze(igra.getPolozajCrni().getX(), igra.getPolozajCrni().getY()).size();

			
			//ocene igralcev 
			int ocenaCrni2 = stCrniSos2 - UTEZ * stBeliSos2;
			int ocenaBeli2 = stBeliSos2 - UTEZ*stCrniSos2;
			
			return (jaz == Igralec.CRNI? ocenaCrni2: ocenaBeli2);

		}
		assert false;
		return 0; 
	}
	
}