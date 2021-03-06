package com.chap.memo.memoNodes;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import com.eaio.uuid.UUID;

public class Unode implements MemoNode {
	private Node myNode = null;

	public Unode(Node myNode) {
		if (myNode == null) {
			throw new NullPointerException();
		}
		this.myNode = myNode;
	}

	public Unode(MemoNode myNode) {
		if (myNode == null) {
			throw new NullPointerException();
		}
		this.myNode = myNode.getRealNode();
	}

	public Unode(Unode myNode) {
		// Strange clone like action?
		if (myNode == null) {
			throw new NullPointerException();
		}
		this.myNode = myNode.getRealNode();
	}

	public static MemoNode store(String value) {
		return new Unode(Node.store(value));
	}

	public static MemoNode store(UUID id, String value) {
		return new Unode(Node.store(id, value));
	}
	
	public Node getRealNode() {
		return myNode;
	}

	@Override
	public String toString() {
		return myNode.toString();
	}

	public MemoNode update(String value) {
		myNode = myNode.update(value);
		return myNode;
	}

	/* Get the value at a given time */
	public String valueAt(Date timestamp) {
		return myNode.valueAt(timestamp);
	}

	/* time-ordered list of Nodes, newest first */
	public ArrayList<Node> history() {
		return myNode.history();
	}

	public UUID getId() {
		return myNode.getId();
	}

	public String getValue() {
		return myNode.getValue();
	}

	public Date getTimestamp() {
		return myNode.getTimestamp();
	}

	@Override
	public Arc addParent(MemoNode parent, boolean doOther) {
		Arc arc = myNode.addParent(parent.getRealNode(), doOther);
		myNode = arc.child;
		if (myNode == null)
			System.out.println("Error, actuatedNode going to null, addParent - bool");
		return arc;
	}

	@Override
	public Arc addChild(MemoNode child, boolean doOther) {
		Arc arc = myNode.addChild(child.getRealNode(), doOther);
		myNode = arc.parent;
		if (myNode == null)
			System.out.println("Error, actuatedNode going to null, addChild - bool");
		return arc;
	}

	public Arc addParent(MemoNode node) {
		Arc arc = myNode.addParent(node);
		myNode = arc.child;
		if (myNode == null)
			System.out.println("Error, actuatedNode going to null, addParent");
		return arc;
	}

	public Arc addChild(MemoNode node) {
		Arc arc = myNode.addChild(node);
		myNode = arc.parent;
		if (myNode == null)
			System.out.println("Error, actuatedNode going to null, addChild");
		return arc;
	}

	public MemoNode delParent(MemoNode node) {
		myNode = myNode.delParent(node);
		return this;
	}

	public MemoNode delChild(MemoNode node) {
		myNode = myNode.delChild(node);
		return this;
	}

	public MemoNode delParent(MemoNode node, boolean doOther) {
		myNode = myNode.delParent(node.getRealNode(), doOther);
		return this;
	}

	public MemoNode delChild(MemoNode node, boolean doOther) {
		myNode = myNode.delChild(node.getRealNode(), doOther);
		return this;
	}

	public MemoNode bulkAddParents(ArrayList<MemoNode> parents) {
		myNode = myNode.bulkAddParents(parents);
		return this;
	}

	public MemoNode bulkAddChildren(ArrayList<MemoNode> children) {
		myNode = myNode.bulkAddChildren(children);
		return this;
	}

	public MemoNode bulkDelParents(ArrayList<MemoNode> parents) {
		myNode = myNode.bulkDelParents(parents);
		return this;
	}

	public MemoNode bulkDelChildren(ArrayList<MemoNode> children) {
		myNode = myNode.bulkDelChildren(children);
		return this;
	}

	public ArrayList<MemoNode> getChildren() {
		return myNode.getChildren();
	}

	public ArrayList<MemoNode> getParents() {
		return myNode.getParents();
	}

	public ArrayList<MemoNode> getChildrenByValue(String value, int topx) {
		return myNode.getChildrenByValue(value, topx);
	}

	public ArrayList<MemoNode> getChildrenByRegEx(Pattern regex, int topx) {
		return myNode.getChildrenByRegEx(regex, topx);
	}

	public ArrayList<MemoNode> getChildrenByRange(int lower, int upper, int topx) {
		return myNode.getChildrenByRange(lower, upper, topx);
	}

	public String getPropertyValue(String propName) {
		return myNode.getPropertyValue(propName);
	}

	public MemoNode setPropertyValue(String propName, String propValue) {
		myNode = (Node) myNode.setPropertyValue(propName, propValue);
		return this;
	}

	public ArrayList<MemoNode> search(ArrayList<MemoNode> preambles,
			ArrayList<MemoNode> patterns, int topx) {
		return myNode.search(preambles, patterns, topx);
	}

	public ArrayList<MemoNode> search(MemoNode preamble, MemoNode pattern,
			int topx) {
		return myNode.search(preamble, pattern, topx);
	}

	public ArrayList<MemoNode> search(MemoNode algorithm, int topx) {
		ArrayList<MemoNode> preambles = algorithm.getChildrenByValue(
				"PreAmble", -1);
		ArrayList<MemoNode> patterns = algorithm.getChildrenByValue("Pattern",
				-1);
		return myNode.search(preambles, patterns, topx);
	}
	public String toJSONString(int depth){
		return myNode.toJSONString(depth);
	}
	public JSONTuple toJSON(int depth){
		return myNode.toJSON(depth);
	}
}
