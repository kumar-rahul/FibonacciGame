package fbgames.utility;

import java.util.LinkedList;

import fbgames.event.IMove;
import fbgames.model.CellModel;
import fbgames.service.FbGameService;

public class MoveDown implements IMove {

	FbGameService fbs;
	public MoveDown(FbGameService fs){
		this.fbs = fs;
	}
	@Override
	public boolean move() {
		String move = "DOWN";
//		FbGameService fbs = new FbGameService();
		LinkedList<CellModel> cells = fbs.getEdges(move);
		int cellIndex;		
		
		for(cellIndex=cells.size()-1; cellIndex>0; cellIndex--){
			CellModel c1 =  cells.get(cellIndex);
			CellModel c2 =  cells.get(cellIndex-1);

			if(fbs.isMergeState(c1,c2,move)){
				fbs.mergeCells(c1,c2,move);
				cellIndex--;
			}else{
				fbs.updateWithoutMerge(c1,move);
			}
		}
		if(cellIndex==0){
			CellModel c1 =  cells.get(0);
			fbs.updateWithoutMerge(c1,move);
		}

		fbs.updateMergeCellList();
		boolean isNextMove = fbs.nextMoveExist();
		fbs.printBoard();
		return isNextMove;
	}

}
