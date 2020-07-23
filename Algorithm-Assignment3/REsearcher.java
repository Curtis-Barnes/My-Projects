import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.lang.*;
import java.util.ArrayList;
import java.io.IOException;

public class REsearcher{
	public static void main(String[] args){
	
		int startState = 0;
		
		ArrayList<String> symbolArr = new ArrayList<String>();
		ArrayList<Integer> n1Arr = new ArrayList<Integer>();
	 	ArrayList<Integer> n2Arr = new ArrayList<Integer>();
		ArrayList<Integer> considered = new ArrayList<Integer>();
		boolean linematch = false;
		Scanner scan = new Scanner(System.in); 	//scanner to read in the fsm 
		Scanner fScan;
		int currState = 0;
		int pointer = 0;
		boolean initState = true;
		boolean stillLooking = false;
		try{
			if(args.length == 1){
				File searchFile = new File(args[0]);	//get the name of the file to search through from the command line
				fScan = new Scanner(searchFile);
	
				String[] line;
				
			while(scan.hasNext()){
				line = scan.nextLine().split(",");
				if(line.length == 4){
					symbolArr.add(line[1]);	//add the symbol to look for to the symbol array
					n1Arr.add(Integer.parseInt(line[2]));	//add the next state to the next state array
					n2Arr.add(Integer.parseInt(line[3]));	//add the alt. next state to the alt. next state array
				}
			}

			String sLine = "";
			String fullLine = "";
			while(fScan.hasNext()){
				boolean eol = false;
				initState = true;
				if(stillLooking){
					sLine = sLine.substring(pointer);
					stillLooking = false;
					pointer = 0;
				}
				else{
					pointer = 0;
					linematch = false;
					sLine = fScan.nextLine();
					fullLine = sLine;
				}
				Deque dq = new Deque();
				dq.put(n1Arr.get(0).toString());
				considered.clear();
				System.out.println(sLine);
				while(dq.nsEmpty() == false){
					//pop off the scan, add a new scan to the end
					if(linematch == true || stillLooking == true || eol == true){
						break;
					}
					dq.pop();
					dq.put("SCAN");	
					considered.clear();
					while(dq.csEmpty() == false){	//IF THERE ARE CURRENT STATES TO BE CHECKED	
						//dq.print();
						currState = Integer.parseInt(dq.pop());	//set current state to head of deque
						//System.out.print("in state " + currState);
						if(n1Arr.get(currState) == 0 && n2Arr.get(currState) == 0){	//if the end of the regexp has been reached, a match has been found
							System.out.println("matched " + fullLine);
							linematch = true;
							break;
						}
						//check if the text matches curr state symbol
						if(n1Arr.get(currState) == n2Arr.get(currState)){	//if the state is not a branching state, check the character against the file to search			
							if(pointer >= sLine.length()){ //if there is still more line to be checked
								eol = true;
								break;
							}			 
							boolean match = false;
							boolean notContains = false;
							boolean wild = false;
							if(symbolArr.get(currState).charAt(0) == '^'){	//if the collection of chars shouldn't be matched
								notContains = true;
							}	
							else if(symbolArr.get(currState).charAt(0) == '.'){	//if the char to match is a wildcard
								wild = true;
							}
							if(initState){	//check through the whole line looking for the a match to the initial state
								initState = false;								
								for(int i = pointer; i < sLine.length(); i++){ 	//for each char in the line to check								
									if(notContains == false){
										if(wild == false){
											if(symbolArr.get(currState).contains(Character.toString(sLine.charAt(i)))){	//if it matches
												match = true;
												pointer = i + 1;
												break;
											}
										}
										else{	//if the char to find is a wild card & matches anything, set match to true
											match = true;
											pointer = i + 1;
											break;
										}
									}
									else{
										String notFind = symbolArr.get(currState).substring(1);
										if(notFind.contains(Character.toString(sLine.charAt(i)))){	//if it matches
											match = false;
										}
										else{ 
											match = true; 
											pointer = i + 1; 
											break;
										}
									}
								}
								if(match == false){	//if there is no match for the first char in the regex anywhere in the expression, move to the next line
									eol = true;
									break;
								}									
							}
							else{					
								if(notContains == false){
									if(wild == false){
										//System.out.println(sLine + " " + symbolArr.get(currState) + " pointer " + pointer);
										if(symbolArr.get(currState).contains(Character.toString(sLine.charAt(pointer)))){	//if it matches
											match = true;
											pointer++;
										}
									}
									else{	//if the char to find is a wild card & matches anything, set match to true
										match = true;
										pointer++;
									}
								}
								else{
									String notFind = symbolArr.get(currState).substring(1);
									if(notFind.contains(Character.toString(sLine.charAt(pointer)))){	//if it matches
										match = false;
									}
									else{ 
										match = true; 
										pointer++; 
									}
								}
							}
							if(match == false && dq.csEmpty() == false){
								continue;
							}
							else if(match == false && pointer <= sLine.length() && pointer != 0){	//if no match was found on the current line, move to the next line
								stillLooking = true;								
								break;
							}
							else if(match == false && dq.csEmpty() == true){
								System.out.println("no match");
								break;
							}
							
							else if(match == true){ //match found, move to next state in the FSM
								//clear all the states in the current states 
								dq.clearCurr();
								if(n1Arr.get(currState) == n2Arr.get(currState)){	//if the state is not a branching state
									dq.put(n1Arr.get(currState).toString());
								}
								else{	//if the state is a branching state
									dq.put(n1Arr.get(currState).toString());
									dq.put(n2Arr.get(currState).toString());
								}
								break;						
							}else{
								System.out.println("no match not empty");
								dq.print();	
							}						
						}else{	//if branching state, push possible states onto current states
							//if state has already been considered, break;
							
							if(considered.indexOf(n1Arr.get(currState)) == -1){
								dq.push(n1Arr.get(currState).toString());
								considered.add(n1Arr.get(currState));						
							}
					
							if (considered.indexOf(n2Arr.get(currState)) == -1){
								dq.push(n2Arr.get(currState).toString());
								considered.add(n2Arr.get(currState));
							}
							//dq.print();
						}				
					}
				}
			}
			}
			else{
				System.out.println("Usage: filename to search, < fsm output");
				System.exit(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static class Deque {
		ArrayList<String> deque = new ArrayList<String>();

		public Deque(){
			deque.add("SCAN");	//add a marker to seperate the current states from the possible next states
		}

		/* push (add) a state onto the front of the deque*/
		public void push(String state){
			deque.add(0, state);
		}

		/* remove the state off the head of the deque, return the removed state*/
		public String pop(){
			String front = deque.get(0);
			deque.remove(0);
			return front;
		}

		/* add the state to the end of the queue*/
		public void put(String state){
			deque.add(state);
		}

		public void print(){
			System.out.println(deque);
		}

		public boolean nsEmpty(){
			//find the index of scan, check if there are any states after this
			int scan = deque.indexOf("SCAN");
			if(deque.size() - 1 > scan){
				return false;
			}
			return true;
		}

		public boolean csEmpty(){
			int scan = deque.indexOf("SCAN");
			if(scan == 0){ //if scan is the first element in the list (there are no current states) return true
				return true;
			}
			return false;
		}
		/* remove all the current states from the queue*/
		public void clearCurr(){
			int scan = deque.indexOf("SCAN");
			for(int i = 0; i < scan; i++){
				deque.remove(0);
			}
		}
	}
}
