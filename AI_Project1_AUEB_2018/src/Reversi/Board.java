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
	public int moveIndex;
	Node root = new Node();
	int depth = 0;
	Tile[][] matrix;
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
		matrix = board;
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
		target = source;
		/*for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				target [i][j].setState(source[i][j].getState());
				
			}
		}
		*/
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
			
		clearMoves(); //clears List moves before filling it with current instant's legal moves.
		String othercolor;
		if (color.equals("WHITE")){
			othercolor = "BLACK";
		} else {
			othercolor = "WHITE";
		}
		
		
		if (color.equals(Main.myColor)) {
			findLegalMoves(myTiles, color, othercolor);
		}else if (color.equals(Main.opponentsColor)){
			findLegalMoves(opponentTiles, color, othercolor);
		}else {
			System.err.println("Something is wrong. check method findlegalmoves.");
		}
		
 	}
	
 	public void findLegalMoves (List<int[]> list, String color, String othercolor){
	 	for (int[] tile: list) {
	 		int i = tile[0];
	 		int j = tile[1];
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

 	
		

 	public void clearMoves() {
 		for(int[] mv: moves) {
 			matrix[mv[0]][mv[1]].setState(States.EMPTY);
 		}
 		moves.clear();
 	}
 	
	public int appreciateMove (int x, int y) {
		return MY[x][y];
	}
	
	public int appreciateMove (int i,int j, String othercolor) {
		int count=0;
		if (matrix[i-1][j-1].getState().equals(States.valueOf(othercolor))) {
			count++;
		}
		if (matrix[i-1][j].getState().equals(States.valueOf(othercolor))) {
			count++;
		}
		if (matrix[i-1][j+1].getState().equals(States.valueOf(othercolor))) {
			count++;
		}
		if (matrix[i][j-1].getState().equals(States.valueOf(othercolor))) {
			count++;
		}
		if (matrix[i][j+1].getState().equals(States.valueOf(othercolor))) {
			count++;
		}
		if (matrix[i+1][j-1].getState().equals(States.valueOf(othercolor))) {
			count++;
		}
		if (matrix[i+1][j].getState().equals(States.valueOf(othercolor))) {
			count++;
		}
		if (matrix[i+1][j+1].getState().equals(States.valueOf(othercolor))) {
			count++;
		}
		//counting the opponents color moves in the matrix-that means we have more possible moves so more moves better score
		
		
		 return MY[i][j]/2+count*5;
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

	
	public void readMove() {
		
		int[] opponentsMove = new int [2];
		int i;
		String answer = "";
		System.out.println("It's your turn. Type the coordinates of the tile you choose with a space between them. E.g.: x y \nN O T E:: x value is the number of the row while y is the number of the column.");
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
				/*if (isLegalMove(opponentsMove[0], opponentsMove[1])) {
					System.out.println("Your move [" + opponentsMove[0] + ", " + opponentsMove[1] + "] "  + "is valid!");
					break;*/
				moveIndex = findPairInList(moves, opponentsMove[0], opponentsMove[1], 3, 4);
				if(moveIndex < moves.size()) {
					System.out.println("Your move [" + opponentsMove[0] + ", " + opponentsMove[1] + "] "  + "is valid!");
					break;
				}else {
					throw new IOException();
				}
				
			}catch (IOException e){
				System.err.println("The given coordinates [" + opponentsMove[0] + ", " + opponentsMove[1] + "] "  + "don't respond to a legal move. You can see your legal moves as asterisks (*) on the board. Please try again.");
			}catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("You entered more than two values. Try again.");
			}
		} while (true);	
		
	}
		
 	//utility method
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
 	 
 	public int findPairInList(List<int[]> list, int x, int y, int k, int l) {
 		int i = 0;
		for (int[] arr: list) {
			if (arr[k] == x && arr[l] == y) {
				//moveIndex = i;
				break;
			}
			i++;
		}
		return i;
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
	
	public void makeMove (String color) {
		int offset = 0;
		int x = 0;
		int y = 0;
		int k = 0;
		int[] move = moves.get(moveIndex); 
		updateTiles(States.valueOf(color), move[3], move[4]);
		if (move[2] > 3) {
			offset = -4;
		}else {
			offset = 4;
		}
		x = move[3];
		y = move[4];
		k = move[2] + offset;
		while (x != move[0] || y != move[1]) {
			x += dx[k];
			y += dy[k];
			updateTiles(States.valueOf(color), x, y);
			System.out.println("x: " + x + " y: " + y);
		}

	}
	
	//to ebala giati den yparxei allos tropos na kanei thn kinhsh pou 8elw akribws
	//h apo panw kanei mono thn kinhsh pou grafei o paikths
	public void makeMoveOpp(String color, int[] move) { //, Board b) {
		int offset = 0;
		int x = 0;
		int y = 0;
		int k = 0;
		updateTiles(States.valueOf(color), move[3], move[4]);
		if (move[2] > 3) {
			offset = -4;
		}else {
			offset = 4;
		}
		x = move[3];
		y = move[4];
		k = move[2] + offset;
		while (x != move[0] || y != move[1]) {
			x += dx[k];
			y += dy[k];
			updateTiles(States.valueOf(color), x, y);
			System.out.println("x: " + x + " y: " + y);
		}
	}
	
	public void simulateMoves(String color, Node node, int depth) {
		//moveIndex = 0;
		String oppcolor;
		if(color == "WHITE") {
			oppcolor = "BLACK";
		}else {
			oppcolor = "WHITE";
		}
		Board simboard = new Board(matrix);							//computer's
		Board temp = new Board(simboard.matrix);					//opponent's
		Board save = new Board(simboard.matrix);					//original
		System.out.print("________________SAVE");
		save.printBoard();
		System.out.print("________________SAVE");
		simboard.findLegalMoves(color);																		//find sim's available moves
		while(depth++ < 2) {
			simboard.moveIndex = 0;
			for(int[] move : simboard.moves) {
				simboard.copyBoard(simboard.matrix, save.matrix);											//simboard.matrix, hasn't changed. at the first loop, it equals save.matrix
				System.out.println("Move in: " + move[3] + "," + move[4] + " depth " + depth);
				Node sim = new Node(appreciateMove(move[3], move[4], oppcolor), node, null, color, move[3], move[4]);
				node.setChild(sim);
				simboard.makeMove(oppcolor);																//make a move for the computer.
				temp.copyBoard(temp.matrix, simboard.matrix);												//temp's matrix = updated board (after the move)
				temp.findLegalMoves(oppcolor);																//find temp's available moves
				for(int[] moveopp : temp.moves) {
					System.out.println("Move in: " + moveopp[3] + "," + moveopp[4] + " loop " + depth);
					Node sim2 = new Node(appreciateMove(moveopp[3], moveopp[4], oppcolor), sim, null, oppcolor, moveopp[3], moveopp[4]);
					sim.setChild(sim2);
				}
				simboard.moveIndex++;
			}
		}
		for(Node ch : this.root.getChildren()) {
			System.out.println("This: " + ch.getScore() + " for move (" + ch.coord[0] + "," + ch.coord[1] + ")");
			
		}
	}
	
	public Node MiniMax(Node root, String color) {
		
		Node opt = new Node();
		
		opt = this.max(root.children);
		//if(root.getChildren().size() == 0) {return opt;}
		
		return opt;
	}
	
	public Node max(List<Node> e_moves) {
		Node maxMove = new Node();
		if(e_moves != null) {
			for(Node move : e_moves) {
				if(move.getScore() >= maxMove.getScore()) {
					maxMove = move;
				}
			}
		}
		return maxMove;
	}
	
	public Node min(List<Node> e_moves) {
		Node minMove = new Node(200, null, null, "", -1, -1);
		if(e_moves != null) {
			for(Node move : e_moves) {
				if(move.getScore() <= minMove.getScore()) {
					minMove = move;
				}
			}
		}
		return minMove;
	}
	
	public void printTree(Node node) {
		
		if (node.getChildren().size() == 0) {return;}
		
		for(Node ch : node.getChildren()) {
			
			System.out.println("This: " + ch.getScore() + " for move (" + ch.coord[0] + "," + ch.coord[1] + ")");
			printTree(ch);
		}
		
	}
}

