import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class GtsReader {
	private List<Point> points;
	private List<Segment> segments;
	private List<Face> faces;

	public GtsReader(int coef) {
		points = new LinkedList<Point>();
		segments = new LinkedList<Segment>();
		faces = new LinkedList<Face>();

		try {
			InputStream ips = new FileInputStream("ressources/sphere.gts");
			BufferedReader br = new BufferedReader(new InputStreamReader(ips));
			String ligne = br.readLine();
			String result[] = ligne.split(" ");
			int nbPoint = Integer.parseInt(result[0]);
			int nbSegments = Integer.parseInt(result[1]);
			int nbFaces = Integer.parseInt(result[2]);
			int limite = 0;
			
			// Points
			while (ligne != null && limite < nbPoint) {
				if (ligne.charAt(0) != '#') {
					result = ligne.split(" ");
					double a = Double.parseDouble(result[0]) * coef;
					double b = Double.parseDouble(result[1]) * coef;
					double c = Double.parseDouble(result[2]) * coef;

					points.add(new Point((int) a, (int) b, (int) c));
					limite++;
				}
				ligne = br.readLine();
			}
			// Segments
			ligne = br.readLine();
			limite = 0;
			while (ligne != null && limite < nbSegments) {
				if (ligne.charAt(0) != '#') {
					result = ligne.split(" ");
					int d = Integer.parseInt(result[0]);
					int e = Integer.parseInt(result[1]);
					
					if(d == nbPoint)
						d = nbPoint - 1;
					if(e == nbPoint)
						e = nbPoint - 1;
					segments.add(new Segment(points.get(d), points.get(e)));
					limite++;
				}
				ligne = br.readLine();
			}
			// Faces
			ligne = br.readLine();
			limite = 0;
			while (ligne != null && limite < nbFaces) {
				if (ligne.charAt(0) != '#') {
					result = ligne.split(" ");
					int f = Integer.parseInt(result[0]);
					int g = Integer.parseInt(result[1]);
					int h = Integer.parseInt(result[2]);
					
					if(f == nbSegments)
						f = nbSegments - 1;
					if(g == nbSegments)
						g = nbSegments - 1;
					if(h == nbSegments)
						h = nbSegments - 1;
					
					faces.add(new Face(segments.get(f), segments.get(g), segments.get(h)));
					limite++;
				}
				ligne = br.readLine();
			}
			// Fermeture du reader
			br.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public List<Point> getListPoint() {
		return points;
	}

	public List<Segment> getListSegments() {
		return segments;
	}

	public List<Face> getListFaces() {
		return faces;
	}
}