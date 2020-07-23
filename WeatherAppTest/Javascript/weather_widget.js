/*
 * Constructor function for a WeatherWidget instance.
 * 
 * container_element : a DOM element inside which the widget will place its UI
 *
 */
 
function WeatherWidget(container_element){

	//declare the data properties of the object 
	var towns = ['Auckland', 'Christchurch', 'Dunedin', 'Hamilton', 'Tauranga', 'Wellington']; //List of towns in the database
	var lines = []; //Stores each line of weather info to display in widget
	

		
		//declare an inner object literal to represent the widget's UI
		var _ui = {
			container : null, //Div that contains widget
			select : null, //Drop down menu
			option : null, //Drop down elements to choose from
			br : null,
			sortDiv : null, //Div that stores sorting radio buttons
			radio : null, //Radio buttons
			label : null, //Labels for form controls
			radioTown : null, //Radio button to sort by town
			radioTemp : null, //Radio button to sort by max temperature
			
		}
		
		
		
		//write a function to create and configure the DOM elements for the UI
		var _createUI = function(container){
			_ui.container = container; //Div that contains widget
			_ui.label = document.createElement("LABEL"); //Labels for drop down menu
			_ui.label.innerHTML = "Select Town: "; //sets label text
			_ui.container.appendChild(_ui.label); //adds label to container
			_ui.container.className = "monitor";
			_ui.select = document.createElement("SELECT"); //Creates dropdown menu
			_ui.select.onchange = callAjax; //calls php function to search database for town info
			
			_ui.container.appendChild(_ui.select); //adds dropdown menu to container
			
			
			
			for (var i = 0; i < towns.length; i++) { //For each town
				_ui.option = document.createElement("OPTION"); //Create an option
  				_ui.option.value = towns[i]; //Set option value to equal the name of the town
				_ui.option.text = towns[i]; //Set option text to equal the name of the town
				_ui.select.appendChild(_ui.option);	//adds option to the dropdown menu
			}
			_ui.br = document.createElement("BR"); //creates break character
			_ui.container.appendChild(_ui.br); //Adds break character to container
			_ui.sortDiv = document.createElement("DIV"); //Creates div to contain sorting controls
			_ui.sortDiv.className = "sortBar";
			_ui.container.appendChild(_ui.sortDiv); //Adds sortDiv to container
			_ui.label = document.createElement("LABEL"); //Creates label
			_ui.label.innerHTML = "Sort By: "; //Sets label text
			_ui.sortDiv.appendChild(_ui.label); //Adds label to div
			_ui.radioTown = document.createElement("INPUT"); //Creates input object
			_ui.label = document.createElement("LABEL"); //Creates label for input object
			_ui.label.name = "orderTown"; //creates label for town radio button
			_ui.radioTown.id = "orderTown";
			_ui.label.innerHTML = "Town "; //sets label text
			_ui.radioTown.setAttribute("type", "radio"); //Sets input object as radio button
			_ui.radioTown.name = container_element.id + "sortRadio"; //Sets both radio buttons to same name so only one can be selected at a time
			_ui.sortDiv.appendChild(_ui.radioTown); //Adds radio button to div
			_ui.sortDiv.appendChild(_ui.label); //adds label to div
			
			
			_ui.radioTemp = document.createElement("INPUT"); //Creates input object
			_ui.label = document.createElement("LABEL"); //Creates label
			_ui.label.name = "orderTemp"; //creates label for temperature radio button
			_ui.label.innerHTML = "Max Temp "; //Sets label text
			_ui.radioTemp.id = "orderTemp"; 
			_ui.radioTemp.setAttribute("type", "radio"); //Sets input object as radio button
			_ui.radioTemp.name = container_element.id + "sortRadio"; //Sets both radio buttons to same name so only one can be selected at a time
			_ui.sortDiv.appendChild(_ui.radioTemp); //Adds radio button to div
			_ui.sortDiv.appendChild(_ui.label); //adds label to div
			
			
			
			
			
		}
	
	//Method that calls ajax function
	var callAjax = function(){
		ajaxRequest("POST", "PHP/weather.php", "town="+ this.value, _createLine);
	}
	
	//add any other methods required for the functionality
	
	//Ajax object used to retrieve town information from the database
	var ajaxRequest = function(method, url, data, callback){
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
	
	
	//Creates a WLine object and adds it to the lines array to be sorted and accessed by _sortLines()
	var _createLine = function(response){
			var array = JSON.parse(response); //Converts string into an array
			
			var town = array[0]; //town name
			var outlook = array[1]; //current weather outlook
			var min = array[2]; //min temperature
			var max = array[3]; //max temperature
					
			var line = new WLine(town, outlook, min, max); //Creates line object
			lines.push(line); //Adds line to lines array
			
			_sortLines(); //Sorts the lines based upon the users radio button input
		
			
	}
	
	//This method will either sort WLine objects alphabetically by town name or by maximum temperature depending
	//on user radio button input
	var _sortLines = function(){
			
		
			
			for(var i=0; i < lines.length; i++){ //For each WLine in lines
				_ui.container.removeChild(lines[i].getDiv()) //Remove line from container object
			}

			
			if(_ui.radioTown.checked == true){ //If the order by town button has been selected
				lines.sort(function(a, b) { //Sort array by town
					if (a.getTown() < b.getTown()) return -1;
   					if (a.getTown() > b.getTown()) return 1;
   					return 0;
				});
			}else if(_ui.radioTemp.checked == true){ //If the order by town button has been selected
				lines.sort(function(a, b) { //sort array by maximum temperature
  					return a.getMax() - b.getMax();
				});
			}
			
			for(var i=0; i < lines.length; i++){ //For each line in lines
				_ui.container.appendChild(lines[i].getDiv()); //add line to container div
			}
			
	}
	
	
	
	 
	 /**
	  * private method to intialise the widget's UI on start up
	  * this method is complete
	  */
	  var _initialise = function(container_element){
	  	_createUI(container_element);
	  	}
	  	
	  	
	/*********************************************************
	* Constructor Function for the inner WLine object to hold the 
	* full weather data for a town
	********************************************************/
	
	var WLine = function(wtown, woutlook, wmin, wmax){
		
		//declare the data properties for the object
		
		var town = wtown;
		var outlook = woutlook;
		var min = wmin;
		var max = wmax;
		
		//declare an inner object literal to represent the widget's UI
		
		var _ui = {
			lineDiv : null,
			container : null,
			line : null,
	
		}


		//write a function to create and configure the DOM elements for the UI
		var _createUI = function(container){
			_ui.container = container; //container div
			_ui.lineDiv = document.createElement("DIV"); //div that contains line
			_ui.lineDiv.className = "list";
			_ui.container.appendChild(_ui.lineDiv); //adds line div to container
			_ui.line = document.createElement("P"); //creates p element
			_ui.line.innerHTML = town + "	" + outlook + "	" + min + "	" + max; //sets text to display in line
			_ui.lineDiv.appendChild(_ui.line); //adds p element to the div
			
		}
		
		//Add any remaining functions you need for the object
		
		//returns the town name
		this.getTown = function(){
			return town;	
		}
		
		//returns the max temperature
		this.getMax = function(){
			return max;	
		}
		
		//returns the div for the line
		this.getDiv = function(){
			return _ui.lineDiv;	
		}
		
		//_createUI() method is called when the object is instantiated
		_createUI(container_element);


	 
  	};  //this is the end of the constructor function for the WLine object 
	
	
	//  _initialise method is called when a WeatherWidget object is instantiated
	 _initialise(container_element);
}
	 
//end of constructor function for WeatherWidget 	 
	 
	 
