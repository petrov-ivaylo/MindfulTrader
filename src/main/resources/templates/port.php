<?php
    
    	$mysqli = NEW MySQLi('eu-cdbr-west-03.cleardb.net','b2374bc2da749a','5a7dbb13','heroku_03b3862830df1d7');
		
		$resultSet = $mysqli->query("SELECT first_name FROM customers");
        
    ?>
    
    <select name="heroku_03b3862830df1d7">
    
    <?php
    
	while($rows = $resultSet->fetch_assoc())
	{
		$first_name = $rows['first_name'];
		echo "<option value='$first_name'>$first_name</option>";
	}
    
    ?>
    
    </select>