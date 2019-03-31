package heuristic;

import game.GameBoard;

public class PairsHeuristic extends Heuristic
{
    public void calculate(GameBoard gameBoard) 
    {
        char[][] board = gameBoard.getBoard();
        int nPairs = getPairs(board);
    }

    public int getPairs(char[][] board)
    {
        int pairs = 0;
        char currentColor = 'X';

        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++)
                if(board[i][j] != '_' && board[i][j] == currentColor)
                    pairs++;
                else
                    currentColor = board[i][j];

        for(int i = 0; i < board[0].length; i++)
            for(int j = 0; j < board.length; j++)
            if(board[j][i] != '_' && board[j][i] == currentColor)
                pairs++;
            else
                currentColor = board[j][i];

        return pairs;
    }

    public int compareTo(Heuristic h) 
    {
        return this.value - h.value;
    }

}