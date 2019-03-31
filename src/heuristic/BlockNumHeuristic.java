package heuristic;

import game.GameBoard;

public class BlockNumHeuristic extends Heuristic {
    private int purple=0, orange=0, red=0, blue=0, green=0, yellow=0;

  public void calculate(GameBoard board) {
    char[][] boardChar = board.getBoard();

    auxiliar(boardChar);
    if((purple | orange | red | blue | green | yellow) < 3 && (purple | orange | red | blue | green | yellow) > 0){
        this.value = Integer.MAX_VALUE;
    }
  }

    public int compareTo(Heuristic h) {
    return this.value - h.value;
  }
}