package com.mindfultrader.webapp.controllers;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mindfultrader.webapp.data.TestDatabase;

@Controller
public class PortfolioCotroller {
	
	@GetMapping(value = "/portfolio", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String listUsers() throws SQLException {
		
		//System.out.println(TestDatabase.getDataSource());
		//TestDatabase.getData();
		//return "";
		
		
			   String sb = new String();
			   sb += "<select>";
			   for(String name:TestDatabase.getCompaniesNamesPortfolio()) {
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
		
		TestDatabase.insert_data_to_portfolio(name, symbol1);
        
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
		   for(String name:TestDatabase.getCompaniesNamesPortfolio()) {
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
		   
		   sb+= "<form action=\"/delete_company_from_watchlist\">";
		   sb += "<select name=\"cmp_name\" id=\"cmp_name\">";
		   for(String name:TestDatabase.getCompaniesNamesWatchlist()) {
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
		   
		   return sb;
		
        //return "port";
    }
	
	
	@RequestMapping(value = "/delete_company_from_portfolio")
	private String deleteCompanyPortfolio(String cmp_name){
	    //return "redirect:/port";
		String cmp_id = TestDatabase.getCompanyIdByName(cmp_name);
		TestDatabase.delete_company_from_portfolio(cmp_id);
		//System.out.println(cmp_name);
	    return "portfolio_update";
	}
	
	@RequestMapping("/add_to_watchlist")
    public String processWatchlist(String cmp_name) throws SQLException {
		
		TestDatabase.insert_data_to_watchlist(cmp_name);
        
		return "portfolio_update";
    }
	
	@RequestMapping("/add_to_portfolio")
    public String addToPortfolio(String cmp_name) throws SQLException {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDatabase.class);
		 
		DataSource ds = ctx.getBean(DataSource.class);
		JdbcTemplate jt = new JdbcTemplate(ds);
		String cmp_symbol = jt.queryForObject("select Company_Symbol from companies where Company_Name = ?", String.class, cmp_name);

		TestDatabase.insert_data_to_portfolio(cmp_name,cmp_symbol);
        //return listPortfolioCompanies();
		return "portfolio_update";
    }
	
	@RequestMapping(value = "/delete_company_from_watchlist")
	private String deleteCompanyWatchlist(String cmp_name){
	    //return "redirect:/port";
		String cmp_id = TestDatabase.getCompanyIdByName(cmp_name);
		TestDatabase.delete_company_from_watchlist(cmp_id);
		//System.out.println(cmp_name);
	    return "portfolio_update";
	}
	
	/*@RequestMapping("/requestdata2/run")
    public ModelAndView run(String cmp_name) 
    {
		String symbol = TestDatabase.getCompanySymbolByName(cmp_name);
        String uri = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data?symbol=" + symbol + "&region=US";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(uri)
                    .header("x-rapidapi-key", "71c138c430msh94f21a0e7d20608p1bab39jsnfc8e8fcb1aab")
                    .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                    .asJson();
        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("Algorithm has run :) ");

        
        System.out.println(response.getBody().getArray());
        
        //Create MVC object for webapp
                ModelAndView mv = new ModelAndView();
                mv.setViewName("dataresult");
                mv.addObject("conclusion1", response.getBody());
                
                
        return mv;
        

    }*/
	
}

