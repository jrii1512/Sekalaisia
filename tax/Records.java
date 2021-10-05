package VeroPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Records {

	private String table;
	private Connection conn2;
	private ResultSet rs;
	private String data;

	public Records() {
		this.rs = null;
		this.data = null;
	}
	
	  static void infoBox(String infoMessage, String titleBar)
	    {
		  JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }

	public int Count(String table, Connection conn2) {
		int numberOfRows = 0;
		String query = "SELECT COUNT(*) FROM " + table;
		// create the java statement
		try {

			PreparedStatement st = conn2.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				numberOfRows = rs.getInt("count(*)");
			}
			System.out.println("There are " + numberOfRows + " records in " + table);
			return numberOfRows;
		} catch (Exception e) {
			System.out.println("Check XAMPP mysql module");
			e.printStackTrace();
		}
		return 0;
	}

	public void DropTable(String t, Connection conn2) throws SQLException {

		try {
			System.out.println("Table value is " + t);
			String delSql = "DROP TABLE " + t;
			System.out.println(delSql);
			Statement delDB = conn2.createStatement();
			delDB.execute(delSql);
		} catch (Exception e) {
			System.out.println("Cannot destroy table, check mysql module");
			e.printStackTrace();
		}

	}

	public void UpdateRecords(String mTable, Connection conn, int id, String hankinta, String liike, double hinta,
			java.sql.Date hPaiva, String oma) throws SQLException, ParseException {

		// System.out.println(hankinta + "\n" + liike + "\n" + hinta + "\n" + hPaiva +
		// "\n" + oma + "\n");

		Statement stm = conn.createStatement();
		String query = "UPDATE hankinnat SET " + "Hankinta = " + "'" + hankinta + "'" + ", Hinta = " + "'" + hinta + "'"
				+ ", Liike = " + "'" + liike + "'" + ", PVM = " + "'" + hPaiva + "'" + " where ID = " + id;
		System.out.println(query);
		stm.executeUpdate(query);

	}

	// Insert record
	public void CreateRecord(String table, Connection conn, java.sql.Date sqlDate, String laji, String hankinta, String liike, double hinta,
			double alv, double myyntiVero, double tilitys,  String oma) throws SQLException {
		try {

			Calendar calendar = Calendar.getInstance();
			// java.util.Date startDate = new java.util.Date(calendar.getTime().getTime());
			String query = " insert into " + table + " (PVM, Laji, Hankinta, Liike, Hinta, ALV, MyyntiVero, Tilitys, Oma)"
					+ " VALUES (?, ?, ?, ?, ?, ?,?, ?, ?)";
			PreparedStatement preparedStmt = conn2.prepareStatement(query);
			preparedStmt.setDate(1, sqlDate);
			preparedStmt.setString(2, laji);
			preparedStmt.setString(3, hankinta);
			preparedStmt.setString(4, liike);
			preparedStmt.setDouble(5, hinta);
			preparedStmt.setDouble(6, alv);
			preparedStmt.setDouble(7, myyntiVero);
			preparedStmt.setDouble(8, tilitys);
			preparedStmt.setString(9, oma);

			preparedStmt.execute();
			System.out.println("Record inserted");
			Verotus v = new Verotus();
			v.infoBox("Tiedosto luotu", "Tiedoston lisäys");
			VeroTsekkaus vT = new VeroTsekkaus();
			vT.ReadRecords(table, conn);
			vT.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//Matkakulut
	//CreateTravel(mTable, conn, a, e, matka, syy, km, vahennys, kirjattu);
	public void CreateTravel(String table, Connection conn2, java.sql.Date aDate, java.sql.Date eDate, String matka, String syy, int km, double vahennys, String oma) throws SQLException {
		try {
			
			Calendar calendar = Calendar.getInstance();
			// java.util.Date startDate = new java.util.Date(calendar.getTime().getTime());
			String query = " insert into " + table + " (Matka, Alku, Loppu, Syy, Pituus, Vähennys, Kirjattu)"
					+ " VALUES (?, ?, ?, ?, ?, ?,?)";
			PreparedStatement preparedStmt = conn2.prepareStatement(query);
			preparedStmt.setString(1, matka);
			preparedStmt.setDate(2, aDate);
			preparedStmt.setDate(3, eDate);
			preparedStmt.setString(4, syy);
			preparedStmt.setInt(5, km);			
			preparedStmt.setDouble(6, vahennys);			
			preparedStmt.setString(7, oma);

			preparedStmt.execute();
			System.out.println("Record inserted");			
			
			Matkakulut mT = new Matkakulut();
			infoBox("Tiedosto luotu", "Tiedoston lisäys");
			mT.ReadTravel(table, conn2);
			//mT.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// Luetaan recordit, connection vielä auki joten ei tarvinne luoda uutta.
	public ResultSet ReadRecords(String table, Connection conn2) {
		String query = "SELECT * FROM " + table;
		// create the java statement
		try {
			Statement st = conn2.createStatement();
			// execute the query, and get a java resultset
			rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next()) {
				int id = rs.getInt("ID");
				String hPaiva = rs.getString("PVM");
				String laji = rs.getString("Laji");
				String hankinta = rs.getString("Hankinta");
				String liike = rs.getString("Liike");
				double summa = rs.getDouble("Hinta");
				double alv = rs.getDouble("ALV");		
				double myyntiVero = rs.getDouble("MyyntiVero");
				double tilitys = rs.getDouble("Tilitys");
				String omaVero = rs.getString("Oma");

			}

			return rs;

		} catch (SQLException e) {
			System.out.println("Check XAMPP mysql module");
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet GetResultSet() {
		return this.rs;
	}

	public void DeleteRecords(String table, Connection conn2, int row) {

		String query = "DELETE FROM " + table + " Where ID = ?";
		try {
			PreparedStatement preparedStmt = conn2.prepareStatement(query);
			preparedStmt.setInt(1, row);
			preparedStmt.execute();
		} catch (SQLException e) {
			System.out.println("Check XAMPP mysql module");
			e.printStackTrace();
		}

	}
}
