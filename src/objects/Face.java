package objects;

import java.io.Serializable;

import data.Data;

public class Face implements Comparable<Face>, Serializable{
	private static final long serialVersionUID = 1L;
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
	public Face valAbs(){
		double a1 = Math.abs(getSommetA().getX());
		double a2 = Math.abs(getSommetA().getY());
		double a3 = Math.abs(getSommetA().getZ());
		double a4 = Math.abs(getSommetB().getX());
		double a5 = Math.abs(getSommetB().getY());
		double a6 = Math.abs(getSommetB().getZ());
		double a7 = Math.abs(getSommetC().getX());
		double a8 = Math.abs(getSommetC().getY());
		double a9 = Math.abs(getSommetC().getZ());
		Point p1 = new Point(a1, a2, a3);
		Point p2 = new Point(a4, a5, a6);
		Point p3 = new Point(a7, a8, a9);
		Segment s1 = new Segment(p1, p2);
		Segment s2 = new Segment(p2, p3);
		Segment s3 = new Segment(p3, p1);
		
		return new Face(s1, s2, s3);
	}
}