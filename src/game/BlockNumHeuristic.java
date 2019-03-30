package game;

public class BlockNumHeuristic extends Heuristic {
    private int purple=0, orange=0, red=0, blue=0, green=0, yellow=0;

  public void update(GameBoard board) {
    char[][] boardChar = board.getBoard();

    switch(currentHeuristic){
      case 1:
          auxiliar(boardChar);
          if((purple | orange | red | blue | green | yellow) < 3 && (purple | orange | red | blue | green | yellow) > 0){
              this.value = Integer.MAX_VALUE;
          }
          break;
      case 2:
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
          break;
    }
  }

    private void auxiliar(char[][] boardChar) {
        for (int i = 0; i < boardChar.length; i++) {
          for (int j = 0; j < boardChar[i].length; j++) {
            if(boardChar[i][j] != '_') {
                this.value++;
                switch(boardChar[i][j]){
                    case 'p':
                        purple++;
                        break;
                    case 'o':
                        orange++;
                        break;
                    case 'r':
                        red++;
                        break;
                    case 'b':
                        blue++;
                        break;
                    case 'g':
                        green++;
                        break;
                    case 'y':
                        yellow++;
                        break;
                }
            }
          }
        }
    }

    public int compareTo(Heuristic h) {
    return this.value - h.value;
  }
}