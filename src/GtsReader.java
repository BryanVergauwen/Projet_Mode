import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class GtsReader {
	private List<Point> list;

	public GtsReader() {
		list = new LinkedList<Point>();
		try {
			InputStream ips = new FileInputStream("ressources/cube.gts");
			BufferedReader br = new BufferedReader(new InputStreamReader(ips));
			String ligne = br.readLine();
			String result[] = ligne.split(" ");
			int nbPoint = Integer.parseInt(result[0]);
			int i = 0;
			
			while (ligne != null && i < nbPoint) {
				if (ligne.charAt(0) != '#') {
					result = ligne.split(" ");
					int a = Integer.parseInt(result[0]);
					int b = Integer.parseInt(result[1]);
					int c = Integer.parseInt(result[2]);

					list.add(new Point(a, b, c));
					i++;
				}
				ligne = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	public List<Point> getListPoint() {
		return list;
	}
}