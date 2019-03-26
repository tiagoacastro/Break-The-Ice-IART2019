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
            {'_', '_', '_', 'c', '_', '_', '_'},
            {'_', '_', '_', 'a', '_', '_', '_'},
            {'_', '_', '_', 'x', '_', '_', '_'},
            {'_', '_', '_', 'b', '_', '_', '_'},
            {'_', 'b', '_', 'c', '_', 'b', '_'}
        };

        GameNode root = new GameNode(null, 0, 0, "root", new GameBoard(board));
        int[] coords = {10, 3};

        root.getBoard().printBoard();

        root.move("left", coords).getBoard().printBoard();

        
    }
}