package comp2402TreeEditor;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Trie extends Tree{
	//       =====
	
	/*
	 * Default constructor
	 */
	public Trie(){
	}
	
	 
	
	/*
	 * Performs an insert of a Trie.
	 */
	public void insert(String word){
	    // Initialize the root if it hasn't been
		if (this.root() == null){
			// A Trie has a Root with no value (or Key in the DataADT case).
			setRoot(new TrieNode(new Data(" ")));
		}

		// Cases
		
		// Case 0: There has already been a word added.
		if (this.root().children().size() > 0){
	    	this.addWordTraversely(word);
	    }
		// Case 1: There are no words added. This is the first.
		else {
			this.addWord((TrieNode) this.root(), word, word);
		}
	
	}
	
	/*
	 * Performs a remove on a Trie
	 */
	public void remove(String word){
		// Find the root of the node
		TrieNode currentNode = this.findWordTraversely(word, false);
		
		((TrieNode) currentNode.parent()).removeChildNode(currentNode);
		// If any letter in the word I am removing is the parent
		// of another word, make that other word un touched.
		
		// You lose 6 marks if you don't provide a remove method.
	}
	 
	/*
	 * Performs a find on a Trie
	 */
	public DataADT find(String word) {
		this.findWordTraversely(word, true);
		
		return null;
    }   
	
	//===================================================================================
    // Traversal Functions
    //===================================================================================
	
	/*
	 * Investigates the root in order to determine if a word is in the tree.
	 */
	private TrieNode findWordTraversely(String word, boolean highlightNodes) {
		// Clear selections.
		this.clearSelections();
		
		// Get the root to begin traversing.
		TrieNode root = getLetterRoot(String.valueOf(word.charAt(0)));
		
		// Tells us if we found a complete word
		String lettersFound = "";
		
		// Case 0: Found the sub-tree to traverse
		if (root != this.root()){
			// Traverse and mark as red
			// Index to letters in our word
			int letterIndex = 0;
			
			// Starting off our iteration with this node.
			TrieNode currentNode = root;
			
			// Keep going 
			while (currentNode != null){
				// Current letter we are looking at of our word.
				String letter = "";
				
				if (!(letterIndex >= word.length())){
					// Update value
					letter = String.valueOf(word.charAt(letterIndex));
				}
				
				// Compare
				if (currentNode.getData().key().equals(letter)){
					if (currentNode.isSelected() == false && highlightNodes == true){
						currentNode.toggleSelected();
					}
					lettersFound += letter;
					letterIndex++;
				}
				else {
					if (currentNode.isSelected() ){
						currentNode.toggleSelected();
					}
				}
				
				// Move along
				currentNode = currentNode.getChildNode();
			}
			
			// Returns the head of the node if we found the word
			if (lettersFound.equals(word)){
				return root;
			}
		}
		// Case 1: Did not find the subtree that has the first letter of the word
		else {
			if (root == null){
				// Return as no point in inspecting further.
				return null;
			}
		}
		return null;
    }   
	
	/*
	 * Investigates the root's children if the beginning letter has already been added.
	 * 
	 * Then adds under that existing root.
	 * 
	 * NOTE: Currently we are only looking at the root's IMMEDIATE children. 
	 * We MIGHT need to inspect further. Confirmation from PROF required.
	 */
	private void addWordTraversely(String word){
		// Get the root to begin traversing.
		TrieNode root = getLetterRoot(String.valueOf(word.charAt(0)));
		
		// Only traverse down a sub-tree if we have found a sub tree
		if (root != this.root()){
			// Index to letters in our word
			int letterIndex;
			
			// Determine index based on length
			if (word.length() == 1){
				letterIndex = 0;
			}
			else {
				letterIndex = 1;
			}
			
			// Starting off our iteration with this node.
			TrieNode currentNode = root.getChildNode();
			
			// Help with logic, remove later, merge with currentNode
			TrieNode lastNode = root;
			
			// In the case that our word is a small portion of a word already, flag it
			// since we need to mark it with a label
			TrieNode markNode = null;
			
			// Keep going 
			while (currentNode != null){
				// Current letter we are looking at of our word.
				String letter = String.valueOf(word.charAt(letterIndex));
				// Compare
				if (currentNode.getData().key().equals(letter)){
					lastNode = currentNode;
					letterIndex++;
				}
				
				// Check if we have run out of letters to go over.
				// If we have, this mean that our word is a small portion of a word
				// already in the tree, so flag it so that it can be marked later on.
				if (letterIndex >= word.length()){
					markNode = currentNode;
					currentNode = null;
				}
				else{
					// Move along
					currentNode = currentNode.getChildNode();
				}
			}
			
			// Add the word if there is nothing to mark.
			if (markNode == null && root != lastNode){
				// Add the word.
				this.addWord(lastNode, word.substring(letterIndex), word);
			}
			else if (root == lastNode){
				// Mark the root
				root.setNodeLabel(word);
			}
			else {
				// Mark if there is something to mark.
				markNode.setNodeLabel(word);
			}
		}
		else {
			this.addWord((TrieNode) this.root(), word, word);
		}
	}
	
    //===================================================================================
    // Helper Functions
    //===================================================================================
	
	/*
	 * This function will investigate the top level root to locate 
	 * a sub-tree that's root is the same as a letter.
	 * 
	 * If no root is found, the root will be returned.
	 */
	private TrieNode getLetterRoot(String letter){
		TrieNode letterNode = new TrieNode(new Data(letter));
		
		// Start search on the root.
		ArrayList<TreeNodeADT> rootChildren = this.root().children();
		// Last node
		TrieNode lastNode = (TrieNode) this.root();
		// Iterate through the top root's children to determine
		// if a child tree exists that's root is our letter.
		for (int i = 0; i < rootChildren.size(); i++){
			if (rootChildren.get(i).getData().key().equals(letter)){
				return (TrieNode) rootChildren.get(i);
			}
		}
		// No child was found, return the root.
		return (TrieNode) this.root();
	}
	
	/*
	 * Transforms a word into a nested-children tree of letters.
	 * 
	 * I.E 'apple' becomes 'a->p->p->l->e'
	 */
	private void addWord(TrieNode root, String word, String label){
		// This variable changes to whatever the last added node is 
		// in the iteration in order to properly add nested children.
		TrieNode currentParent = root;
		// Iterate over the characters and add them one by one.
		for(int i = 0; i < word.length(); i++){ 
			// Current letter in the iteration
			String currentLetter = String.valueOf(word.charAt(i));
			// Data for our new node.
			Data newNodeData = new Data(currentLetter);
			// Create the new node.
			TrieNode newNode = new TrieNode(newNodeData);
			// Add the node.
			currentParent.addChildNode(newNode);
			// Update our track-variable
			currentParent = newNode;
		}
		currentParent.setNodeLabel(label);
	}
	
	//===================================================================================
    // Behaviour Restriction functions
    //===================================================================================
	
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

