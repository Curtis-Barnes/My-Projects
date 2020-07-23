import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class REcompiler{

	private static ArrayList<String> characters = new ArrayList<String>(); //Stores characters of interest
	private static ArrayList<Integer> nextState1 = new ArrayList<Integer>(); //Stores next state to jump to
	private static ArrayList<Integer> nextState2 = new ArrayList<Integer>(); //Stores alternative state to jump to
	private static int currentState = 1; //Index for the current state
	private static String regexp = ""; //Stores the regular expression
	private static int index = 0; //Index used to read through regular expression
	private static int bStart = 0; //keeps track of the first state inside ()
	private static int bEnd = 0; //keeps track of the end state inside ()
	private static boolean b = false;	//if the index before is a bracker
	private static int bChange = 0; //state to change
	private static int bChange1 = 0;
	private static boolean b1 = false;	


	public static void main(String[] args){
		try{

			if(args.length == 1){ //If the user has supplied a regular expression
				regexp = args[0]; //Regular expression = user input
				//isVocab('*');
				setState(0, " ", 1, 1);

				//System.out.println(regexp);
				
				int initial = expression();
				setState(currentState, " ", 0, 0);

				if(nextState1.get(1) == 2 && nextState2.get(1) == 2){
					nextState1.set(0, 1);
					nextState2.set(0, 1);
				}
					

				for(int i = 0; i < nextState1.size(); i++){
					System.out.println(i + "," + characters.get(i) + "," + nextState1.get(i) + "," + nextState2.get(i));
				}

			}else{
				System.out.println("Usage: Please provide regular expression to validate");
			}	
					
			
		}
		catch(Exception e){
			e.printStackTrace(); //Displays error message
		}

	}

	
	/*
		This method adds a state to the finite state machine
	*/
	public static void setState(int state, String ch, int n1, int n2){
		characters.add(ch); //Sets the character we are interested in
		nextState1.add(n1); //Sets the next state
		nextState2.add(n2); //Sets the next alternative state
		
		
	}

	/*
		This method processes an expression
	*/
	private static int expression(){
		int r = term();
		if(index == regexp.length()){
			return -1;
		}
		if(isVocab(regexp.charAt(index)) || regexp.charAt(index) == '(' || isFactor(regexp.charAt(index))){
			expression();
		}
		else if(regexp.charAt(index) == ')'){
			return r;
		}
		else{
			error();
		}
		return r;
		
	}

	private static int factor(){
		int r;
		String chars = "";
		if(index >= regexp.length()) error();
		if(regexp.charAt(index) == '\\'){
			index++; //move past the escape char
			setState(currentState, String.valueOf(regexp.charAt(index)), currentState+1, currentState+1);
			index++;
			r = currentState;
			currentState++;
			return r;
		}		
		else if(regexp.charAt(index) == '('){
			index++;
			bStart = currentState;
			r = expression();
			//bStart = r;

			if(index > regexp.length()){
				error();
			}			
			else if(regexp.charAt(index) == ')'){
				index++;
			}
			else{
				error();
			}

			if(b == true){
				int c = currentState;
				if(b1) c++;
				nextState1.set(bChange, c);
				nextState2.set(bChange, c);
				nextState1.set(bChange1, c);
				nextState2.set(bChange1, c);
				b = false;
				b1 = false;
			}

			bEnd = currentState - 1;
			return r;
		}else if(regexp.charAt(index) == '['){ //Checks for match from list of literals
			index++;
			if(index >= regexp.length()) error();
			if(regexp.charAt(index) == ']'){
				chars += ']';
				index++;
			}

			while(regexp.charAt(index) != ']' && index < regexp.length()-1){
				chars += regexp.charAt(index);
				index++;
			}
			if(regexp.charAt(index) != ']'){
				error();
			}

			setState(currentState, chars, currentState + 1, currentState + 1);
			r = currentState;
			currentState++;
			index++;
			
			return r;		
		}else if(regexp.charAt(index) == '^'){
			index++;

			if(index >= regexp.length()) error();

			if(regexp.charAt(index) == '['){
				index++;
			}else{
				error();
			}

			chars += '^';

			if(regexp.charAt(index) == ']'){
				chars += ']';
				index++;
			}

			while(regexp.charAt(index) != ']' && index < regexp.length()-1){
				chars += regexp.charAt(index);
				index++;
			}

			if(regexp.charAt(index) != ']'){
				error();
			}
			setState(currentState, chars, currentState + 1, currentState + 1);
			r = currentState;
			currentState++;
			index++;
			
			return r;
		}else if(isVocab(regexp.charAt(index)) || regexp.charAt(index) == '.'){
			setState(currentState, String.valueOf(regexp.charAt(index)), currentState+1, currentState+1);
			index++;
			r = currentState;
			currentState++;
			return r;	
		}else{
			error();
			return -1;
		}		
	}

	private static int term(){
		int prevState = currentState - 1;
		int r = factor();
		int t1 = r;
		
		if(index == regexp.length()){		
 			return r;
		}
		if(regexp.charAt(index) == '*'){ //Closure
			prevState = currentState-1;
			if(regexp.charAt(index-1) != ')'){
				if(nextState1.get(prevState-1) == nextState2.get(prevState-1)){	//if the machine is non-branching, reset state 2
					nextState2.set(prevState-1, currentState);
				}
				nextState1.set(prevState-1, currentState);
			}
			else {
				int change = bStart -1;
				if(nextState1.get(change) == nextState2.get(change)){	//if the machine is non-branching, reset state 2
					nextState2.set(change, currentState);
				}
				nextState1.set(change, currentState);
			} 
			index++;
			setState(currentState, " ", r, currentState + 1);
			r = currentState;
			currentState++;
		}else if(regexp.charAt(index) == '?'){
			prevState = currentState - 1;
			if(regexp.charAt(index-1) != ')'){
				setState(currentState, " ", prevState, currentState+1);	//make a branching machine
				if(nextState1.get(prevState-1) == nextState2.get(prevState-1)){	//if the machine is non-branching, reset state 2
					nextState2.set(prevState-1, currentState);
				}
				nextState1.set(prevState-1, currentState); 
			}
			else{
				int change = bStart - 1;
				setState(currentState, " ", bStart, currentState+1);
				if(nextState1.get(change) == nextState2.get(change)){	//if the machine is non-branching, reset state 2
					nextState2.set(change, currentState);
				}
				nextState1.set(change, currentState);
			}

			nextState1.set(prevState, currentState+1);
			nextState2.set(prevState, currentState+1);
	
			index++;
			r = currentState;
			currentState++;
		}else if(regexp.charAt(index) == '|'){
			if(regexp.charAt(index+1) == '(' && regexp.charAt(index-1) == ')'){
				b = true;
				bChange = bEnd;		
				bChange1 = bStart;		
			}

			prevState = currentState - 1;	//set the previous state
			
			int state1 = currentState - 1;
				
			if(regexp.charAt(index-1) == ')'){	//if there is a set of brackets before the infix
				state1 = bStart + 1;
				nextState1.set(bStart-1, currentState);
				nextState2.set(bStart-1, currentState);	
				nextState1.set(bStart, currentState+2);
				nextState2.set(bStart, currentState+2);								
			}				

			//build a branching machine
			setState(currentState, " ", currentState+1, state1);
			int oState = currentState - 2;
			if(regexp.charAt(index-1) != ')'){
				if(nextState1.get(oState) == nextState2.get(oState)){	//check if the state before the first infix char was branching
					nextState2.set(oState, currentState);	//set the prev -1 state to current state
				}
				nextState1.set(oState, currentState);
			}

			if(nextState1.get(prevState) == nextState2.get(prevState)){	//if the machine is non-branching, reset state 2
				nextState2.set(prevState, currentState+2);
			}
			nextState1.set(prevState, currentState+2);	

			if(regexp.charAt(index+1) == '(' && regexp.charAt(index-1) != ')'){
				bChange = currentState -1;
				b = true;
				//b1 = true;	
			}
			
			
			index++;
			r = currentState;
			currentState++;

			int t2 = term();
		}
		return r;
	}
	
	public static boolean isVocab(Character c){
		String notVocab = "*?|()[]^\\";
		if(notVocab.contains(Character.toString(c))){
			return false;
		}
		else{
			return true;
		}
	}

	public static boolean isFactor(Character c){
		String notVocab = "[^(\\";
		if(notVocab.contains(Character.toString(c))){
			return true;
		}
		else{
			return false;
		}
	}

	private static void error(){
		System.out.println("Invalid Regular expression at index : " + index);
		System.exit(0);
	}	
}
