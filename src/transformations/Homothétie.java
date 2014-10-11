package transformations;

import java.util.List;

import objects.Point;

public class Homothétie{
	public Homothétie(List<Point> points, double zoom) {
		for (Point p : points) {
			p.setX(p.getX() * zoom);
			p.setY(p.getY() * zoom);
			p.setZ(p.getZ() * zoom);
		}
	}
}
