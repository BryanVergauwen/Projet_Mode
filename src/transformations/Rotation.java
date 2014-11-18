package transformations;

import java.util.Collections;
import java.util.List;

import objects.Face;
import objects.Point;

public class Rotation {
	private final String X = "X";
	private final String Y = "Y";

	public Rotation(List<Point> points, List<Face> faces, double angle, String axe) {
		double x = 0, y = 0, z = 0;

		if (axe.equalsIgnoreCase(Y)) {
			for (Point p : points) {
				x = p.getX();
				y = p.getY();
				z = p.getZ();

				p.setX(x * Math.cos(angle) - z * Math.sin(angle));
				p.setY(y);
				p.setZ(x * Math.sin(angle) + z * Math.cos(angle));
			}
		}
		if (axe.equalsIgnoreCase(X)) {
			for (Point p : points) {
				x = p.getX();
				y = p.getY();
				z = p.getZ();

				p.setY(y * Math.cos(angle) - z * Math.sin(angle));
				p.setX(x);
				p.setZ(y * Math.sin(angle) + z * Math.cos(angle));
			}
		}
		for (Face f : faces){
			f.normalisation();
			f.barycentre();
		}
		Collections.sort(faces);
	}
}
