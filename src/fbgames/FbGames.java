package fbgames;

import java.util.InputMismatchException;
import java.util.Scanner;


public class FbGames {

	public static void main(String[] args) {
		
		int row=0, col=0;
		Scanner scan = new Scanner(System.in);
		Controller ctrl;
	    System.out.println("Please select board size(row>1 and col>1).");
	    System.out.println("Enter row count");

        try {
    	    row = scan.nextInt();
	    if(row<=1) {
    	    		System.out.println("Invalid row count. Row should be >1. Start new game");    	    	
	    }else {
	    	    System.out.println("Enter col count");
	    	    col = scan.nextInt();
	    	    if(col<=1) {
    	    			System.out.println("Invalid col count. Col should be >1. Start new game");    	    	
	    	    }else {
	        		ctrl = new Controller(row,col);
	        	    while(true){
	        		    System.out.println("===============");
	        		    System.out.println("Select Sequence Move:");
	        		    System.out.println("1.Left");
	        		    System.out.println("2.Right");
	        		    System.out.println("3.Up");
	        		    System.out.println("4.Down");
	        		    
	        	        try {
	        	    	    int in = scan.nextInt();
	           		boolean status = ctrl.callService(in);
	           			 
	        	    	    if(!status) {
	        	    			System.out.println("GAME OVER!!");
	        	    			break;
	        	    	    }
	        	        } catch (InputMismatchException e) {
	        	            System.out.print("INVALID CHOICE! Enter only an Integer.\n");
	        	        }
	        		    scan.nextLine();
	        	    }        		
	    	    }
	    	    
	    }
        } catch (InputMismatchException e) {
            System.out.print("INVALID USER INPUT! Enter only an Integer.\n Start Game again.");
        }
		
	}

}
