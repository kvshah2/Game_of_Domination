package FocusGame;

import java.awt.Color;
import FocusUI.Board;
import FocusUI.GUI;
import java.util.ArrayList;

/**
 * Computes game logic in coordinance with GUI class
 * @author Nathan French
 * @version 2021-11-25
 *
 */

public class Game {
	private Color[] colors = {new Color(0, 145, 255),new Color(251, 255, 0),new Color(0, 255, 68),new Color(255, 0, 0)};
	
	private Player[] players;
	private int turn;
	private int splitAmount;
	private Object[][] space;
	private GUI userInterface;
	private Board board;
	private OffBoard offBoard;
	private int[] selectedSpace;
	private SaveStorage saveStore;
	private Boolean savedGame;
	
	public Game(GUI ui, Board board) {
		players = new Player[4];
		space = new Object[8][8];
		userInterface = ui;
		this.board = board;
		turn = 0;
		splitAmount = 0;
		selectedSpace = null;
		saveStore = new SaveStorage();
		savedGame = false;
	}
	
	public void clearData() {
		offBoard = new OffBoard(new int[] {0,0,0,0},new int[] {0,0,0,0});
		savedGame = false;
		splitAmount = 0;
		selectedSpace = null;
		turn = 0;
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				if (col < players.length) {
					players[col] = null;
				}
				space[row][col] = null;
			}
		}
		board.setBoard(space,colors);
		board.deHighlight();
	}
	
	public void changePieceColors(String pieceColors) {
		if (pieceColors.equals("Orange, pink, cyan, yellow")) {
			colors = new Color[]{new Color(255, 119, 0),new Color(255, 0, 221),new Color(0, 255, 247),new Color(242, 255, 0)};
		} else {
			colors = new Color[]{new Color(0, 145, 255),new Color(251, 255, 0),new Color(0, 255, 68),new Color(255, 0, 0)};
		}
		
	}
	
	public void setupGame(Object[][] playerData) {
		for (int i = 0; i < 4; i ++) {
			String name = (String) playerData[i][0];
			int difficulty = (int) playerData[i][1];
			if (difficulty == 0) {
				players[i] = new Human(name,colors[i],false,0);
			} else {
				players[i] = new CPU(name,colors[i],difficulty);
			}
		}
		int[][] startPattern = {{0,0,0,0,3,1,3,1},{1,1,1,1,3,1,3,1},{0,0,0,0,3,1,3,1},{1,1,1,1,3,1,3,1},
								{2,0,2,0,2,2,2,2},{2,0,2,0,3,3,3,3},{2,0,2,0,2,2,2,2},{2,0,2,0,3,3,3,3}};
		
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				if (!board.checkIfNonSpace(row,col)) {
					int pawnColor = startPattern[row][col];
					space[row][col] = new Pawn(pawnColor);
				}
			}
		}
		
		offBoard = new OffBoard(new int[] {0,0,0,0},new int[] {0,0,0,0});
		takeTurn(false);
	}
	
	public String[] getSaves() {
		return(saveStore.getSaves());
	}
	
	public Object[][] sendPlayerData() {
		Object[][] playerData = new Object[4][2];
		for (int i=0; i< players.length; i++) {
			playerData[i][0] = players[i].getPlayerName();
			if (players[i] instanceof CPU) {
				playerData[i][1] = ((CPU) players[i]).getDifficulty();
			} else {
				playerData[i][1] = 0;
			}
		}
		return(playerData);
	}
	
	public Object[][] loadGame(String fileName) {
		ArrayList<String> data = saveStore.loadGameData(fileName);
		int row = 0;
		int col = 0;
		
		clearData();
		
		for (int i = 0; i < data.size(); i++) {
			String currentLine = data.get(i);
			if (i < 4) {
				int difficulty = Integer.parseInt( i == 3 ? (data.get(4)).substring(i) : (data.get(4)).substring(i,i+1) );
				players[i] = difficulty == 0 ? new Human(currentLine,colors[i],false,0) : new CPU(currentLine,colors[i],difficulty);
				offBoard.setReserve(i, Integer.parseInt(data.get(i+5)));
				offBoard.setCaptured(i, Integer.parseInt(data.get(i+9)));
			} else if (i == 4) {
				i = 13;
				turn = Integer.parseInt(data.get(i));
			} else {
				if (!board.checkIfNonSpace(row,col) && !currentLine.equals("")) {
					
					if (currentLine.length() < 2) {
						int pawnColor = Integer.parseInt(currentLine);
						space[row][col] = new Pawn(pawnColor);
					} else {
						ArrayList<Pawn> pawns = new ArrayList<>();
						for (int pawn = 0; pawn < currentLine.length(); pawn++) {
							int pawnColor = Integer.parseInt(currentLine.substring(pawn,pawn+1));
							pawns.add(new Pawn(pawnColor));
						}
						space[row][col] = new Stack(pawns);
					}
					
				}
				col ++;
				if (col > 7) {
					col = 0;
					row ++;
				}
			}
		}
		takeTurn(true);
		savedGame = true;
		return(sendPlayerData());
	}
	
	public void saveGame(String fileName) {
		int linesNeeded = (players.length*3 + 2 + space.length*space.length);
		String[] data = new String[linesNeeded];
		int[] reserveList = offBoard.returnReserve();
		int[] captureList = offBoard.returnCaptured();
		int row = 0; 
		int col = 0;
		data[4] = "";
		for (int i = 0; i < data.length; i++) {
			if (i < 4) {
				data[i] = players[i].getPlayerName();
				data[4] += players[i] instanceof CPU ? ((CPU) players[i]).getDifficulty()+"" : "0";
				data[i+5] = reserveList[i]+"";
				data[i+9] = captureList[i]+"";
			} else if(i == 4) {
				i = 13;
				data[i] = turn+"";
			} else {
				Object piece = space[row][col];
				if (piece instanceof Pawn) {
					data[i] = ((Pawn) piece).getPlayer()+"";
				} else if (piece instanceof Stack) {
					ArrayList<Pawn> pawns = ((Stack) piece).splitStack(((Stack) piece).getStackSize());
					space[row][col] = new Stack(pawns);
					data[i] = "";
					for (Pawn pawn : pawns) {
						data[i] += ((Pawn) pawn).getPlayer()+"";
					}
				}
				col ++;
				if (col > 7) {
					col = 0;
					row ++;
				}
			}
		}
		saveStore.saveGameData(fileName,data);
		savedGame = true;
	}
	
	public Color[] getColors() {
		return(colors);
	}
	
	public void setSplitAmount(int amount) {
		splitAmount = amount;
	}
	
	public Boolean checkIfSaved() {
		return(savedGame);
	}
	
	private void sendToCaptured(Pawn pawn) {
		offBoard.addToCaptured(pawn.getPlayer());
	}
	
	private void sendToReserve(Pawn pawn) {
		offBoard.addToReserve(pawn.getPlayer());
	}
	
	private void checkStack(Stack stack) {
		int excessPawns = stack.getStackSize() - 5;
		if (stack.getStackSize() > 0) {
			ArrayList<Pawn> bottomPawns = (stack.splitStack(excessPawns));
			for (Pawn pawn : bottomPawns) {
				if (pawn.getPlayer() == turn-1) { sendToReserve(pawn); }else{ sendToCaptured(pawn);}
			}
		}
	}
	
	private Boolean attemptMove(int row, int col) {
		Object piece1 = space[selectedSpace[0]][selectedSpace[1]];
		Object piece2 = space[row][col];
		if (piece1 == null) return false;
		
		if (!(row == selectedSpace[0] || col == selectedSpace[1])) return false;
		int spaceDistance = Math.abs(selectedSpace[0] - row) + Math.abs(selectedSpace[1] - col);
		
		if (piece1 instanceof Pawn) {
			if (((Pawn) piece1).getPlayer() != turn-1 || spaceDistance > 1) return false;
			
			if (piece2 == null) {
				space[row][col] = piece1;
			} else {
				if (piece2 instanceof Pawn) {
					ArrayList<Pawn> pawns = new ArrayList<>();
					pawns.add((Pawn) piece2);
					pawns.add((Pawn) piece1);
					space[row][col] = new Stack(pawns);
				} else {
					((Stack) piece2).addToStack((Pawn) piece1,true);
					checkStack((Stack) piece2);
				}
			}
			space[selectedSpace[0]][selectedSpace[1]] = null;
			
		} else {
			Pawn topPawn = ((Stack) piece1).getTop();
			if (topPawn.getPlayer() != turn-1 || spaceDistance > splitAmount) return false;
			int otherHalf = ((Stack) piece1).getStackSize() - splitAmount;
			Stack leftBehind = otherHalf > 0 ? new Stack(((Stack) piece1).splitStack(otherHalf)) : null;
			
			if (piece2 == null) {
				if (((Stack) piece1).getStackSize() == 1) piece1 = ((Stack) piece1).getTop();
				space[row][col] = piece1;
			} else {
				if (piece2 instanceof Pawn) {
					((Stack) piece1).addToStack((Pawn) piece2,false);
					checkStack((Stack) piece1);
					space[row][col] = piece1;
				} else {
					ArrayList<Pawn> pawns = ((Stack) piece1).splitStack(((Stack) piece1).getStackSize());
					for (Pawn pawn : pawns) {
						((Stack) piece2).addToStack(pawn,true);
					}
					checkStack((Stack) piece2);
				}
			}
			space[selectedSpace[0]][selectedSpace[1]] = leftBehind;
			
		}
		takeTurn(false);
		savedGame = false;
		return(true);
	}
	
	public void addReserve() {
		if (turn < 1) return;
		if (offBoard.returnReserve()[turn-1] >  0 && selectedSpace != null) {
			offBoard.removeFromReserve(turn-1);
			Pawn pawn = new Pawn(turn-1);
			Object piece = space[selectedSpace[0]][selectedSpace[1]];
			if (piece == null) {
				space[selectedSpace[0]][selectedSpace[1]] = pawn;
			} else {
				if (piece instanceof Pawn) {
					ArrayList<Pawn> pawns = new ArrayList<>();
					pawns.add((Pawn) piece);
					pawns.add(pawn);
					space[selectedSpace[0]][selectedSpace[1]] = new Stack(pawns);
				} else {
					Stack stack = ((Stack) space[selectedSpace[0]][selectedSpace[1]]);
					stack.addToStack(pawn,true);
					checkStack(stack);
				}
			}
			takeTurn(false);
			savedGame = false;
		}
	}
	
	private void viewStack(int row, int col, Boolean hover) {
		Object piece  = space[row][col];
		if (hover==false) splitAmount = 1;
		if (piece instanceof Pawn) {
			userInterface.viewStack(colors, new Pawn[] {(Pawn) piece},hover);
		} else if (piece instanceof Stack) {
			if (hover==false) splitAmount = ((Stack) piece).getStackSize();
			ArrayList<Pawn> copyStack = ((Stack) piece).splitStack(((Stack) piece).getStackSize());
			space[row][col] = new Stack(copyStack);
			Pawn[] pawns = new Pawn[copyStack.size()];
			userInterface.viewStack(colors, copyStack.toArray(pawns),hover);
		} else {
			userInterface.viewStack(colors, null,hover);
		}
		
		if (hover==false) userInterface.updateMoveAmount(splitAmount);
	}
	
	public void selectSpace(int row,int col) {
		
		if (!board.checkIfNonSpace(row,col) && turn > 0 && !(players[turn-1] instanceof CPU)) {
			
			if (selectedSpace == null) {
				selectedSpace = new int[] {row,col};
				viewStack(row, col, false);
				
			} else if (selectedSpace[0] == row && selectedSpace[1] == col){
				selectedSpace = null;
				board.deHighlight();
				userInterface.viewStack(colors, null, false);
			} else {
				if (!attemptMove(row,col)) {
					userInterface.viewStack(colors, null, false);
					selectedSpace = null;
					board.deHighlight();
				}
			}
			
			if (selectedSpace != null) {
				Color orange = new Color(255, 183, 0);
				Color yellow = new Color(17, 255, 0);
				board.hightlightSpace(row,col, colors[0].equals(new Color(0, 145, 255)) ? orange : yellow);
			}
			
			
		}
	}
	
	public void hoverSpace(int row, int col) {
		if (!board.checkIfNonSpace(row,col) && turn > 0) {
			viewStack(row, col, true);
		}
	}
	
	private int[] checkPlayers() {
		int[] playMoves = new int[] {0,0,0,0};
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				if (!board.checkIfNonSpace(row,col) && space[row][col] != null) {
					if (space[row][col] instanceof Pawn) {
						playMoves[((Pawn) space[row][col]).getPlayer()] ++;
					} else {
						Pawn pawn = ((Stack) space[row][col]).getTop();
						playMoves[pawn.getPlayer()] ++;
					}
				}
			}
		}
		int[] reserves = offBoard.returnReserve();
		for (int i = 0 ; i < reserves.length; i++) {
			playMoves[i] += reserves[i];
		}
		return(playMoves);
	}
	
	private void takeTurn(Boolean load) {
		board.setBoard(space,colors);
		if (!load) {
			int lastPlayer = turn;
			int[] playableMoves = checkPlayers();
			while(true) {
				turn = (turn%4)+1;
				if (playableMoves[turn-1] > 0) break;
				if ((turn%4)+1 == lastPlayer) {
					turn = lastPlayer*-1;
					break;
				}
			}
		}
		userInterface.viewStack(colors, null, false);
		userInterface.viewStack(colors, null, true);
		selectedSpace = null;
		board.deHighlight();
		userInterface.updateRCCount(offBoard.returnReserve(),offBoard.returnCaptured());
		userInterface.updateStatus(turn);
		userInterface.updateMoveAmount(1);
		
		if (turn > 0 && players[turn-1] instanceof CPU) {
			int[] move = ((CPU) players[turn-1]).getMove(space,offBoard.returnReserve(),(turn-1));
			if (move[5] == 0) {
				splitAmount = move[4];
				selectedSpace = new int[] {move[0],move[1]};
				attemptMove(move[2], move[3]);
			} else {
				selectedSpace = new int[] {move[0],move[1]};
				addReserve();
			}
		}
		
	}
	
}
