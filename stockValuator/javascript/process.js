var user; //stores username
var stock; //stores stock id

/*
	ajaxRequest object used to dynamically call php functions
*/
function ajaxRequest(method, url, data, callback){
	
	let request = new XMLHttpRequest();
	request.open(method,url,true);
	if(method == "POST"){
		request.setRequestHeader
 		('Content-Type','application/x-www-form-urlencoded');
	}
 	request.onreadystatechange = function(){
	if (request.readyState == 4) {
		if (request.status == 200) {
			let response = request.responseText;
			callback(response);
		} else {
			//err(response.statusText);
		}
		}
	}
	request.send(data); //Add data if using post
}
 
 /*
	searches for user info in database
*/
 function checkLogin(){
 	var username = document.getElementById("username").value; //gets username
 	var password = document.getElementById("password").value; //gets password
 	ajaxRequest("POST", "php/LoginForm.php", "username="+ username + "&password=" + password, hideForm); //calls php function to search database for user details
 }
 
  /*
	Hides the login form and makes buttons visible for navigation and functionality
*/
 function hideForm(response){
 
 	if(response != "failed"){ //if the previous php function was able to find matching user info in the database
 	
 		var buttonMyStocks = document.getElementById("myStocks"); 
		var buttonBrowse = document.getElementById("browseStocks");
	
		//hides browse toggle buttons
		buttonMyStocks.style.display = "inline-block";
		buttonBrowse.style.display = "inline-block";
 	
 		var buttonAdd = document.getElementById("addStock");
		var buttonRemove = document.getElementById("removeStock");
		
		//hides the remove stock button
		buttonRemove.style.display = "block";
		
 		var username = document.getElementById("username");
 		var password = document.getElementById("password");
 	
 	
 	
 		var usernameLabel = document.getElementById("usernameLabel");
 		var passwordLabel = document.getElementById("passwordLabel");
 	
 		var loginBtn = document.getElementById("loginBtn");
 		
 		var div = document.getElementById("loginForm");
 		var showStocks = document.getElementById("userStocks");
 	
 		//hides username and password inputs
 		username.style.display = 'none';
 		password.style.display = 'none';
 	
 		//hides labels
 		usernameLabel.style.display = 'none';
 		passwordLabel.style.display = 'none';
 	
 		//hides login button and unnecessary divs
 		loginBtn.style.display = 'none';
 		div.style.display = 'none';
 		showStocks.style.display = 'block';
 		
 		user = response; //stores username of logged in user
 		
 		//displays username above stock collection
 		var heading = document.getElementById("usernameH2");
 		heading.innerHTML = user + "'s Stocks:";
 		heading.style.display = 'block';
 	
 		ajaxRequest("POST", "php/userStocks.php", "username="+ user, showStocks); //php script retrieves all of the users stocks
 		
 	}
 
 }
 
  /*
	Retrieves all of the users selected stocks in an unordered list
*/
function showMyStocks(){
	//displays username above stock collection
 	var heading = document.getElementById("usernameH2");
 	heading.innerHTML = user + "'s Stocks:";
 	heading.style.display = 'block';
 	
	var buttonAdd = document.getElementById("addStock");
	var buttonRemove = document.getElementById("removeStock");
	
	//hides the add stock button and siplays the remove stock button
	buttonAdd.style.display = "none";
	buttonRemove.style.display = "block";
	
	//clears stock list to prevent double ups
	var list = document.getElementById("stockList");
	while (list.hasChildNodes()) {  
  		list.removeChild(list.firstChild);
	} 
	ajaxRequest("POST", "php/userStocks.php", "username="+ user, showStocks); //php function retrieves all of a users selected stocks
}

 /*
	Retrieves all available stocks
*/
function showAllStocks(){

	//displays username above stock collection
 	var heading = document.getElementById("usernameH2");
 	heading.innerHTML = "Browse All Stocks:";
 	heading.style.display = 'block';
 		
	var buttonAdd = document.getElementById("addStock");
	var buttonRemove = document.getElementById("removeStock");
	
	//hides the remove stock button and displays the add stock button
	buttonAdd.style.display = "block";
	buttonRemove.style.display = "none";
	
	//clears stock list to prevent double ups
	var list = document.getElementById("stockList");
	while (list.hasChildNodes()) {  
  		list.removeChild(list.firstChild);
	} 
	ajaxRequest("POST", "php/browseStocks.php", "", showStocks); //php function retrieves all of a users selected stocks
}
 
 /*
	Displays all stocks retrieved by the php script
*/
function showStocks(result){
	
	var list = document.getElementById("stockList"); //retrieves JSON string from the php script
	var array = JSON.parse(result); //Converts string into an array
	for (var i = 1; i < array.length; i+=2) {		//For loop increments by 2 as only odd indexes of the array store company names
        	var li = document.createElement("li"); //creates list element
        	li.classList.add("userStocks");
        	li.id = array[i-1]; //retrieves the companies id from the previous index of the array
        	li.onclick = stockSelected; //shows stock info when it is clicked
        	li.innerHTML = array[i]; //Displays company name in the li
  			list.appendChild(li);
  			
    }
    
}

 /*
	Retrieves all info for a stock when it is selected
*/
function stockSelected(){
	stock = this.id; //gets id of the html element that called the function
	var div = document.getElementById("stockInfo");
  	div.style.display = "block"; //displays stockInfo window
  	ajaxRequest("POST", "php/stockInfo.php", "id="+ stock, showStockInfo); //php script retrieves stock info
  	
  	
}

/*
	Displays a stocks information
*/
function showStockInfo(response){
	
	var array = JSON.parse(response);
  	var companyName = document.getElementById("companyName");
  	companyName.innerHTML = array[0];
  	
  	var id = document.getElementById("companyId");
  	id.innerHTML = "Company ID: " + array[1];
  	
  	var currentPrice = document.getElementById("currentPrice");
  	currentPrice.innerHTML = "Current Price: $" + array[2];
  	
  	var recentChange = document.getElementById("recentChange");
  	recentChange.innerHTML = "Recent Change: $" + array[3];
  	
  	var annualTrend = document.getElementById("annualTrend");
  	annualTrend.innerHTML = "Annual Trend: " + array[4];
  	
  	var changedDirection = document.getElementById("changedDirection");
  	changedDirection.innerHTML = "Recent Changed Direction: " + array[5];
  	
}




/*
	Adds a stock to the users list of selected stocks
*/
function addStock(){
	ajaxRequest("POST", "php/addStock.php", "username="+ user + "&stock=" + stock, showAllStocks);
}

/*
	Removes a stock from the users list of selected stocks
*/
function removeStock(){
	ajaxRequest("POST", "php/removeStock.php", "username="+ user + "&stock=" + stock, showMyStocks);
}


