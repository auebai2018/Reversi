package Reversi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.CORBA.SystemException;
import org.omg.CosNaming._BindingIteratorImplBase;

import Reversi.Tile.States;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Board {

	public static final int dim = 8;
	public static int orx, ory;
	Tile [][] matrix;
	List <int[]> myTiles = new ArrayList<>(); 
	List <int[]> opponentTiles = new ArrayList<>();
	List <int[]> moves = new ArrayList<>();		
	//Every array on the List moves, must contain 6 elements.
	//[0]::original x dimension
	//[1]::original y dimension
	//[2]::number of direction (1:N, 2:NE, 3:E, 4:SE, 5:S, 6:SW, 7:W, 8:NW)
	//[3]::final x dimension
	//[4]::final y dimension
	//[5]::score ???
	
	
	public Board () {
		matrix = new Tile [dim][dim];
		initBoard(matrix);	
	}
	
	public Board (Tile [][] board) {
		matrix = new Tile [dim][dim];
		copyBoard(board, matrix);
	}
	
	public void initBoard (Tile[][] board) {
		
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				Tile tl = new Tile();
				board [i][j] = tl;
			}
		}
		board[dim/2 - 1][dim/2 - 1].setState(States.WHITE);
		board[dim/2 - 1][dim/2].setState(States.BLACK);
		board[dim/2][dim/2 - 1].setState(States.BLACK);
		board[dim/2][dim/2].setState(States.WHITE);
		//printBoard();
		
		//if (States.valueOf(Main.myColor).equals(States.WHITE)) {
		if (Main.first) {
			myTiles.add(new int[] {dim/2 - 1, dim/2 - 1});
			myTiles.add(new int[] {dim/2, dim/2 - 1});
			opponentTiles.add(new int[] {dim/2 - 1, dim/2});
			opponentTiles.add(new int[] {dim/2, dim/2});
		} else {
			myTiles.add(new int[] {dim/2 - 1, dim/2});
			myTiles.add(new int[] {dim/2, dim/2});
			opponentTiles.add(new int[] {dim/2 - 1, dim/2 - 1});
			opponentTiles.add(new int[] {dim/2, dim/2 - 1});
		}
	}
	
	public void copyBoard (Tile[][] target, Tile [][] source) {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				target [i][j] = source[i][j];
			}
		}
	}
	
	/*public void findLegalMoves (List<int[]> tileList, String color) {
		
		moves.clear(); //clears List moves before filling it with current instant's legal moves.
		//String color = matrix[tileList[0]][tileList.get(0)[1]];
		for (int[] cell: tileList) {
			orx = cell[0];
			ory = cell[1];
			checkNorth(cell[0], cell[1], color);
			checkNorthEast(cell[0], cell[1], color);
			checkEast(cell[0], cell[1], color);
			checkSouthEast(cell[0], cell[1], color);
			checkSouth(cell[0], cell[1], color);
			checkSouthWest(cell[0], cell[1], color);
			checkWest(cell[0], cell[1], color);
			checkNorthWest(cell[0], cell[1], color);
		}
	}
	
	*/
	
	// @param color is the color of the player for any given instance
	public void findLegalMoves (String color) {
			
		moves.clear(); //clears List moves before filling it with current instant's legal moves.
		String othercolor;
		if (color.equals("WHITE")){
			othercolor = "BLACK";
		} else {
			othercolor = "WHITE";
		}

		final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
		final int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

		for(int i = 0; i <8; i++){
			for (int j = 0; j < dim; j++){
				//if it is your tile, check if there are moves
				if (matrix[i][j].getState().equals(States.valueOf(color))){
					for (int k = 0; k < 8; k++) {
						int sx = dx[k];
						int sy = dy[k];
						if(matrix[i+sx][j+sy].getState().equals(States.valueOf(othercolor))){
							while ((i+sx)<8 && (i+sx)>0 && (j+sy)<8 && (j+sy)>0){
								sx = sx + dx[k];
								sy = sy + dy[k];
								if (matrix[i+sx][j+sy].getState().equals(States.valueOf("LEGALMOVE"))){
									break;
								}
								if (matrix[i+sx][j+sy].getState().equals(States.valueOf("EMPTY"))){
									matrix[i+sx][j+sy].setState(States.LEGALMOVE);
									moves.add(new int[] {i, j, k, i+sx, j+sy});
									//printBoard();
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void appreciateMove () {
		//set score
	}
	
	public void printBoard() {

		System.out.println("------------------------------");
		System.out.print("  ");
		for(int k = 0; k < dim; k++) {
			System.out.print(k+" ");
		}
		System.out.println();

		for(int i = 0; i < dim; i++) {
			System.out.print(i+"|");
			for(int j = 0; j < dim; j++) {

				if(matrix[i][j].getState() == States.EMPTY) {
					System.out.print("_");
				}else if(matrix[i][j].getState() == States.WHITE) {
					System.out.print("W");
				}else if(matrix[i][j].getState() == States.BLACK) {
					System.out.print("B");
				}else if(matrix[i][j].getState() == States.LEGALMOVE) {
					System.out.print("*");
				}
				System.out.print("|");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public int[] readMove() {
		
		int[] opponentsMove = new int [2];
		int i;
		String answer = "";
		System.out.println("It's your turn. Type the coordinates of the tile you choose with a space between them. E.g.: x y");
		do {
			try {	
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
				answer = br.readLine();
				Scanner sc = new Scanner(answer);
				i = 0;
				while (sc.hasNext()) {
					if(sc.hasNextInt()) {
						opponentsMove[i] = sc.nextInt();
						i++;
					}
				}
				sc.close();
				if (isLegalMove(opponentsMove[0], opponentsMove[1])) {
					System.out.println("Your move [" + opponentsMove[0] + ", " + opponentsMove[1] + "] "  + "is valid!");
					return opponentsMove;
				}else {
					throw new IOException();
				}
			}catch (IOException e){
				System.out.println("The given coordinates [" + opponentsMove[0] + ", " + opponentsMove[1] + "] "  + "don't respond to a legal move. You can see your legal moves as asterisks (*) on the board. Please try again.");
			}
		} while (true);	
		
		/*int[] opponentsMove = new int [2];
		int x, y;
		System.out.println("It's your turn. Give the coordinates of the tile you choose. E.g. [x, y]");
		do {
			try {	
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
				x = br.read();	
				y = br.read();
				if (isLegalMove(x, y)) {
					opponentsMove [0] = x;
					opponentsMove [1] = y;
					return opponentsMove;
				}else {
					throw new IOException();
				}
			}catch (IOException e){
				System.out.println("The given coordinates don't respond to a legal move. You can see your legal moves as asterisks (*) on the board. Please try again.");
			}
		} while (true);	*/
	}
		
 	public void printMoves () {
		System.out.println('\n');
		for (int[] cell: moves) {
			for (int i = 0; i < cell.length; i++) {
				System.out.print(cell[i] + "	");
			}
		}
		System.out.print('\n');
	}
	
	public boolean isLegalMove (int x, int y) {
		
		for (int[] move: moves) {
			if (move[3] == x && move[4] == y) {
				return true;
			}
		}
		return false;
	}
	
	public void makeMove () {
		
	}
}

