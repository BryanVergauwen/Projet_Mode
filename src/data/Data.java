package data;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import objects.Vecteur;

public class Data {
	public static final int COEFF1 = 500, COEFF2 = 300;
	public static final Vecteur LUMIERE = new Vecteur(0, 0, 1);
	public static final Image WALLPAPER = Toolkit.getDefaultToolkit().getImage("ressources/images/degrade.jpg");
	public static final ImageIcon WALLP_TMP = new ImageIcon("ressources/images/degrade.jpg");
	public static final Icon CHARGEMENT = new ImageIcon("ressources/images/chargement.gif");
	public static final Image ICON3D = Toolkit.getDefaultToolkit().getImage("ressources/images/ICON3D.png");
	public static double alphaX=0.0, alphaY=0.0;
	public static final String TITLE = "GTSReader";
}