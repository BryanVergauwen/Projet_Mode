package transformations;

import java.util.List;

import objects.Point;
import data.Constantes;

public class Homothetie{
	public Homothetie(List<Point> points, double zoom) {
		for (Point p : points) {
			p.setX(p.getX() * zoom);
			p.setY(p.getY() * zoom);
			p.setZ(p.getZ() * zoom);
			Constantes.barycentre.setX(Constantes.barycentre.getX()*zoom);
			Constantes.barycentre.setY(Constantes.barycentre.getY()*zoom);
			Constantes.barycentre.setZ(Constantes.barycentre.getZ()*zoom);
		}
	}
}
