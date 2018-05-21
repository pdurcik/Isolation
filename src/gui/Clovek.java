package gui;

import logika.Igralec;
import logika.Poteza;

public class Clovek extends Strateg{
	private GlavnoOkno master;
	private Igralec jaz;
	
	public Clovek(GlavnoOkno master, Igralec jaz) {
		this.master = master;
		this.jaz = jaz;
	}
	
	public void na_potezi() {
	}
	
	public void prekini() {
	}
	
	public void klik(int i, int j) {
		master.odigraj(new Poteza(i,j));
	}

}
