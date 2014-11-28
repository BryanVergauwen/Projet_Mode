package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionBDD {
	private Connection c = null;
	private Statement stmt = null;

	public void insert(String s1, String s2) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bdd/ressources.db");

			stmt = c.createStatement();
			String sql = "INSERT INTO RESSOURCES VALUES('" + s1 + "', '" + s2 + "')";
			stmt.executeUpdate(sql);
		} 
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		finally{
			try {
				stmt.close();
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	public void delete() {

	}
	public void select() {

	}
}