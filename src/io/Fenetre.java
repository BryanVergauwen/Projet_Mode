package io;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JFrame;

import objects.Face;
import objects.Point;
import objects.Segment;
import transformations.Homothetie;
import transformations.Rotation;


public class Fenetre extends JFrame implements MouseWheelListener, MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private double zoom;
	private GtsReader reader = new GtsReader(100);
	private List<Segment> listeSegments = reader.getListSegments();
	private List<Face> listeFaces = reader.getListFaces();
	private List<Point> listePoints = reader.getListPoint();
	private Point current;
	
	public Fenetre() {
		super("Fenetre_Test");
		setVisible(true);
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		setLocationRelativeTo(null);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public void paint(Graphics g) {
		int coeff1 = 500;
		int coeff2 = 350;
		int[] tabX = new int[3];
		int[] tabY = new int[3];

		// Reset fenetre
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Dessin des faces
		g.setColor(Color.GRAY);
		for (int i = 0; i < listeFaces.size(); i++) {
			Face current = listeFaces.get(i);

			// Tab X
			tabX[0] = (int)current.getSommetA().getX();
			tabX[1] = (int)current.getSommetB().getX();
			tabX[2] = (int)current.getSommetC().getX();
			
			// Tab Y
			tabY[0] = (int)current.getSommetA().getY();
			tabY[1] = (int)current.getSommetB().getY();
			tabY[2] = (int)current.getSommetC().getY();
			
			// ajout des coeffs
			for(int tmp = 0; tmp < tabX.length; tmp++)
				tabX[tmp] += coeff1;
			for(int tmp = 0; tmp < tabY.length; tmp++)
				tabY[tmp] += coeff2;
			
			// Dessin du triangle
			g.fillPolygon(tabX, tabY, 3);
		}
		
		// Dessin des segments
		g.setColor(Color.BLACK);
		for (Segment s : listeSegments){
			int x1 = coeff1 + (int)s.getOrigine().getX();
			int x2 = coeff2 + (int)s.getOrigine().getY();
			int x3 = coeff1 + (int)s.getFin().getX();
			int x4 = coeff2 + (int)s.getFin().getY();
			g.drawLine(x1, x2, x3, x4);
		}

		/* g.drawLine(getWidth() / 2, getHeight(), getWidth() / 2, -getHeight());
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2); */
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0)
			zoom = 1.1;
		else
			zoom = 0.9;
		new Homothetie(listePoints, zoom);
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY(), 0);
		
		if(p.getY() < current.getY())
			new Rotation(listePoints, Math.toRadians(10), "X");
		else if(p.getX() < current.getX())
			new Rotation(listePoints, Math.toRadians(10), "Y");
		else if(p.getY() > current.getY())
			new Rotation(listePoints, Math.toRadians(10), "Z");
		current = p;
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(current == null)
			current = new Point(e.getX(), e.getY(), 0);
		else{
			current.setX(e.getX());
			current.setY(e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}