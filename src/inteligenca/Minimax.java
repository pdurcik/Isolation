package inteligenca;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import logika.Igra;
import logika.Igralec;
import logika.Poteza;

public class Minimax extends SwingWorker<Poteza, Object>{

	private GlavnoOkno master;
	private int globina;
	private Igralec jaz; // koga igramo
	
	/**
	 * @param master glavno okno, v katerem vlečemo poteze
	 * @param globina koliko potez naprej gledamo
	 * @param jaz koga igramo
	 */
	public Minimax(GlavnoOkno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = minimax(0, igra);
		assert (p.poteza != null);
		return p.poteza;
	}
	
	@Override
	public void done() {
		try {
			Poteza p = this.get();
			if (p != null) { master.odigraj(p); }
		} catch (Exception e) {
		}
	}

	/**
	 * Z metodo minimax poišči najboljšo potezo v dani igri.
	 * 
	 * @param k števec globine, do kje smo že preiskali
	 * @param igra
	 * @return najboljša poteza (ali null, če ji ni), skupaj z oceno najboljše poteze
	 */
	private OcenjenaPoteza minimax(int k, Igra igra) {
		Igralec naPotezi = null;
		// Ugotovimo, ali je konec, ali je kdo na potezi?
		switch (igra.stanje()) {
		case NA_POTEZI_BELI: naPotezi = Igralec.BELI; break;
		case NA_POTEZI_CRNI: naPotezi = Igralec.CRNI; break;
		
		// Igre je konec, ne moremo vrniti poteze, vrnemo le vrednost pozicije
		case ZMAGA_BELI:
			return new OcenjenaPoteza(
					null,
					(jaz == Igralec.BELI ? Ocena.ZMAGA : Ocena.ZGUBA));
		case ZMAGA_CRNI:
			return new OcenjenaPoteza(
					null,
					(jaz == Igralec.CRNI ? Ocena.ZMAGA : Ocena.ZGUBA));

		}
		assert (naPotezi != null);
		
		// Nekdo je na potezi, ugotovimo, kaj se splača igrati
		if (k >= globina) {
			// dosegli smo največjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					Ocena.oceniPozicijo(jaz, igra));
		}
		
		// Hranimo seznam do sedaj najboljših videnih potez
		LinkedList<Poteza> najboljse = new LinkedList<Poteza>();
		int ocenaNajboljse = 0;
		
		for (Poteza p : igra.poteze()) {
			// V kopiji igre odigramo potezo p
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			// Izračunamo vrednost pozicije po odigrani potezi p
			int ocenaP = minimax(k+1, kopijaIgre).vrednost;
			// če je p boljša poteza, si jo zabeležimo
			if (najboljse.isEmpty() // če nimamo kandidata za najboljšo potezo
				|| (naPotezi == jaz && ocenaP > ocenaNajboljse) // maksimiziramo
				|| (naPotezi != jaz && ocenaP < ocenaNajboljse) // minimiziramo
				) {
				najboljse.clear();
				najboljse.add(p);
				ocenaNajboljse = ocenaP;
			} else if ((naPotezi == jaz && ocenaP == ocenaNajboljse)
					|| (naPotezi != jaz && ocenaP == ocenaNajboljse) ){
				
				najboljse.add(p);
				
			}
		}
		// Vrnemo najboljšo najdeno potezo in njeno oceno
		
		assert (najboljse.isEmpty());
		Random rand = new Random();
		Poteza najboljsa = najboljse.get(rand.nextInt(najboljse.size()));
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
}

}