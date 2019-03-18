package game;

public class BreakTheIce 
{
    public static void main(String[] args) throws Exception 
    {
        //7 width, 12 height

        char[][] board = 
        {
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', 'b', '_', '_', '_'},
            {'_', '_', '_', 'b', '_', '_', '_'},
            {'_', '_', '_', 'b', '_', '_', '_'},
            {'_', '_', '_', 'b', '_', '_', '_'},
            {'_', '_', '_', 'b', '_', '_', '_'},
            {'_', 'b', 'x', 'c', 'a', 'b', '_'}
        };

        GameNode root = new GameNode(null, 0, 0, "root", board);
        int[] coords = {11, 3};

        root.printBoard();

        root.switchBlock("down", coords).printBoard();

        
    }
}