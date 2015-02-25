package comp2402TreeEditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class TrieNode extends TreeNode{
	private TrieNode childNode;
	private boolean drawAsCircle = true;
	
	public TrieNode getChildNode(){ return childNode;}
	public TrieNode getShape(){ return childNode;}
	public void setDrawAsCircle(boolean drawAsCircle){ this.drawAsCircle = drawAsCircle;}
	
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
	
	/*
	 * Override parent's draw method as a TrieNode gets drew as squares for word endings
	 */
	public void draw(Graphics2D aPen) {
        TreeNode parent = ((TreeNode) this.parent());
        
        //draw connection to parent
        if(this.parent() != null)
            aPen.drawLine(parent.getLocation().x, parent.getLocation().y,
                     getLocation().x, getLocation().y);

        // Draw a filled circle/rect around the center of the node
		 aPen.setColor(getDrawingColor());
		
		 
		// Will draw circle/rectangle depending on property.
		if (this.drawAsCircle == true){
	        aPen.fillOval(this.getLocation().x - TreeNode.RADIUS, this.getLocation().y - TreeNode.RADIUS, 
						   TreeNode.RADIUS * 2, TreeNode.RADIUS * 2);
		}
		else {
			aPen.fillRect(this.getLocation().x - TreeNode.RADIUS, this.getLocation().y - TreeNode.RADIUS, 
					   TreeNode.RADIUS * 2, TreeNode.RADIUS * 2);
		}
        // Draw a black border around the circle
        aPen.setColor(Color.black);
        
        if (this.drawAsCircle == true){
            aPen.drawOval(this.getLocation().x - TreeNode.RADIUS, this.getLocation().y - TreeNode.RADIUS, 
    					   TreeNode.RADIUS * 2, TreeNode.RADIUS * 2);
		}
		else {
	        aPen.drawRect(this.getLocation().x - TreeNode.RADIUS, this.getLocation().y - TreeNode.RADIUS, 
						   TreeNode.RADIUS * 2, TreeNode.RADIUS * 2);
		}
		 
		 //draw the label on the node
		 drawNodeLabel(aPen);
	}
}
	
