package objects;

import data.Data;
import exceptions.MalFormedFaceException;

public class Face implements Comparable<Face>{
	private Point a, b, c;
	private Vecteur normal;
	private double barycentre;

	public Face(Segment a, Segment b, Segment c) {
		this.a = a.getOrigine();
		this.b = a.getFin();
		
		if(!b.getOrigine().equals(this.a) && !b.getOrigine().equals(this.b))
			this.c = b.getOrigine();
		else if(!b.getFin().equals(this.a) && !b.getFin().equals(this.b))
			this.c = b.getFin();
		else
			throw new MalFormedFaceException();
		normalisation();
		barycentre();
	}
	
	public void barycentre(){
		barycentre = (a.getZ()+b.getZ()+c.getZ())/3;
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

	@Override
	public int compareTo(Face f) {
		double tmp = this.barycentre - f.barycentre;
		
		if(tmp < 0)
			return -1;
		else if(tmp == 0)
			return 0;
		return 1;
	}
	public int compareTo2(Face f) {
		if(getSommetA().getX() < f.getSommetA().getX())
			return -1;
		else if(getSommetA().getX() == f.getSommetA().getX())
			return 0;
		return 1;
	}
	public int[] getTriangleX(){
		int[] tmp = new int[3];
		
		tmp[0] = (int) (getSommetA().getX()+Data.alphaX);
		tmp[1] = (int) (getSommetB().getX()+Data.alphaX);
		tmp[2] = (int) (getSommetC().getX()+Data.alphaX);
		
		for (int i = 0; i < tmp.length; i++)
			tmp[i] += Data.COEFF1;
		
		return tmp;
	}
	public int[] getTriangleY(){
		int[] tmp = new int[3];
		
		tmp[0] = (int) (getSommetA().getY()+Data.alphaY);
		tmp[1] = (int) (getSommetB().getY()+Data.alphaY);
		tmp[2] = (int) (getSommetC().getY()+Data.alphaY);
		
		for (int i = 0; i < tmp.length; i++)
			tmp[i] += Data.COEFF2;
		
		return tmp;
	}
}
