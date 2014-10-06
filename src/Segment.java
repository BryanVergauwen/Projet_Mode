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
}