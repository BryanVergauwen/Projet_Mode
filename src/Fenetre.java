import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JFrame;

public class Fenetre extends JFrame implements MouseWheelListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private double zoom;
	private GtsReader reader = new GtsReader(100);
//	private List<Segment> listeSegments = reader.getListSegments();
	private List<Face> listeFaces = reader.getListFaces();
	private int mouseX=0, mouseY=0;

	public Fenetre() {
		super("Fenetre_Test");
		setVisible(true);
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		setLocationRelativeTo(null);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
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
		
	/*	// Dessin des segments
		g.setColor(Color.BLACK);
		for (Segment s : listeSegments)
			g.drawLine(coeff1 + (int)s.getOrigine().getX(), coeff2 + (int)s.getOrigine().getY(), 
					coeff1 + (int)s.getFin().getX(), coeff2 + (int)s.getFin().getY());

		g.drawLine(getWidth() / 2, getHeight(), getWidth() / 2, -getHeight());
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2); */
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0)
			zoom = 1.1;
		else
			zoom = 0.9;
		reader.updatePoints(zoom);
		repaint();
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//TODO Operation de rotation sur les points
		reader.rotatePoints(Math.PI);
		mouseX=e.getX();
		mouseY=e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
}