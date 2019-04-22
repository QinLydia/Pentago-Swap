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
import pentago_swap.PentagoCoord;
// imported
import pentago_swap.PentagoMove;
//


/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260726872");
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
    		
    		//bestMove = boardState.getRandomMove();
    		
    		List<PentagoMove> moves = boardState.getAllLegalMoves();
    		if(boardState.getTurnNumber()<2) {
    			for(int i = 0; i < moves.size() - 1; i++){
    				PentagoCoord c = moves.get(i).getMoveCoord();
    				int x = c.getX();
    				int y = c.getY();
    				if ((x == 1 && y == 1)||(x == 1 && y == 4)||(x == 4 && y == 1)||(x == 4 && y == 4)) {
    					if(boardState.isLegal(moves.get(i))) {
    						bestMove = moves.get(i);
    						return bestMove;
    					}
    				}
    			}
    		}
    		else {
    			alphaBeta(boardState, initDepth, Double.NEGATIVE_INFINITY, Double.MAX_VALUE);
    		}
//    		PentagoBoardState CloneBoard =(PentagoBoardState) boardState.clone();
//    		System.out.print("###Score0###: "+evaluate(CloneBoard,1)+'\n');
//    		
//    		CloneBoard.processMove((PentagoMove)bestMove);
//    		System.out.print("###Score1###: "+evaluate(CloneBoard,1)+'\n');
        // Return your move to be processed by the server.
    		
    		if(boardState.isLegal((PentagoMove) bestMove)) {
    			return bestMove;}
    		else { 
    			return boardState.getRandomMove();
    		}
    		
    }
    
    public double alphaBeta(PentagoBoardState bs, int depth, double alpha, double beta) {
    	
    		// if game is over, or depth is 0
		// evaluate the node, return its heuristic
		if(depth == 0 || bs.gameOver()) {
			return evaluate(bs,0);
		}
    		
    		// get all possible moves for the current state
    		List<PentagoMove> moves = bs.getAllLegalMoves();
    		if(bs.getTurnPlayer() == player_id) {
    			double highestValue = Double.NEGATIVE_INFINITY;
    			for (PentagoMove move: moves) {
    				PentagoBoardState cloneBS = (PentagoBoardState) bs.clone();
    				cloneBS.processMove(move);
    				double currentValue = alphaBeta(cloneBS, depth-1, alpha, beta);
    				if(currentValue > highestValue) {
    					highestValue = currentValue;
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
    			return highestValue;
    			//return alpha;
    		}
    		else {
    			double lowestValue = Double.MAX_VALUE;
    			for(PentagoMove move: moves) {
    				PentagoBoardState cloneBS = (PentagoBoardState) bs.clone();
    				cloneBS.processMove(move);
    				double currentValue = alphaBeta(cloneBS, depth-1, alpha, beta);
    				if(currentValue < lowestValue) {
    					lowestValue = currentValue;
    				}
    				if(currentValue < beta) {
    					beta = currentValue;
    					//bestMove = move;
    				}
    				if(alpha >= beta) {
    					break;
    				}
    			}
    			return lowestValue;
    			//return beta;
    		}
    		
    }
    
    // calculate the scores at current boardState
    // The higher the scores, the higher the possibility to win
    public double evaluate(PentagoBoardState bs, int print) {
    	
    		if(player_id == bs.getWinner()) {
    			return Double.MAX_VALUE;
    		}
    		else if(1-player_id == bs.getWinner()) {
    			return Double.NEGATIVE_INFINITY;
    		}
    		
    		double white_score = 0;
    		double black_score = 0;
    		
    		
    		Piece color = Piece.WHITE;
    		white_score = numPiecesRow(bs, color) + numPiecesColumn(bs, color) + numPiecesDiag1(bs, color) + numPiecesDiag2(bs, color);
    		
    		color = Piece.BLACK;
    		black_score = numPiecesRow(bs, color) + numPiecesColumn(bs, color) + numPiecesDiag1(bs, color) + numPiecesDiag2(bs, color);

    		
    		if(player_id == 0) {
    			if(bs.getTurnPlayer()<=12) { 
    				return white_score - black_score + 1.25*stepsToSuccess(bs,Piece.BLACK);
    			}
    			return white_score - black_score + stepsToSuccess(bs,Piece.BLACK)*2;

    		}
    		else {
    			return black_score - white_score + stepsToSuccess(bs,Piece.WHITE)*2;
    		}
    		
    }
    

    // find overall number of pieces of the same color at each row
    public int numPiecesRow(PentagoBoardState bs, Piece color) {
    		
    		Piece op_color = (color.equals(Piece.WHITE))? Piece.BLACK: Piece.WHITE;
	    	int num = 0;
	    	int[] nums = new int[6];
	    	for(int x=0; x<6; x++) {
	    		num = 0;
	    		for(int y=0; y<6; y++) {
	    			if(bs.getPieceAt(x, y).equals(color)) {
	    				num++;
	    			}
	    			if(bs.getPieceAt(x, y).equals(op_color)) {
	    				num = 0;
	    				break;
	    			}
	    		}
	    		nums[x] = num;
	    		
	    	}
//	    	Arrays.sort(nums);
//	    	return nums[5];
	    	int sum = 0;
	    	for(int i: nums) {
	    		sum += i;
	    	}
	    	return sum;
    }

    // find overall number of pieces of the same color at each column
    public int numPiecesColumn(PentagoBoardState bs, Piece color) {
    		
    		Piece op_color = (color.equals(Piece.WHITE))? Piece.BLACK: Piece.WHITE;
	    	int num = 0;
	    	int[] nums = new int[6];
	    	for(int y=0; y<6; y++) {
	    		num = 0;
	    		for(int x=0; x<6; x++) {
	    			if(bs.getPieceAt(x, y).equals(color)) {
	    				num++;
	    			}
	    			if(bs.getPieceAt(x, y).equals(op_color)) {
	    				num=0;
	    				break;
	    			}
	    		}
	    		nums[y] = num;
	    	}
//	    	Arrays.sort(nums);
//	    	return nums[5];
	    	int sum = 0;
	    	for(int i: nums) {
	    		sum += i;
	    	}
	    	return sum;
    }
    
    // diagonal cases with max 6 pieces (2 longest diagonal)
    public double numPiecesDiag1(PentagoBoardState bs, Piece color) {
    		double num_1 = 0;
    		double num_2 = 0;
    		double[] nums = new double[2];
    		Piece op_color = (color.equals(Piece.WHITE))? Piece.BLACK: Piece.WHITE;
    		
    		for(int x = 0; x < 6; x++) {
    			if(bs.getPieceAt(x, x).equals(color)) {
    				num_1++;
    			}
    			if(bs.getPieceAt(x, x).equals(op_color)) {
    				num_1=0;
    				break;
    			}
    		}
    		nums[0] = num_1;
    		for(int x = 0; x < 6; x++) {
    			if(bs.getPieceAt(x, 5-x).equals(color)) {
    				num_2++;
    			}
    			if(bs.getPieceAt(x, 5-x).equals(op_color)) {
    				num_2=0;
    				break;
    			}
    		}
    		nums[1] = num_2;
    		
    		Arrays.sort(nums);
    		return nums[1];
    		
    }
    
    // diagonal cases with max 5 pieces
    public double numPiecesDiag2(PentagoBoardState bs, Piece color) {
    		int num_3 = 0, num_4 = 0, num_5 = 0, num_6 = 0;
    		int[] nums = new int[4];
    		
    		Piece op_color = (color.equals(Piece.WHITE))? Piece.BLACK: Piece.WHITE;
    		
    		// case 3
    		for(int x = 1; x < 6; x++) {
    			if(bs.getPieceAt(x, x-1).equals(op_color)) {
    				num_3 = 0;
    				break;
    			}
    			if(bs.getPieceAt(x, x-1).equals(color)) {
    				num_3++;
    			}
    		}
    		nums[0] = num_3;
    		
    		// case 4
    		for(int x = 0; x < 5; x++) {
    			if(bs.getPieceAt(x, x+1).equals(op_color)) {
    				num_4 = 0;
    				break;
    			}
    			if(bs.getPieceAt(x, x+1).equals(color)) {
    				num_4++;
    			}
    		}
    		nums[1] = num_4;
    		
    		// case 5
    		for(int x = 0; x < 5; x++) {
    			if(bs.getPieceAt(x, 4-x).equals(op_color)) {
    				num_5 = 0;
    				break;
    			}
    			if(bs.getPieceAt(x, 4-x).equals(color)) {
    				num_5++;
    			}
    		}
    		nums[2] = num_5;
    		
    		// case 6
    		for(int x = 1; x < 6; x++) {
    			if(bs.getPieceAt(x, 6-x).equals(op_color)) {
    				num_6 = 0;
    				break;
    			}
    			if(bs.getPieceAt(x, 6-x).equals(color)) {
    				num_6++;
    			}
    		}
    		nums[3] = num_6;
    		
    		Arrays.sort(nums);
    		return nums[3];
    		
    }
    
    public int stepsToSuccess(PentagoBoardState bs, Piece color) {
    		if(numPiecesDiag1(bs,color) >= 4 || numPiecesRow(bs,color)>=4 || numPiecesColumn(bs,color)>=4) {
    			if(numPiecesRow(bs,color)>=4 || numPiecesColumn(bs,color)>=4) {
    				if(numPiecesDiag1(bs,color)>=4) {
    					return Integer.MAX_VALUE;
    				}
    				return 3;
    			}
    			return 2;
    		}
    		else if(numPiecesDiag1(bs,color) == 3 || numPiecesRow(bs,color)==3 || numPiecesColumn(bs,color)==3) {
    			if(numPiecesRow(bs,color) == 3 || numPiecesColumn(bs,color) == 3) {
    				if(numPiecesDiag1(bs,color) == 3) {
    					return Integer.MAX_VALUE;
    				}
    				return 2;
    			}
    			return 1;
    		}
    		else {
    			return 0;
    		}
    		
    }
    
    
}