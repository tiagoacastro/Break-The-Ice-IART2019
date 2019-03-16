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
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', 'b', '_', '_', '_'},
            {'_', '_', 'b', 'b', '_', 'b', '_'}
        };

        GameNode root = new GameNode(null, 0, 0, "root", board);

        root.printBoard();

        int[] pieceCoords = {10, 3};
        GameNode node = root.move("left", pieceCoords);

        node.printBoard();

        pieceCoords[1] = 2;

        node.move("left", pieceCoords).printBoard();
    }
}