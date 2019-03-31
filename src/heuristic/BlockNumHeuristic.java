package heuristic;

import game.GameBoard;

public class BlockNumHeuristic extends Heuristic {
  public void calculate(GameBoard board) {
    char[][] boardChar = board.getBoard();

    if(!possibleBoard(boardChar))
      return;

    this.value /= board.getMaxMoves();
    if (value > board.getMaxMoves())
        value = board.getMaxMoves();
  }
}