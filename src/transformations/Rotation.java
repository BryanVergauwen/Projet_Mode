package transformations;

import java.util.List;

import objects.Point;

public class Rotation{
	private final String X = "X";
	private final String Y = "Y";

	public Rotation(List<Point> points, double angle, String axe){
		if(axe.equalsIgnoreCase(X)){
			for (Point p : points){
				p.setX(p.getX() * Math.cos(angle) + p.getX() * Math.sin(-angle));
				p.setZ(p.getZ() * Math.sin(angle) + p.getZ() * Math.cos(angle));
			}
		}
		else if(axe.equalsIgnoreCase(Y)){
			for (Point p : points){
				p.setY(p.getY() * Math.cos(angle) + p.getY() * Math.sin(-angle));
				p.setZ(p.getZ() * Math.sin(angle) + p.getZ() * Math.cos(angle));
			}
		}
	}
}
