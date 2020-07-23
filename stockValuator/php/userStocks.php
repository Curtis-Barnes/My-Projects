<?php

	$username = "cjb78";
	$password = "my10855606sql";
	$hostname = "mysql.cms.waikato.ac.nz"; 
	$db = "cjb78"; 
	
	$con=mysqli_connect($hostname,$username,$password,$db); //Establishes connection with the database
		
				
				$username = $_POST['username']; //Uses $_POST method to retrieve the username
				$array = array();
				
				$query = "SELECT id FROM `Users` WHERE username = '$username'"; //Gets the users id
				$result = mysqli_query($con, $query); //Queries the database
				$row = mysqli_fetch_assoc($result); //Retrieves the first row of results			
				$userId = $row[id];
				
												
				$query = "SELECT * FROM `Selected_Stocks` WHERE user_id = '$userId'"; //Retrieves all stocks that the user has previously selected
				$result = mysqli_query($con, $query); //Queries the database
				
				while($row = mysqli_fetch_assoc($result)){
					
					if($row != false){ //If the row exists
					
						$query = "SELECT id, companyname FROM `Stocks` WHERE id = $row[stock_id]"; //Retrieves the name of the company
						$innerResult = mysqli_query($con, $query); //Queries the database
						$innerRow = mysqli_fetch_assoc($innerResult); //Retrieves the first row of results
						$array[] = $innerRow[id];
						$array[] = $innerRow[companyname];
						
						
					
					}
						
				
				}
				
				$json = json_encode($array); //encodes array as JSON string
				echo $json;
				
?>
