import java.awt.Color;
import java.awt.Font;
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
		listePoint.add(new Point(0, 1, 0));
		listePoint.add(new Point(10, 10, 1));
	}
	public void paint(Graphics g){
		if(listePoint != null){
			g.setFont(new Font("Arial", Font.PLAIN, 50));
			g.setColor(Color.black);
			for(Point p : listePoint)
				g.fillOval(p.getX() * 20, p.getY() * 20, 50, 50);
		}
	}
}