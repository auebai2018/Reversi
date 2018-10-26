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
	
 	/**Tile n;
	Tile ne;
	Tile e;
	Tile se;
	Tile s;
	Tile sw;
	Tile w;
	Tile nw;
	
	Tile () {
		this(States.empty, null, null, null, null, null, null, null, null);
	}

	Tile (States st, Tile n, Tile ne, Tile e, Tile se, Tile s, Tile sw, Tile w, Tile nw) {
		this.state = st;
		this.n = n;
		this.ne = e;
		this.e = e;
		this.se = se;
		this.s = s;
		this.sw = sw;
		this.w = w;
		this.nw = nw;
	}
	
	States getState () {
		return state;
	}
	
	void setState (States st) {
		this.state = st;
	}
	
	Tile getN () {
		return n;
	}
	
	void setN (Tile tl) {
		this.n = tl;
	}
	
	Tile getNE() { 
		return ne;
	}
	
	void setNE(Tile tl) { 
		this.ne = tl;
	}
	
	Tile getE () {
		return e;
	}
	
	void setE (Tile tl) {
		this.e= tl;
	}
	
	Tile getSE () {
		return se;
	}
	
	void setSE (Tile tl) {
		this.se = tl;
	}
	
	Tile getS () {
		return s;
	}
	
	void setS (Tile tl) {
		this.s = tl;
	}
	
	Tile getSW () {
		return sw;
	}
	
	void setSW (Tile tl) {
		this.sw = tl;
	}
	
	Tile getW () {
		return w;
	}
	
	void setW (Tile tl) {
		this.w = tl;
	}
	
	Tile getNW () {
		return nw;
	}
	
	void setNW (Tile tl) {
		this.nw = tl;
	}
	*/
}
