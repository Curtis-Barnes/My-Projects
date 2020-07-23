<?php

	$username = "cjb78";
	$password = "my10855606sql";
	$hostname = "mysql.cms.waikato.ac.nz"; 
	$db = "cjb78"; 
	
	$con=mysqli_connect($hostname,$username,$password,$db); //Establishes connection with the database
		
				
				$username = $_POST['username']; //Uses $_POST method to retrieve the username
				$stock = $_POST['stock']; //Uses $_POST method to retrieve the stock
				
				$array = array();
				
				$query = "SELECT id FROM `Users` WHERE username = '$username'"; //Retrieves the users id
				$result = mysqli_query($con, $query); //Queries the database
				$row = mysqli_fetch_assoc($result); //Retrieves the first row of results			
				$userId = $row[id];
				
				$query = "SELECT * FROM `Selected_Stocks` WHERE user_id = '$userId' AND stock_id = '$stock'"; //Checks if database contains matching username and password
				$result = mysqli_query($con, $query); //Queries the database
				$row = mysqli_fetch_assoc($result); //Retrieves the first row of results
				
				if($row != false){ //If the row exists
					
					echo("failed");
					
				}else{
					$query = "INSERT INTO Selected_Stocks (user_id, stock_id) VALUES ('$userId', '$stock')"; //Adds stock to users list of selected stocks
					echo($query);
					$result = mysqli_query($con, $query); //Queries the database
					
				}
				
				
?>
