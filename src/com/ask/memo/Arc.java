package com.ask.memo;

public class Arc {
	public Node parent = null;
	public Node child = null;
	public Arc(){}
	public Arc(Node a,Node b){
		this.parent=a;
		this.child=b;
	}
}
