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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	private int cptMouse = 0, decalX = 231, decalY = 55;
	private Graphics2D g2;
	private GtsReader reader;
	private List<Face> listeFaces;
	private List<Point> listePoints;
	private JPanel modeles;
	private DefaultListModel dl = new DefaultListModel();
	private JList listeModeles = new JList(dl);
	private JScrollPane scrollPane = new JScrollPane(listeModeles);
	private String[] files = getFiles();
	private JMenuBar menuBar = new JMenuBar();
	private List<JMenu> jMenus;
	private List<JMenuItem> jMenuItems;
	private Color color = new Color(125, 125, 125);
	private Map<Face, Color> alea;
	private boolean export = false;
	
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
		
		modeles = new JPanel();
		modeles.setLayout(new BoxLayout(modeles, BoxLayout.Y_AXIS));
		modeles.setBackground(Color.white);
		
		modeles.add(scrollPane);
		getContentPane().add(modeles, BorderLayout.WEST);
		getContentPane().setBackground(Color.white);

		for(int i = 0; i < files.length; i++)
			dl.add(i, files[i]);
		listeModeles.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				updateModel(listeModeles.getSelectedValue().toString());
			}
		});
		
		initJMenuBar();
		triListe();

		// Temporaire !
		new Homothetie(listePoints, 0.05);
		paintComponent(getGraphics());
		paintComponent(getGraphics());
	}

	private void initJMenuBar() {
		jMenuItems = new LinkedList<JMenuItem>();
		jMenus = new LinkedList<JMenu>();

		jMenus.add(new JMenu("Fichier"));
		jMenus.add(new JMenu("Edition"));
		jMenus.add(new JMenu("Outils"));
		jMenus.add(new JMenu("Aide"));
		
		jMenuItems.add(new JMenuItem("Ouvrir"));
		jMenuItems.add(new JMenuItem("Enregistrer"));
		jMenuItems.add(new JMenuItem("Exporter"));
		jMenuItems.add(new JMenuItem("Quitter"));
		jMenuItems.add(new JMenuItem("Modifier la couleur"));
		jMenuItems.add(new JMenuItem("Couleur aléatoire (uniforme)"));
		jMenuItems.add(new JMenuItem("Toutes couleurs aleatoires"));
		
		jMenus.get(0).add(jMenuItems.get(0));
		jMenus.get(0).add(jMenuItems.get(1));
		jMenus.get(0).add(jMenuItems.get(2));
		jMenus.get(0).add(jMenuItems.get(3));
		jMenus.get(2).add(jMenuItems.get(4));
		jMenus.get(2).add(jMenuItems.get(5));
		jMenus.get(2).add(jMenuItems.get(6));
		
		for(JMenu j : jMenus)
			menuBar.add(j);
		setJMenuBar(menuBar);
		
		// Bouton ouvrir
		jMenuItems.get(0).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileopen = null;
				try {
					fileopen = new JFileChooser(new File( "." ).getCanonicalPath());
				} catch (IOException e) {}
				FileFilter filter = new FileNameExtensionFilter(".gts", "gts");
				fileopen.addChoosableFileFilter(filter);

				int ret = fileopen.showDialog(null, "Open file");
				if (ret == JFileChooser.APPROVE_OPTION)
					updateModel(fileopen.getSelectedFile().getName());
			}
		});
		
		// boutton exporter
		jMenuItems.get(2).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exportImage();
			}
		});
		
		// Bouton quitter
		jMenuItems.get(3).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		// Bouton modif couleur
		jMenuItems.get(4).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alea = null;
				color = JColorChooser.showDialog(null, "Palette de couleur", null);
				paintComponent(getGraphics());
			}
		});
		
		// Bouton couleur aléatoire
		jMenuItems.get(5).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alea = null;
				color = randomColor();
				paintComponent(getGraphics());
			}
		});
		
		// Bouton couleurs aleatoires
		jMenuItems.get(6).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alea = new HashMap<Face, Color>();
				
				for(int i = 0; i < listeFaces.size(); i++)
					alea.put(listeFaces.get(i), randomColor());
				paintComponent(getGraphics());
			}
		});
	}

	protected void exportImage() {
		JFileChooser save = null;
		try {
			export = true;
			save = new JFileChooser(new File(".").getCanonicalPath());
			if(save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				BufferedImage bi = new BufferedImage(this.getWidth() - decalX, this.getHeight(), BufferedImage.TYPE_INT_RGB); 
				Graphics g = bi.createGraphics();
				paintComponent(g);
				g.dispose();

				ImageIO.write(bi, "png", new File(save.getSelectedFile().getAbsolutePath() + ".png"));
				export = false;
			}
		}
		catch (Exception e) {}	
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
	
	private Color randomColor(){
		Random r = new Random();
		
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
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
		
		String[] tmp = new String[files.size()];
		
		for(int i = 0; i < files.size(); i++)
			tmp[i] = files.get(i).getName();
		
		return tmp;
	}
	private void updateModel(String modele){
		double x=0,y=0,z=0;
		
		reader = new GtsReader(100, modele);
		listeFaces = reader.getListFaces();
		listePoints = reader.getListPoint();
		for(int i=0 ; listePoints.size()>i ; i++){
			x=+listePoints.get(i).getX();
			y=+listePoints.get(i).getY();
			z=+listePoints.get(i).getZ();
		}
		x=x/listePoints.size();
		y=y/listePoints.size();
		z=z/listePoints.size();
		Constantes.barycentre=new Point(x, y, z);
		alea = null;
		color = new Color(125, 125, 125);
		paintComponent(getGraphics());
	}

	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g;
		Graphics offgc;
		Image offscreen = null;
		
		if(export)
			decalX = decalY = 0;
		
		offscreen = createImage(this.getWidth() - decalX, this.getHeight() - decalY);
		if(offscreen != null){
			offgc = offscreen.getGraphics();
			
			// Dessin des faces
			double scal = 0.0; // Produit scalaire pour la lumiere
			
			offgc.drawImage(Constantes.wallpaper, 0, 0, this);
			for (int i = 0; i < listeFaces.size(); i++) {
				scal = Constantes.lumiere.prodScalaire(listeFaces.get(i).getNormal());
				scal = Math.abs(scal);
				
				if(alea == null)
					offgc.setColor((new Color((int)(color.getRed() * scal), (int)(color.getGreen() * scal), (int)(color.getBlue() * scal))));
				else
					offgc.setColor(alea.get(listeFaces.get(i)));
				offgc.fillPolygon(listeFaces.get(i).getTriangleX(), listeFaces.get(i).getTriangleY(), 3);
			}
			g2.drawImage(offscreen, decalX, decalY, this);
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