package Reversi;

import Reversi.Tile.States;

public class Board {

	public static final int dim = 8;
	Tile [][] matrix;
	
	
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
		board[dim/2][dim/2].setState(States.WHITE);
		board[dim/2][dim/2 + 1].setState(States.BLACK);
		board[dim/2 + 1][dim/2].setState(States.WHITE);
		board[dim/2 + 1][dim/2 + 1].setState(States.BLACK);	
	}
	
	public boolean checkNorth (int x, int y) {

		if (x != 0) {
			if (matrix[x-1][y].getState().equals(States.valueOf(Main.opponentsColor)));
				return true;
		}
		return false;	
	}
	
	public boolean checkNorthEast (int x, int y) {
		
		if (x != 0 && y != dim) {
			if (matrix[x-1][y+1].getState().equals(States.valueOf(Main.opponentsColor))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkEast (int x, int y) {
		
		if (y != dim) {
			if (matrix[x][y+1].getState().equals(States.valueOf(Main.opponentsColor))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkSouthEast (int x, int y) {
		
		if (x != dim && y != dim) {
			if (matrix[x+1][y+1].getState().equals(States.valueOf(Main.opponentsColor))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkSouth (int x, int y) {
		
		if (x != dim) {
			if (matrix[x+1][y].getState().equals(States.valueOf(Main.opponentsColor))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkSouthWest (int x, int y) {
		
		if (x != dim && y != 0) {
			if (matrix[x+1][y-1].getState().equals(States.valueOf(Main.opponentsColor))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkWest (int x, int y) {
		
		if (y != 0) {
			if (matrix[x][y-1].getState().equals(States.valueOf(Main.opponentsColor))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkNorthWest (int x, int y) {
		
		if (x != 0 && y != 0) {
			if (matrix[x-1][y-1].getState().equals(States.valueOf(Main.opponentsColor))) {
				return true;
			}
		}
		return false;
	}
}

