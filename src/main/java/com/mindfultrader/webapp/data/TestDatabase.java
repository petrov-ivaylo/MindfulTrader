package com.mindfultrader.webapp.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mindfultrader.webapp.services.CustomUserDetails;
 
public class TestDatabase {
	
	
	
	@Bean
	public static BasicDataSource getDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://eu-cdbr-west-03.cleardb.net:3306/heroku_03b3862830df1d7");
		ds.setUsername("b2374bc2da749a");
		ds.setPassword("5a7dbb13");
		ds.setMaxTotal(10);
		ds.setInitialSize(10);
		ds.setTestOnBorrow(true);
		ds.setValidationQuery("SELECT 1");
		ds.setDefaultAutoCommit(true);
		return ds;
	}
	
	
	
	
	//@Autowired
	//private static DataSource dataSource;	
	  
    /*static {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://eu-cdbr-west-03.cleardb.net:3306/heroku_03b3862830df1d7");
        dataSource.setUsername("b2374bc2da749a");
        dataSource.setPassword("5a7dbb13");
 
        dataSource.setMinIdle(1);
        dataSource.setMaxIdle(7);
        dataSource.setMaxTotal(9);
 
    }*/
  
  public static List<String> getData() throws SQLException {
	  
  List<String> names = new ArrayList<String>();
  
  ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
	 
  DataSource ds = ctx.getBean(DataSource.class);
  JdbcTemplate jt = new JdbcTemplate(ds);
  String user_id = CustomUserDetails.getUserId().toString();
  String sql = "SELECT companies.Company_Name\r\n"
  		+ "FROM watchlistportfolio\r\n"
  		+ "JOIN companies\r\n"
  		+ "On watchlistportfolio.Company_ID = companies.Company_ID\r\n"
  		+ "WHERE (watchlistportfolio.User_ID=" + "\"" + user_id + "\"" + "and watchlistportfolio.Type = \"p\")";
  names = jt.queryForList(sql,String.class);
  System.out.println(names);

  
  
    /*Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    //try {
 
    	String user_id = CustomUserDetails.getUserId().toString();

    	connection = dataSource.getConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT watchlistportfolio.Company_ID, companies.Company_Name\r\n"
        		+ "FROM watchlistportfolio\r\n"
        		+ "JOIN companies\r\n"
        		+ "On watchlistportfolio.Company_ID = companies.Company_ID\r\n"
        		+ "WHERE (watchlistportfolio.User_ID=" + "\"" + user_id + "\"" + "and watchlistportfolio.Type = \"p\")");	
 
  	
        while (resultSet.next()) {
        	 
        	 
        	  // Get the data from the current row using the column index - column data are in the VARCHAR format
        	 
        	  String data = resultSet.getString(1);
        	 
        	  //System.out.println("Fetching data by column index for row " + resultSet.getRow() + " : " + data);
        	 
        	 
        	  // Get the data from the current row using the column name - column data are in the VARCHAR format
        	 
        	  data = resultSet.getString("Company_Name");
        	  
        	  names.add(data);
        	 
        	  //System.out.println("Fetching data by column name for row " + resultSet.getRow() + " : " + data);
        	 
        	}
        
   // }
    
    //finally {
   	 
        resultSet.close();
        statement.close();
        connection.close();
   // }
    */
    return names;
  }
}