package io;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import objects.Face;
import objects.Point;
import objects.Segment;
import transformations.Homothetie;
import transformations.Rotation;
import transformations.Translation;

public class Fenetre extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private double zoom;
	private GtsReader reader = new GtsReader(100);
	private List<Segment> listeSegments = reader.getListSegments();
	private List<Face> listeFaces = reader.getListFaces();
	private List<Point> listePoints = reader.getListPoint();
	private Point current;
	private int cptMouse = 0;
	private JPanel arretes, faces;
	private Graphics2D ga, gf;

	public Fenetre() {
		frame = new JFrame();
		faces = new JPanel();
		arretes = new JPanel();

		frame.setTitle("Fenetre_Test");
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		frame.setSize(1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.white);
		frame.setLocationRelativeTo(null);
		frame.addMouseWheelListener(this);
		frame.addMouseMotionListener(this);
		frame.addMouseListener(this);

		arretes.setPreferredSize(new Dimension(frame.getWidth() / 2 - 30, frame.getHeight() - 60));
		faces.setPreferredSize(new Dimension(frame.getWidth() / 2 - 30, frame.getHeight() - 60));
		arretes.setBackground(Color.white);
		faces.setBackground(Color.white);

		frame.setContentPane(this);
		frame.getContentPane().add(arretes, BorderLayout.WEST);
		frame.getContentPane().add(faces, BorderLayout.EAST);
	}

	public void paintComponent(Graphics g) {
		int coeff1 = 250;
		int coeff2 = 300;
		int[] tabX = new int[3];
		int[] tabY = new int[3];

		// init graphics
		ga = (Graphics2D) arretes.getGraphics();
		gf = (Graphics2D) faces.getGraphics();

		// Reset fenetre
		ga.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		gf.clearRect(0, 0, frame.getWidth(), frame.getHeight());

		// Dessin des faces
		gf.setColor(Color.GRAY);
		for (int i = 0; i < listeFaces.size(); i++) {
			Face current = listeFaces.get(i);

			// Tab X
			tabX[0] = (int) current.getSommetA().getX();
			tabX[1] = (int) current.getSommetB().getX();
			tabX[2] = (int) current.getSommetC().getX();

			// Tab Y
			tabY[0] = (int) current.getSommetA().getY();
			tabY[1] = (int) current.getSommetB().getY();
			tabY[2] = (int) current.getSommetC().getY();

			// ajout des coeffs
			for (int tmp = 0; tmp < tabX.length; tmp++)
				tabX[tmp] += coeff1;
			for (int tmp = 0; tmp < tabY.length; tmp++)
				tabY[tmp] += coeff2;

			// Dessin du triangle
			gf.fillPolygon(tabX, tabY, 3);
		}

		// Dessin des segments
		ga.setColor(Color.BLACK);
		for (Segment s : listeSegments) {
			int x1 = coeff1 + (int) s.getOrigine().getX();
			int x2 = coeff2 + (int) s.getOrigine().getY();
			int x3 = coeff1 + (int) s.getFin().getX();
			int x4 = coeff2 + (int) s.getFin().getY();
			ga.drawLine(x1, x2, x3, x4);
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
					new Rotation(listePoints, Math.toRadians(10), "X");
				else if (p.getX() < current.getX())
					new Rotation(listePoints, Math.toRadians(10), "Y");
				else if (p.getY() > current.getY())
					new Rotation(listePoints, Math.toRadians(10), "Z");
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