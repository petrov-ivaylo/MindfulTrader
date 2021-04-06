package com.mindfultrader.webapp.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mindfultrader.webapp.services.CustomUserDetails;
 
public class TestDatabase {
	
	
	//This should be the pool representation
	//The link to the tutorial I have watched for this is: https://www.youtube.com/watch?v=3UpUG7puGzA
	/*@Bean
	public static BasicDataSource getDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://eu-cdbr-west-03.cleardb.net:3306/heroku_03b3862830df1d7");
		ds.setUsername("b2374bc2da749a");
		ds.setPassword("5a7dbb13");
		ds.setMaxTotal(10);
		ds.setInitialSize(1);
		ds.setTestOnBorrow(true);
		ds.setValidationQuery("SELECT 1");
		ds.setDefaultAutoCommit(true);
		return ds;
	}*/
	
	@Autowired
	JdbcTemplate jt;
	 
	//A function to return all the companies in the user's portfolio
  public List<String> getCompaniesNamesPortfolio() throws SQLException {
	  
  List<String> names = new ArrayList<String>();
  
  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
	 
  DataSource ds = ctx.getBean(DataSource.class);
  JdbcTemplate jt = new JdbcTemplate(ds);*/
  String user_id = CustomUserDetails.getUserId().toString();
  String sql = "SELECT companies.Company_Name\r\n"
  		+ "FROM watchlistportfolio\r\n"
  		+ "JOIN companies\r\n"
  		+ "On watchlistportfolio.Company_ID = companies.Company_ID\r\n"
  		+ "WHERE (watchlistportfolio.User_ID=" + "\"" + user_id + "\"" + "and watchlistportfolio.Type = \"p\")";
  names = jt.queryForList(sql,String.class);
  System.out.println(names);
  //ctx.close();
  return names;
  }
  
//A function to return all the companies in the user's watchlist
  public List<String> getCompaniesNamesWatchlist() throws SQLException {
	  
	  List<String> names = new ArrayList<String>();
	  
	  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);*/
	  String user_id = CustomUserDetails.getUserId().toString();
	  String sql = "SELECT companies.Company_Name\r\n"
	  		+ "FROM watchlistportfolio\r\n"
	  		+ "JOIN companies\r\n"
	  		+ "On watchlistportfolio.Company_ID = companies.Company_ID\r\n"
	  		+ "WHERE (watchlistportfolio.User_ID=" + "\"" + user_id + "\"" + "and watchlistportfolio.Type = \"w\")";
	  names = jt.queryForList(sql,String.class);
	  System.out.println(names);
	  //ctx.close();
	  return names;
	  }
  
//A function to return all the companies in our database
  public List<String> getAllDBCompanies() throws SQLException {
	  
	  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);*/

	  List<String> names = new ArrayList<String>();
	  String sql = "SELECT companies.Company_Name\r\n"
		  		+ "FROM companies\r\n";
	  
	  names = jt.queryForList(sql,String.class);
	  //ctx.close();
		return names;
	  }
  
  //A function to return the symbols of the companies in the user's portfolio
  public List<String> getCompaniesSymbols() throws SQLException {
	  
	  List<String> symbols = new ArrayList<String>();
	  
	  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);*/
	  //String user_id = CustomUserDetails.getUserId().toString();
	  List<String> cmp_names = getCompaniesNamesPortfolio();
	  int i = 0;
	  while (i<cmp_names.size()) {
		  
		  String sql = "SELECT companies.Company_Symbol\r\n"
			  		+ "FROM companies\r\n"
			  		+ "WHERE (companies.Company_Name=" + "\"" + cmp_names.get(i) + "\"" + ")";
		  symbols.add(jt.queryForObject(sql, String.class));
		  i+=1;
	  }
	  
	 // ctx.close();
	  return symbols;
	  }
  
  //A function to return a company's id by having its name
  public String getCompanyIdByName(String name) {
	  
	  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);*/
	  
	    String sql = "SELECT companies.Company_ID\r\n"
	    		+ "FROM companies\r\n"
	    		+ "WHERE (companies.Company_Name = ?)";

	    String id = (String) jt.queryForObject(
	            sql, String.class, name);

	    //ctx.close();
	    return id;
	}
  
//A function to return a company's id by having its symbol
  public String getCompanyIdBySymbol(String symbol) {
	  
	  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);*/
	  
	    String sql = "SELECT companies.Company_ID\r\n"
	    		+ "FROM companies\r\n"
	    		+ "WHERE (companies.Company_Symbol = ?)";

	    String id = (String) jt.queryForObject(
	            sql, String.class, symbol);

	    //ctx.close();
	    return id;
	}
  
//A function to return a company's Symbol by having its name
  public String getCompanySymbolByName(String name) {
	  
	  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);*/
	  
	    String sql = "SELECT companies.Company_Symbol\r\n"
	    		+ "FROM companies\r\n"
	    		+ "WHERE (companies.Company_Name = ?)";

	    String id = (String) jt.queryForObject(
	            sql, String.class, name);
	    
	    //ctx.close();
	    return id;
	}
  
  //A function to insert a company into the user's portfolio
  public void insert_data_to_portfolio(String cmp_name, String cmp_symbol) {
	  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);*/
	  //String user_id = CustomUserDetails.getUserId().toString();
	  //String sql = "INSERT IGNORE INTO companies (Company_Name, Company_Symbol) VALUES (" + "\"" + cmp_name + "\"" + "\"" + cmp_name + ",\"" + cmp_symbol + "\"" + ")";
	  jt.update("INSERT IGNORE INTO companies (Company_Name, Company_Symbol) VALUES(?,?)", cmp_name, cmp_symbol);
	  
	  List<Integer> companies = new ArrayList<Integer>();
	  
	  companies = jt.queryForList("select Company_ID from watchlistportfolio where User_ID = " + "\"" + CustomUserDetails.getUserId().toString() + "\"" + " and Type = " + "\"" + "p" + "\"",Integer.class);
	  String cmp_id = getCompanyIdByName(cmp_name);
	  //System.out.println(companies.get(0).toString().getClass());
	  //System.out.println(cmp_id.getClass());
	  int bl = 0;
	  for(int i=0;i<companies.size();i++) {
		  //System.out.println(companies.get(i).toString());
		  //System.out.println(cmp_id);
		  if (companies.get(i).toString().equals(cmp_id)) {
			  bl =1;
			  System.out.println("YES");
			  break;
		  }
	  }
	  
	  if (bl==0){
	  String sql = "Insert into watchlistportfolio(User_ID,Company_ID,Type) values(?,?,?)";
	  //String cmp_id = getCompanyIdByName(cmp_name);
	  String type = "p";
	  
	  jt.update(sql, CustomUserDetails.getUserId().toString(), cmp_id, type);
	  }
	  //ctx.close();
  }
  
//A function to insert a company into the user's watchlist
  public void insert_data_to_watchlist(String cmp_name) {
	  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);*/
	  //String user_id = CustomUserDetails.getUserId().toString();
	  String cmp_symbol = jt.queryForObject("select Company_Symbol from companies where Company_Name = ?", String.class, cmp_name);
	  //System.out.println(cmp_symbol);
	  //String sql = "INSERT IGNORE INTO companies (Company_Name, Company_Symbol) VALUES (" + "\"" + cmp_name + "\"" + "\"" + cmp_name + ",\"" + cmp_symbol + "\"" + ")";
	  jt.update("INSERT IGNORE INTO companies (Company_Name, Company_Symbol) VALUES(?,?)", cmp_name, cmp_symbol);
	  
	  List<Integer> companies = new ArrayList<Integer>();
	  
	  companies = jt.queryForList("select Company_ID from watchlistportfolio where User_ID = " + "\"" + CustomUserDetails.getUserId().toString() + "\"" + " and Type = " + "\"" + "w" + "\"",Integer.class);

	  int bl = 0;
	  for(int i=0;i<companies.size();i++) {
		  if (companies.get(i).toString().equals(jt.queryForObject("select Company_ID from companies where Company_Name = ?", String.class, cmp_name))) {
			  bl = 1;
			  System.out.println("YES");
			  break;
		  }
	  }
	  if (bl==0){
	  String sql = "Insert ignore into watchlistportfolio(User_ID,Company_ID,Type) values(?,?,?)";
	  String cmp_id = getCompanyIdByName(cmp_name);
	  String type = "w";
	  
	  jt.update(sql, CustomUserDetails.getUserId().toString(), cmp_id, type);
	  }
	 // ctx.close();
  }
  
  //A function to delete a company from the user's portfolio by the company's name
  public void delete_company_from_portfolio(String id) {
	  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);*/
	  
	  String sql = "delete from watchlistportfolio where Company_ID = ? and Type = ?";
	  Object[] record = new Object[] {id, "p"};
	  jt.update(sql, record);
	 // ctx.close();
  }
  
  //A function to delete a company from the user's watchlist by the company's name
  public void delete_company_from_watchlist(String id) {
	  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
	  DataSource ds = ctx.getBean(DataSource.class);
	  JdbcTemplate jt = new JdbcTemplate(ds);*/
	  
	  String sql = "delete from watchlistportfolio where Company_ID = ? and Type = ?";
	  Object[] record = new Object[] {id, "w"};
	  jt.update(sql, record);
	  //ctx.close();
  }
  
}