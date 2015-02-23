package comp2402TreeEditor;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.JOptionPane;

//DISCLAIMER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//==========
//This code is designed for classroom illustration
//It may have intentional omissions or defects that are
//for illustration or assignment purposes

//This code is based on hierarchy that still requires lots of casting
//
//That being said: Please report any bugs to me so I can fix them
//...Lou Nel (ldnel@scs.carleton.ca)
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


public class BinaryHeap extends BTree implements BTreeADT{
	//       =====
    //This class represents a binary heap
	
	private BinaryHeapNode lastNode = null;
    private BinaryHeapNode rightMostNode = null;

	//CONSTRUCTORS==================================================================
	public BinaryHeap() {
    }
	
	
	public DataADT top(){
		//Answer the top (root) data item in the heap which should be the smallest
		//This operation allows us to inspect the top of the heap
		
		if(this.isEmpty()) return null;
		else return root().getData();
	}
	
	public DataADT removeTop(){
		//This is the main operation that a heap is designed for.
		//Remove the top (root) node from heap and return its data item
		//which should be the one with the smallest key value
		//The heap should re-adjust in O(log(n)) time.
		
	   if(this.isEmpty()) return null;
	   
	   DataADT rootData = root().getData();
	   removeNode((BinaryHeapNode) root());
	   return rootData;
	}



    //BTreeADT inteface methods =========================================================
     public void insert(String dataString){
    
       	/*A binary heap tree inserts nodes based on the key value such that for any internal
    	 * node it's key value is smaller, or a at least no bigger, then those of its two children
    	 * This heap condition is recursively true for any internal node.
    	
    	O(log(n)) //heap is at most O(log(n)) high for a heap of n nodes
   	    */
   	    
    	
   	    //System.out.println("BSTree::insert(String)");
    	Data data = new Data(dataString);
    	BinaryHeapNode nodeToAdd = new BinaryHeapNode(data);

    	if (!this.isEmpty() && lastNode != this.getRoot()){
    		BinaryHeapNode parent = (BinaryHeapNode) lastNode.parent();
    		// Determine which method to follow 
    		String type = lastNode.determineTypeForInsert();
    		
    		if (type == "left"){
    			// Add node to right of this node's parent.
    			parent.setRightChild(nodeToAdd);
    			nodeToAdd.setParent(parent);
    			lastNode = nodeToAdd;
        		// In case we have violated the order, restore.
    			restoreOrderUpheap();
    		}
    		else if (type == "right"){
    			// Find the leftmost node in the tree
    			BinaryHeapNode leftmostNode = this.getLeftmostNode();
    			// Add node to left of this node.
    			leftmostNode.setLeftChild(nodeToAdd);
    			nodeToAdd.setParent(leftmostNode);
    			lastNode = nodeToAdd;
        		// In case we have violated the order, restore.
    			restoreOrderUpheap();
    			
    		}
    		else if (type == "right-most"){
    			
    			BinaryHeapNode currentNode = (BinaryHeapNode) lastNode.parent();
    			
    			// Find the node to insert left-under
    			boolean moveUpwardsInTree = true;
    			while (currentNode.leftChild() != null){
    				// Switch the direction if we've hit the root.
    				if (currentNode.isRoot()){
    					moveUpwardsInTree = false;
    				}
    				
    				// Move in the tree depending on the flag
    				if (moveUpwardsInTree){
    					currentNode = (BinaryHeapNode) currentNode.parent();
    				}
    				else {
    					currentNode = (BinaryHeapNode) currentNode.leftChild();
    				}
    			}
    			// Add node to left of this node.
    			currentNode.setLeftChild(nodeToAdd);
    			nodeToAdd.setParent(currentNode);
    			lastNode = nodeToAdd;
        		// In case we have violated the order, restore.
    			restoreOrderUpheap();
    		}
    		
    	}
    	else if (this.isEmpty()){
    		// This is the first node being added to the tree.
    		this.setRoot(nodeToAdd);
    		lastNode = nodeToAdd;
    	}
    	else{
    		// This is the second node being added to the tree
    		// Add node immediately to left of the root
    		lastNode.setLeftChild(nodeToAdd);
    		nodeToAdd.setParent(lastNode);
    		lastNode = nodeToAdd;
    		// In case we have violated the order, restore.
    		restoreOrderUpheap();
    	}

        // Once node is created to insert
        // if node.parent.leftChild == null
        //      rightMostChild = node;
    }
    
    public void remove(String aKeyString){
    	
    //Remove a node whose key value is aKeyString
    //It will take O(n) to find the node to remove but from that point
    //the removal  of the node will take O(log(n)) time
    
     
    BinaryHeapNode nodeToRemove = null;	
    
    //O(N) to find a  node to remove
    //Heaps are not designed for searching so this is an O(n)
    //operation
    for(TreeNodeADT t : this.nodes()){
    	if(t.getData().key().equals(aKeyString)) {
    		nodeToRemove = (BinaryHeapNode) t;
    		break;
    	}
    	
    }
    if(nodeToRemove == null) return;
    
    //do a removal from the heap and maintain the heap property
    //using the private method
    removeNode(nodeToRemove);
    
    }
    
    private void removeNode(BinaryHeapNode nodeToRemove){
    	// Cases
    	
    	if (nodeToRemove.isRoot()){
    		// Node is the root
    		// 1. Identify new last node
    		BinaryHeapNode newLastNode = getNewLastNode();
    		// 2. Replace the root key with the key of the lastnode 
    		this.root().setData(lastNode.getData());
    		// 3. Remove node
    		lastNode.getParent().removeChildNode(lastNode);
    		// 4. Update the last node
    		lastNode = newLastNode;
    		// 5. Restore heap order via downheap.
    		this.restoreOrderDownheap();
    		System.out.println(lastNode.value());
    	}
    	else if (nodeToRemove.isLeaf()){
    		BinaryHeapNode newLastNode = null;
    		if (nodeToRemove == lastNode){
    			newLastNode = getNewLastNode();
    		}
    		// Node has no children
    		nodeToRemove.getParent().removeChildNode(nodeToRemove);
    		
    		if (newLastNode != null){
    			lastNode = newLastNode;
    		}
    		
    		// Check for complete tree, switching right/left if need be
    	}
    	else {
    		// Node has children
    		// Node is the root
    		this.removeSubTreeRoot(nodeToRemove);
    	}
    	//remove the nodeToRemove from the heap and restore heap property
    	//by bubbling bigger key values down.
    	//The removal takes no more than O(log(n)) time
    	
    	
    	//TO DO:
    	//Implement Removal from a Heap, maintaining the Heap Property
    	//Must be O(log(n)) at most for a heap of n nodes
 
    }
    
    

 
    
    //===================================================================================
    
   
	private void removeSubTreeRoot(BinaryHeapNode nodeToRemove) {
		// Not a leaf, so traverse through it's children and 
		// keep switching
		if (!nodeToRemove.isLeaf()){
			Data parentData = (Data) nodeToRemove.getData();
    		BinaryHeapNode smallestChild = this.getSmallestChild(nodeToRemove);
    		
    		// Switch
    		nodeToRemove.setData(smallestChild.getData());		
    		smallestChild.setData(parentData);
    		
    		removeSubTreeRoot(smallestChild);
		}
		else {
			BinaryHeapNode parent = (BinaryHeapNode) nodeToRemove.parent();
			
			// If we are the left child of our parent, switch the right child
			// of our parent to the left to maintain a complete tree
			if (parent.leftChild() == nodeToRemove && parent.rightChild() != null){
				parent.setLeftChild((BTreeNode) parent.rightChild());
			}
			// Remove the node
			parent.removeChildNode(nodeToRemove);
		}
		
	}


	public void createNewRoot(Point aLocation){
    	//create a new root for the tree    	
    	setRoot(new BinaryHeapNode(aLocation));
    }    
    public void createChildForNode(TreeNode aNode, Point aLocation){
    	/*Graphical creating of nodes is not allowed for a binary heap
    	 *since the heap
    	 *wants to control where Nodes are placed.
    	*/
         
        JOptionPane.showMessageDialog(getOwner(), 
        "MUST use ADT Insert to add nodes to Binary Heap", 
        "Not allowed for Binary Heap", 
        JOptionPane.ERROR_MESSAGE);
  
    	
    }

    public boolean allowsGraphicalDeletion(){ 
    //Binary Heaps  do not allow the deletion of arbitrary nodes since the heap
    //must get a chance to restore itself. The TreeADT remove method should be used
    //to delete nodes
      return false;
    }

    //Helper functions
    
    // Function determines the new last node after a removal
    // is about to happen
    private BinaryHeapNode getNewLastNode() {
    	BinaryHeapNode newLastNode = null;
    	// Get type
    	String type = lastNode.determineTypeForRemove();
    	
    	// Case 1: last node is currently a right child. 
		if (type == "right"){
			// New last node will be the left child of the current last node’s parent.
			newLastNode = (BinaryHeapNode) ((BinaryHeapNode) lastNode.parent()).leftChild();
		}
		// Case 2: last node is currently a left most child. 
		else if (type == "left-most"){
		    // New last node will be the rightmost leaf node	
			newLastNode = getRightmostLeafNode();
    	}
		// Case 3: last node is currently a left child
		else if (type == "left"){
    		BinaryHeapNode currentNode = (BinaryHeapNode) lastNode.parent();
    		String direction = "up";
			
    		// Find the new last node by traversing the tree.
			while (newLastNode == null){
				// Exit condition
				if (direction == "down" && currentNode.isLeaf()){
					newLastNode = currentNode;
					// Clear the direction so we don't move.
					direction = "";
				}
				// Go up until a right child or the root is reached.
				if (((BTreeNode) currentNode.parent()).rightChild() == currentNode || currentNode.isRoot()){
					// We've hit a right child or the root is reached, change direction.
					// Go to the left child.
					direction = "left";
				}
				
				if (direction == "up"){
					// Go up
					currentNode = (BinaryHeapNode) currentNode.parent();
				}
				else if (direction == "left"){
					// Go left
					currentNode = (BinaryHeapNode) ((BinaryHeapNode) currentNode.parent()).leftChild();
					// Change direction immediately
					// Go down right until a leaf is reached.
					direction = "down";
				}
				else if (direction == "down"){
					// Go down right
					currentNode = (BinaryHeapNode) currentNode.rightChild();
				}
			}
    	}
		
		return newLastNode;
	}
    
    // Returns the rightmost leaf node
    private BinaryHeapNode getRightmostLeafNode(){
    	// 1. Travel up until a node is a right child OR the Root is reached
		// 2. Travel to the immediate right child.
		// 3. Travel down right until a leaf is reached.
		BinaryHeapNode rightmostNode = null;
		BinaryHeapNode currentNode = (BinaryHeapNode) lastNode.parent();
		
		String direction = "up";
		while (rightmostNode == null){
			// Exit condition
			if (direction == "down" && currentNode.isLeaf()){
				rightmostNode = currentNode;
			}
			// Switching direction from "up" to "right" once we hit either a right child or the root.
			if (currentNode.parent() != null && ((BTreeNode) currentNode.parent()).rightChild() == currentNode && direction == "up"){
				currentNode = (BinaryHeapNode) currentNode.parent();
				// Switch direction, moving right
				direction = "right";
			}
			if (currentNode == this.getRoot()){
				// Switch direction, moving right
				direction = "right";
			}
			
			// Determining direction to move current nod ein.
			if (direction == "up"){
				currentNode = (BinaryHeapNode) currentNode.parent();
			}
			else if (direction == "right"){
				// Move immediately to the right child
				currentNode = (BinaryHeapNode) currentNode.rightChild();
				// Switch direction, moving downwards
				direction = "down";
			}
			else if (direction == "down"){
				currentNode = (BinaryHeapNode) currentNode.rightChild();
			}
		}
		return rightmostNode;
    }
    
    private BinaryHeapNode getLeftmostNode(){
    	// 1. Travel up until a node is a left child OR the Root is reached
		// 2. Travel to the immediate right child.
		// 3. Travel down left until a leaf is reached.
		BinaryHeapNode leftmostNode = null;
		BinaryHeapNode currentNode = (BinaryHeapNode) lastNode.parent();
		
		String direction = "up";
		while (leftmostNode == null){
			// Exit condition
			if (direction == "down" && currentNode.isLeaf()){
				leftmostNode = currentNode;
			}
			// Switching direction if need be.
			if (currentNode.parent() != null && ((BTreeNode) currentNode.parent()).leftChild() == currentNode){
				currentNode = (BinaryHeapNode) currentNode.parent();
				// Switch direction, moving right
				direction = "right";
			}
			if (currentNode == this.getRoot()){
				// Switch direction, moving right
				direction = "right";
			}
			
			// Determining direction to move current nod ein.
			if (direction == "up"){
				currentNode = (BinaryHeapNode) currentNode.parent();
			}
			else if (direction == "right"){
				// Move immediately to the right child
				currentNode = (BinaryHeapNode) currentNode.rightChild();
				// Switch direction, moving downwards
				direction = "down";
			}
			else if (direction == "down"){
				currentNode = (BinaryHeapNode) currentNode.leftChild();
			}
		}
		
		return leftmostNode;
    }
    
    /*
     *  Function restores the order of a Heap via Upheap.
     *
     *  Keep going up and swapping value with parent until
     *  a node is hit that has a lower value than newNode.
     */
    private void restoreOrderUpheap(){
    	BinaryHeapNode currentNode = (BinaryHeapNode) lastNode;
        BinaryHeapNode parentNode;
        Data parentData;
        
        boolean restoredOrder = false;
        
        while (restoredOrder == false){
        	if (currentNode.parent() != null){
	        	// Compare the node we just added to the current node in iteration
	        	int comparisonResult = currentNode.getData().compare(currentNode.parent().getData());
	        	
	        	if (comparisonResult < 0){
	        		// Reference to the parent
	        		parentNode = (BinaryHeapNode) currentNode.parent();
	        		// Reference to the parent data
	        		parentData = (Data) parentNode.getData();
	        		// Swap values
	        		parentNode.setData(currentNode.getData());
	        		currentNode.setData(parentData);
	        		// Change node
	        		currentNode = parentNode;
	        	}
		    	else if (comparisonResult >= 0){
		    		// > 0: The value we have added is a bigger value than it's parent
		    		// = 0: The value we have added is the same value as it's parent
		    		restoredOrder = true;
		    	}
        	}
        	else {
        		restoredOrder = true;
        	}
        }
    }
    /*
     *  Function restores the order of a Heap via Downheap.
     *
     *  
     */
    private void restoreOrderDownheap(){
    	BinaryHeapNode currentNode = (BinaryHeapNode) this.root();
        BinaryHeapNode parentNode;
        Data parentData;
        
        boolean restoredOrder = false;
        
        while (restoredOrder == false){
        	// Determine the smallest of two children
        	BinaryHeapNode smallestChild = this.getSmallestChild(currentNode);
        	
        	if (smallestChild != null){
            	// Compare the smallest child with the current node 
            	int comparisonResult = smallestChild.getData().compare(currentNode.getData());
            	
            	if (comparisonResult < 0){
            		// The current node is bigger than it's child, swap
            		parentData = (Data) currentNode.getData();
            		// Set the currentNode's data to it's child
            		currentNode.setData(smallestChild.getData());
            		// Set the smallestChild's data to the currentNode
            		smallestChild.setData(parentData);
            	}
            	currentNode = smallestChild;
        	}
        	else {
        		restoredOrder = true;
        	}
        }
    }

    
    // Function returns the smallest of the children of a node
    // If the node only has one child, that child will be returned
	private BinaryHeapNode getSmallestChild(BinaryHeapNode parentNode) {
		BinaryHeapNode leftChild = (BinaryHeapNode) parentNode.leftChild();
		BinaryHeapNode rightChild = (BinaryHeapNode) parentNode.rightChild();
		BinaryHeapNode smallestChild = null;
		
		// 1. Node has two children
		if (rightChild != null && leftChild != null){
			// Compare two children
			int comparisonResult = rightChild.getData().compare(leftChild.getData());
			if (comparisonResult < 0){
				// < 0: The rightChild is smaller than the leftChild
				smallestChild = rightChild;
        	}
	    	else if (comparisonResult >= 0){
	    		// > 0: The rightChild is bigger than the leftChild
	    		// = 0: The rightChild is equal to the leftChild
	    		smallestChild = leftChild;
	    	}
		}
		// 2. Node only has a right child
		else if (rightChild != null){
			smallestChild = rightChild;
		}
		// 3. Node only has a left child
		else if (leftChild != null){
			smallestChild = leftChild;
		}
		return smallestChild;
	}

}
