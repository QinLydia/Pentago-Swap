package otherpeople;

import boardgame.Move;

import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260737363");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
    	
    	int alpha=Integer.MIN_VALUE;
        int beta=Integer.MAX_VALUE;
        PentagoBoardState subState;
        PentagoMove myMove = null;
        for(PentagoMove m: boardState.getAllLegalMoves()) {
        	subState=(PentagoBoardState)boardState.clone();
    		subState.processMove(m);
    		if(subState.getWinner() == getColor()) {
    			myMove = m;
    			break;
    		}else if(subState.getWinner() != getColor() && subState.gameOver()) {
    			break;
    		}
    		beta=MyTools.minNode(subState,alpha,beta);
    		
    		if(alpha<beta) {
    			alpha=beta;
    			myMove=m;
    		}
    		
        }
        

        // Return your move to be processed by the server.
        return myMove;
    }
}
