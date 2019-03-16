package game;

import java.util.Arrays;

public class BreakTheIce 
{
    public static void main(String[] args) throws Exception 
    {
        char[][] board = new char[10][7];

        for(int i = 0; i < board.length; i++)
            Arrays.fill(board[i], '_');

        GameNode root = new GameNode(null, 0, 0, "root", board);

        root.printBoard();
        
    }
}