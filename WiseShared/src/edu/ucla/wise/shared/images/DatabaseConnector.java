package edu.ucla.wise.shared.images;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {

	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/";
	private static final String USER = "root";
	private static final String PASSWORD = "password";

	static {
		try {
			Class.forName(DRIVER_NAME).newInstance();
			System.out.println("*** Driver loaded");
		} catch (Exception e) {
			System.out.println("*** Error : " + e.toString());
			System.out.println("*** ");
			System.out.println("*** Error : ");
			e.printStackTrace();
		}

	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static Connection getConnection(String databaseName)
			throws SQLException {
		return DriverManager.getConnection(URL + databaseName, USER, PASSWORD);
	}

	public static InputStream getImageFromDatabase(String imageName) {
		Connection conn = null;
		PreparedStatement pstmnt = null;
		InputStream is = null;

		try {
			conn = getConnection("wise_shared");
			String querySQL = "SELECT filecontents FROM images WHERE filename = '"
					+ imageName + "'";
			pstmnt = conn.prepareStatement(querySQL);
			ResultSet rs = pstmnt.executeQuery();

			while (rs.next()) {
				is = rs.getBinaryStream(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error while retrieving file from database");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return is;
	}

	public static List<String> getNamesOfImagesInDatabase() {

		List<String> listOfImageNames = new ArrayList<String>();
		
		Connection conn = null;
		PreparedStatement pstmnt = null;
		
		try{
			conn = getConnection("wise_shared");
			String querySQL = "SELECT filename FROM images";
			pstmnt = conn.prepareStatement(querySQL);
			ResultSet rs = pstmnt.executeQuery();
			
			while(rs.next()){
				listOfImageNames.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listOfImageNames;
	}

}
