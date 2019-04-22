package student_player;

import java.util.stream.IntStream;

import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoBoardState.Piece;

public class Danger {
	private PentagoBoardState pbs;
	private int AIcolor;

	Danger(PentagoBoardState pbs, int AIcolor){
		this.pbs = pbs;
		this.AIcolor = AIcolor;
	}

	public boolean check_Six_Four_Danger() {

		PentagoBoardState pbs = this.pbs;

		Piece opColor;
		if(AIcolor == 0) {opColor = Piece.WHITE;}else {opColor = Piece.BLACK;}

		int checker = 0;  
		int danger = 0;

		//Check the COLUMN==========================================================================================================            
		for (int column: IntStream.range(0,6).toArray()) {
			for (int checkRow :IntStream.range(0, 6).toArray()) {
				if (pbs.getPieceAt(checkRow, column) != opColor) {
					checker += 1;  
					if(pbs.getPieceAt(checkRow, column)!=Piece.EMPTY && 0<checkRow && checkRow<5){ 
						danger +=1;
						} 
				}
				else { 
					break;
				}
			}
			//System.out.print("row: ("+checker+", "+danger+"), ");
			if(checker == 6 && danger == 4) {return true;};
			checker = 0;  
			danger = 0;
		}

		checker = 0;
		danger = 0;

		//Check the ROW=============================================================================================================

		for (int row: IntStream.range(0,6).toArray()) {
			for (int checkCol :IntStream.range(0, 6).toArray()) {
				if (pbs.getPieceAt(row, checkCol)!=opColor) {checker += 1;  if(pbs.getPieceAt(row, checkCol)!=Piece.EMPTY && 0<checkCol && checkCol<5){ danger +=1;} }
				else { break;}
			}
			//System.out.print("col: ("+checker+", "+danger+"), ");
			if(checker == 6 && danger == 4) {return true;};
			checker = 0;  
			danger = 0;
		}

		checker = 0;
		danger = 0;
		//Check the Top-left to Bottom-right corner================================================================================

		for (int checkDiag: IntStream.range(0,6).toArray()) {
			if (pbs.getPieceAt(checkDiag, checkDiag)!=opColor) {checker += 1;  if(pbs.getPieceAt(checkDiag, checkDiag)!=Piece.EMPTY && 0<checkDiag && checkDiag<5){ danger +=1;} }
			else { break;}
		};

		//System.out.print("tl-br: ("+checker+", "+danger+"), ");
		if(checker == 6 && danger == 4) {return true;};
		checker = 0;
		danger = 0;

		//Check the Top-right to Bottom-left corner=================================================================================

		for (int checkDiag: IntStream.range(0,6).toArray()) {
			if (pbs.getPieceAt(checkDiag, 5 - checkDiag)!=opColor) {checker += 1;  if(pbs.getPieceAt(checkDiag, 5 - checkDiag)!=Piece.EMPTY && 0<checkDiag && checkDiag<5){ danger +=1;} }
			else { break;}
		};

		//System.out.print("tr-bl: ("+checker+", "+danger+") \n ");
		if(checker == 6 && danger == 4) {return true;};

		return false;

	}
}
