package student_player;

// imported
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//
import boardgame.Move;

import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState.Piece;
import pentago_swap.PentagoBoardState;

// imported
import pentago_swap.PentagoMove;
//

/** A player file submitted by a student. */
public class StudentPlayer8 extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer8() {
        super("760726872");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    
    private Move bestMove;
    private int initDepth = 3;
    
    public Move chooseMove(PentagoBoardState boardState) {
    		//System.currentTimeMillis()
    		
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
        //MyTools.getSomething();

        //Move myMove = boardState.getRandomMove();
    		bestMove = boardState.getRandomMove();
    		
    		alphaBeta(boardState, initDepth, Double.NEGATIVE_INFINITY, Double.MAX_VALUE);
        // Return your move to be processed by the server.
        return bestMove;
    }
    
    public double alphaBeta(PentagoBoardState bs, int depth, double alpha, double beta) {
    	
    		// if game is over, or depth is 0, or there is no legal moves
		// evaluate the node, return its heuristic
		if(depth == 0 || bs.gameOver()) {
			return evaluate(bs);
		}
    		
    		// get all possible moves for the current state
    		List<PentagoMove> moves = bs.getAllLegalMoves();
    		//Collections.shuffle(moves);
    		if(bs.getTurnPlayer() == player_id) {
    			double highestSeenValue = Double.NEGATIVE_INFINITY;
    			for (PentagoMove move: moves) {
    				PentagoBoardState cloneBS = (PentagoBoardState) bs.clone();
    				cloneBS.processMove(move);
    				double currentValue = alphaBeta(cloneBS, depth-1, alpha, beta);
    				if(currentValue > highestSeenValue) {
    					highestSeenValue = currentValue;
    				}
    				if(currentValue > alpha) {
    					alpha = currentValue;
    					if(depth == initDepth) {
    						bestMove = move;
    					}
    				}
    				if(alpha >= beta) {
    					break; 
    				}
    			}
    			return highestSeenValue;
    			//return alpha;
    		}
    		else {
    			double lowestSeenValue = Double.MAX_VALUE;
    			for(PentagoMove move: moves) {
    				PentagoBoardState cloneBS = (PentagoBoardState) bs.clone();
    				cloneBS.processMove(move);
    				double currentValue = alphaBeta(cloneBS, depth-1, alpha, beta);
    				if(currentValue < lowestSeenValue) {
    					lowestSeenValue = currentValue;
    				}
    				if(currentValue < beta) {
    					beta = currentValue;
    					//bestMove = move;
    				}
    				if(alpha >= beta) {
    					break;
    				}
    			}
    			return lowestSeenValue;
    			//return beta;
    		}
    		
    }
    
    // calculate the scores at current boardState
    // The higher the scores, the higher the possibility to win
    public double evaluate(PentagoBoardState bs) {
    	
    		if(player_id == bs.getWinner()) {
    			return Double.MAX_VALUE;
    		}
    		else if(1-player_id == bs.getWinner()) {
    			return Double.NEGATIVE_INFINITY;
    		}
    		
    		double white_score=0;
    		double black_score=0;
    		
    		if (player_id == 0) {//white
    			//double score = (double) numPiecesRow(bs) + numPiecesColumn(bs) ;
    			Piece color = Piece.WHITE;
    			//white_score = numPiecesRow(bs, color) + numPiecesColumn(bs, color) + 0.3*numPiecesDiag(bs, color);
    			white_score = numPiecesRow(bs, color) + numPiecesColumn(bs, color);
    		}
    		else {
    			//double op_score = (double) numPiecesRow(bs) + numPiecesColumn(bs);
    			Piece color = Piece.BLACK;
    			//black_score = numPiecesRow(bs, color) + numPiecesColumn(bs, color) + 0.3*numPiecesDiag(bs, color);
    			black_score = numPiecesRow(bs, color) + numPiecesColumn(bs, color);
    		}
    		
    		if (player_id == 0) {
    			return white_score - black_score;
    		}
    		else {
    			return black_score - white_score;
    		}
    		
    		//return white_score - black_score;
		//double score = (double) numPiecesRow(bs) + numPiecesColumn(bs);
    		//double val = score - op_score;
    		
    		//return score;
    		
    }
    
    

    // find max number of pieces of the same color at each row
    public int numPiecesRow(PentagoBoardState bs) {
    		
    		int num = 0;
    		int maxNum = 0;
	    	for(int x=0; x<6; x++) {
	    		for(int y=0; y<6; y++) {
	    			if(bs.getPieceAt(x, y).equals(Piece.WHITE)) {
	    				num++;
	    			}
	    		}
	    		if(num > maxNum) {
	    			maxNum = num;
	    		}
	    	}
	    	return maxNum;
    }
    // with color
    public int numPiecesRow(PentagoBoardState bs, Piece color) {

    	int num = 0;
    	int[] nums = new int[6];
    	for(int x=0; x<6; x++) {
    		num = 0;
    		for(int y=0; y<6; y++) {
    			if(bs.getPieceAt(x, y).equals(color)) {
    				num++;
    			}
    		}
    		nums[x] = num;
    		
    	}
    	Arrays.sort(nums);
    	return nums[5];
    }

    // find max number of pieces of the same color at each column
    public int numPiecesColumn(PentagoBoardState bs) {

	    	int num = 0;
	    	int maxNum = 0;
	    	for(int y=0; y<6; y++) {
	    		for(int x=0; x<6; x++) {
	    			if(bs.getPieceAt(x, y).equals(Piece.WHITE)) {
	    				num++;
	    			}
	    		}
	    		if(num > maxNum) {
	    			maxNum = num;
	    		}
	    	}
	    	return maxNum;
    }
    // with color
    public int numPiecesColumn(PentagoBoardState bs, Piece color) {

    	int num = 0;
    	int[] nums = new int[6];
    	for(int y=0; y<6; y++) {
    		num = 0;
    		for(int x=0; x<6; x++) {
    			if(bs.getPieceAt(x, y).equals(color)) {
    				num++;
    			}
    		}
    		nums[y] = num;
    	}
    	Arrays.sort(nums);
    	return nums[5];
    }
    
    // find max number of pieces of the same color at each diagonal
    public int numPiecesDiag(PentagoBoardState bs) {
    		
	    	int num_1 = 0;
	    	int num_2 = 0;
	    	int num_3 = 0;
	    	int num_4 = 0;
	    	int num_5 = 0;
	    	int num_6 = 0;
	    	int[] nums = new int[5];
	    //	int maxNum = 0;
	    	for(int x=0; x<6; x++) {
	    		if(bs.getPieceAt(x, x).equals(Piece.WHITE)) {
	    			num_1++;	
	    		}
	    	}
	    	nums[0] = num_1;
	    	
	    	for(int x=1; x<6; x++) {
	    		if(bs.getPieceAt(x, x-1).equals(Piece.WHITE)) {
	    			num_2++;
	    		}
	    	}
	    	nums[1] = num_2;
	    	
	    	for(int x=0; x<5; x++) {
	    		if(bs.getPieceAt(x, x+1).equals(Piece.WHITE)) {
	    			num_3++;
	    		}
	    	}
	    	nums[2] = num_3;
	    	
	    	for(int x=0; x<6; x++) {
	    		for(int y=5; y>=0; y--) {
	    			if(bs.getPieceAt(x, y).equals(Piece.WHITE)) {
	    				num_4++;
	    			}
	    		}
	    	}
	    	nums[3] = num_4;
	    	
	    	for(int x=1; x<6; x++) {
	    		for(int y=5; y>=1; y--) {
	    			if(bs.getPieceAt(x, y).equals(Piece.WHITE)) {
	    				num_5++;
	    			}
	    		}
	    	}
	    	nums[4] = num_5;
	    	
	    	for(int x=0; x<5; x++) {
	    		for(int y=4; y>=0; y--) {
	    			if(bs.getPieceAt(x, y).equals(Piece.WHITE)) {
	    				num_6++;
	    			}
	    		}
	    	}
	    	nums[5] = num_6;
	    	
	    	Arrays.sort(nums);
	    	
	    return nums[5];
    }
    
    // with color
    public int numPiecesDiag(PentagoBoardState bs, Piece color) {

	    	int num_1 = 0;
	    	int num_2 = 0;
	    	int num_3 = 0;
	    	int num_4 = 0;
	    	int num_5 = 0;
	    	int num_6 = 0;
	    	int[] nums = new int[5];
	
	    	Piece op_color = (color.equals(Piece.WHITE))? Piece.BLACK: Piece.WHITE;
	
	    	// case 1
	    	int op_num_1 = 0;
	    	for(int x=0; x<6; x++) {
	    		if(bs.getPieceAt(x, x).equals(color)) {
	    			num_1++;	
	    		}
	    		if(bs.getPieceAt(x, x).equals(op_color)) {
	    			//num_1 = 0;
	    			//break;
	    			op_num_1++;
	    		}
	
	    	}
	    	if(op_num_1 != 0) {
	    		num_1 = 0;
	    	}
	    	nums[0] = num_1;
	
	    	for(int x=1; x<6; x++) {
	    		if(bs.getPieceAt(x, x-1).equals(color)) {
	    			num_2++;
	    		}
	    		if(bs.getPieceAt(x, x-1).equals(op_color)) {
	    			num_2 = 0;
	    			break;
	    		}
	    	}
	    	nums[1] = num_2;
	
	    	for(int x=0; x<5; x++) {
	    		if(bs.getPieceAt(x, x+1).equals(color)) {
	    			num_3++;
	    		}
	    		if(bs.getPieceAt(x, x+1).equals(op_color)) {
	    			num_3 = 0;
	    			break;
	    		}
	    	}
	    	nums[2] = num_3;
	
	    	// case 4
	    	int op_num = 0;
	    	for(int x=0; x<6; x++) {
	    		if(bs.getPieceAt(x, 5-x).equals(color)) {
	    			num_4++;
	    		}
	    		if(bs.getPieceAt(x, 5-x).equals(op_color)) {
	    			op_num++;
	    			//num_4 = 0;
	    			//break;
	    		}
	    	}
	    	if(op_num != 0) {
	    		num_4 = 0;
	    	}
	
	    	nums[3] = num_4;
	
	    	// case 5
	    	for(int x=1; x<6; x++) {
	    		if(bs.getPieceAt(x, 6-x).equals(color)) {
	    			num_5++;
	    		}
	    		if(bs.getPieceAt(x, 6-x).equals(op_color)) {
	    			num_5 = 0;
	    			break;
	    		}
	    	}
	    	nums[4] = num_5;
	
	    	// case 6
	    	for(int x=0; x<5; x++) {
	    		if(bs.getPieceAt(x, 4-x).equals(color)) {
	    			num_6++;
	    		}
	    		if(bs.getPieceAt(x, 4-x).equals(op_color)) {
	    			num_6 = 0;
	    			break;
	    		}
	    	}
	    	nums[5] = num_6;
	
	    	Arrays.sort(nums);
	    	//System.out.println(nums[5]);
	
	    	return nums[5];
    }


    
}