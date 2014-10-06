import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;

public class Fenetre extends JFrame{
	private static final long serialVersionUID = 1L;
	private List<Point> listePoint = new GtsReader().getListPoint();
	
	public Fenetre(){
		super("Fenetre_Test");
		setVisible(true);
		setSize(800, 600);
		setBackground(Color.white);
		setLocationRelativeTo(null);
	}
	public void paint(Graphics g){
		if(listePoint != null){
			for(Point p : listePoint)
				g.fillOval(30 + p.getX() * 40, 50 + p.getY() * 40, 10, 10);
		}
	}
}