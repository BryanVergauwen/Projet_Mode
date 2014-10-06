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
		try{
			InputStream ips=new FileInputStream("ressources/test.gts"); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne=br.readLine();
			String result[] = ligne.split(" ");
			int nbPoint = Integer.parseInt(result[0]);
			int i=0;
			while ((ligne=br.readLine())!=null || nbPoint>i){
				if(ligne.charAt(0)!='#') {
					result = ligne.split(" ");
					list.add(new Point(Integer.parseInt(result[0]), Integer.parseInt(result[1]), Integer.parseInt(result[2])));
					i++;
				}
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public List<Point> getListPoint() {
		return list;
	}
}
