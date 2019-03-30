package game;

public class BlockNumHeuristic extends Heuristic {

  public void update(GameBoard board) {
    char[][] boardChar = board.getBoard();
    for (int i = 0; i < boardChar.length; i++) {
      for (int j = 0; j < boardChar[i].length; j++) {
        if(boardChar[i][j] != '_') {
          this.value++;
        } 
      }
    }
  }

  public int compareTo(Heuristic h) {
    return this.value - h.value;
  }
}