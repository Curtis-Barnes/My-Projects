<?php

	$username = "cjb78";
	$password = "my10855606sql";
	$hostname = "mysql.cms.waikato.ac.nz"; 
	$db = "cjb78";
	
	$con=mysqli_connect($hostname,$username,$password,$db); //Establishes connection with the database
		
				$array = array();
				$userStocks = array();
					
					
				$query = "SELECT id, companyname FROM `Stocks`"; //Retrieves the name and id of every stock in the database
				$result = mysqli_query($con, $query); //Queries the database
						
				while($row = mysqli_fetch_assoc($result)){ //While there are rows
					$array[] = $row[id];
					$array[] = $row[companyname];
				}
						

				$json = json_encode($array); //encodes results as a JSON string
				echo $json;
				
?>
