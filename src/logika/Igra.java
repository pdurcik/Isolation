package logika;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Igra {
	
	// Velikost igralne plošèe
	public static final int N = 7;
	
	// Atributi	
	private Polje[][] plosca;
	private Igralec naPotezi;
	private Polozaj polozajBeli;
	private Polozaj polozajCrni;
	
	// Nova igra v zaèetni poziciji, ki je izbrana nakljuèno. Na potezi je BELI.
	
	public Igra() {
		Random rand = new Random();
		
		plosca = new Polje[N][N];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = Polje.AKTIVNO;
			}
		}
		// Zacetni položaj belega igralca je izbran nakljuèno.
		// Polje na katerem je figura je tudi neaktivno.
		int  x1 = rand.nextInt(N);
		int  y1 = rand.nextInt(N);
		polozajBeli = new Polozaj(x1, y1);// nov polozaj z imenom belega igralca
		plosca[x1][y1] = Polje.NEAKTIVNO;
		
		// Zaæetni položaj èrnega igralca je izbran nakljuèno
		// in ni isti kot zaèetni položaj belega igralca.
		// Polje na katerem je figura je tudi neaktivno.
		while (true) {
			int  x2 = rand.nextInt(N);
			int  y2 = rand.nextInt(N);
			if (plosca[x2][y2] == Polje.AKTIVNO) {
				polozajCrni = new Polozaj(x2, y2);// nov polozaj z imenom crnega igralca
				plosca[x2][y2] = Polje.NEAKTIVNO;
				break;
			}
		}
		naPotezi = Igralec.BELI;		
	}
	
	// Preveri, èe je polje na katerega prestavimo figuro s potezo p veljavno
	public boolean veljavnoPolje(Poteza poteza) {
		int x = poteza.getX();
		int y = poteza.getY();
		if (0 <= x && x < N && 0 <= y && y < N && plosca[x][y] == Polje.AKTIVNO) {
			return true;
		}
		else {return false;}
	}
	
	// Vrne položaj igralca ki je na potezi	
	public Polozaj polozajIgralca() {
		if (naPotezi == Igralec.BELI) {
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
					
					if(veljavnoPolje(poteza)) {
						moznePoteze.add(poteza);
					}
				}
			}
		}
		
		return(moznePoteze);
	}
	
	public Stanje stanje() {
		//Preverimo, ce imamo zmagovalca
		//ce igralec nima vec moznih potez, je zmagovalec nasprotnik
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
		int razdalja = Math.abs(p.getX() - polozajIgralca().getX() ) + Math.abs(p.getY() - polozajIgralca().getY()) ;
		
		//poteza p je veljavna, ce je njena manhattanska razdalja od polozaja igralca na potezi
		//enaka 3 in da je polje[p.x][p.y] aktivno
		if (razdalja == 3 && plosca[p.getX()][p.getY()] == Polje.AKTIVNO) {
			plosca[p.getX()][p.getY()] = Polje.NEAKTIVNO;
			polozajIgralca().setXY(p.getX(), p.getY());
			naPotezi = naPotezi.nasprotnik();
			return true;
					
		}else {
			return false;
			}
	}
}
