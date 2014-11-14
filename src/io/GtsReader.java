package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

import objects.Face;
import objects.Point;
import objects.Segment;

public class GtsReader extends Reader{
	public GtsReader(int coef, String file) {
		try {
			InputStream ips = new FileInputStream("ressources/gts/" + file);
			BufferedReader br = new BufferedReader(new InputStreamReader(ips));
			String ligne = br.readLine();
			String result[] = ligne.split(" ");
			nbPoint = Integer.parseInt(result[0]);
			nbSegments = Integer.parseInt(result[1]);
			nbFaces = Integer.parseInt(result[2]);
			int limite = 0;
			ligne = br.readLine();

			// Points
			while (ligne != null && limite < nbPoint) {
				if (ligne.charAt(0) != '#') {
					result = ligne.split(" ");
					double a = Double.parseDouble(result[0]) * coef;
					double b = Double.parseDouble(result[1]) * coef;
					double c = Double.parseDouble(result[2]) * coef;

					points.add(new Point(a, b, c));
					limite++;
				}
				ligne = br.readLine();
			}
			
			// Segments
			limite = 0;
			while (ligne != null && limite < nbSegments) {
				if (ligne.charAt(0) != '#') {
					result = ligne.split(" ");
					int d = Integer.parseInt(result[0]) - 1;
					int e = Integer.parseInt(result[1]) - 1;

					segments.add(new Segment(points.get(d), points.get(e)));
					limite++;
				}
				ligne = br.readLine();
			}
			
			// Faces
			limite = 0;
			while (ligne != null && limite < nbFaces) {
				if (ligne.charAt(0) != '#') {
					result = ligne.split(" ");
					int f = Integer.parseInt(result[0]) - 1;
					int g = Integer.parseInt(result[1]) - 1;
					int h = Integer.parseInt(result[2]) - 1;

					faces.add(new Face(segments.get(f), segments.get(g), segments.get(h)));
					limite++;
				}
				ligne = br.readLine();
			}
			Collections.sort(faces);

			// Fermeture du reader
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String toString(){
		return "GTSReader";
	}
}