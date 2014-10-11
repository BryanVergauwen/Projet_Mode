package objects;
public class Face {
	private Segment a, b, c;

	public Face(Segment a, Segment b, Segment c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public String toString() {
		return a + ";" + b + ";" + c;
	}

	public Point getSommetA() {
		return a.getOrigine();
	}

	public Point getSommetB() {
		if(!b.getFin().equals(getSommetA()))
			return b.getFin();
		return b.getOrigine();
	}

	public Point getSommetC() {
		if(!c.getFin().equals(getSommetB()))
			return c.getFin();
		return c.getOrigine();
	}
	public boolean equals(Face f){
		return a.equals(f.a) && b.equals(f.b) && c.equals(f.c);
	}
}
