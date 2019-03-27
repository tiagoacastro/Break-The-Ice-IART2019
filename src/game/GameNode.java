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
        return null;
    }

    public boolean depthSearch()
    {
        return false;
    }

    public boolean testGoal()
    {
        return false;
    }

    public GameNode move(String direction, int[] pieceCoords)
    {
        GameBoard newBoard = null;
        String operator = "null";

        if(!board.testPieceCoords(pieceCoords))
        {
            System.out.println("Invalid piece in move " + direction);
            return null;
        }

        switch(direction)
        {
            case "left":
                newBoard = board.movePieceLeft(pieceCoords);
                operator = "Move piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") left";
                break;

            case "right":
                newBoard = board.movePieceRight(pieceCoords);
                operator = "Move piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") right";
                break;

            default:
                System.out.println("Invalid move direction: " + direction);
                return null;
        }

        return new GameNode(this, operator, newBoard);
    }

    public GameNode switchBlock(String direction, int[] pieceCoords)
    {
        GameBoard newBoard = null;
        String operator = "null";

        if(!board.testPieceCoords(pieceCoords))
        {
            System.out.println("Invalid piece in move " + direction);
            return null;
        }

        switch(direction)
        {
            case "left":
                newBoard = board.switchPieceLeft(pieceCoords);
                operator = "Switch piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") left";
                break;

            case "right":
                newBoard = board.switchPieceRight(pieceCoords);
                operator = "Switch piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") right";
                break;

            case "up":
                newBoard = board.switchPieceUp(pieceCoords);
                operator = "Switch piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") up";
                break;

            case "down":
                newBoard = board.switchPieceDown(pieceCoords);
                operator = "Switch piece at (" + pieceCoords[0] + ", " + pieceCoords[1] + ") down";
                break;

            default:
                System.out.println("Invalid switch direction: " + direction);
                return null;
        }

        return new GameNode(this, operator, newBoard);
    }

    public void printBoard()
    {
        board.printBoard();
    }

    public GameBoard getBoard()
    {
        return this.board;
    }

    public void setBoard(GameBoard board)
    {
        this.board = board;
    }
}