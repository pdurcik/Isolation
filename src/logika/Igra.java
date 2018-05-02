package logika;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Igra {
	
	Random rand = new Random();
	
	
	// Velikost igralne plošèe
	public static final int N = 7;
	
	// Atributi
	
	private Polje[][] plosca;
	private Igralec naPotezi;
	private Polozaj polozajBeli;
	private Polozaj polozajCrni;
	

	
	// Nova igra v zaèetni poziciji, ki je izbrana nakljuèno. Na potezi je BELI.
	
	public Igra() {
		plosca = new Polje[N][N];
		
		polozajBeli = new Polozaj(Igralec.BELI.toString());
		polozajCrni = new Polozaj(Igralec.CRNI.toString());

		for (int i = 0; i < N; i++) {
			for (int j = 0; j<N; i++) {
				plosca[i][j] = Polje.AKTIVNO;
			}
		}
		//Zacetni položaj belega igralca je izbran nakljuèno
		//polje na katerem je figura je tudi neaktivno
		int  x1 = rand.nextInt(N);
		int  y1 = rand.nextInt(N);
		polozajBeli.setX(x1);
		polozajBeli.setY(y1);
		plosca[x1][y1] = Polje.NEAKTIVNO;
		
		// Zaæetni položaj èrnega igralca je izbran nakljuèno
		// in ni isti kot zaèetni položaj belega igralca
		while (true) {
			int  x2 = rand.nextInt(N);
			int  y2 = rand.nextInt(N);
			if (plosca[x2][y2] == Polje.AKTIVNO) {
				polozajCrni.setX(x2);
				polozajCrni.setY(y2);
				plosca[x2][y2] = Polje.NEAKTIVNO;
				break;
			}
		}
		naPotezi = Igralec.BELI;		
	}
	
	// Preveri, èe je poteza veljavna	
	public boolean veljavnaPoteza(Poteza poteza) {
		int x = poteza.getX();
		int y = poteza.getY();
		if (0 <= x && x <= N && 0 <= y && y <= N && plosca[x][y] == Polje.AKTIVNO) {
			return true;
		}
		else {return false;}
	}
	
	// Vrne položaj igralca ki je na potezi
	
	public Polozaj polozajIgralca() {
		if (naPotezi.toString() == polozajBeli.getIme()) {
			return polozajBeli;
		}
		else { return polozajCrni;}
	}
	
	// Seznam možnih potez za igralca, ki je na potezi
	
	public List<Poteza> poteze(){
		LinkedList<Poteza> moznePoteze = new LinkedList<Poteza>();
		Polozaj polozaj = polozajIgralca(); //polozaj igralca, ki je na potezi
		
		int x = polozaj.getX();
		int y = polozaj.getY();
		
		int[] premiki = {-2,-1,1,2}; //tabela premikov konja
		
		for (int i: premiki) {
			for (int j:premiki) {
				if (Math.abs(i) != Math.abs(j)) {
					Poteza poteza = new Poteza(x + i,y + j);
					
					if(veljavnaPoteza(poteza)) {
						moznePoteze.add(poteza);
					}
					
				}
			}
		}
		
		return(moznePoteze);
	}
	
	public Stanje stanje() {
		//Preverimo, ce imamo zmagovalca
		//ce igralec nima vec moznih potez je zmagovalec nasprotnik
		if (poteze().isEmpty()) {
			if (naPotezi.toString() == "BELI") {
				return Stanje.ZMAGA_CRNI;
			}else {
				return Stanje.ZMAGA_BELI;
			}
		} else { 
			if (naPotezi.toString() == "BELI") {
				return Stanje.NA_POTEZI_BELI;
			} else {
				return Stanje.NA_POTEZI_CRNI;
			}
		}
		
		
	}
	
	//Odigaj potezo - vrne True, èe je bila poteza uspešno odigrana
	
	public boolean odigraj(Poteza p) {	
		
		if (poteze().contains(p)) {
			plosca[p.getX()][p.getY()] = Polje.NEAKTIVNO;
			polozajIgralca().setX(p.getX());
			polozajIgralca().setY(p.getY());
			naPotezi = naPotezi.nasprotnik();
			return true;
					
		}else {
			return false;
			}
		
	}
	
	

}
