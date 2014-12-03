package io;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import objects.Face;
import objects.Point;
import transformations.Homothetie;
import transformations.Rotation;
import transformations.Translation;
import data.Data;
import database.GestionBDD;

public class Fenetre extends JFrame implements KeyListener, MouseWheelListener, MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private Point current;
	private int cptMouse = 0, decalX, decalY;
	private GtsReader reader;
	private List<Face> listeFaces;
	private List<Point> listePoints;
	private JPanel modeles;
	private JFrame chargement;
	private DefaultListModel<String> dl;
	private JList<String> listeModeles;
	private Color color = new Color(125, 125, 125);
	private Map<Face, Color> alea;
	private boolean export = false, fullScreen = false;
	private GestionBDD g = new GestionBDD();
	private String modele = null;
	
	public Fenetre() {
		super(Data.TITLE);
		long debut = System.currentTimeMillis();
		
		initFrameChargement();
		initBDD();
		initJMenuBar();
		initFrame();
		paramListeModeles();
		modifFrame();
		addListeners();
		setUI();
		validate();
		paintComponent(getGraphics());
		long fin = System.currentTimeMillis();
		if(fin - debut < 5000){
			try {
				Thread.sleep(5000 - (fin - debut));
			} 
			catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		chargement.dispose();
		setVisible(true);
	}

	private void setUI() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (InstantiationException e) {
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
		catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void initFrameChargement() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		chargement = new JFrame();
		chargement.setLocation(dim.width/2 - chargement.getSize().width/2 - (Data.CHARGEMENT.getIconWidth()/2), dim.height/2 - chargement.getSize().height/2 - (Data.CHARGEMENT.getIconHeight()/2));
		chargement.getContentPane().add(new JLabel(Data.CHARGEMENT));
		chargement.setUndecorated(true);
		chargement.pack();
		chargement.setVisible(true);		
	}

	private void modifFrame() {
		modeles = new JPanel();
		modeles.setLayout(new BoxLayout(modeles, BoxLayout.Y_AXIS));
		modeles.setBackground(Color.white);
		modeles.add(new JScrollPane(listeModeles));
		
		getContentPane().add(modeles, BorderLayout.WEST);
	}

	private void paramListeModeles() {
		List<String> tmp = g.select("nom");
		dl = new DefaultListModel<String>();
		for(int i = 0; i < g.getNbLignes(); i++)
			dl.add(i, tmp.get(i));
		
		listeModeles = new JList<String>(dl);
	    listeModeles.setBackground(Color.WHITE);
	    listeModeles.setSelectionForeground(Color.RED);
	    listeModeles.setSelectionBackground(Color.LIGHT_GRAY);
	    listeModeles.setFont(new Font("Serif", Font.BOLD, 15));
		listeModeles.setFocusable(false);
		listeModeles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void initFrame() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		setContentPane(new JLabel(Data.WALLP_TMP));
		setLayout(new BorderLayout());
		setSize(dim.width, dim.height - 50);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(Data.ICON3D);
	}

	private void addListeners() {
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		setDropTarget(new DropTarget(this, new DropTargetAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void drop(DropTargetDropEvent e) {
				e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				for (DataFlavor flavor : e.getCurrentDataFlavors()) {
					try {
						List<File> files = (List<File>) e.getTransferable().getTransferData(flavor);
						File f = files.get(0);
						if(f.getAbsolutePath().substring(f.getAbsolutePath().length() - 4, f.getAbsolutePath().length()).equalsIgnoreCase(".gts")){
							g.insert(f.getName().toLowerCase(), f.getAbsolutePath());
							modele = g.selectWhere("nom = '" + f.getName().toLowerCase() + "'");
							updateModel();
						}
					} 
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}));
		listeModeles.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				modele = g.selectWhere("nom = '" + listeModeles.getSelectedValue().toString() + "'");
				updateModel();
			}
		});
	}

	private void initBDD() {
		File[] f = new File("ressources/gts").listFiles();
		List<File> files = new LinkedList<File>();
		int lengthFile;
		
		for(int i = 0; i < f.length; i++){
			lengthFile = f[i].getAbsolutePath().length();
			if(f[i].getAbsolutePath().substring(lengthFile-4, lengthFile).equalsIgnoreCase(".gts"))
				files.add(f[i]);
		}
		
		// Mise a jour des fichiers dans le dossier .gts
		for(int i = 0; i < files.size(); i++)
			g.insert(files.get(i).getName().toLowerCase(), files.get(i).getAbsolutePath());
		
		// Suppression dans la base des fichiers supprimes de l'ordinateur
		List<String> tmp = g.select("path");
		File file;
		for(String path : tmp){
			file = new File(path);
			if(!file.exists())
				g.delete("path = '" + path + "'");
		}
	}

	private void initJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		List<JMenu> jMenus;
		List<JMenuItem> jMenuItems;
		
		jMenuItems = new LinkedList<JMenuItem>();
		jMenus = new LinkedList<JMenu>();

		jMenus.add(new JMenu("Fichier"));
		jMenus.add(new JMenu("Edition"));
		jMenus.add(new JMenu("Affichage"));
		jMenus.add(new JMenu("Outils"));
		jMenus.add(new JMenu("Aide"));
		
		jMenuItems.add(new JMenuItem("Nouvelle fenetre"));
		jMenuItems.add(new JMenuItem("Ouvrir"));
		jMenuItems.add(new JMenuItem("Enregistrer"));
		jMenuItems.add(new JMenuItem("Exporter"));
		jMenuItems.add(new JMenuItem("Quitter"));
		jMenuItems.add(new JMenuItem("Plein ecran"));
		jMenuItems.add(new JMenuItem("Modifier la couleur"));
		jMenuItems.add(new JMenuItem("Couleur aleatoire (uniforme)"));
		jMenuItems.add(new JMenuItem("Toutes couleurs aleatoires"));
		
		jMenus.get(0).add(jMenuItems.get(0));
		jMenus.get(0).add(jMenuItems.get(1));
		jMenus.get(0).add(jMenuItems.get(2));
		jMenus.get(0).add(jMenuItems.get(3));
		jMenus.get(0).add(jMenuItems.get(4));
		jMenus.get(2).add(jMenuItems.get(5));
		jMenus.get(3).add(jMenuItems.get(6));
		jMenus.get(3).add(jMenuItems.get(7));
		jMenus.get(3).add(jMenuItems.get(8));
		
		for(JMenu j : jMenus)
			menuBar.add(j);
		setJMenuBar(menuBar);
		
		// Bouton nouvelle fenetre
		jMenuItems.get(0).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Fenetre();
			}
		});
		
		// Bouton ouvrir
		jMenuItems.get(1).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileopen = null;
				try {
					fileopen = new JFileChooser(new File( "." ).getCanonicalPath());
				} catch (IOException e) {}
				FileFilter filter = new FileNameExtensionFilter(".gts", "gts");
				fileopen.addChoosableFileFilter(filter);

				int ret = fileopen.showDialog(null, "Open file");
				File f = fileopen.getSelectedFile();
				if (ret == JFileChooser.APPROVE_OPTION && f.getAbsolutePath().substring(f.getAbsolutePath().length() - 4, f.getAbsolutePath().length()).equalsIgnoreCase(".gts")){
					g.insert(fileopen.getSelectedFile().getName().toLowerCase(), fileopen.getSelectedFile().getAbsolutePath());
					modele = g.selectWhere("nom = '" + fileopen.getSelectedFile().getName().toLowerCase() + "'");
					updateModel();
				}
			}
		});
		
		// boutton exporter
		jMenuItems.get(3).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exportImage();
			}
		});
		
		// Bouton quitter
		jMenuItems.get(4).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose(); // Dans le cas ou plusieurs fenetres sont ouvertes, une seule est fermee
			}
		});
		
		// Bouton full Screen
		jMenuItems.get(5).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!fullScreen){
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					fullScreen = true;
				}
				else{
					setExtendedState(JFrame.NORMAL);
					fullScreen = false;
				}
			}
		});
		
		
		// Bouton modif couleur
		jMenuItems.get(6).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alea = null;
				color = JColorChooser.showDialog(null, "Palette de couleur", null);
				paintComponent(getGraphics());
			}
		});
		
		// Bouton couleur aleatoire
		jMenuItems.get(7).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alea = null;
				color = randomColor();
				paintComponent(getGraphics());
			}
		});
		
		// Bouton couleurs aleatoires
		jMenuItems.get(8).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alea = new HashMap<Face, Color>();
				
				for(int i = 0; i < listeFaces.size(); i++)
					alea.put(listeFaces.get(i), randomColor());
				paintComponent(getGraphics());
			}
		});
	}

	private void exportImage() {
		JFileChooser save = null;
		try {
			export = true;
			save = new JFileChooser(new File(".").getCanonicalPath());
			if(save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				BufferedImage bi = new BufferedImage(this.getWidth() - decalX, this.getHeight(), BufferedImage.TYPE_INT_RGB); 
				Graphics tmp = bi.createGraphics();
				paintComponent(tmp);
				tmp.dispose();

				ImageIO.write(bi, "png", new File(save.getSelectedFile().getAbsolutePath() + ".png"));
			}
			export = false;
		}
		catch (Exception e) {}
	}

	private Color randomColor(){
		Random r = new Random();
		
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}

	private void updateModel(){
		String[] tmp = modele.split("\\\\");
		setTitle(Data.TITLE + " - " + tmp[tmp.length-1].toLowerCase());
		reader = new GtsReader(100, modele);
		listeFaces = reader.getListFaces();
		listePoints = reader.getListPoint();
		alea = null;
		color = new Color(125, 125, 125);
		Data.alphaX = Data.alphaY = 0; // recentrage de la figure
		paintComponent(getGraphics());
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Graphics offgc;
		Image offscreen = null;
		
		decalX = 197;
		decalY = 52;
		if(export)
			decalX = decalY = 0;
		
		offscreen = createImage(this.getWidth() - decalX, this.getHeight() - decalY);
		if(offscreen != null){
			offgc = offscreen.getGraphics();
			
			// Dessin des faces
			double scal = 0.0; // Produit scalaire pour la lumiere
			
			offgc.drawImage(Data.WALLPAPER, 0, 0, this);
			if(modele != null){
				for (int i = 0; i < listeFaces.size(); i++) {
					scal = Data.LUMIERE.prodScalaire(listeFaces.get(i).getNormal());
					scal = Math.abs(scal);
					
					if(alea == null)
						offgc.setColor((new Color((int)(color.getRed() * scal), (int)(color.getGreen() * scal), (int)(color.getBlue() * scal))));
					else{
						Color tmp = alea.get(listeFaces.get(i));
						offgc.setColor(new Color((int)(tmp.getRed() * scal), (int)(tmp.getGreen() * scal), (int)(tmp.getBlue() * scal)));
					}
					offgc.fillPolygon(listeFaces.get(i).getTriangleX(), listeFaces.get(i).getTriangleY(), 3);
				}
			}
			g2.drawImage(offscreen, decalX, decalY, this);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double zoom;
		
		if(modele != null){
			if (e.getWheelRotation() < 0)
				zoom = 1.1;
			else
				zoom = 0.9;
			new Homothetie(listePoints, zoom);
			paintComponent(getGraphics());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(modele != null){
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
					new Translation(listePoints, -5, "Y");
				if (p.getY() > current.getY())
					new Translation(listePoints, 5, "Y");
				if (p.getX() > current.getX())
					new Translation(listePoints, 5, "X");
				if (p.getX() < current.getX())
					new Translation(listePoints, -5, "X");
				current = p;
				paintComponent(getGraphics());
			}
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
		if(modele != null){
			if (current == null)
				current = new Point(e.getX(), e.getY(), 0);
			else {
				current.setX(e.getX());
				current.setY(e.getY());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(modele != null){
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

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(modele != null){
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
}