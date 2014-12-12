package data;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import objects.Vecteur;

public class Data {
	public static final int COEFF1 = 500, COEFF2 = 300;
	public static final Vecteur LUMIERE = new Vecteur(0, 0, 1);
	public static Image WALLPAPER = Toolkit.getDefaultToolkit().getImage("ressources/images/degrade.jpg");
	public static final Icon CHARGEMENT = new ImageIcon("ressources/images/chargement.gif");
	public static final Image ICON3D = Toolkit.getDefaultToolkit().getImage("ressources/images/ICON3D.png");
	public static double alphaX = 0.0, alphaY = 0.0;
	public static final String TITLE = "GTSReader";
	public static final String FICHIER_AIDE = "ressources/text/qqc.txt";
	public static final Icon OPEN = new ImageIcon("ressources/icons/OPEN.png");
	public static final Icon SAVE = new ImageIcon("ressources/icons/SAVE.png");
	public static final Icon EXPORT = new ImageIcon("ressources/icons/EXPORT.png");
	public static final Icon QUIT = new ImageIcon("ressources/icons/QUIT.png");
	public static final Icon HELP = new ImageIcon("ressources/icons/HELP.png");
}