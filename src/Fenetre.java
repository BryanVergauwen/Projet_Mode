import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JFrame;

public class Fenetre extends JFrame implements MouseWheelListener {
	private static final long serialVersionUID = 1L;
	private double zoom;
	private GtsReader reader = new GtsReader(100);
	private List<Segment> listeSegments = reader.getListSegments();
	private List<Face> listeFaces = reader.getListFaces();

	public Fenetre() {
		super("Fenetre_Test");
		setVisible(true);
		setSize(1000, 700);
		setBackground(Color.white);
		setLocationRelativeTo(null);
		addMouseWheelListener(this);
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
			Segment s1 = current.getA();
			Segment s2 = current.getB();
			Segment s3 = current.getC();
			boolean b1, b2, b3, b4;

			// Tab X
			tabX[0] = (int)s1.getOrigine().getX();
			if((int)s2.getOrigine().getX() == tabX[0])
				tabX[1] = (int)s2.getFin().getX();
			else
				tabX[1] = (int)s2.getOrigine().getX();
			
			b1 = (int)s3.getOrigine().getX() == tabX[0];
			b2 = (int)s3.getOrigine().getX() == tabX[1];
			
			if((b1 || b2) && tabX[1] != (int)s3.getFin().getX())
				tabX[2] = (int)s3.getFin().getX();
			else
				tabX[2] = (int)s3.getOrigine().getX();
			
			// Tab Y
			tabY[0] =(int)s1.getOrigine().getY();
			if((int)s2.getOrigine().getY() == tabY[0])
				tabY[1] = (int)s2.getFin().getY();
			else
				tabY[1] = (int)s2.getOrigine().getY();
			
			b3 = (int)s3.getOrigine().getY() == tabY[0];
			b4 = (int)s3.getOrigine().getY() == tabY[1];
			
			if((b3 || b4) && tabY[1] != (int)s3.getFin().getY())
				tabY[2] = (int)s3.getFin().getY();
			else
				tabY[2] = (int)s3.getOrigine().getY();
			
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

		g.drawLine(getWidth() / 2, getHeight(), getWidth() / 2, -getHeight());
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0)
			zoom = 1.1;
		else
			zoom = 0.9;
		reader.updatePoints(zoom);
		repaint();
	}
}