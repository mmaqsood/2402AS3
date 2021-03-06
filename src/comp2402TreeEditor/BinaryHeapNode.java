package comp2402TreeEditor;

import java.util.*;
import java.awt.*;
import java.io.*;

//DISCLAIMER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//==========
//This code is designed for classroom illustration
//It may have intentional omissions or defects that are
//for illustration or assignment purposes
//
//This code is based on a hierarchy that still requires lots of casting

//That being said: Please report any bugs to me so I can fix them
//...Lou Nel (ldnel@scs.carleton.ca)
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


public class BinaryHeapNode extends BTreeNode implements BTreeNodeADT{
	//This class represents a node in a Binary Heap
	
	/*
	 *	Returns wheter the current node is one of the following:
	 *	- Left Child
	 *	- Right Child
	 *	- Right Most Child
	 */
	public String determineTypeForInsert(){
		BinaryHeapNode rightMostNode = null;
		BinaryHeapNode parent = (BinaryHeapNode) this.parent();
		
		if (parent.leftChild() == this){
			return "left";
		}
		else if (parent.rightChild() == this){
			if (this.isRightmostNode()){
				return "right-most";
			}
			else {
				return "right";
			}
		}
		return null;
	}
	
	/*
	 *	Returns wheter the current node is one of the following:
	 *	- Left Child
	 *	- Left Most Child
	 *	- Right Child
	 *	- Right Most Child
	 */
	public String determineTypeForRemove(){
		BinaryHeapNode rightMostNode = null;
		BinaryHeapNode parent = (BinaryHeapNode) this.parent();
		
		if (parent != null && parent.leftChild() != null && parent.leftChild() == this){
			if (this.isLeftmostNode()){
				return "left-most";
			}
			else {
				return "left";
			}
		}
		else if (parent != null && parent.rightChild() != null && parent.rightChild() == this){
			return "right";
		}
		else {
			return "root";
		}
	}
	
	// Tells us if the node is the leftmost child in the tree
	private boolean isLeftmostNode(){
		boolean isLeftmostNode = false;
		// Traverse through the tree
		BinaryHeapNode currentNode = this;
		
		while (currentNode.parent() != null){
			// Check if this is a right child
			isLeftmostNode = ((BinaryHeapNode) currentNode.parent()).leftChild() == currentNode;
			// Move up
			currentNode = (BinaryHeapNode) currentNode.parent();
		}
		return isLeftmostNode;
	}
	
	// Tells us if the node is the rightmost child in the tree
	private boolean isRightmostNode(){
		boolean isRightmostNode = false;
		// Traverse through the tree 
		BinaryHeapNode currentNode = this;
		
		while (currentNode.parent() != null){
			// Check if this is a right child
			isRightmostNode = ((BinaryHeapNode) currentNode.parent()).rightChild() == currentNode;
			// Move up
			currentNode = (BinaryHeapNode) currentNode.parent();
		}
		return isRightmostNode;
		
	}

	// CONSTRUCTORS ========================================================
	public BinaryHeapNode() {
	}

	public BinaryHeapNode(Point aPoint) {
		super(aPoint);
	}
	
	public BinaryHeapNode(Data data) {
		super(data);
	}

    


}