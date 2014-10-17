package transformations;

import java.util.List;

import objects.Point;

public class Translation {
	private final String X = "X";
	private final String Y = "Y";
	private final String Z = "Z";

	public Translation(List<Point> points, double coeff, String axe) {
		double x = 0, y = 0, z = 0;

		if (axe.equalsIgnoreCase(X)) {
			for (Point p : points) {
				y = p.getY();

				p.setY(y + coeff);
			}
		} 
		else if (axe.equalsIgnoreCase(Y)) {
			for (Point p : points) {
				x = p.getX();
				
				p.setX(x + coeff);
			}
		}
		else if (axe.equalsIgnoreCase(Z)) {
			for (Point p : points) {
				z = p.getZ();
				
				p.setZ(z + coeff);
			}
		}
	}
}
