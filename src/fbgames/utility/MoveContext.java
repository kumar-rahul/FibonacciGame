package fbgames.utility;

import fbgames.event.IMove;

public class MoveContext {
	private IMove sequence;

	   public MoveContext(IMove sequence){
	      this.sequence = sequence;
	   }

	   public boolean executeSequence(){
	      return sequence.move();
	   }
}
