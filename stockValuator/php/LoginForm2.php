<?php
	
	$username = "cjb78";
	$password = "my10855606sql";
	$hostname = "mysql.cms.waikato.ac.nz"; 
	$db = "cjb78"; 
	
	
	try{
		$conn= new PDO('mysql:host=localhost;dbname=Stock_Price_Tracker;','root','root'); //Establishes connection with the database	
		$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);	
	}catch(PDOException $e){
		echo 'Connection failed: ' . $e->getMessage();
	}
			
			try{
				$username = $_POST['username']; //Uses $_POST method to retrieve the username
				$password = $_POST['password']; //Uses $_POST method to retrieve the password
				
				
				
				foreach($conn->query('SELECT * FROM "Users" WHERE username = "$username" AND password = "$password"') as $row) {
    				echo $row['username'];
				}
			}catch(){
				echo "failed";
			}
	
				
?>
