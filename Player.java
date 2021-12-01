package FocusGame;

import java.awt.Color;

/**
 * This class is super for human and cpu players
 * @author Nathan French
 * @version 2021-10-18
 *
 */

public class Player {
	
	private String playerName;
	private Color playerColor;
	
	public Player(String name, Color color) {
		playerName = name;
		playerColor = color;
	}
	
	// Accssor for player name
	public String getPlayerName() {
		return(playerName);
	}
	
	// Accessor for player color
	public Color getPlayerColor() {
		return(playerColor);
	}
}
