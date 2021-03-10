package com.mindfultrader.webapp.data;

// A class to extract the required data from the database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mindfultrader.webapp.services.CustomUserDetails;
 
public class TestDatabase {
	
	//private static List<String> names = new ArrayList<String>();
  
  public static List<String> getData() {
	  
	  List<String> names = new ArrayList<String>();
	  
    Connection connection = null;
    try {
 
  // Load the MySQL JDBC driver
 
  String driverName = "com.mysql.jdbc.Driver";
 
  Class.forName(driverName);
 
 
  // Create a connection to the database
 
  String serverName = "eu-cdbr-west-03.cleardb.net";
 
  String schema = "heroku_03b3862830df1d7";
 
  String url = "jdbc:mysql://" + serverName +  "/" + schema;
 
  String username = "b2374bc2da749a";
 
  String password = "5a7dbb13";
 
  connection = DriverManager.getConnection(url, username, password);
 
   
 
  	System.out.println("Successfully Connected to the database!");
 
   
    } catch (ClassNotFoundException e) {
 
    	System.out.println("Could not find the database driver " + e.getMessage());
    } catch (SQLException e) {
 
    	System.out.println("Could not connect to the database " + e.getMessage());
    }
    catch(Exception e){
    };
 
    try {
 
 
// Get a result set containing all data from test_table
 
Statement statement = connection.createStatement();

String user_id = CustomUserDetails.getUserId().toString();
System.out.println(user_id);

ResultSet results = statement.executeQuery("SELECT watchlistportfolio.Company_ID, companies.Company_Name\r\n"
		+ "FROM watchlistportfolio\r\n"
		+ "JOIN companies\r\n"
		+ "On watchlistportfolio.Company_ID = companies.Company_ID\r\n"
		+ "WHERE (watchlistportfolio.User_ID=" + "\"" + user_id + "\"" + "and watchlistportfolio.Type = \"p\")");
 

// For each row of the result set ...
 
while (results.next()) {
 
 
  // Get the data from the current row using the column index - column data are in the VARCHAR format
 
  String data = results.getString(1);
 
  System.out.println("Fetching data by column index for row " + results.getRow() + " : " + data);
 
 
  // Get the data from the current row using the column name - column data are in the VARCHAR format
 
  data = results.getString("Company_Name");
  
  names.add(data);
 
  System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
 
 
}
 
 
/*
 
  * Please note :
 
  * ResultSet API provides appropriate methods for retrieving data 
 
  * based on each column data type e.g.
 
  * 
 
  * boolean bool = rs.getBoolean("test_col");
 
  * byte b = rs.getByte("test_col");
 
  * short s = rs.getShort("test_col");
 
  * int i = rs.getInt("test_col");
 
  * long l = rs.getLong("test_col");
 
  * float f = rs.getFloat("test_col");
 
  * double d = rs.getDouble("test_col");
 
  * BigDecimal bd = rs.getBigDecimal("test_col");
 
  * String str = rs.getString("test_col");
 
  * Date date = rs.getDate("test_col");
 
  * Time t = rs.getTime("test_col");
 
  * Timestamp ts = rs.getTimestamp("test_col");
 
  * InputStream ais = rs.getAsciiStream("test_col");
 
  * InputStream bis = rs.getBinaryStream("test_col");
 
  * Blob blob = rs.getBlob("test_col");
 
  */
 
    } catch (SQLException e) {
 
    	System.out.println("Could not retrieve data from the database " + e.getMessage());
    }
    
    //System.out.println(names);
    return names;
    
  }

/*public static List<String> getNames() {
	main();
	return names;
}

public static void setNames(List<String> names) {
	TestDatabase.names = names;
}*/
}