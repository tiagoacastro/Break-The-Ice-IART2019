package heuristic;

import game.GameBoard;

public class ColorHeuristic extends Heuristic 
{
  public void calculate(GameBoard board) 
  {
      char[][] boardChar = board.getBoard();

      if(!possibleBoard(boardChar))
        return;

      int i = 0;
      if (purple > 0)
        i++;
      if (orange > 0)
        i++;
      if (red > 0)
        i++;
      if (blue > 0)
        i++;
      if (green > 0)
        i++;
      if (yellow > 0)
        i++;

      this.value = (this.value * board.getColors()) / board.getBlocks();
      if (value > board.getMaxMoves())
        value = board.getMaxMoves();
    
  }


}