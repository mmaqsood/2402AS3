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
    	
    	//TO DO: Replace this code with your code to implement the heap
    	boolean DoThisForNow = true;
    	if(DoThisForNow){
    		super.insert(dataString);
    		return;
    	}
   	
    	//TO DO Implement insertion into a Binary Heap
    	//Must be O(log(n)) time at most for heap of n nodes
    	
    	
    	
    }
    
    public void remove(String aKeyString){
    	
    //Remove a node whose key value is aKeyString
    //It will take O(n) to find the node to remove but from that point
    //the removal  of the node will take O(log(n)) time
    
     //TO DO: remove the next few lines once you have completed your removeNode() method
     boolean DoThisForNow = true;
     if(DoThisForNow) {
    	 super.remove(aKeyString);
    	 return;
     }
     //END OF TO DO SECTION
    
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
    	//remove the nodeToRemove from the heap and restore heap property
    	//by bubbling bigger key values down.
    	//The removal takes no more than O(log(n)) time
    	
    	
    	//TO DO:
    	//Implement Removal from a Heap, maintaining the Heap Property
    	//Must be O(log(n)) at most for a heap of n nodes
    	
 
    }
    
    

 
    
    //===================================================================================
    
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

}
