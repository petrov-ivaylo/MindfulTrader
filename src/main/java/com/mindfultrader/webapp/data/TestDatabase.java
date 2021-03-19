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
	
	
	//This should be the pool representation
	//The link to the tutorial I have watched for this is: https://www.youtube.com/watch?v=3UpUG7puGzA
	@Bean
	public static BasicDataSource getDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://eu-cdbr-west-03.cleardb.net:3306/heroku_03b3862830df1d7");
		ds.setUsername("b2374bc2da749a");
		ds.setPassword("5a7dbb13");
		ds.setMaxTotal(9);
		ds.setInitialSize(1);
		ds.setTestOnBorrow(true);
		ds.setValidationQuery("SELECT 1");
		ds.setDefaultAutoCommit(true);
		return ds;
	}
	 
  public static List<String> getCompaniesNames() throws SQLException {
	  
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
  return names;
  }
  
  public static List<String> getCompaniesSymbols() throws SQLException {
	  
	  List<String> symbols = new ArrayList<String>();
	  
	  ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);
	  //String user_id = CustomUserDetails.getUserId().toString();
	  List<String> cmp_names = getCompaniesNames();
	  int i = 0;
	  while (i<cmp_names.size()) {
		  
		  String sql = "SELECT companies.Company_Symbol\r\n"
			  		+ "FROM companies\r\n"
			  		+ "WHERE (companies.Company_Name=" + "\"" + cmp_names.get(i) + "\"" + ")";
		  symbols.add(jt.queryForObject(sql, String.class));
		  i+=1;
	  }
	  return symbols;
	  }
  
  //A function to return a company's id by having its name
  public static String getCompanyIdByName(String name) {
	  
	  ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);
	  
	    String sql = "SELECT companies.Company_ID\r\n"
	    		+ "FROM companies\r\n"
	    		+ "WHERE (companies.Company_Name = ?)";

	    String id = (String) jt.queryForObject(
	            sql, String.class, name);

	    return id;
	}
  
  public static void insert_data(String cmp_name, String cmp_symbol) {
	  ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);
	  //String user_id = CustomUserDetails.getUserId().toString();
	  
	  //String sql = "INSERT IGNORE INTO companies (Company_Name, Company_Symbol) VALUES (" + "\"" + cmp_name + "\"" + "\"" + cmp_name + ",\"" + cmp_symbol + "\"" + ")";
	  jt.update("INSERT IGNORE INTO companies (Company_Name, Company_Symbol) VALUES(?,?)", cmp_name, cmp_symbol);
	  
	  String sql = "Insert ignore into watchlistportfolio(User_ID,Company_ID,Type) values(?,?,?)";
	  String cmp_id = getCompanyIdByName(cmp_name);
	  String type = "p";
	  
	  jt.update(sql, CustomUserDetails.getUserId().toString(), cmp_id, type);
  }
  
  //A function to delete a company from the user portfolio by the company's name
  public static void delete_company_port(String id) {
	  ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);
	  
	  String sql = "delete from watchlistportfolio where Company_ID = ?";
	  Object[] record = new Object[] {id};
	  jt.update(sql, record);
  }
  
}