package objects;

import exceptions.MalFormedFaceException;

public class Face {
	private Point a, b, c;
	private Vecteur normal;

	public Face(Segment a, Segment b, Segment c) {
		this.a = a.getOrigine();
		this.b = a.getFin();
		
		if(!b.getOrigine().equals(this.a) && !b.getOrigine().equals(this.b))
			this.c = b.getOrigine();
		else if(!b.getFin().equals(this.a) && !b.getFin().equals(this.b))
			this.c = b.getFin();
		else
			throw new MalFormedFaceException();
		normal = new Vecteur(new Vecteur(this.a, this.b), new Vecteur(this.a, this.c));
	}
	
	public Vecteur getNormal(){
		return this.normal;
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
	public void normalisation(){
		this.normal = new Vecteur(new Vecteur(this.a, this.b), new Vecteur(this.a, this.c));
	}
}
