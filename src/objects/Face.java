package objects;

import exceptions.MalFormedFaceException;

public class Face {
	private Point a, b, c;

	public Face(Segment a, Segment b, Segment c) {
		this.a = a.getOrigine();
		this.b = a.getFin();
		
		if(!b.getOrigine().equals(this.a) && !b.getOrigine().equals(this.b))
			this.c = b.getOrigine();
		else if(!b.getFin().equals(this.a) && !b.getFin().equals(this.b))
			this.c = b.getFin();
		else
			throw new MalFormedFaceException();
	}

	public String toString() {
		return a + ";" + b + ";" + c;
	}

	public Point getSommetA() {
		return a;
	}

	public Point getSommetB() {
		return b;
	}

	public Point getSommetC() {
		return c;
	}
	public boolean equals(Face f){
		return a.equals(f.a) && b.equals(f.b) && c.equals(f.c);
	}
}
