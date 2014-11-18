package io;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

public class Fenetre extends JFrame implements KeyListener, MouseWheelListener, MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private double zoom;
	private Point current;
	private int cptMouse = 0;
	private Graphics2D g2;
	private Reader reader;
	private List<Face> listeFaces;
	private List<Point> listePoints;
	private JPanel dessin, modeles;
	private DefaultListModel dl = new DefaultListModel();
	private JList listeModeles = new JList(dl);
	private String[] files = getFiles();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fichier = new JMenu("Fichier");
	private JMenuItem quitter = new JMenuItem("Quitter");
	
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
		addKeyListener(this);
		
		listeModeles.setFocusable(false);
		
		dessin = new JPanel();
		modeles = new JPanel();
		modeles.setLayout(new BoxLayout(modeles, BoxLayout.Y_AXIS));
		modeles.setBackground(Color.white);
		for(int i = 0; i < files.length; i++)
			dl.add(i, files[i]);
		listeModeles.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				updateModel(listeModeles.getSelectedValue().toString());
			}
		});
		
		quitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		fichier.add(quitter);
		menuBar.add(fichier);
		setJMenuBar(menuBar);
		
		triListe();
		modeles.add(listeModeles);
		getContentPane().add(modeles, BorderLayout.WEST);
		getContentPane().add(dessin, BorderLayout.EAST);
		getContentPane().setBackground(Color.white);
		
		// Temporaire !
		new Homothetie(listePoints, 0.05);
		paintComponent(getGraphics());
		paintComponent(getGraphics());
	}

	private void triListe() {
		List<String> tmp = new LinkedList<String>();
		
		for(int i = 0; i < dl.size(); i++)
			tmp.add(dl.get(i).toString());
		Collections.sort(tmp);
		dl.clear();
		
		for(int i = 0; i < files.length; i++)
			dl.add(i, tmp.get(i));
	}

	private String[] getFiles(){
		File[] f = new File("ressources/gts").listFiles();
		List<File> files = new LinkedList<File>();
		int lengthFile;
		
		for(int i = 0; i < f.length; i++){
			lengthFile = f[i].getName().length();
			if(f[i].getName().substring(lengthFile-4, lengthFile).equalsIgnoreCase(".gts"))
				files.add(f[i]);
		}
		
		f = new File("ressources/obj").listFiles();
		for(int i = 0; i < f.length; i++){
			lengthFile = f[i].getName().length();
			if(f[i].getName().substring(lengthFile-4, lengthFile).equalsIgnoreCase(".obj"))
				files.add(f[i]);
		}
		
		String[] tmp = new String[files.size()];
		
		for(int i = 0; i < files.size(); i++)
			tmp[i] = files.get(i).getName();
		
		return tmp;
	}
	private void updateModel(String modele){
		int lengthFile = modele.length();

		if(modele.substring(lengthFile-4, lengthFile).equalsIgnoreCase(".obj"))
			reader = new ObjReader(100, modele);
		else if(modele.substring(lengthFile-4, lengthFile).equalsIgnoreCase(".gts"))
			reader = new GtsReader(100, modele);
		listeFaces = reader.getListFaces();
		listePoints = reader.getListPoint();
		paintComponent(getGraphics());
	}

	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g;
		Graphics offgc;
		Image offscreen = null;
		
		offscreen = createImage(this.getWidth() - 150, this.getHeight());
		if(offscreen != null){
			offgc = offscreen.getGraphics();
			
			// Dessin des faces
			double scal = 0.0; // Produit scalaire pour la lumiere
			
			offgc.drawImage(Constantes.wallpaper, 0, 0, this);
			for (Face f : listeFaces) {
				scal = Constantes.lumiere.prodScalaire(f.getNormal());
				scal = Math.abs(scal);
				
				offgc.setColor(new Color((int)(30 + Constantes.COLOR*scal),(int)(30 + Constantes.COLOR*scal), (int)(30 + Constantes.COLOR*scal)));
				offgc.fillPolygon(f.getTriangleX(), f.getTriangleY(), 3);
			}
			g2.drawImage(offscreen, 150, 0, this);
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
					new Rotation(listePoints, listeFaces, Math.toRadians(-10), "X");
				if (p.getX() > current.getX())
					new Rotation(listePoints, listeFaces, Math.toRadians(-10), "Y");
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

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Z)
			new Rotation(listePoints, listeFaces, Math.toRadians(10), "X");
		if (e.getKeyCode() == KeyEvent.VK_Q)
			new Rotation(listePoints, listeFaces, Math.toRadians(10), "Y");
		if (e.getKeyCode() == KeyEvent.VK_S)
			new Rotation(listePoints, listeFaces, Math.toRadians(-10), "X");
		if (e.getKeyCode() == KeyEvent.VK_D)
			new Rotation(listePoints, listeFaces, Math.toRadians(-10), "Y");
		paintComponent(getGraphics());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Z)
			new Rotation(listePoints, listeFaces, Math.toRadians(10), "X");
		if (e.getKeyCode() == KeyEvent.VK_Q)
			new Rotation(listePoints, listeFaces, Math.toRadians(10), "Y");
		if (e.getKeyCode() == KeyEvent.VK_S)
			new Rotation(listePoints, listeFaces, Math.toRadians(-10), "X");
		if (e.getKeyCode() == KeyEvent.VK_D)
			new Rotation(listePoints, listeFaces, Math.toRadians(-10), "Y");
		paintComponent(getGraphics());	
	}
}