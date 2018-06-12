package inteligenca;

import java.util.LinkedList;
import java.util.List;

import logika.Igra;
import logika.Polozaj;
import logika.Poteza;

public class KvaziGlobina {
		
	public static int ocenaGlobine(Igra igra, Polozaj polozaj) {
		int globina = 0;
		
		List<Poteza> prvePoteze= igra.moznePoteze(polozaj.getX(), polozaj.getY());
		LinkedList<Poteza> sosednePoteze = new LinkedList<Poteza>();
		
		//nastavimo ta polja na neaktivna
		for(Poteza poteza: prvePoteze) {
			igra.setNeaktivno(poteza.getX(), poteza.getY());
		}
		
		while (!prvePoteze.isEmpty()) {

			for(Poteza poteza: prvePoteze) {
				//v sosedne poteze shranimo vse mozzne poteze iz prvih potez
				sosednePoteze.addAll(igra.moznePoteze(poteza.getX(), poteza.getY()));
				//nastavimo ta polja na neaktivna
				for(Poteza poteza2: sosednePoteze) {
					igra.setNeaktivno(poteza2.getX(), poteza2.getY());
				}	
			}
			
			prvePoteze.clear();
			prvePoteze.addAll(sosednePoteze);
			sosednePoteze.clear();
			globina ++;
			
		}
		return globina;
	}

}
