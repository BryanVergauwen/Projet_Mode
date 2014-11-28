package transformations;

import java.util.List;

import data.Constantes;

import objects.Point;

public class Translation {
	private final String X = "X";
	private final String Y = "Y";

	public Translation(List<Point> points, double coeff, String axe) {
		if (axe.equalsIgnoreCase(Y))
			Constantes.alphaY += coeff*1.1;
		if (axe.equalsIgnoreCase(X))
			Constantes.alphaX += coeff*1.1;
	}
}
