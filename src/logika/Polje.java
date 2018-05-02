package logika;

public enum Polje{
	AKTIVNO,
	NEAKTIVNO;

	public String toString() {
		switch(this) {
		case AKTIVNO: return "AKTIVNO";
		case NEAKTIVNO: return "NEAKTIVNO";
		default: return "?";
		}
	}
	
}
