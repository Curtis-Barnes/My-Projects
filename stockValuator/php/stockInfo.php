<?php

	$username = "cjb78";
	$password = "my10855606sql";
	$hostname = "mysql.cms.waikato.ac.nz"; 
	$db = "cjb78";
	
	$con=mysqli_connect($hostname,$username,$password,$db); //Establishes connection with the database
	
				$id = $_POST['id']; //Uses $_POST method to retrieve the id
				$array = array();
				
				$query = "SELECT * FROM `Stocks` WHERE id = '$id'"; //Retrieves stock info of stock with a matching id
				$result = mysqli_query($con, $query); //Queries the database
				$row = mysqli_fetch_assoc($result); //Retrieves the first row of results
				if($row != false){ //If the row exists
					$array[] = $row[companyname];
					$array[] = $row[id];
					$array[] = $row[currentprice];
					$array[] = $row[recentchange];
					$array[] = $row[annualtrend];
					$array[] = $row[recentchangedirection];
					$json = json_encode($array); //encodes array as JSON string
					echo $json;
					
				}else{ //If the row does not exist 
				
					echo "failed";
				}
				
	
?>
