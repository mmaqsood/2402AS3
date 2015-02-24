package comp2402TreeEditor;

import java.awt.Point;

public class TrieNode extends TreeNode{
	private TrieNode childNode;
	
	public TrieNode getChildNode(){ return childNode;}
	
	public void addChildNode(TrieNode childNode){
		// Mark as child inside.
		this.childNode = childNode;
		// Add in the parent class as other UI things are handled there.
		super.addChildNode(childNode);
	}
	public TrieNode() {
	}

	public TrieNode(Point aPoint) {
		super(aPoint);
	}
	
	public TrieNode(Data data) {
		super(data);
	}
}
	
