package com.mindfultrader.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PortfolioCotroller {
	
	 /*@GetMapping("/portfolio")
	    public String viewHomePage() {
	        return "index";
	    }*/
	
	
	/*@RequestMapping(value = "/portfolio", method = RequestMethod.GET)
    public @ResponseBody
    String viewportfolio(HttpServletRequest req)
            throws Exception {
        return "<?php\r\n"
        		+ "    \r\n"
        		+ "    	$mysqli = NEW MySQLi('eu-cdbr-west-03.cleardb.net','b2374bc2da749a','5a7dbb13','heroku_03b3862830df1d7');\r\n"
        		+ "		\r\n"
        		+ "		$resultSet = $mysqli->query(\"SELECT first_name FROM customers\");\r\n"
        		+ "        \r\n"
        		+ "    ?>\r\n"
        		+ "    \r\n"
        		+ "    <select name=\"heroku_03b3862830df1d7\">\r\n"
        		+ "    \r\n"
        		+ "    <?php\r\n"
        		+ "    \r\n"
        		+ "	while($rows = $resultSet->fetch_assoc())\r\n"
        		+ "	{\r\n"
        		+ "		$first_name = $rows['first_name'];\r\n"
        		+ "		echo \"<option value='$first_name'>$first_name</option>\";\r\n"
        		+ "	}\r\n"
        		+ "    \r\n"
        		+ "    ?>\r\n"
        		+ "    \r\n"
        		+ "    </select>";
    }*/
	
	@RequestMapping("/portfolio")
	public ModelAndView portfolio () {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("port");
	    return modelAndView;
	}
	
}
