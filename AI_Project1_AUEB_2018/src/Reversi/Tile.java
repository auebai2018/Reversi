package Reversi;


public class Tile {
	
	public enum States {
		white, black, empty, outOfBounds;
	}
	
	States state;
	
	Tile () {
		this(States.empty);
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
