/*
 * QuestionTree.java plays a yes/no guessing game where the user thinks of an object the programs 
 * tries to guess by asking questions. Eventually the computer will come to an answer and ask if it 
 * is the object the user was thinking of. If so, the computer wins, otherwise the user reveals the 
 * object and provides a question that distinguishes the correct object from the incorrect object. 
 * Another behavior of the program is reading and writing to a file with updated questions and 
 * answers so it becomes more intelligent after each round.
 */

import java.util.*; // Allows scanners to be used and user interaction.
import java.io.*; // Allows the print stream to write to an external file.

public class QuestionTree {
	private Scanner console; // Remembers the input.
	private QuestionNode overallRoot; // Remembers the beginning, or root node.

	//Post: Constructs a new scanner for user interaction and a beginning object for the computer
	//      to guess which defaults to computer.
	public QuestionTree() {
		overallRoot = new QuestionNode("computer");
		console = new Scanner(System.in);
	}
	
	//Post: Accepts input to read and builds the set of guessing questions from the file.
	public void read(Scanner input) {
		overallRoot = reader(input);
	}
	
	//Post: Accepts a print stream and writes to an external file with updated information.
	public void write(PrintStream output) {
		writer(overallRoot, output);
	}
	
	//Post: Plays a single round of the guessing game.
	public void askQuestions() {
		overallRoot = asker(overallRoot);
	}
	
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
    
    //Post: Private helper method that accepts an input file and builds the set of questions for the
    //      program to guess.
    private QuestionNode reader(Scanner input) {
    	String type = input.nextLine();
    	String info = input.nextLine();
    	QuestionNode piece = new QuestionNode(info);
    	if (type.equals("A:")) // leaf node, base case
    		return piece;
    	 else { // branch node
    		piece.left = reader(input);
    		piece.right = reader(input);
    		return piece;
    	}
    }
    
    //Post: Private helper method that accepts the beginning object as a location to start from and
    //      writes the information to the output file.
    private void writer(QuestionNode root, PrintStream output) {
    	if (root != null) {
    		output.println(" " + root.data);
    		writer(root.left, output);
    		writer(root.right, output);
    	}
    }
    
    //Post: Private helper method that accepts either a question to ask or answer to guess. If the 
    //		guess is wrong, then the user reveals the answer and the question that distinguishes
    //		the incorrect guess from the answer. The program remembers both pieces of information,
    //		ignoring the case of the answer.
    private QuestionNode asker(QuestionNode piece) {
    	String prompt;
    	if (piece.left == null && piece.right == null) { // leaf node
    		prompt = "Would your object happen to be " + piece.data + "?";
    		if (yesTo(prompt))
    			System.out.println("Great, I got it right!");
    		else {
    			System.out.print("What is the name of your object? ");
    			String answer = console.nextLine().trim().toLowerCase();
    			System.out.println("Please give me a yes/no question that");
    			System.out.println("distinguishes between your object");
    			System.out.print("and mine--> ");
    			String question = console.nextLine().trim();
    			prompt = "And what is the answer to your object?";
    			QuestionNode temp = null;
    			if (yesTo(prompt)) 
    				temp = new QuestionNode(question, new QuestionNode(answer), 
    					new QuestionNode(piece.data));
    			else 
    				temp = new QuestionNode(question, new QuestionNode(piece.data), 
        					new QuestionNode(answer));
    			piece = temp;
    		}		
    	} else { // branch node
    		prompt = piece.data;
    		if (yesTo(prompt))
    			piece.left = asker(piece.left);
    		else
    			piece.right = asker(piece.right);
    	}
    	return piece;
    }
}
