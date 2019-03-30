package game;

public class ColorHeuristic extends Heuristic {
    public int compareTo(Heuristic h) {
        return this.value - h.value;
    }

  public void update(GameBoard board) {
      char[][] boardChar = board.getBoard();

    auxiliar(boardChar);
    if((purple | orange | red | blue | green | yellow) < 3 && (purple | orange | red | blue | green | yellow) > 0)
      this.value = Integer.MAX_VALUE;
    else {
      int i = 0;
      if(purple > 0) i++;
      if(orange > 0) i++;
      if(red > 0) i++;
      if(blue > 0) i++;
      if(green > 0) i++;
      if(yellow > 0) i++;

      this.value *= i;
    }
  }


}