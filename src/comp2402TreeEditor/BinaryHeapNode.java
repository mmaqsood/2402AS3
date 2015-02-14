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