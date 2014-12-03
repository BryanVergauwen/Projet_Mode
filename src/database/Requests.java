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
			c = DriverManager.getConnection("jdbc:sqlite:bdd/ressources.db");
			stmt = c.createStatement();
			String sql = "INSERT OR IGNORE INTO GTSFILES VALUES('" + s1 + "', '" + s2 + "')";
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

	public List<String> select(String champ) {
		List<String> tmp = new LinkedList<String>();
		String champTmp = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bdd/ressources.db");
			stmt = c.createStatement();
			if(!champ.equals("*"))
				rs = stmt.executeQuery("SELECT " + champ + " FROM GTSFILES ORDER BY " + champ);
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
	
	public String selectWhere(String condition) {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bdd/ressources.db");
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT path FROM GTSFILES WHERE " + condition);
			return rs.getString(1);
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
			c = DriverManager.getConnection("jdbc:sqlite:bdd/ressources.db");
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
			c = DriverManager.getConnection("jdbc:sqlite:bdd/ressources.db");
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
			c = DriverManager.getConnection("jdbc:sqlite:bdd/ressources.db");
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
}