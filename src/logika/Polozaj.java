package logika;

public class Polozaj {
	
	private int x;
	private int y;
	
	public Polozaj(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Polozaj(Polozaj polozaj) {
		this.x = polozaj.getX();
		this.y = polozaj.getY();
		
	}

	public int getX() {
		return x;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getY() {
		return y;
	}	
}