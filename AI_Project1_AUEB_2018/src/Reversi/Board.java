package Reversi;

import java.util.ArrayList;
import java.util.List;
import Reversi.Tile.States;

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
		matrix = new Tile [8][8];
		initBoard(matrix);	
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
		printBoard();
		
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
	
	// param color is the color of the player for any given instance
	public void findLegalMoves (String color) {
			
		moves.clear(); //clears List moves before filling it with current instant's legal moves.
		String othercolor;
		if (color.equals("WHITE")){
			othercolor = "BLACK";
		} else {
			othercolor = "WHITE";
		}

		final int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
		final int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};

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
									printBoard();
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
	
	/*
	
	//Every check�(x,y) method, checks if the given coordinates are not on the edges of the matrix,
	//if the neighboring cell is occupied by the opponent (so, this direction may lead to a possible move)
	//and if it finds an EMPTY cell (so, we have a legal move), the method adds the coordinates to the moves List.
	
	public boolean checkNorth (int x, int y, String color) {

		if (x != 0) {
			if (matrix[x-1][y].getState().equals(States.valueOf(color))) {
				x--;
				checkNorth(x, y, color);
			}else if (matrix[x-1][y].getState().equals(States.valueOf("EMPTY"))){
				//int score = appreciateMove(x, y);
				//moves.add(new int[] {orx, ory, 1, x, y, score});
				moves.add(new int[] {orx, ory, 1, x, y});
				return true;
			}
		}
		return false;	
	}
	
	public boolean checkNorthEast (int x, int y, String color) {
		
		if (x != 0 && y != dim) {
			if (matrix[x-1][y+1].getState().equals(States.valueOf(color))) {
				x--;
				y++;
				checkNorthEast(x, y, color);
			}else if (matrix[x-1][y+1].getState().equals(States.valueOf("EMPTY"))) {
				moves.add(new int[] {x--, y++});
				return true;
			}
		}
		return false;
	}
	
	public boolean checkEast (int x, int y, String color) {
		
		if (y != dim) {
			if (matrix[x][y+1].getState().equals(States.valueOf(color))) {
				y--;
				checkEast (x, y, color);
			}else if (matrix[x][y+1].getState().equals(States.valueOf("EMPTY"))) {
				moves.add(new int[] {x, y++});
				return true;
			}
		}
		return false;
	}
	
	public boolean checkSouthEast (int x, int y, String color) {
		
		if (x != dim && y != dim) {
			if (matrix[x+1][y+1].getState().equals(States.valueOf(color))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkSouth (int x, int y, String color) {
		
		if (x != dim) {
			if (matrix[x+1][y].getState().equals(States.valueOf(color))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkSouthWest (int x, int y, String color) {
		
		if (x != dim && y != 0) {
			if (matrix[x+1][y-1].getState().equals(States.valueOf(color))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkWest (int x, int y, String color) {
		
		if (y != 0) {
			if (matrix[x][y-1].getState().equals(States.valueOf(color))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkNorthWest (int x, int y, String color) {
		
		if (x != 0 && y != 0) {
			if (matrix[x-1][y-1].getState().equals(States.valueOf(color))) {
				return true;
			}
		}
		return false;
	}
	*/
}

