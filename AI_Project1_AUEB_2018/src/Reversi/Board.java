package Reversi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Reversi.Tile.States;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Board {

	public static final int dim = 8;
	public static int moveIndex;
	Tile [][] matrix;
	final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
	final int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
	List <int[]> myTiles = new ArrayList<>(); 
	List <int[]> opponentTiles = new ArrayList<>();
	List <int[]> moves = new ArrayList<>();			/***
													 * Every array on the List moves, must contain 6 elements.
													 *[0]::original x dimension
													 *[1]::original y dimension
													 *[2]::number of direction (0:N, 1:NE, 2:E, 3:SE, 4:S, 5:SW, 6:W, 7:NW)
													 *[3]::final x dimension
													 *[4]::final y dimension
													 *[5]::score ???
													 */
	public static final int[][] MY = {
			{200, 0, 100, 100, 100,100,0,200},
			{0,0,10,10,10,10,0,0},
			{100,10,30,30,30,30,10,100},
			{100,10,30,30,30,30,10,100},
			{100,10,30,30,30,30,10,100},
			{100,10,30,30,30,30,10,100},
			{0,0,10,10,10,10,0,0},
			{200, 0, 100, 100, 100,100,0,200}
	};
	
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
		updateTiles(States.WHITE, dim/2 - 1, dim/2 - 1);
		updateTiles(States.BLACK, dim/2 - 1, dim/2);
		updateTiles(States.BLACK, dim/2, dim/2 - 1);
		updateTiles(States.WHITE, dim/2, dim/2);
		
	}
	
	public void copyBoard (Tile[][] target, Tile [][] source) {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				target [i][j] = source[i][j];
				
			}
		}
	}

	private void updateTiles (States st, int x, int y) {
		
		if(matrix[x][y].getState() == States.EMPTY) {
			if(st == States.valueOf(Main.myColor)) {
				myTiles.add(new int[] {x, y});
			}else if (st == States.valueOf(Main.opponentsColor))  {
				opponentTiles.add(new int[] {x, y});
			}
			matrix[x][y].setState(st);
		}else if (matrix[x][y].getState() == States.valueOf(Main.myColor)){ //change board after opponents move.
			opponentTiles.add(myTiles.remove(findPairInList(myTiles, x, y)));
		}else if (matrix[x][y].getState() == States.valueOf(Main.opponentsColor)) { //change board after my move.
			myTiles.add(opponentTiles.remove(findPairInList(opponentTiles, x, y)));
		}
		matrix[x][y].setState(st);
	}
	
	// @param color is the color of the player for any given instance
 	public void findLegalMoves (String color) {
			
		moves.clear(); //clears List moves before filling it with current instant's legal moves.
		String othercolor;
		if (color.equals("WHITE")){
			othercolor = "BLACK";
		} else {
			othercolor = "WHITE";
		}

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
									moves.add(new int[] {i, j, k, i+sx, j+sy, appreciateMove(i+sx, j+sy)});
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
	
	public int appreciateMove (int x, int y) {
		return MY[x][y];
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
					matrix[i][j].setState(States.EMPTY);
				}
				System.out.print("|");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/*public int[] readMove() {
		
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
		
	}*/
	
	public void readMove() {
		
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
					break;
				}else {
					throw new IOException();
				}
			}catch (IOException e){
				System.out.println("The given coordinates [" + opponentsMove[0] + ", " + opponentsMove[1] + "] "  + "don't respond to a legal move. You can see your legal moves as asterisks (*) on the board. Please try again.");
			}
		} while (true);	
		
	}
		
 	public void printList (List <int[]> list) {
		System.out.println('\n');
		for (int[] arr: list) {
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + "	");
			}
			System.out.print('\n');
		}
		System.out.print('\n');
	}	
 	
 	public int findPairInList(List<int[]> list, int x, int y) {
 		int i = 0;
		for (int[] arr: list) {
			if (arr[0] == x && arr[1] == y) {
				break;
			}
			i++;
		}
		return i;
 	 }
 	 
 	public boolean findPairInList(List<int[]> list, int x, int y, int k, int l) {
 		int i = 0;
		for (int[] arr: list) {
			if (arr[k] == x && arr[l] == y) {
				moveIndex = i;
				return true;
			}
			i++;
		}
		return false;
 	 }
 	
 	public boolean isLegalMove (int x, int y) {
		int i = 0;
		for (int[] move: moves) {
			if (move[3] == x && move[4] == y) {
				moveIndex = i;
				return true;
			}
			i++;
		}
		return false;
	}
	
	//should work both for opponent and computer. probably, must create to different methods.
	public void makeMove () {
		int offset = 0;
		int x = 0;
		int y = 0;
		int k = 0;
		int[] move = moves.get(moveIndex); 
		//matrix[move[3]][move[4]].setState(States.valueOf(Main.opponentsColor));
		//opponentTiles.add(new int[] {move[3], move[4]});
		updateTiles(States.valueOf(Main.opponentsColor), move[3], move[4]);
		if (move[2] > 3) {
			offset = -4;
		}else {
			offset = 4;
		}
		//System.out.println("right over here!!");

		x = move[3];
		y = move[4];
		k = move[2] + offset;
		while (x != move[0] || y != move[1]) {
			x += dx[k];
			y += dy[k];
			updateTiles(States.valueOf(Main.opponentsColor), x, y);
			System.out.println("x: " + x + " y: " + y);
		}

	}
}

