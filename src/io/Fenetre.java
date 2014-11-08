package io;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import objects.Face;
import objects.Point;
import transformations.Homothetie;
import transformations.Rotation;
import transformations.Translation;
import data.Constantes;

public class Fenetre extends JFrame implements MouseWheelListener, MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private double zoom;
	private GtsReader reader = new GtsReader(100);
	private List<Face> listeFaces = reader.getListFaces();
	private List<Point> listePoints = reader.getListPoint();
	private Point current;
	private int cptMouse = 0;
	private Graphics2D g2;

	public Fenetre() {
		JLabel wallpaper = new JLabel(Constantes.wallpaper);

		setTitle("Fenetre_Test");
		setVisible(true);
		add(wallpaper);
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);

		// Temporaire !
		new Homothetie(listePoints, 0.2);
		paintComponent(getGraphics());
		paintComponent(getGraphics());
	}

	public void paintComponent(Graphics g) {
		// clear
		super.paintComponents(g);
		
		// init graphics
		g2 = (Graphics2D) g;
		
		// Dessin des faces
		int[] triangleX = new int[3];
		int[] triangleY = new int[3];
		double scal=0.0;//porduit scalaire pour la lumiere
		for (int i = 0; i < listeFaces.size(); i++) {
			Face current = listeFaces.get(i);
			
			//modif
			scal=Constantes.lumiere.prodScalaire(current.getNormal());
			//ATTENTION CECI EST JUSTE EN ATTENTE DE CORRECTION
			if(scal<0)
				scal=scal*-1;
			//FIN
			g2.setColor(new Color((int)(Constantes.COLOR*scal),(int)(Constantes.COLOR*scal), (int)(Constantes.COLOR*scal)));
			// Tab X
			triangleX[0] = (int) current.getSommetA().getX();
			triangleX[1] = (int) current.getSommetB().getX();
			triangleX[2] = (int) current.getSommetC().getX();

			// Tab Y
			triangleY[0] = (int) current.getSommetA().getY();
			triangleY[1] = (int) current.getSommetB().getY();
			triangleY[2] = (int) current.getSommetC().getY();

			// ajout des coeffs
			for (int tmp = 0; tmp < triangleX.length; tmp++)
				triangleX[tmp] += Constantes.COEFF1;
			for (int tmp = 0; tmp < triangleY.length; tmp++)
				triangleY[tmp] += Constantes.COEFF2;

			// Dessin du triangle
			g2.fillPolygon(triangleX, triangleY, 3);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0)
			zoom = 1.1;
		else
			zoom = 0.9;
		new Homothetie(listePoints, zoom);
		paintComponent(getGraphics());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			Point p = new Point(e.getX(), e.getY(), 0);

			cptMouse++;
			if (cptMouse == 4) {
				if (p.getY() < current.getY())
					new Rotation(listePoints, listeFaces, Math.toRadians(10), "X");
				else if (p.getX() < current.getX())
					new Rotation(listePoints, listeFaces, Math.toRadians(10), "Y");
				else if (p.getY() > current.getY())
					new Rotation(listePoints, listeFaces, Math.toRadians(10), "Z");
				current = p;
				cptMouse = 0;
				paintComponent(getGraphics());
			}
		} 
		else if (SwingUtilities.isRightMouseButton(e)) {
			Point p = new Point(e.getX(), e.getY(), 0);

			if (p.getY() < current.getY())
				new Translation(listePoints, -5, "X");
			else if (p.getY() > current.getY())
				new Translation(listePoints, 5, "X");
			else if (p.getX() > current.getX())
				new Translation(listePoints, 5, "Y");
			else if (p.getX() < current.getX())
				new Translation(listePoints, -5, "Y");
			else if (p.getZ() > current.getZ())
				new Translation(listePoints, 5, "Z");
			else if (p.getZ() < current.getZ())
				new Translation(listePoints, -5, "Z");
			current = p;
			paintComponent(getGraphics());
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (current == null)
			current = new Point(e.getX(), e.getY(), 0);
		else {
			current.setX(e.getX());
			current.setY(e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}