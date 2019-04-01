package heuristic;

import game.GameBoard;

public class BlockNumHeuristic extends Heuristic 
{
      /**
       * Calculates the heuristic's value based on the blocks left on the board.
       * @param board The board to which the heuristic's value is calculated.
       */
      public void calculate(GameBoard board) {
            char[][] boardChar = board.getBoard();

            if(notPossibleBoard(boardChar))
              return;

            this.value = (this.value * board.getMaxMoves()) / board.getBlocks();
            if (value > board.getMaxMoves())
                value = board.getMaxMoves();
      }

      /**
       * Returns a new instance of this heuristic.
       * @return New instance of BlockNumHeuristic.
       */
      public Heuristic getNewHeuristic()
      {
        return new BlockNumHeuristic();
      }
}