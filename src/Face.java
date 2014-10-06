public class Face {
	private Segment a, b, c;
	
	public Face(Segment a, Segment b, Segment c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public String toString(){
		return a+";"+b+";"+c;
	}
	public Segment getA(){
		return a;
	}
	public Segment getB(){
		return b;
	}
	public Segment getC(){
		return c;
	}
}
