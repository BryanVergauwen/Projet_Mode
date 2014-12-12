package objects;

import data.Data;

public class Segment {
	private Point x, y;
	
	public Segment(Point x, Point y){
		this.x = x;
		this.y = y;
	}
	public Point getOrigine(){
		return x;
	}
	public Point getFin(){
		return y;
	}
	public String toString(){
		return x+";"+y;
	}
	public boolean equals(Segment s){
		return x.equals(s.x) && y.equals(s.y);
	}
	public int getSegment1(){
		return (int) (this.getOrigine().getX() + Data.COEFF1 + Data.alphaX);
	}
	public int getSegment2(){
		return (int) (this.getOrigine().getY() + Data.COEFF2 + Data.alphaY);
	}
	public int getSegment3(){
		return (int) (this.getFin().getX() + Data.COEFF1 + Data.alphaX);
	}
	public int getSegment4(){
		return (int) (this.getFin().getY() + Data.COEFF2 + Data.alphaY);
	}
}