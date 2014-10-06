import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JFrame;

public class Fenetre extends JFrame implements MouseWheelListener {
	private static final long serialVersionUID = 1L;
	private int zoom = 300;
	private GtsReader reader = new GtsReader(zoom);
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

		g.setColor(Color.GRAY);
		for (int i = 0; i < listeFaces.size(); i++) {
			tabX[0] = coeff1 + listeFaces.get(i).getA().getOrigine().getX();
			tabX[1] = coeff1 + listeFaces.get(i).getB().getOrigine().getX();
			tabX[2] = coeff1 + listeFaces.get(i).getC().getOrigine().getX();

			tabY[0] = coeff2 + listeFaces.get(i).getA().getOrigine().getY();
			tabY[1] = coeff2 + listeFaces.get(i).getB().getOrigine().getY();
			tabY[2] = coeff2 + listeFaces.get(i).getC().getOrigine().getY();

			g.fillPolygon(tabX, tabY, 3);
		}

		g.setColor(Color.BLACK);
		for (Segment s : listeSegments)
			g.drawLine(coeff1 + s.getOrigine().getX(), coeff2 + s.getOrigine().getY(), 
					coeff1 + s.getFin().getX(), coeff2 + s.getFin().getY());

		g.drawLine(getWidth() / 2, getHeight(), getWidth() / 2, -getHeight());
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0)
			zoom += 50;
		else
			zoom -= 50;
		reader = new GtsReader(zoom);
		listeFaces = reader.getListFaces();
		listeSegments = reader.getListSegments();
		repaint();
	}
}