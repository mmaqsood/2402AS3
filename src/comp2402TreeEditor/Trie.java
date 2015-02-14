package comp2402TreeEditor;

import java.awt.Point;

import javax.swing.JOptionPane;


public class Trie extends Tree{
	//       =====
	//TO DO: write missing code
	
	
    public void createChildForNode(TreeNode aNode, Point aLocation){
	    	/*Graphical creating of nodes is not allowed for a Trie
	    	 *since it must control where Nodes are placed.
	    	*/
	         
	        JOptionPane.showMessageDialog(getOwner(), 
	        "MUST use ADT Insert to add nodes to Trie", 
	        "Operation Not allowed for Trie", 
	        JOptionPane.ERROR_MESSAGE);
	  
	    	
	}

	public boolean allowsGraphicalDeletion(){ 
	    //Tries  do not allow the deletion of arbitrary nodes since the Trie
	    //must get a chance to restore itself. The TreeADT "remove" method should be used
	    //to delete nodes
	      return false;
	}
}

