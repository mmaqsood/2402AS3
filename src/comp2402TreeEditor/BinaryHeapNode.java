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
	public String determineType(){
		// TODO: Implement rightMost ability
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