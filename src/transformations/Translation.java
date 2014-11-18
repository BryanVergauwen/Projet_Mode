package transformations;

import java.util.List;

import objects.Point;

public class Translation {
	private final String X = "X";
	private final String Y = "Y";

	public Translation(List<Point> points, double coeff, String axe) {
		double x = 0, y = 0;

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
	}
}
