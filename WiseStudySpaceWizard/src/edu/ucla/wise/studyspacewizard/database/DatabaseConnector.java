package edu.ucla.wise.studyspacewizard.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.ucla.wise.studyspacewizard.StudySpaceCreatorConstants;

public class DatabaseConnector {

	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

	static
	{
		try
		{
			Class.forName(DRIVER_NAME).newInstance();
			System.out.println("*** Driver loaded");
		}
		catch(Exception e)
		{
			System.out.println("*** Error : "+e.toString());
			System.out.println("*** ");
			System.out.println("*** Error : ");
			e.printStackTrace();
		}

	}

	private static final String URL = "jdbc:mysql://localhost/";
	private static final String USER = "root";
	private static final String PASSWORD = "password";

	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection( URL, USER, PASSWORD);
	}

	public static Connection getConnection(String databaseName) throws SQLException
	{
		return DriverManager.getConnection(URL + databaseName, USER, PASSWORD);
	}


	public static boolean executeSqlScript(String sqlScriptPath, String databaseName){

		ArrayList<String> sqlStatementList = SqlScriptExecutor.createQueries(sqlScriptPath);

		for(String sqlStatement : sqlStatementList){
			if(executeSqlStatement(sqlStatement, databaseName)){
				System.out.println("Executed: " + sqlStatement);
			}
			else{
				System.out.println("Could not execute: " + sqlStatement);
				return false;
			}
		}
		return true;
	}

	public static boolean executeSqlStatement(String statement, String databaseName){

		try {
			Connection connection = getConnection(databaseName);
			Statement stmt = connection.createStatement();

			stmt.executeUpdate(statement);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean createDatabase(String databaseName){
		try{
			Connection connection = getConnection();
			Statement stmt = connection.createStatement();

			stmt.executeUpdate("CREATE DATABASE "+databaseName);
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean writeStudySpaceParams(String studySpaceName, String serverURL, String serverAppName, String serverSharedLinkName, 
			String directoryName, String dbUsername, String dbName, String dbPassword, String projectTitle, String databaseEncryptionKey){

		try{
			Connection connection = getConnection(StudySpaceCreatorConstants.COMMON_DATABASE_NAME);
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO "+StudySpaceCreatorConstants.STUDY_SPACE_METADATA_TABLE_NAME+"(server_url, serverApp, sharedFiles_linkName,dirName, dbuser, dbpass, dbname, proj_title, db_crypt_key) values (?,?,?,?,?,?,?,?,?)");
			
			stmt.setString(1, serverURL);
			stmt.setString(2, serverAppName);
			stmt.setString(3, serverSharedLinkName);
			stmt.setString(4, directoryName);
			stmt.setString(5, dbUsername);
			stmt.setString(6, dbPassword);
			stmt.setString(7, dbName);
			stmt.setString(8, projectTitle);
			stmt.setString(9, databaseEncryptionKey);
			
			if(stmt.executeUpdate()==1){
				return true;
			}
			else return false;
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
	}


}
