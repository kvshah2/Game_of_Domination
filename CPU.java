package FocusGame;

/**
 * A Computer controled player that makes its own moves
 * @author Nathan, Ddzviti
 * @version 2021-11-25
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CPU extends Player{
	
	private int difficulty;
	
	public CPU(String name, Color color, int difficulty) {
		super(name,color);
		this.difficulty = difficulty;
	}
	
	// Accessor for difficulty
	public int getDifficulty() {
		return(difficulty);
	}
	
	private Boolean checkIfLegal(int row, int col) {
		int[][] nonSpaces = new int[][]{{0,0},{0,1},{1,0},{6,0},{7,0},{7,1},
	            {0,6},{0,7},{1,7},{6,7},{7,7},{7,6}};
	    if (row > 7 || row < 0 || col > 7 || col < 0) return false;
	    for (int[] coord : nonSpaces) {
	    	if (coord[0] == row && coord[1] == col) return false;
	    }
		return(true);
	}
	
	public int[] getMove(Object [][] space, int [] reserve, int playerNumber) {
         ArrayList<Object> availablePieces = new ArrayList<>();
         ArrayList<int[]> pieceCoords = new ArrayList<>();

         for (int row = 0; row < 8; row++) {
             for (int col = 0; col < 8; col++){
                 if (space[row][col] != null){
                	 if (space[row][col] instanceof Pawn) {
                		 if (((Pawn) space[row][col]).getPlayer() == playerNumber) {
                        	 availablePieces.add(space[row][col]);
                        	 pieceCoords.add(new int[] {row,col});
                		 } 
                	 } else {
                		 if ( (((Stack) space[row][col]).getTop()).getPlayer() == playerNumber) {
                        	 availablePieces.add(space[row][col]);
                        	 pieceCoords.add(new int[] {row,col});
                		 }                		 
                	 }
                 }
             }
         }
         
         if (availablePieces.isEmpty()) {
        	 while (true) {
        		 int randRow = ThreadLocalRandom.current().nextInt(0, 8);
        		 int randCol = ThreadLocalRandom.current().nextInt(0, 8);
        		 if (checkIfLegal(randRow,randCol)) {
        			 return(new int[] {randRow,randCol,0,0,0,1});
        		 }
        	 }
         }
         
        int sP = 0;
        if (difficulty == 2) {
	        int t = 0;
	        for (int i = 0; i < availablePieces.size(); i++) {
	        	int targetSize = availablePieces.get(t) instanceof Pawn ? 1: ((Stack) availablePieces.get(t)).getStackSize();
	        	int nextSize = availablePieces.get(i) instanceof Pawn ? 1: ((Stack) availablePieces.get(i)).getStackSize();
	            if (nextSize > targetSize) t = i;
	        }
	        sP = t;
        }else {
        	sP = ThreadLocalRandom.current().nextInt(0, availablePieces.size());
        }
            
         int split = 1;
         int move = 1; 
         while (true) {
             int newRow = pieceCoords.get(sP)[0];
             int newCol = pieceCoords.get(sP)[1];

             if (availablePieces.get(sP) instanceof Stack) {
                int size = ((Stack) availablePieces.get(sP)).getStackSize();
                split = size;
                move =  ThreadLocalRandom.current().nextInt(1, size+1);
            }

         	int randInt = ThreadLocalRandom.current().nextInt(0, 4);
         	if (randInt < 2) {
         		newRow = randInt == 0 ? newRow-move : newRow+move;
         		if (checkIfLegal(newRow,newCol)) return(new int[] {pieceCoords.get(sP)[0],pieceCoords.get(sP)[1],newRow,newCol,split,0});
         	} else {
         		newCol = randInt == 3 ? newCol-move : newCol+move;
         		if (checkIfLegal(newRow,newCol)) return(new int[] {pieceCoords.get(sP)[0],pieceCoords.get(sP)[1],newRow,newCol,split,0});
         	}
         }
	 }
}