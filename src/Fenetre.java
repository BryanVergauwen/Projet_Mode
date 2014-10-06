import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;

public class Fenetre extends JFrame {
	private static final long serialVersionUID = 1L;
	private GtsReader reader = new GtsReader();
	private List<Point> listePoint = reader.getListPoint();
	private List<Segment> listeSegments = reader.getListSegments();
	private List<Face> listeFaces = reader.getListFaces();

	public Fenetre() {
		super("Fenetre_Test");
		setVisible(true);
		setSize(1000, 700);
		setBackground(Color.white);
		setLocationRelativeTo(null);
	}

	public void paint(Graphics g) {
		for (Segment s : listeSegments)
			g.drawLine(500 + s.getOrigine().getX(), 350 + s.getOrigine().getY(), 
					500 + s.getFin().getX(), 350 + s.getFin().getY());
		
		g.drawLine(getWidth()/2, getHeight(), getWidth()/2, -getHeight());
		g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
	}
}