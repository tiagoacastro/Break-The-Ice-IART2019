package game;

public interface GameBoard
{
    public boolean testPieceCoords(int[] pieceCoords);
    public char[][] dropPiece(char[][] board, int[] pieceCoords);
    public char[][] dropColumn(char[][] board, int line);
    public char[][] copyBoard(char[][] board);
    public void printBoard(char[][] board);
}