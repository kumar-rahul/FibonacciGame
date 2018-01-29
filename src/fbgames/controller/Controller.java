package fbgames.controller;

import java.util.Scanner;

import fbgames.service.FbGameService;

public class Controller {
	FbGameService fs;

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
				status = fs.moveLeft();
		      break;

		   case 2 :
				System.out.println("right");
				status = fs.moveRight();
		      break;

		   case 3 :
				System.out.println("up");
				status = fs.moveUp();
		      break;
		      
		   case 4 :
				System.out.println("down");
				status = fs.moveDown();
		      break;
			   
		   default: System.out.println("Invalid Choice. Try Again"); 
		}
		return status;
	}
	
}
