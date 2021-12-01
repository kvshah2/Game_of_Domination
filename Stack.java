package FocusGame;
import java.util.ArrayList;

/**
 * Game piece that represents a stack of pawns
 * @author Nathan
 * @version 2021-11-15
 *
 */

public class Stack {
	private ArrayList<Pawn> pawns;
	
	public Stack(ArrayList<Pawn> pawns) {
		this.pawns = pawns;
	}
	
	public Pawn getTop() {
		return(pawns.get(pawns.size() - 1));
	}
	
	public ArrayList<Pawn> splitStack(int numberOfPawns) {
		if (numberOfPawns <= pawns.size()) {
			ArrayList<Pawn> newStack = new ArrayList<>();
			for (int i = 0; i < numberOfPawns; i++) {
				newStack.add(pawns.remove(0));
			}
			return(newStack);
		}
		return(null);
	}
	
	public void addToStack(Pawn pawn,Boolean addToTop) {
		if (addToTop) {
			pawns.add(pawn);
		} else {
			ArrayList<Pawn> newStack = new ArrayList<>();
			newStack.add(pawn);
			for (Pawn stackPawn : pawns) {
				newStack.add(stackPawn);
			}
			pawns = newStack;
		}
	}
	
	public int getStackSize() {
		return(pawns.size());
	}
}
