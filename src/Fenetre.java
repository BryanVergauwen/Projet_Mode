import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

public class Fenetre extends JFrame{
	private static final long serialVersionUID = 1L;
	private List<Point> listePoint;
	
	public Fenetre(){
		super("Fenetre_Test");
		setVisible(true);
		setSize(800, 600);
		setBackground(Color.white);
		setLocationRelativeTo(null);
		listePoint = new LinkedList<Point>();
		listePoint.add(new Point(5, 5, 0));
		listePoint.add(new Point(0, 0, 0));
		listePoint.add(new Point(10, 5, 1));
	}
	public void paint(Graphics g){
		if(listePoint != null){
			for(Point p : listePoint)
				g.fillOval(30 + p.getX() * 20, 50 + p.getY() * 20, 10, 10);
		}
	}
}