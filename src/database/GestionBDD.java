package database;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class GestionBDD {
	public GestionBDD(Requests r){
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
			r.insert(files.get(i).getName().toLowerCase(), files.get(i).getAbsolutePath());
		
		// Suppression dans la base des fichiers supprimes de l'ordinateur
		List<String> tmp = r.select("path");
		File file;
		for(String path : tmp){
			file = new File(path);
			if(!file.exists())
				r.delete("path = '" + path + "'");
		}
	}
}
