<?php
	$username = "cjb78";
	$password = "my10855606sql";
	$hostname = "mysql.cms.waikato.ac.nz"; 
	$db = "cjb78";

	//connection to the database
	$dbhandle = mysql_connect($hostname, $username, $password) 
  	or die("Unable to connect to MySQL");
	echo "Connected to MySQL<br>";
?>
