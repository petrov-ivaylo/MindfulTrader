package com.mindfultrader.webapp.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mindfultrader.webapp.services.CustomUserDetails;

@Controller
public class PortfolioCotrollerOLD {
	
	@Autowired
	JdbcTemplate jt;
	
	@GetMapping(value = "/portfolio", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String listUsers() throws SQLException {
		
		//System.out.println(TestDatabase.getDataSource());
		//TestDatabase.getData();
		//return "";
		
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
		
		
			   String sb = new String();
			   sb += "<select>";
			   for(String name:names) {
				   //System.out.println("<option value=\""+name+"\">"+name+"</option>");
			      sb += "<option value=\""+name+"\">"+name+"</option>";}
			   sb += "</select>";	
			   /*sb += "<div class=\"form-group row\">\r\n"
			   		+ "<label class=\"col-4 col-form-label\">Last Name: </label>\r\n"
			   		+ "<div class=\"col-8\">\r\n"
			   		+ "<input type=\"text\" th:field=\"*{lastName}\" class=\"form-control\"\r\n"
			   		+ "required minlength=\"2\" maxlength=\"20\" />\r\n"
			   		+ "</div>\r\n"
			   		+ "</div>";*/
			   
			   sb += "<form action=\"/process_portfolio\">"
			   		+ "<div><label> Company Name: <input type=\"name\" name=\"name\"/> </label></div>\r\n"
			   		+ "<div><label> Company Symbol: <input type=\"symbol1\" name=\"symbol1\"/> </label></div>\r\n"
			   		+ "<div><input type=\"submit\" value=\"Submit\"/></div>"
			   		+ "</form>";
			   
			   /*sb +=  "<form action=\"/process_portfolio\">"
			   		+ "<input type=\"submit\" value=\"Submit\">"
			   		+ "</form>";*/
			   
			   return sb;
			   
			   
	}

	//@RequestMapping(value = "/process_portfolio", produces = MediaType.TEXT_HTML_VALUE)
    //@ResponseBody
	@RequestMapping("/process_portfolio")
    public String processPortfolio(String name, String symbol1) throws SQLException {
		
		jt.update("INSERT IGNORE INTO companies (Company_Name, Company_Symbol) VALUES(?,?)", name, symbol1);
		  
		  List<Integer> companies = new ArrayList<Integer>();
		  
		  companies = jt.queryForList("select Company_ID from watchlistportfolio where User_ID = " + "\"" + CustomUserDetails.getUserId().toString() + "\"" + " and Type = " + "\"" + "p" + "\"",Integer.class);
		  
		  String sql = "SELECT companies.Company_ID\r\n"
		    		+ "FROM companies\r\n"
		    		+ "WHERE (companies.Company_Name = ?)";

		  String cmp_id = (String) jt.queryForObject(sql, String.class, name);;
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
		  sql = "Insert into watchlistportfolio(User_ID,Company_ID,Type) values(?,?,?)";
		  //String cmp_id = getCompanyIdByName(cmp_name);
		  String type = "p";
		  
		  jt.update(sql, CustomUserDetails.getUserId().toString(), cmp_id, type);
		  }
		
		//TestDatabase.insert_data_to_portfolio(name, symbol1);
        
		return "portfolio_update";
    }
	
	@GetMapping(value = "/port", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String listPortfolioCompanies(/*Model model*/) throws SQLException {
		/*List<ArrayList<String>> listCompanies = new ArrayList<ArrayList<String>>();
		int i = 0;
		List<String> companies_names = TestDatabase.getCompaniesNames();
		List<String> companies_symbols = TestDatabase.getCompaniesSymbols();
		while (i<companies_names.size()){
			ArrayList<String> elem = new ArrayList<String>();
			elem.add(companies_names.get(i));
			elem.add(companies_symbols.get(i));
			listCompanies.add(elem);
			i+=1;
		}*/
        //List<String> listCompanies = TestDatabase.getCompaniesNames();
        //model.addAttribute("listCompanies", listCompanies);
		
		String sb = new String();
			sb+= "<form action=\"/delete_company_from_portfolio\">";
		   sb += "<select name=\"cmp_name\" id=\"cmp_name\">";
		   
		   List<String> names = new ArrayList<String>();
		   
		   String user_id = CustomUserDetails.getUserId().toString();
		   String sql = "SELECT companies.Company_Name\r\n"
		   		+ "FROM watchlistportfolio\r\n"
		   		+ "JOIN companies\r\n"
		   		+ "On watchlistportfolio.Company_ID = companies.Company_ID\r\n"
		   		+ "WHERE (watchlistportfolio.User_ID=" + "\"" + user_id + "\"" + "and watchlistportfolio.Type = \"p\")";
		   names = jt.queryForList(sql,String.class);
		   
		   for(String name:names) {
			   //System.out.println("<option value=\""+name+"\">"+name+"</option>");
		      sb += "<option value=\""+name+"\">"+name+"</option>";}
		   sb += "</select>";	
		   /*sb += "<div class=\"form-group row\">\r\n"
		   		+ "<label class=\"col-4 col-form-label\">Last Name: </label>\r\n"
		   		+ "<div class=\"col-8\">\r\n"
		   		+ "<input type=\"text\" th:field=\"*{lastName}\" class=\"form-control\"\r\n"
		   		+ "required minlength=\"2\" maxlength=\"20\" />\r\n"
		   		+ "</div>\r\n"
		   		+ "</div>";*/
		   
		   sb += ""
		   	//	+ "<div><label> Company Name: <input type=\"name\" name=\"name\"/> </label></div>\r\n"
		   	//	+ "<div><label> Company Symbol: <input type=\"symbol1\" name=\"symbol1\"/> </label></div>\r\n"
		   		+ "<div><input type=\"submit\" value=\"Delete\"/></div>"
		   		+ "<div><input type=\"submit\" value=\"Move to your watchlist\" formaction=\"/add_to_watchlist\"/></div>"
		   		+ "<div><input type=\"submit\" value=\"Run the algorithm\" formaction=\"/requestdata2/run\"/></div>"
		   		+ "</form>";
		   
		   /*sb +=  "<form action=\"/process_portfolio\">"
		   		+ "<input type=\"submit\" value=\"Submit\">"
		   		+ "</form>";*/
		   
			  /*AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
				 
			  DataSource ds = ctx.getBean(DataSource.class);
			  JdbcTemplate jt = new JdbcTemplate(ds);*/
		   user_id = CustomUserDetails.getUserId().toString();
		   sql = "SELECT companies.Company_Name\r\n"
			  		+ "FROM watchlistportfolio\r\n"
			  		+ "JOIN companies\r\n"
			  		+ "On watchlistportfolio.Company_ID = companies.Company_ID\r\n"
			  		+ "WHERE (watchlistportfolio.User_ID=" + "\"" + user_id + "\"" + "and watchlistportfolio.Type = \"w\")";
			  names = jt.queryForList(sql,String.class);
		   
		   sb+= "<form action=\"/delete_company_from_watchlist\">";
		   sb += "<select name=\"cmp_name\" id=\"cmp_name\">";
		   for(String name:names) {
			   //System.out.println("<option value=\""+name+"\">"+name+"</option>");
		      sb += "<option value=\""+name+"\">"+name+"</option>";}
		   sb += "</select>";		   
		   sb += ""
		   	//	+ "<div><label> Company Name: <input type=\"name\" name=\"name\"/> </label></div>\r\n"
		   	//	+ "<div><label> Company Symbol: <input type=\"symbol1\" name=\"symbol1\"/> </label></div>\r\n"
		   		+ "<div><input type=\"submit\" value=\"Delete\"/></div>"
		   		+ "<div><input type=\"submit\" value=\"Move to your portfolio\" formaction=\"/add_to_portfolio\"/></div>"
		   		+ "<div><input type=\"submit\" value=\"Run the algorithm\" formaction=\"/requestdata2/run\"/></div>"
		   		+ "</form>";
		   
		   sql = "SELECT companies.Company_Name\r\n"
				  		+ "FROM companies\r\n";
			  
		   names = jt.queryForList(sql,String.class);
		   
		   sb+= "<form action=\"/add_to_portfolio\">";
		   sb += "<select name=\"cmp_name\" id=\"cmp_name\">";
		   for(String name:names) {
			   //System.out.println("<option value=\""+name+"\">"+name+"</option>");
		      sb += "<option value=\""+name+"\">"+name+"</option>";}
		   sb += "</select>";		   
		   sb += ""
		   	//	+ "<div><label> Company Name: <input type=\"name\" name=\"name\"/> </label></div>\r\n"
		   	//	+ "<div><label> Company Symbol: <input type=\"symbol1\" name=\"symbol1\"/> </label></div>\r\n"
		   		+ "<div><input type=\"submit\" value=\"Move to your portfolio\"/></div>"
		   		+ "<div><input type=\"submit\" value=\"Move to your watchlist\" formaction=\"/add_to_watchlist\"/></div>"
		   		+ "</form>";
		   
		   sb += "<form action=\"/users\">"
		   		+ "<div><input type=\"submit\" value=\"Return to the users page\"/></div>"
		   		+ "</form>";
		   
		   return sb;
		
        //return "port";
    }
	
	
	@RequestMapping(value = "/delete_company_from_portfolio")
	private String deleteCompanyPortfolio(String cmp_name){
	    //return "redirect:/port";
		
		String sql = "SELECT companies.Company_ID\r\n"
	    		+ "FROM companies\r\n"
	    		+ "WHERE (companies.Company_Name = ?)";

		String cmp_id = (String) jt.queryForObject(
	            sql, String.class, cmp_name);
		
		sql = "delete from watchlistportfolio where Company_ID = ? and Type = ?";
		  Object[] record = new Object[] {cmp_id, "p"};
		  jt.update(sql, record);
		//System.out.println(cmp_name);
	    return "redirect:port";
	}
	
	@RequestMapping("/add_to_watchlist")
    public String processWatchlist(String cmp_name) throws SQLException {
		
		String sql = "SELECT companies.Company_ID\r\n"
	    		+ "FROM companies\r\n"
	    		+ "WHERE (companies.Company_Name = ?)";

		String cmp_id = (String) jt.queryForObject(
	            sql, String.class, cmp_name);
		
		sql = "delete from watchlistportfolio where Company_ID = ? and Type = ?";
		  Object[] record = new Object[] {cmp_id, "p"};
		  jt.update(sql, record);
		  
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
		  sql = "Insert ignore into watchlistportfolio(User_ID,Company_ID,Type) values(?,?,?)";
		  //String cmp_id = getCompanyIdByName(cmp_name);
		  String type = "w";
		  
		  jt.update(sql, CustomUserDetails.getUserId().toString(), cmp_id, type);
		  }
        
		return "redirect:port";
    }
	
	@RequestMapping("/add_to_portfolio")
    public String addToPortfolio(String cmp_name) throws SQLException {
		
		String cmp_symbol = jt.queryForObject("select Company_Symbol from companies where Company_Name = ?", String.class, cmp_name);

		String sql = "SELECT companies.Company_ID\r\n"
	    		+ "FROM companies\r\n"
	    		+ "WHERE (companies.Company_Name = ?)";

		String cmp_id = (String) jt.queryForObject(
	            sql, String.class, cmp_name);
		
		sql = "delete from watchlistportfolio where Company_ID = ? and Type = ?";
		  Object[] record = new Object[] {cmp_id, "w"};
		  jt.update(sql, record);


		  jt.update("INSERT IGNORE INTO companies (Company_Name, Company_Symbol) VALUES(?,?)", cmp_name, cmp_symbol);
		  
		  List<Integer> companies = new ArrayList<Integer>();
		  
		  companies = jt.queryForList("select Company_ID from watchlistportfolio where User_ID = " + "\"" + CustomUserDetails.getUserId().toString() + "\"" + " and Type = " + "\"" + "p" + "\"",Integer.class);
		 
		  int bl = 0;
		  for(int i=0;i<companies.size();i++) {
			  if (companies.get(i).toString().equals(cmp_id)) {
				  bl =1;
				  System.out.println("YES");
				  break;
			  }
		  }
		  
		  if (bl==0){
		  sql = "Insert into watchlistportfolio(User_ID,Company_ID,Type) values(?,?,?)";
		  //String cmp_id = getCompanyIdByName(cmp_name);
		  String type = "p";
		  
		  jt.update(sql, CustomUserDetails.getUserId().toString(), cmp_id, type);
		  }
		
        //return listPortfolioCompanies();
		return "redirect:port";
		//return "portfolio_update";
    }
	
	@RequestMapping(value = "/delete_company_from_watchlist")
	private String deleteCompanyWatchlist(String cmp_name){
	    //return "redirect:/port";
		String sql = "SELECT companies.Company_ID\r\n"
	    		+ "FROM companies\r\n"
	    		+ "WHERE (companies.Company_Name = ?)";

		String cmp_id = (String) jt.queryForObject(
	            sql, String.class, cmp_name);
		
		sql = "delete from watchlistportfolio where Company_ID = ? and Type = ?";
		  Object[] record = new Object[] {cmp_id, "w"};
		  jt.update(sql, record);
		  
	    return "redirect:port";
	}
	
	/*@RequestMapping("/move_to_portfolio")
    public String moveToPortfolio(String cmp_name) throws SQLException {
		
		String cmp_symbol = 
		TestDatabase.insert_data_to_portfolio(cmp_name, cmp_symbol);
		
		return "redirect:port";
    }*/
	
}