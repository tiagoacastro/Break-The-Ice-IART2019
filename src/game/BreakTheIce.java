package game;

import java.util.ArrayList;

public class BreakTheIce 
{
    public static void main(String[] args) throws Exception 
    {
        //7 width, 12 height

        char[][] board0 = 
        {
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', 'c', '_', '_', '_'},
            {'_', '_', '_', 'c', '_', '_', '_'},
            {'_', '_', '_', 'c', '_', '_', '_'},
            {'_', '_', '_', 'd', '_', '_', '_'},
            {'_', '_', '_', 'a', '_', '_', '_'},
            {'_', '_', '_', 'a', '_', '_', '_'},
            {'_', '_', '_', 'b', '_', '_', '_'},
            {'_', '_', '_', 'b', '_', '_', '_'},
            {'_', '_', '_', 'b', '_', '_', '_'}
        };

        char[][] board = 
        {
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', 'y', '_', '_', '_', '_', '_'},
            {'_', 'g', 'y', 'y', '_', '_', '_'},
            {'_', 'y', 'y', 'b', 'b', '_', '_'},
            {'_', 'g', 'r', 'r', 'b', '_', '_'},
            {'_', 'g', 'b', 'r', 'r', 'b', '_'},
            {'y', 'b', 'r', 'b', 'r', 'b', 'b'}
        };

        GameNode root = new GameNode(null, 0, 0, "root", 0, new GameBoard(board, 5));
        int[] coords = {10, 3};

        Bot bot = new Bot(root);
        bot.search(0);

        
        /*
        GameNode node = root.switchBlock("down", coords);

        node.printBoard();
        
        if(newBoard.getBoard() != null)
            newBoard.printBoard();
        else
            System.out.println("Null pointer"); */
        
    }
}