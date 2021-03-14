package com.mindfultrader.webapp.controllers;

import java.sql.SQLException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mindfultrader.webapp.data.TestDatabase;

@Controller
public class PortfolioCotroller {
	
	@GetMapping(value = "/portfolio", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String listUsers(Model model) throws SQLException {
		
		//System.out.println(TestDatabase.getDataSource());
		//TestDatabase.getData();
		//return "";
		
		
			   String sb = new String();
			   sb += "<select>";
			   for(String name:TestDatabase.getData()) {
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
			   sb += "<form>\r\n"
			   		+ "      <fieldset>\r\n"
			   		+ "        <legend>Text input</legend>\r\n"
			   		+ "        <p>\r\n"
			   		+ "<div><label> Company: <input type=\"company\" name=\"company\"/> </label></div>"
			   		+ "        </p>\r\n"
			   		+ "<form th:action=\"@{/process_portfolio}\" th:object=\"${user}\"\r\n"
			   		+ "method=\"post\" style=\"max-width: 600px; margin: 0 auto;\">"
			   		+ "<div>\r\n"
			   		+ "<button type=\"submit\" class=\"btn btn-primary\">Sign Up</button>\r\n"
			   		+ "</div>"
			   		+ "</form>"
			   		+ "</fieldset>\r\n"
			   		+ "</form>";
			   /*sb += "<form th:action=\"@{/process_portfolio}\" th:object=\"${user}\"\r\n"
			   		+ "method=\"post\" style=\"max-width: 600px; margin: 0 auto;\">"
			   		+ "<div>\r\n"
			   		+ "<button type=\"submit\" class=\"btn btn-primary\">Sign Up</button>\r\n"
			   		+ "</div>"
			   		+ "</form>";*/
			   
			   return sb;
			   
			   
	}

	/*@PostMapping("/process_portfolio")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
         
        userRepo.save(user);
         
        return "register_success";
    }*/
	
	/*public ModelAndView save_company(String company) 
    {
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
        System.out.println(response.getBody());
        
        //Create MVC object for webapp
                ModelAndView mv = new ModelAndView();
                mv.setViewName("dataresult");
                mv.addObject("conclusion1", response.getBody());
                
                
        return mv;
		Connection connection = null;
        try
        {
        	//connection = TestDatabase.
          // create a mysql database connection
          String myDriver = "com.mysql.jdbc.Driver";
          String serverName = "eu-cdbr-west-03.cleardb.net";
          String schema = "heroku_03b3862830df1d7";
          String MyUrl = "jdbc:mysql://" + serverName +  "/" + schema;
          Class.forName(myDriver);
          String username = "b2374bc2da749a";
          String password = "5a7dbb13";
          connection = DriverManager.getConnection(MyUrl, username, password);
        
          // create a sql date object so we can use it in our INSERT statement
          //Calendar calendar = Calendar.getInstance();
          //java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

          // the mysql insert statement
          String query = " insert into watchlistportfolio (User_ID, Company_ID, Type)"
            + " values (?, ?, ?)";

          // create the mysql insert preparedstatement
          PreparedStatement preparedStmt = conn.prepareStatement(query);
          preparedStmt.setString (1, "Barney");
          preparedStmt.setString (2, "Rubble");
          preparedStmt.setDate   (3, startDate);
          preparedStmt.setBoolean(4, false);
          preparedStmt.setInt    (5, 5000);

          // execute the preparedstatement
          preparedStmt.execute();
          
          conn.close();
        }
        catch (Exception e)
        {
          System.err.println("Got an exception!");
          System.err.println(e.getMessage());
        }
      }

    }*/
	
	
}

