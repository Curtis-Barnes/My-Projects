<?php

	$username = "cjb78";
	$password = "my10855606sql";
	$hostname = "mysql.cms.waikato.ac.nz"; 
	$db = "cjb78";
	
	$con=mysqli_connect($hostname,$username,$password,$db); //Establishes connection with the database
	 
	
	
	

   /***************************
    * 
    * Add code here to query the DB for weather information for the given town
    * 
    * Construct a PHP array object containing the weather data 
    * and return as JSON
    * 
    */
    
	$town = $_POST['town']; //Uses $_POST method to retrieve the town name
	$array = array(); //Array used to store data retrieved from database
   
   $query = "SELECT * FROM `weather` WHERE `town` = '$town'"; //Select all information relating to the specified town
				$result = mysqli_query($con, $query); //Queries the database
				$row = mysqli_fetch_assoc($result); //Retrieves the first row of results
				if($row != false){ //If the row exists
					$array[] = $row[town]; //Stores town name in array
					$array[] = $row[outlook]; //Stores town outlook in array
					$array[] = $row[min_temp];//Stores min temperature in array
					$array[] = $row[max_temp];//Stores max temperature in array

					
				}
				
				$json = json_encode($array); //encodes array as JSON string
				echo $json; //Returns json object
	


