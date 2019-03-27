package game;

import java.util.ArrayList;

public class GameNode extends Node
{
    private GameBoard board;

    public GameNode(Node parentNode, int depth, int pathCost, String operator, GameBoard board)
    {
        super(parentNode, depth, pathCost, operator);

        this.board = board;
    }

    public GameNode(Node parentNode, String operator, GameBoard board)
    {
        super(parentNode, operator);

        this.board = board;
    }

    public ArrayList<Node> expandNode()
    {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        char[][] board = getBoard();
        char piece;
        int[] pieceCoords = new int[2];

        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++)
            {
                piece = board[i][j];
                pieceCoords[0] = i;
                pieceCoords[1] = j;

                if(piece != '_')
                {
                    if(j > 0)
                    {
                        

                        if(board[i][j - 1] != '_')
                            nodeList.add(this.switchBlock("left", pieceCoords));
                        else
                            nodeList.add(this.move("left", pieceCoords));
                    }

                    if(j < board[i].length - 1)
                    {
                        if(board[i][j + 1] != '_')
                            nodeList.add(this.switchBlock("right", pieceCoords));
                        else
                            nodeList.add(this.move("right", pieceCoords));
                    }

                    if(i < board.length - 1 && board[i + 1][j] != '_')
                        nodeList.add(this.switchBlock("down", pieceCoords));

                    if(i > 0 && board[i - 1][j] != '_')
                        nodeList.add(this.switchBlock("up", pieceCoords));
                }
            }

        return nodeList;
    }

    public boolean depthSearch()
    {
        return false;
    }

    public boolean testGoal()
    {
        char[][] board = getBoard();

        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++)
                if(board[i][j] != '_')
                    return false;

        return true;
    }

    public GameNode move(String direction, int[] pieceCoords)
    {
        GameBoard newBoard = null;
        String op = "null";
        

        if(!board.testPieceCoords(pieceCoords))
        {
            System.out.println("Invalid piece in move " + direction);
            return null;
        }

        switch(direction)
        {
            case "left":
                newBoard = board.movePieceLeft(pieceCoords);
                op = "Move piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") left";
                break;

            case "right":
                newBoard = board.movePieceRight(pieceCoords);
                op = "Move piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") right";
                break;

            default:
                System.out.println("Invalid move direction: " + direction);
                return null;
        }

        return new GameNode(this, op, newBoard);
    }

    public GameNode switchBlock(String direction, int[] pieceCoords)
    {
        GameBoard newBoard = null;
        String op = "null";

        if(!board.testPieceCoords(pieceCoords))
        {
            System.out.println("Invalid piece in move " + direction);
            return null;
        }

        

        switch(direction)
        {
            case "left":
                newBoard = board.switchPieceLeft(pieceCoords);
                op = "Switch piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") left";
                break;

            case "right":
                newBoard = board.switchPieceRight(pieceCoords);
                op = "Switch piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") right";
                break;

            case "up":
                newBoard = board.switchPieceUp(pieceCoords);
                op = "Switch piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") up";
                break;

            case "down":
                newBoard = board.switchPieceDown(pieceCoords);
                op = "Switch piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") down";
                break;

            default:
                System.out.println("Invalid switch direction: " + direction);
                return null;
        }

        return new GameNode(this, op, newBoard);
    }

    public void printBoard()
    {
        board.printBoard();
    }

    public GameBoard getGameBoard()
    {
        return this.board;
    }

    public char[][] getBoard()
    {
        return board.getBoard();
    }

    public void setGameBoard(GameBoard board)
    {
        this.board = board;
    }
}