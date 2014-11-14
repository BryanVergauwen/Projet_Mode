package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import objects.Face;
import objects.Point;
import objects.Segment;

public class ObjReader extends Reader{
	public ObjReader(int coef, String file) {
		try {
			InputStream ips = new FileInputStream("ressources/obj/" + file);
			BufferedReader br = new BufferedReader(new InputStreamReader(ips));
			String ligne = br.readLine();
			String result[] = ligne.split(" ");
			ligne = br.readLine();

			// Points
			while (true) {
				if(ligne.length() == 0 || (ligne.length() > 2 && (ligne.substring(0, 2).equals("vt") || ligne.charAt(0) == 'f')))
					break;
				if (ligne.charAt(0) == 'v') {
					result = ligne.split(" ");
					double a = Double.parseDouble(result[1]) * coef;
					double b = Double.parseDouble(result[2]) * coef;
					double c = Double.parseDouble(result[3]) * coef;

					points.add(new Point(a, b, c));
				}
				ligne = br.readLine();
			}
			
			// Segments
			while (true) {
				if(!br.ready())
					break;
				if (ligne.length() > 0 && ligne.charAt(0) == 'f') {
					result = ligne.split(" ");
					String[] tmp = new String[3];
					
					tmp[0] = result[1];
					tmp[1] = result[2];
					tmp[2] = result[3];
					
					int f = Integer.parseInt(tmp[0].charAt(0)+"") - 1;
					int g = Integer.parseInt(tmp[1].charAt(0)+"") - 1;
					int h = Integer.parseInt(tmp[2].charAt(0)+"") - 1;
					
					segments.add(new Segment(points.get(f), points.get(g)));
					segments.add(new Segment(points.get(g), points.get(h)));
					segments.add(new Segment(points.get(h), points.get(f)));
				}
				ligne = br.readLine();
			}
			
			// Faces
			for(int i = 0; i < segments.size(); i+=3)
				faces.add(new Face(segments.get(i), segments.get(i+1), segments.get(i+2)));
			
			// Fermeture du reader
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String toString(){
		return "OBJReader";
	}
}