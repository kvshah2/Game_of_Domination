package FocusGame;

/**
 * Represents a game piece for the game "Focus"
 * @author Nathan French
 * @version 2021-11-15
 *
 */

public class Pawn {

	private int player;
	
	public Pawn(int player) {
		this.player = player;
	}
	
	public int getPlayer() {
		return(player);
	}
}
