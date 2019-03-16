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
            {'_', '_', 'b', '_', '_', 'b', '_'}
        };

        GameNode root = new GameNode(null, 0, 0, "root", board);
        int[] coords = {6, 3};

        root.printBoard();

        char[][] newBoard = root.dropColumn(board, 3);

        root.setBoard(newBoard);
        root.printBoard();
    }
}