/*
 * QuestionNode.java is a node object that holds the information to play the 20 questions game, 
 * including the question/answer of each piece and any left/right children it may have.
 */

public class QuestionNode {
	public String data; // Remembers the question/answer of the node.
	public QuestionNode left; // Remembers the left child of this node.
	public QuestionNode right; // Remembers the right child of this node.  
	
	//Post: Accepts a question/answer and constructs a new node with the information.
	public QuestionNode(String data) {
		this(data, null, null);
	}
	
	//Post: Accepts a question/answer, a left child node, and a right child node, and constructs a 
	//		new node with the information. 
	public QuestionNode(String data, QuestionNode left, QuestionNode right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

}
