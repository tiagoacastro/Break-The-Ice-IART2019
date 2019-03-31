package heuristic;

import game.GameBoard;

public class BlockNumHeuristic extends Heuristic {
  public void calculate(GameBoard board) {
    char[][] boardChar = board.getBoard();

    auxiliar(boardChar);
    if((purple | orange | red | blue | green | yellow) < 3 && (purple | orange | red | blue | green | yellow) > 0){
        this.value = Integer.MAX_VALUE;
    }

    this.value /= board.getMaxMoves();
    if (value > board.getMaxMoves())
        value = board.getMaxMoves();
  }
}