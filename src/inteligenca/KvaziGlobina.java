package inteligenca;

import java.util.LinkedList;
import java.util.List;

import logika.Igra;
import logika.Polozaj;
import logika.Poteza;

public class KvaziGlobina {
	private Igra igra;
	private Polozaj polozaj;
	
	public KvaziGlobina(Igra igra, Polozaj polozaj) {
		this.igra = igra;
		this.polozaj = polozaj;
	}
	
	public int ocenaGlobine() {
		int globina = 1;
		
		List<Poteza> prvePoteze= igra.moznePoteze(polozaj.getX(), polozaj.getY());
		LinkedList<Poteza> sosednePoteze = new LinkedList<Poteza>();
		
		//nastavimo ta polja na neaktivna
		for(Poteza poteza: prvePoteze) {
			igra.setNeaktivno(poteza.getX(), poteza.getY());
		}
		
		while (!prvePoteze.isEmpty()) {

			for(Poteza poteza: prvePoteze) {
				sosednePoteze.addAll(igra.moznePoteze(poteza.getX(), poteza.getY()));
				//nastavimo ta polja na neaktivna
				for(Poteza poteza2: sosednePoteze) {
					igra.setNeaktivno(poteza2.getX(), poteza2.getY());
				}	
			}
			
			prvePoteze.clear();
			prvePoteze.addAll(sosednePoteze);
			sosednePoteze.clear();
			
		}
		return 0;
	}

}
