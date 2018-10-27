package Reversi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	//public static final boolean first = true;
	public static boolean first;
	
	public static void main (String Args[]) {
		
		turn();
		if (!first) {
			// code if opponent goes first
		}else {
			// code if algorithm goes first
		}
		
	}
	
	// sets variable "final" true if the opponent wants to play first,
	// and false if the opponent wants to play second.
	static void turn () {
		System.out.println("Do you wish to play first?\nType Y or N.");
		String answer = "";		
		do {
			try {	
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
				answer = br.readLine();	
				if (answer.equals("Y") | answer.equals("y")) {
					first = true;
					break;
				}else if (answer.equals("N") | answer.equals("n")){
					first = false;
					break;
				}else {	
					throw new IOException();
				}	
			}catch (IOException e){
				System.out.println("Wrong input. Please type the letter Y for yes and N for no.");
			}
		} while (true);	
	}
}
