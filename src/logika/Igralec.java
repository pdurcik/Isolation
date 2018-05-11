package logika;

public enum Igralec {
	BELI,
	CRNI;
	
	public Igralec nasprotnik() {
		return (this == BELI ? CRNI : BELI);
	}
	
	public String toString() {
		switch(this) {
		case BELI: return "BELI";
		case CRNI: return "CRNI";
		default: return "?";
		}
	}

}
