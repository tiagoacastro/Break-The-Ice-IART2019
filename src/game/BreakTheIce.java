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
            {'_', 'y', '_', '_', '_', '_', '_'},
            {'_', 'g', 'y', 'y', '_', '_', '_'},
            {'_', 'y', 'y', 'b', 'b', '_', '_'},
            {'_', 'g', 'r', 'r', 'b', '_', '_'},
            {'_', 'g', 'b', 'r', 'r', 'b', '_'},
            {'y', 'b', 'r', 'b', 'r', 'b', 'b'}
        };

        GameNode root = new GameNode(null, 0, 0, "root", new GameBoard(board));
        int[] coords = {10, 3};

        root.getBoard().printBoard();

        GameNode node = root.switchBlock("down", coords);

        node.printBoard();
        /*
        if(newBoard.getBoard() != null)
            newBoard.printBoard();
        else
            System.out.println("Null pointer"); */
        
    }
}