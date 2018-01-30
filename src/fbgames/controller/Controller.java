package fbgames.controller;

import fbgames.service.FbGameService;
import fbgames.utility.MoveContext;
import fbgames.utility.MoveDown;
import fbgames.utility.MoveLeft;
import fbgames.utility.MoveRight;
import fbgames.utility.MoveUp;

public class Controller {
	FbGameService fs;
	MoveContext mc;

	public Controller(int row, int col){
		this.fs = new FbGameService();
		fs.setRowCount(row);
		fs.setColCount(col);
		this.callService(0);
	}
	public boolean callService(int event){
		boolean status = true;
		switch(event) {
		   case 0 :			   
				fs.startGame();
		      break;

		   case 1 :
				System.out.println("Left");
				mc = new MoveContext(new MoveLeft(fs));
				status = mc.executeSequence();//fs.moveLeft();
		      break;

		   case 2 :
				System.out.println("right");
				mc = new MoveContext(new MoveRight(fs));
				status = mc.executeSequence();//fs.moveRight();
		      break;

		   case 3 :
				System.out.println("up");
				mc = new MoveContext(new MoveUp(fs));
				status = mc.executeSequence();//fs.moveUp();
		      break;
		      
		   case 4 :
				System.out.println("down");
				mc = new MoveContext(new MoveDown(fs));
				status = mc.executeSequence();//fs.moveDown();
		      break;
			   
		   default: System.out.println("Invalid Choice. Try Again"); 
		}
		return status;
	}
	
}
