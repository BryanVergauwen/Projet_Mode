package transformations;

import java.util.List;

import data.Data;

import objects.Point;

public class Translation {
	private final String X = "X";
	private final String Y = "Y";

	public Translation(List<Point> points, double coeff, String axe) {
		if (axe.equalsIgnoreCase(Y))
			Data.alphaY += coeff*2;
		if (axe.equalsIgnoreCase(X))
			Data.alphaX += coeff*2;
	}
}