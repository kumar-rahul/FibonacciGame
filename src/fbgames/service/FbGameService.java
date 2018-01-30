package fbgames.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import fbgames.model.CellModel;

public class FbGameService {

	List<List<CellModel>> gboard;
	int m,n;
	ArrayList<CellModel> mergedCells = new ArrayList<CellModel>();
	
	private int getRandomRow(){
		Random rand = new Random();
		int mRand  = rand.nextInt(m);
		return mRand;
	}
	private int getRandomCol(){
		Random rand = new Random();
		int nRand  = rand.nextInt(n);
		return nRand;
	}	
	public void setRowCount(int row){
		this.m = row;
	}
	public void setColCount(int col){
		this.n = col;
	}
	
	public void printBoard(){
		print(gboard);
	}
	private void print(List<List<CellModel>> board){
		System.out.println("Game Board: ");
		for(int row=0; row<m; row++){
			String bStr = "";
//			ArrayList<CellModel> boardIndex = (ArrayList<CellModel>) gboard.get(row); 
			for(int col=0; col<n; col++){
//				CellModel cell = gboard.get(row).get(col);
				bStr += gboard.get(row).get(col).getValue()+" ";//cell.getValue()+" ";
			}
			System.out.println(bStr);
		}		
//		System.out.println(Arrays.deepToString(board).replace("], ", "]\n"));
	}
	private void createBoard(){
		gboard = new ArrayList<List<CellModel>>();//new int[m][n];		
		initBoard();
		for(int cell=0; cell<2; cell++){
			populateRandomCell();
		}
	}
	private void initBoard(){
		for(int row=0; row<m; row++){
			List<CellModel> cm = new ArrayList<CellModel>();
			for(int col=0; col<n; col++){
				//create cell
				CellModel cell = new CellModel(row, col, 0, false);
				cm.add(cell);
			}
			//add cell to board
			gboard.add(cm);			
		}		
	}
	private void populateRandomCell(){
		while(true){
			int i = getRandomRow();
			int j = getRandomCol();
			CellModel randCell = gboard.get(i).get(j);
			if(randCell.getValue() == 0){
				randCell.setValue(1);
				break;
			}
		}
	}
	private void populateCell(CellModel c, int newVal, boolean mStatus){
		c.setValue(newVal);
		c.setMerged(mStatus);
	}	
	public LinkedList<CellModel> getEdges(String move){
		LinkedList<CellModel> edges = new LinkedList<CellModel>();
		if(move.toUpperCase().equals("LEFT") || move.toUpperCase().equals("RIGHT")){
			for(int row=0; row<m; row++){
				for(int col=0; col<n; col++){
					if(gboard.get(row).get(col).getValue() != 0){
						CellModel c = gboard.get(row).get(col);
						edges.add(c);
					}
				}
			}
		}else if(move.toUpperCase().equals("UP") || move.toUpperCase().equals("DOWN")){
			for(int col=0; col<n; col++){
				for(int row=0; row<m; row++){
					if(gboard.get(row).get(col).getValue() != 0){
						CellModel c = gboard.get(row).get(col);
						edges.add(c);
					}
				}
			}
		}
		
		return edges;
	}
	private void resetCell(CellModel c){
		populateCell(c,0,false);
	}
	private CellModel storeAtFarthestCell(CellModel c, int value, boolean mStatus, String move){
		CellModel fCell = getFarthestCell(c,move);
		populateCell(fCell,value,mStatus);
//		mergedCells.add(fCell); 
		return fCell;
	}
	private CellModel getFarthestCell(CellModel c, String move){
		CellModel fCell = null;
		
		//condition for valid cell: if empty || if ismerge = false
		
		if(move.toUpperCase().equals("LEFT")){
			 fCell= getValidLeftCell(c);
		}else if(move.toUpperCase().equals("RIGHT")){
			 fCell= getValidRightCell(c);
		}else if(move.toUpperCase().equals("UP")){
			 fCell= getValidUpCell(c);
		}else if(move.toUpperCase().equals("DOWN")){
			 fCell= getValidDownCell(c);
		}
		return fCell;
	}
	private CellModel getValidLeftCell(CellModel c){
		CellModel vCell = null;
		int tempRow = c.getRow();
		int tempCol = 0;
		CellModel tempCell = gboard.get(c.getRow()).get(0);
		while(tempCol<=c.getCol()){
			if(tempCell.getValue() == 0 || (!tempCell.isMerged() && cellsAdjacent(tempCell, c, "LEFT") && validateFS(tempCell,c) )){
				vCell= tempCell;
				break;
			}else if(tempCol==c.getCol()) {
				vCell= tempCell;
				break;
			}
			tempCol++;
			tempCell = gboard.get(tempRow).get(tempCol);
		}
		return vCell;		
	}
	private CellModel getValidRightCell(CellModel c){
		CellModel vCell = null;
		int tempRow = c.getRow();
		int tempCol = n-1;
		CellModel tempCell = gboard.get(c.getRow()).get(n-1);
		while(tempCol>=c.getCol()){
			if(tempCell.getValue() == 0 || (!tempCell.isMerged() && cellsAdjacent(c, tempCell, "RIGHT") && validateFS(tempCell,c) )){
				vCell= tempCell;
				break;
			}else if(tempCol==c.getCol()) {
				vCell= tempCell;
				break;
			}
			tempCol--;
			tempCell = gboard.get(tempRow).get(tempCol);
		}
		return vCell;
	}
	private CellModel getValidUpCell(CellModel c){
		CellModel vCell = null;
		int tempRow = 0;
		int tempCol = c.getCol();
		CellModel tempCell = gboard.get(0).get(c.getCol());
		while(tempRow<=c.getRow()){
			if(tempCell.getValue() == 0 || (!tempCell.isMerged() && cellsAdjacent(tempCell, c, "UP") && validateFS(tempCell,c) )){
				vCell= tempCell;
				break;
			}else if(tempRow == c.getRow()) {
				vCell= tempCell;
				break;
			}
			tempRow++;
			tempCell = gboard.get(tempRow).get(tempCol);
		}			
		return vCell;		
	}
	private CellModel getValidDownCell(CellModel c){
		CellModel vCell = null;
		int tempRow = m-1;
		int tempCol = c.getCol();
		CellModel tempCell = gboard.get(m-1).get(c.getCol());
		while(tempRow>=c.getRow()){
			if(tempCell.getValue() == 0 || (!tempCell.isMerged() && cellsAdjacent(c, tempCell, "DOWN") && validateFS(tempCell,c) )){
				vCell= tempCell;
				break;
			}else if(tempRow == c.getRow()) {
				vCell= tempCell;
				break;
			}
			tempRow--;
			tempCell = gboard.get(tempRow).get(tempCol);
		}
		return vCell;
	}
	public boolean isMergeState(CellModel c1, CellModel c2, String move){
		boolean isCellsAdjacent = cellsAdjacent(c1,c2,move);
		boolean isValidFS = validateFS(c1,c2);
		boolean isAlreadyMerged = mergeValidation(c1,c2);
		
		if(isCellsAdjacent && isValidFS && !isAlreadyMerged){
			return true;
		}
		return false;
	}
	private boolean cellsAdjacent(CellModel c1, CellModel c2, String move){
		int row_1 = c1.getRow();
		int col_1 = c1.getCol();

		int row_2 = c2.getRow();
		int col_2 = c2.getCol();
		
		//check if row/col is same and two cells are adjacent iff they are next to each other with space or without space
		if(row_1==row_2 && (move.toUpperCase().equals("LEFT") || move.toUpperCase().equals("RIGHT"))){
			for(int col=col_1+1; col<col_2; col++) {
				if(gboard.get(row_1).get(col).getValue() != 0) {
					return false;
				}
			}
			return true;
		}else if(col_1==col_2 && (move.toUpperCase().equals("UP") || move.toUpperCase().equals("DOWN"))){
			for(int row=row_1+1; row<row_2; row++) {
				if(gboard.get(row).get(col_1).getValue() != 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	private boolean validateFS(CellModel c1, CellModel c2){
		//FS: stands for fibonacci series
		int value_1 = c1.getValue();
		int value_2 = c2.getValue();

		if(value_1 == value_2 && value_1!=1 && value_2!=1) {
			return false;
		}
		int diff = Math.abs(value_1 - value_2);
		
		if(value_1 <= value_2 && diff <= value_1 || value_2 <= value_1 && diff <= value_2){
			return true;
		}
		return false;
	}
	private boolean mergeValidation(CellModel c1, CellModel c2){
		if(c1.isMerged() || c2.isMerged()){
			return true;
		}
		return false;
	}
	public void mergeCells(CellModel c1, CellModel c2, String move){
		int value_1 = c1.getValue();
		int value_2 = c2.getValue();
		
		int sum = value_1+value_2;
		updateBoardAfterMerge(c1,c2,sum,move);
	}
	private boolean isCellsSame(CellModel fCell, CellModel c){
		int fRow = fCell.getRow();
		int fCol = fCell.getCol();
		
		int c_row = c.getRow();
		int c_col = c.getCol();
		
		if(fRow == c_row && fCol == c_col){
			return true;
		}
		return false;
	}
	public void updateMergeCellList(){
		while(!mergedCells.isEmpty()){
			int mc_row = mergedCells.get(0).getRow();
			int mc_col = mergedCells.get(0).getCol();
			
			CellModel mc_board = gboard.get(mc_row).get(mc_col);
			mc_board.setMerged(false);
			mergedCells.remove(0);
		}
	}
	private void updateBoardAfterMerge(CellModel c1, CellModel c2, int sum, String move){
		CellModel fCell = null;
		if(move.toUpperCase().equals("LEFT")){
			fCell = storeAtFarthestCell(c1, sum, true, move);
			cellsAlignment(fCell, c1, c2);
		}else if(move.toUpperCase().equals("RIGHT")){
			fCell = storeAtFarthestCell(c2, sum, true, move);
			cellsAlignment(fCell, c1, c2);
		}else if(move.toUpperCase().equals("UP")){
			fCell = storeAtFarthestCell(c1,sum, true, move);
			cellsAlignment(fCell, c1, c2);
		}else if(move.toUpperCase().equals("DOWN")){
			fCell = storeAtFarthestCell(c2, sum, true, move);
			cellsAlignment(fCell, c1, c2);
		}
		mergedCells.add(fCell);
	}
	private void cellsAlignment(CellModel fCell, CellModel c1,CellModel c2) {
		if(isCellsSame(fCell,c1)){
			resetCell(c2);
		}else{
			resetCell(c1);
			resetCell(c2);			
		}		
	}
	public void updateWithoutMerge(CellModel c, String move){
		CellModel fCell = null;
		//store at farthest cell in the direction of move
		fCell = storeAtFarthestCell(c, c.getValue(), c.isMerged(), move);
		if(!isCellsSame(fCell,c)){
			resetCell(c);
		}
	}
	private boolean getGameStatus() {
		for(int row=0; row<m; row++){
			for(int col=0; col<n; col++){
				if(gboard.get(row).get(col).getValue() == 0){
					return true;
				}
			}
		}
		return false;
	}
	public boolean nextMoveExist() {
		if(!getGameStatus()) {
			System.out.println("No Next Move Exists. GAME OVER");
			return false;
		}else {
			populateRandomCell();		//populate "1"	at random empty cell after every move
			return true;
		}
	}
	public void startGame(){
		createBoard();
		printBoard();
	}
}
