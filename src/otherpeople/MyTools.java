package otherpeople;


import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

public class MyTools {
    public static int minNode(PentagoBoardState state,int alpha,int beta) {
    	PentagoBoardState subState;
    	
    	for(PentagoMove m: state.getAllLegalMoves()) {
         	subState=(PentagoBoardState)state.clone();
     		subState.processMove(m);
     		beta=Math.min(beta,evaluation(subState));
     		if(beta<=alpha) {
     			return alpha;
     		}
     		
    	}
    	return beta;
    	
    }
    public static int evaluation(PentagoBoardState state) {
    	int heuristic=0;
    	PentagoBoardState.Piece color;
    	if(state.getTurnPlayer()==0) {
    		color=PentagoBoardState.Piece.WHITE;
    	}else {
    		color =PentagoBoardState.Piece.BLACK;
    	}
    	
    	for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                heuristic=heuristic+pairValue(state.getPieceAt(i, j), state.getPieceAt(i, j+1),color);
		if(j==4 && state.getPieceAt(i,j+1)==color){
			heuristic=heuristic+ 1;
		}
            }
        }
    	for (int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
		heuristic=heuristic+pairValue(state.getPieceAt(j, i), state.getPieceAt(j+1, i),color);
		if(j==4 && state.getPieceAt(j+1, i)==color){
			heuristic=heuristic+ 1;
		}
                
            }
        }
        //count main diagonal up-left to down-right
        for (int i = 0; i < 5; i++) {
 		heuristic=heuristic+pairValue(state.getPieceAt(i, i), state.getPieceAt(i+1, i+1),color);
		if(i==4 && state.getPieceAt(i+1, i+1)==color){
			heuristic=heuristic+ 1;
		}
             
        }
        //count main diagonal up-right to down-left
        for (int i = 0; i < 5; i++) {
		heuristic=heuristic+pairValue(state.getPieceAt(i, 5-i), state.getPieceAt(i+1, 4-i),color);
		if(i==4 && state.getPieceAt(i+1, 4-i)==color){
			heuristic=heuristic+ 1;
		}
	
        }

    	return heuristic;
    }

    public static int pairValue(PentagoBoardState.Piece first, PentagoBoardState.Piece second,PentagoBoardState.Piece color){
	int heuristic=0;
	if(first == color&& second == color) {
             heuristic=heuristic+ 2;
        }else if(first == color&& second != color) {
             heuristic=heuristic+ 1;
        }else if(first != color&& second != color) {
             heuristic=heuristic-1;
        }
	
	return heuristic;
    }

    
}

