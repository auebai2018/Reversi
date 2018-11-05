package Reversi;


public class Tile {
	
	public enum States {
		EMPTY, WHITE, BLACK, LEGALMOVE;
	}
	
	States state;
	
	Tile () {
		this(States.EMPTY);
	}
	
	Tile (States st){
		this.state = st;
	}
	
	void setState (States st) {
		this.state = st;
	}
	
	States getState() {
		return this.state;
	}
	
}