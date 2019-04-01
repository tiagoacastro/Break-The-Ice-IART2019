package heuristic;

import game.GameBoard;

public class ColorHeuristic extends Heuristic {
    /**
     * Calculates the heuristic's value based on the colors and pieces left on the board.
     * @param board The board to which the heuristic's value is calculated.
     */
      public void calculate(GameBoard board)
      {
          char[][] boardChar = board.getBoard();

          if(notPossibleBoard(boardChar))
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

          this.value = (i * board.getMaxMoves()) / board.getColors();
      }

      /**
       * Returns a new instance of this heuristic.
       * @return New instance of ColorHeuristic.
       */
      public Heuristic getNewHeuristic()
      {
        return new ColorHeuristic();
      }
}