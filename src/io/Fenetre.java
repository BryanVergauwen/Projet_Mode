package io;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JFrame;

import objects.Face;
import objects.Point;
import objects.Segment;

import transformations.Homothétie;
import transformations.Rotation;


public class Fenetre extends JFrame implements MouseWheelListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private double zoom;
	private GtsReader reader = new GtsReader(100);
	private List<Segment> listeSegments = reader.getListSegments();
	private List<Face> listeFaces = reader.getListFaces();
	private List<Point> listePoints = reader.getListPoint();
	
	public Fenetre() {
		super("Fenetre_Test");
		setVisible(true);
		setSize(1000, 700);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		setLocationRelativeTo(null);
		addMouseWheelListener(this);
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
		for (Segment s : listeSegments)
			g.drawLine(coeff1 + (int)s.getOrigine().getX(), coeff2 + (int)s.getOrigine().getY(), 
					coeff1 + (int)s.getFin().getX(), coeff2 + (int)s.getFin().getY());

		/* g.drawLine(getWidth() / 2, getHeight(), getWidth() / 2, -getHeight());
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2); */
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0)
			zoom = 1.1;
		else
			zoom = 0.9;
		new Homothétie(listePoints, zoom);
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			new Rotation(listePoints, Math.toRadians(10), "X");
		else if(e.getButton() == MouseEvent.BUTTON3)
			new Rotation(listePoints, Math.toRadians(10), "Y");
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}