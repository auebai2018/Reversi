package Reversi;

import java.util.ArrayList;
import java.util.List;


public class Node {
	public int score;
	public int[] coord = new int[2];
	public Node father;
	public List <Node> children = new ArrayList<>();
	public String color;
	
	public Node() {
		this.score = -1;
		this.father = null;
		//this.child = null;
		this.color = "";
	}
	
	public Node(int score, Node father, Node child, String color, int coordx, int coordy) {
		this.score = score;
		this.father = father;
		//this.children.add(child);
		this.color = color;
		this.coord[0] = coordx;
		this.coord[1] = coordy;
		System.out.println("In the tree.");
	}
	
	public int getScore() {
		return this.score;
	}
	
	public Node getFather() {
		return this.father;
	}
	
	public List<Node> getChildren() {
		return this.children;
	}
	
	public String getColor() {
		return this.color;
	}

	public void setChild(Node child) {
		this.children.add(child);
	}
	
	public void traverseNode(Node node) {
		System.out.println(node.color);
		if(node.children != null) {
			
			for(Node ch : node.children) {
				
				traverseNode(ch);
			}
		}
		
	}
}
