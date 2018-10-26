package Reversi;

import Reversi.Tile.States;

public class Board {

	public static final int dim = 8;
	
	public Board () {
		
		Tile [][] matrix = new Tile [8][8];
		initBoard(matrix);	
	}
	
	public void initBoard (Tile[][] board) {
		
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				Tile tl = new Tile();
				board [i][j] = tl;
			}
		}
		board[dim/2][dim/2].setState(States.white);
		board[dim/2][dim/2 + 1].setState(States.black);
		board[dim/2 + 1][dim/2].setState(States.white);
		board[dim/2 + 1][dim/2 + 1].setState(States.black);	
	}
	
}
