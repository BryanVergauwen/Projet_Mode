package io;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class ExportFile {
	public ExportFile(Fenetre f, int decalX, int decalY) {
		JFileChooser save = null;
		try {
			save = new JFileChooser(new File(".").getCanonicalPath());
			if(save.showSaveDialog(f) == JFileChooser.APPROVE_OPTION) {
				BufferedImage bi = new BufferedImage(f.getWidth() - decalX, f.getHeight(), BufferedImage.TYPE_INT_RGB); 
				Graphics tmp = bi.createGraphics();
				f.paintComponent(tmp);
				tmp.dispose();

				ImageIO.write(bi, "png", new File(save.getSelectedFile().getAbsolutePath() + ".png"));
			}
		}
		catch (Exception e) {}
	}
}
