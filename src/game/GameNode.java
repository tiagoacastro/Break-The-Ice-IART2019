package game;

import java.util.ArrayList;

public class GameNode extends Node
{
    private char[][] board;

    public GameNode(Node parentNode, int depth, int pathCost, String operator, char[][] board)
    {
        super(parentNode, depth, pathCost, operator);

        this.board = board;
    }

    public GameNode(Node parentNode, String operator, char[][] board)
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
        char newBoard[][] = board.clone();
        
        if(!testPieceCoords(pieceCoords))
        {
            System.out.println("Invalid piece in move " + direction);
            return null;
        }

        switch(direction)
        {
            case "left":
                
                if(pieceCoords[1] <= 0 || this.board[pieceCoords[0]][pieceCoords[1] - 1] != '_')
                {
                    System.out.println("Can't move left");
                    return null;
                }
                else
                {
                    pieceCoords[1] -= 1;

                    if(pieceCoords[0] < this.board.length - 1 && this.board[pieceCoords[0] + 1][pieceCoords[1]] == '_')
                        newBoard = dropPiece(pieceCoords);
                    else
                    {
                        newBoard[pieceCoords[0]][pieceCoords[1] + 1] = '_'; //Old position
                        newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] + 1];
                    }
                        
                }

            break;
        }

        return new GameNode(this, "Move Left", newBoard);
    }

    public boolean testPieceCoords(int[] pieceCoords)
    {
        return pieceCoords[0] >= this.board.length || pieceCoords[1] >= this.board[0].length;
    }

    public char[][] dropPiece(int[] pieceCoords)
    {
        char[][] newBoard = board.clone();

        do
        {
            pieceCoords[0] += 1;
        }
        while(newBoard[pieceCoords[0] + 1][pieceCoords[1]] != '_');

        return newBoard;
    }

    public void printBoard()
    {
        for(int i = 0; i < board.length; i++)
        {
            System.out.print("|");

            for(int j = 0; j < board[i].length; j++)
            {
                System.out.print(board[i][j]);
                System.out.print('|');
            }
                

            System.out.print("\n");
        }
    }
}