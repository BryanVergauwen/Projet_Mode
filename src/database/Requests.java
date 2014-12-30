package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Requests {
	private Connection c = null;

	public void insert(String s1, String s2) {
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			String sql = "INSERT OR IGNORE INTO GTSFILES VALUES('" + s1 + "', '" + s2 + "', '')";
			stmt.executeUpdate(sql);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} 
		finally {
			try {
				stmt.close();
				c.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addTag(String nomFigure, String tag){
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			String old = "";
			
			if(tag != null && tag.length() > 0){
				List<String> s = getTags(nomFigure);
				
				for(String tmp : s)
					old += tmp + "/";
				stmt.executeUpdate("UPDATE GTSFILES SET TAGS = '" + old + tag + "/' WHERE NOM = '" + nomFigure + "'");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} 
		finally {
			try {
				stmt.close();
				c.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<String> getTags(String nomFigure) {
		List<String> tmp = new LinkedList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT tags FROM GTSFILES WHERE nom = '" + nomFigure + "'");
				
			String current[] = rs.getString("tags").split("/");
			for(String s : current)
				tmp.add(s);
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} 
		finally {
			try {
				rs.close();
				stmt.close();
				c.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}
	
	public List<String> getModeles(String tag){
		List<String> tmp = new LinkedList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT NOM FROM GTSFILES WHERE TAGS LIKE '%" + tag + "/%'");
			while (rs.next())
				tmp.add(rs.getString("nom"));
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} 
		finally {
			try {
				rs.close();
				stmt.close();
				c.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}

	public List<String> select(String champ, boolean OrderBy) {
		List<String> tmp = new LinkedList<String>();
		String champTmp = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			if(OrderBy){
				if(!champ.equals("*"))
					rs = stmt.executeQuery("SELECT " + champ + " FROM GTSFILES ORDER BY " + champ);
			}
			else
				rs = stmt.executeQuery("SELECT " + champ + " FROM GTSFILES");
			while (rs.next()) {
				if(!champ.equals("*"))
					champTmp = rs.getString(champ);
				else
					champTmp = rs.getString("nom") + " " + rs.getString("path");
				tmp.add(champTmp);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} 
		finally {
			try {
				rs.close();
				stmt.close();
				c.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}
	
	public String selectWhere(String champ, String condition) {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT " + champ + " FROM GTSFILES WHERE " + condition);
			return rs.getString(champ);
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} 
		finally {
			try {
				rs.close();
				stmt.close();
				c.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void delete(String condition) {
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			String sql = "DELETE from GTSFILES where " + condition;
			stmt.executeUpdate(sql);
		} 
		catch (Exception e) {
			try {
				stmt.close();
				c.close();
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void resetTable() {
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			String sql = "delete from gtsfiles";
			stmt.executeUpdate(sql);
		} 
		catch (Exception e) {
			try {
				stmt.close();
				c.close();
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			System.exit(0);
		}
	}

	public int getNbLignes() {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT count(*) FROM GTSFILES");
			return rs.getInt(1);
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} 
		finally {
			try {
				rs.close();
				stmt.close();
				c.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public List<String> selectLike(String filtre) {
		List<String> tmp = new LinkedList<String>();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM GTSFILES where nom LIKE '" + filtre + "%' ORDER BY nom");
			while (rs.next())
				tmp.add(rs.getString("nom"));
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} 
		finally {
			try {
				rs.close();
				stmt.close();
				c.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}

	public void deleteTag(String modele, String tag) {
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ressources/bdd/ressources.db");
			stmt = c.createStatement();
			List<String> old = getTags(modele);
			String tags = "";
			
			for(String s : old){
				if(!s.equals(tag))
					tags += s + "/";
			}
			
			stmt.executeUpdate("UPDATE GTSFILES SET TAGS = '" + tags + "' WHERE NOM = '" + modele + "'");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} 
		finally {
			try {
				stmt.close();
				c.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}