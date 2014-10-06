import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;

public class Fenetre extends JFrame {
	private static final long serialVersionUID = 1L;
	private GtsReader reader = new GtsReader();
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
		
		int[] tabX = new int[3];
		int[] tabY = new int[3];
		int coeff1 = 500;
		int coeff2 = 350;
		
		g.setColor(Color.GRAY);
		for(int i = 0; i < listeFaces.size(); i++){
			tabX[0] = coeff1 + listeFaces.get(i).getA().getOrigine().getX();
			tabX[1] = coeff1 + listeFaces.get(i).getB().getOrigine().getX();
			tabX[2] = coeff1 + listeFaces.get(i).getC().getOrigine().getX();
			
			tabY[0] = coeff2 + listeFaces.get(i).getA().getFin().getY();
			tabY[1] = coeff2 + listeFaces.get(i).getB().getFin().getY();
			tabY[2] = coeff2 + listeFaces.get(i).getC().getFin().getY();
			
			g.drawPolygon(tabX, tabY, 3);
		}

		g.drawLine(getWidth()/2, getHeight(), getWidth()/2, -getHeight());
		g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
	}
}