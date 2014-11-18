package data;

import java.awt.Image;
import java.awt.Toolkit;

import objects.Point;
import objects.Vecteur;

public class Constantes {
	public static final int COEFF1 = 500, COEFF2 = 300;
	public static final Vecteur lumiere = new Vecteur(0, 0, 1);
	public static final int COLOR = 100; // maximum 255 (blanc pur) 0 pour du pur noir
	public static final Image wallpaper = Toolkit.getDefaultToolkit().getImage("ressources/degrade.jpg");
	public static Point barycentre;
}