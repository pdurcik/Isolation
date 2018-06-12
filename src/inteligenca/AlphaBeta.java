package inteligenca;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import logika.Igra;
import logika.Igralec;
import logika.Poteza;


public class AlphaBeta  extends SwingWorker<Poteza, Object> {

	private GlavnoOkno master;
	private int alpha = Ocena.ZGUBA - 10;
	private int beta = Ocena.ZMAGA +10;


	private int globina;
	private Igralec jaz; // koga igramo
	
	/**
	 * @param master glavno okno, v katerem vlecemo poteze
	 * @param globina koliko potez naprej gledamo
	 * @param jaz koga igramo
	 */
	public AlphaBeta(GlavnoOkno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = alphaBeta(0, alpha, beta, igra);
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


	private OcenjenaPoteza alphaBeta(int k, int alpha, int beta, Igra igra) {
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
		// Nekdo je na potezi, ugotovimo, kaj se splaca igrati
		if (k >= globina) {
			// dosegli smo najvecjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					Ocena.oceniPozicijo(jaz, igra));
		}
		
		// Hranimo najboljše do sedaj videne poteze in njeihovo oceno.
		
		LinkedList<Poteza> najboljse = new LinkedList<Poteza>();
		Poteza najboljsa = null;
		
		if (naPotezi == jaz) {
			//maximiziramo, v je spodnja ocen aza maximum
			int v = Ocena.ZGUBA;
			for (Poteza p : igra.poteze()) {
				// V kopiji igre odigramo potezo p
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj(p);
				// Izracunamo vrednost pozicije po odigrani potezi p
				int ocenaP = alphaBeta(k+1, alpha, beta, kopijaIgre).vrednost;
				if (ocenaP == v) {
					najboljse.add(p);
				} else if(ocenaP > v) {
					najboljse.clear();
					najboljse.add(p);
					v = ocenaP;
				}
				alpha = Math.max(v, alpha);
				
				if (beta <= alpha) {
					break;
				}
			}
			//ce je vec najboljsih potez izberemo nakkljucno
			Random rand = new Random();
			najboljsa = najboljse.get(rand.nextInt(najboljse.size()));
			return new OcenjenaPoteza(najboljsa, v);
		} else { 
			//nasprotnik minimizira
			//zgornja ocena za minimum
			int v = Ocena.ZMAGA;
			for (Poteza p : igra.poteze()) {
				// V kopiji igre odigramo potezo p
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj(p);
				// Izracunamo vrednost pozicije po odigrani potezi p
				int ocenaP = alphaBeta(k+1, alpha, beta, kopijaIgre).vrednost;
				if (ocenaP == v) {
					najboljse.add(p);
					} else if(ocenaP < v) {
						najboljse.clear();
						najboljse.add(p);
						v = ocenaP;
					}
				
				beta = Math.min(v, beta);
				if (beta <= alpha) {
					break;
				}
			}
			//ponovno nakljucno izberemo izmed najboljsih potez
			Random r = new Random();
			najboljsa = najboljse.get(r.nextInt(najboljse.size()));
			return new OcenjenaPoteza(najboljsa, v);
		}
	}
	
}