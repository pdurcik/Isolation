package gui;

import javax.swing.SwingWorker;

import inteligenca.Minimax;
import logika.Igralec;
import logika.Poteza;

public class Racunalnik extends Strateg {
	private GlavnoOkno master;
	private Igralec jaz;
	private SwingWorker<Poteza,Object> mislec;
	private static int globina = 4;
	
	/**
	 * Ustvari nov objekt, ki vle�e ra�unalnikove poteze.
	 * 
	 * @param master okno, v katerem ra�unalnik vle�e poteze
	 */
	public Racunalnik(GlavnoOkno master, Igralec jaz) {
		this.master = master;
		this.jaz = jaz;
	}
	
	@Override
	public void na_potezi() {
		// Za�nemo razmi�ljati
		mislec = new Minimax(master, globina, jaz);
		mislec.execute();
	}

	@Override
	public void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
	}

	@Override
	public void klik(int i, int j) {
		// Klike ignoriramo
	}

}
