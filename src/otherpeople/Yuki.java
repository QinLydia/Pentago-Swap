package otherpeople;

import java.util.ArrayList;
import java.util.Collections;

import boardgame.Move;

import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState.Piece;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoCoord;
import pentago_swap.PentagoMove;

/** A player file submitted by a student. */
public class Yuki extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public Yuki() {
        super("xxxxxxxxx");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    double time  = 0.0;
    double max = 0.0;
    double win  = 0.0;

	/*
	 * public PentagoMove opponentMove (PentagoBoardState boardState) {
	 * PentagoBoardState state = (PentagoBoardState) boardState.clone();
	 * ArrayList<PentagoMove> Move = state.getAllLegalMoves(); return Move.get(1); }
	 */
    
    public Move chooseMove(PentagoBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
        //MyTools.getSomething();
    	
    	long startTime=System.currentTimeMillis();
    	
    	Move myMove = boardState.getRandomMove();
    	
        ArrayList<PentagoMove> moves = boardState.getAllLegalMoves();
    	Collections.shuffle(moves);
        //go in the center in first two step
        if(boardState.getTurnNumber()<2) {
	        for(int i=0; i<moves.size()-1;i++){
	        	PentagoCoord c = moves.get(i).getMoveCoord();
	        	int x = c.getX();
	        	int y = c.getY();
	        	if ((x == 1&&y == 1)||(x == 1&&y == 4)||(x == 4&&y == 1)||(x == 4&&y == 4)) {
	        		if(boardState.isLegal(moves.get(i))) {
	        			myMove = moves.get(i);
	        			return myMove;
	        		}
	        	}
	        }
        }
        
        //myMove =movement(boardState); 
        
    	//Stop opponent in three continuous color
    	int opp=boardState.getOpponent();
    	//ArrayList<PentagoMove> somemoves = boardState.getAllLegalMoves();
    	Piece oppColour = opp == 0 ? Piece.WHITE : Piece.BLACK;
    	
    	Piece[] a = new Piece[6];
    	
    	if (boardState.getTurnNumber() < 6) {
	    	for (int x=0; x<6; x++) {
	    		int winCounter=0;
	    		for (int y=0; y<6; y++) {
	    			a[y] = boardState.getPieceAt(x,y);
	    			if ( a[y]==oppColour ) {
	    				winCounter ++;
	    			}
	    			if ( a[y]!=oppColour ) {
	    				break;
	    			}
	    		}
	   			if(winCounter>=3) {
	    			for(PentagoMove move:boardState.getAllLegalMoves()) {
	    				//PentagoMove move = boardState.getAllLegalMoves().get(m);
	    				PentagoCoord coord = move.getMoveCoord();
	    				int coordX = coord.getX();
	    				if (coordX==x) {
	    					return myMove;
	    				}
	    			}
	    		}
	    	}
	    	for (int y=0; y<6; y++) {
	    		int winCounter=0;
	    		for (int x=0; x<6; x++) {
	    			a[x] = boardState.getPieceAt(x,y);
	    			if ( a[x]==oppColour ) {
	    				winCounter ++;
	    			}
	   			}
	   			if(winCounter>=3) {
	   				for(PentagoMove move:boardState.getAllLegalMoves()) {
	   					//PentagoMove move = boardState.getAllLegalMoves().get(m);
	   					PentagoCoord coord = move.getMoveCoord();
	    				int coordY = coord.getY();
	    				if (coordY==y) {
	    					return myMove;
	    				}
	    				if ( a[y]!=oppColour ) {
		    				break;
		    			}
	   				}
	   			}
	   		}
    	}
    	
    	//myMove = AvoidOpponent.getSomething(boardState);
       
    	
    	for (int i=0;i<moves.size()-1; i++) {
 			PentagoBoardState boardState1 = (PentagoBoardState) boardState.clone();
 			boardState1.processMove(moves.get(i));
 			if (boardState1.gameOver() && boardState1.getWinner()!=opp) return moves.get(i);
 			else {
 				ArrayList<PentagoMove> moves1 = boardState1.getAllLegalMoves();
 				for (int j=0;j<moves1.size()-1; j++) {
     				PentagoBoardState boardState2 = (PentagoBoardState) boardState1.clone(); 
     				boardState2.processMove(moves1.get(j));
     				if (boardState2.getWinner()==opp) {
     					moves.remove(i);
     				}
     			}
 			}
 		}
    	
    	
        //if (moves.size()<=0) {
    	//	return boardState.getRandomMove(); 
        //}
        
        /*Move movement = boardState.getRandomMove();
		for(int i=0; i<moves.size()-1;i++){
			PentagoBoardState nextState = (PentagoBoardState) boardState.clone();
			PentagoMove move = moves.get(i);
			nextState.processMove(move);
			int processNumber=0;
			while(processNumber<30) {
				PentagoBoardState nextState1 = (PentagoBoardState) nextState.clone();
				ArrayList<PentagoMove> randomMoves=nextState.getAllLegalMoves();
				for(int j=0;j<randomMoves.size()-1;i++) {
					PentagoMove move1 = nextState.getAllLegalMoves().get(i);
					nextState1.processMove(move1);
					if (nextState1.getWinner()!=nextState1.getOpponent()) {
						win++;
					}
				}
			}
			if (win>max) {
				movement = move;
				max = win;
			}
		}
		
		myMove = movement;
        
        return myMove;*/
    		int[] simulationResult = new int[moves.size()]; 
    		int legalMoveSize = moves.size();
    		int counter = 0;
    		while (System.currentTimeMillis()-startTime < 1700) {
			PentagoMove move = moves.get(counter);
		    	PentagoBoardState treeMoveState = (PentagoBoardState) boardState.clone();
		    	treeMoveState.processMove(move);
		    	for (int j=0; j<2; j++) {
		    		//set a timer to make sure not timeout
		    		if (System.currentTimeMillis()-startTime > 1800) break;
		    		
		    		PentagoBoardState defaultMoveState = (PentagoBoardState) treeMoveState.clone();
		    		int currentTurnNumber = defaultMoveState.getTurnNumber();
		    		while (!defaultMoveState.gameOver() && defaultMoveState.getTurnNumber()<=currentTurnNumber+8) {
		    			ArrayList<PentagoMove> nextMoves = defaultMoveState.getAllLegalMoves();
		    			Collections.shuffle(nextMoves);
		    			defaultMoveState.processMove(nextMoves.get(0));
		    			if (System.currentTimeMillis()-startTime > 1800) break;
		    		}
		    		if (defaultMoveState.getWinner()!=opp) simulationResult[counter]++;
		    	}
		    	counter = (counter+1) % legalMoveSize;
		}
    		
    		int optMove = 0;
    	    int maxWinning = simulationResult[0];
    	    for (int i=0; i<simulationResult.length; i++) {
    	    		if (simulationResult[i]>maxWinning) {
    	    			optMove = i;
    	    			maxWinning = simulationResult[i];
    	    		}
    	    }
    	    
    	    
    	    // Return move to be processed by the server.
    	    myMove = moves.get(optMove);
    	    return myMove;
    }
    
    
    
}