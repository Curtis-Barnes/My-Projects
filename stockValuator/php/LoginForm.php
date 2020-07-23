<?php

	$username = "cjb78";
	$password = "my10855606sql";
	$hostname = "mysql.cms.waikato.ac.nz"; 
	$db = "cjb78";
	
	$con=mysqli_connect($hostname,$username,$password,$db); //Establishes connection with the database
	
				$username = $_POST['username']; //Uses $_POST method to retrieve the username
				$password = $_POST['password']; //Uses $_POST method to retrieve the password

				
				
				
				$query = "SELECT * FROM `Users` WHERE username = '$username' AND password = '$password'"; //Checks if database contains matching username and password
				$result = mysqli_query($con, $query); //Queries the database
				$row = mysqli_fetch_assoc($result); //Retrieves the first row of results
				if($row != false){ //If the row exists
					
					echo $username;
					
				}else{ //If the row does not exist 
				
					echo "failed";
				}
				
	
?>
