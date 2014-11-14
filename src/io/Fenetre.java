package io;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import objects.Face;
import objects.Point;
import transformations.Homothetie;
import transformations.Rotation;
import transformations.Translation;
import data.Constantes;

public class Fenetre extends JFrame implements MouseWheelListener, MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private double zoom;
	private Point current;
	private int cptMouse = 0;
	private Graphics2D g2;
	private JList<String> listeModeles;
	private GtsReader reader;
	private List<Face> listeFaces;
	private List<Point> listePoints;
	private JPanel dessin, modeles;
	private String[] gtsFiles = getGTSFiles();
	
	public Fenetre(String modele) {
		updateModel(modele);
		setLayout(new BorderLayout());
		setVisible(true);
		setSize(1000, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);

		dessin = new JPanel();
		modeles = new JPanel();
		modeles.setLayout(new BoxLayout(modeles, BoxLayout.Y_AXIS));
		modeles.setBackground(Color.white);

		listeModeles = new JList<String>(gtsFiles);
		listeModeles.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				updateModel(listeModeles.getSelectedValue());
			}
		});
		
		modeles.add(listeModeles);
		getContentPane().add(modeles, BorderLayout.WEST);
		getContentPane().add(dessin, BorderLayout.EAST);
		
		// Temporaire !
		new Homothetie(listePoints, 0.05);
		paintComponent(getGraphics());
		paintComponent(getGraphics());
	}

	private String[] getGTSFiles(){
		File[] f = new File("ressources/gts").listFiles();
		String[] result = new String[f.length];
		
		for(int i = 0; i < result.length; i++)
			result[i] = f[i].getName()+"   ";
		return result;
	}
	private void updateModel(String modele){
		reader = new GtsReader(100, modele);
		listeFaces = reader.getListFaces();
		listePoints = reader.getListPoint();
		paintComponent(getGraphics());
	}

	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g;
		Graphics offgc;
		Image offscreen = null;

		offscreen = createImage(this.getWidth() - 122, this.getHeight());
		if(offscreen != null){
			offgc = offscreen.getGraphics();
			
			// Dessin des faces
			double scal = 0.0; // Produit scalaire pour la lumiere
			
			offgc.drawImage(Constantes.wallpaper, 0, 0, this);
			for (Face f : listeFaces) {
				scal = Constantes.lumiere.prodScalaire(f.getNormal());
				scal = Math.abs(scal);
				
				offgc.setColor(new Color((int)(Constantes.COLOR*scal),(int)(Constantes.COLOR*scal), (int)(Constantes.COLOR*scal)));
				
				// Dessin du triangle
				offgc.fillPolygon(f.getTriangleX(), f.getTriangleY(), 3);
			}
			g2.drawImage(offscreen, 122, 0, this);
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
					new Rotation(listePoints, listeFaces, Math.toRadians(10), "X");
				if (p.getX() < current.getX())
					new Rotation(listePoints, listeFaces, Math.toRadians(10), "Y");
				if (p.getY() > current.getY())
					new Rotation(listePoints, listeFaces, Math.toRadians(10), "Z");
				current = p;
				cptMouse = 0;
				paintComponent(getGraphics());
			}
		} 
		else if (SwingUtilities.isRightMouseButton(e)) {
			Point p = new Point(e.getX(), e.getY(), 0);

			if (p.getY() < current.getY())
				new Translation(listePoints, -5, "X");
			if (p.getY() > current.getY())
				new Translation(listePoints, 5, "X");
			if (p.getX() > current.getX())
				new Translation(listePoints, 5, "Y");
			if (p.getX() < current.getX())
				new Translation(listePoints, -5, "Y");
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