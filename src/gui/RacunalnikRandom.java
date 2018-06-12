package gui;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import inteligenca.Minimax;
import inteligenca.NakljucnaInteligenca;
import logika.Igra;
import logika.Igralec;
import logika.Poteza;

@SuppressWarnings("unused")
//racunalnik, ki uporablja nakljucno inteligenco
public class RacunalnikRandom extends Strateg {
	private GlavnoOkno master;
	private SwingWorker<Poteza,Object> mislec;
	private boolean prekini;
	private Igralec jaz;


	public RacunalnikRandom(GlavnoOkno master, Igralec jaz) {
		this.master = master;
		this.jaz = jaz;
	
	}
	
	@Override
	public void na_potezi() {
		// Zacnemo razmišljati
		mislec = new NakljucnaInteligenca(master);
		
		//računalnik naključno izbira poteze
		//mislec = new NakljucnaInteligenca(master);
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
	}

}