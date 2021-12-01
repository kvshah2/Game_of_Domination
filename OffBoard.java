package FocusGame;

/**
 * Keeps track of pawns reserved or captured by players
 * @author Nathan French
 * @version 2021-11-21
 *
 */

public class OffBoard {
	private int[] reserve;
	private int[] captured;
	
	public OffBoard(int[] reserve, int[] captured) {
		this.reserve = reserve;
		this.captured = captured;
	}
	
	public void addToReserve(int player) {
		reserve[player]++;
	}
	
	public void removeFromReserve(int player) {
		reserve[player]--;
	}
	
	public void addToCaptured(int player) {
		captured[player]++;
	}
	
	public void setReserve(int player,int amount) {
		reserve[player] = amount;
	}
	
	public void setCaptured(int player, int amount) {
		captured[player] = amount;
	}
	
	public int[] returnReserve() {
		return(reserve);
	}
	
	public int[] returnCaptured() {
		return(captured);
	}
}
