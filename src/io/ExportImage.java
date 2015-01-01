package io;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class ExportImage {
	public ExportImage(Fenetre f, int decalX, int decalY) {
		JFileChooser save = new JFileChooser(new File(".").getAbsolutePath());
		if(save.showSaveDialog(f) == JFileChooser.APPROVE_OPTION) {
			BufferedImage bi = new BufferedImage(f.getWidth() - decalX, f.getHeight(), BufferedImage.TYPE_INT_ARGB); 
			Graphics tmp = bi.createGraphics();
			f.paintComponent(tmp);
			tmp.dispose();

			try {
				ImageIO.write(bi, "png", new File(save.getSelectedFile().getAbsolutePath() + ".png"));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}