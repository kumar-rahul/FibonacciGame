package fbgames;

public class CellModel {
	
	private int row;
	private int col;
	private boolean isMerged;
	private int value=0;
	
	CellModel(int r, int c, int v, boolean mstatus){
		this.row = r;
		this.col = c;
		this.isMerged = mstatus;
		this.value = v;
	}
	public int getRow() {
		return row;
	}
//	public void setRow(int row) {
//		this.row = row;
//	}
	public int getCol() {
		return col;
	}
//	public void setCol(int col) {
//		this.col = col;
//	}
	public boolean isMerged() {
		return isMerged;
	}
	public void setMerged(boolean isMerged) {
		this.isMerged = isMerged;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

}
