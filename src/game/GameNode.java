package game;

import java.util.ArrayList;

public class GameNode extends Node implements GameBoard
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
        if(testPieceCoords(pieceCoords))
        {
            System.out.println("Invalid piece in move " + direction);
            return null;
        }

        switch(direction)
        {
            case "left":
                return moveLeft(pieceCoords);

            case "right":
                return moveRight(pieceCoords);

            default:
                System.out.println("Invalid move direction: " + direction);
                return null;
        }
    }

    // Pretty much cpy pasted from move left, needs to be altered
    public GameNode moveRight(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board);

        if (pieceCoords[1] == board[0].length - 1 || this.board[pieceCoords[0]][pieceCoords[1] + 1] != '_') 
        {
            System.out.println("Can't move right");
            return null;
        } 
        else 
        {
            pieceCoords[1] += 1;

            newBoard[pieceCoords[0]][pieceCoords[1] - 1] = '_'; // Old position
            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] - 1]; // Switch place

            // Make piece in question fall if that's the case
            if (pieceCoords[0] < this.board.length - 1 && this.board[pieceCoords[0] + 1][pieceCoords[1]] == '_')
                newBoard = dropPiece(newBoard, pieceCoords);

            // Make the other pieces above the original one fall if they exist
            if (newBoard[pieceCoords[0] - 1][pieceCoords[1] - 1] != '_') 
                newBoard = dropColumn(newBoard, pieceCoords[1] - 1); 
            
        }

        return new GameNode(this, "Move Left", newBoard);
    }

    public GameNode moveLeft(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board);

        if(pieceCoords[1] == 0 || this.board[pieceCoords[0]][pieceCoords[1] - 1] != '_')
        {
            System.out.println("Can't move left");
            return null;
        }
        else
        {
            pieceCoords[1] -= 1;

            newBoard[pieceCoords[0]][pieceCoords[1] + 1] = '_'; //Old position
            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] + 1]; //Switch place

            //Make piece in question fall if that's the case
            if(pieceCoords[0] < this.board.length - 1 && this.board[pieceCoords[0] + 1][pieceCoords[1]] == '_')
                newBoard = dropPiece(newBoard, pieceCoords);

            //Make the other pieces above the original one fall if they exist
            if(newBoard[pieceCoords[0] - 1][pieceCoords[1] + 1] != '_')    
                newBoard = dropColumn(newBoard, pieceCoords[1] + 1);
            
                
        }

        return new GameNode(this, "Move Left", newBoard);
    }

    public boolean testPieceCoords(int[] pieceCoords)
    {
        return pieceCoords[0] >= this.board.length || pieceCoords[1] >= this.board[0].length 
        || pieceCoords[0] < 0 || pieceCoords[1] < 0 || this.board[pieceCoords[0]][pieceCoords[1]] == '_';
    }

    public char[][] dropPiece(char[][] board, int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(board);
        int[] coords = pieceCoords.clone();
        char piece = newBoard[coords[0]][coords[1]];
        
        newBoard[coords[0]][coords[1]] = '_';

        do
        {
            coords[0] += 1;
        }
        while(coords[0] < board.length - 1 && newBoard[coords[0] + 1][coords[1]] == '_');

        newBoard[coords[0]][coords[1]] = piece;

        return newBoard;
    }

    public char[][] dropColumn(char[][] board, int column)
    {
        int i;
        char[][] newBoard = copyBoard(board);
        int[] pieceCoords = {0, column};

        for(i = 0; i < board.length; i++)
            if(board[i][column] != '_')
                break;

        for(; i < board.length - 1; i++)
            if(board[i + 1][column] == '_')
            {
                pieceCoords[0] = i;

                while(newBoard[pieceCoords[0]][pieceCoords[1]] != '_')
                {
                    newBoard = dropPiece(newBoard, pieceCoords);
                    pieceCoords[0]--;
                }

            }
                
        return newBoard;
    }

    public char[][] copyBoard(char[][] board)
    {
        char newBoard[][] = new char[board.length][board[0].length];

        for(int i = 0; i < board.length; i++)
            newBoard[i] = board[i].clone();

        return newBoard;
    }

    public void printBoard()
    {
        printBoard(board);
    }

    public void printBoard(char[][] board)
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

        System.out.println("-------------------------------------");
    }

    public char[][] getBoard()
    {
        return this.board;
    }

    public void setBoard(char[][] board)
    {
        this.board = board;
    }
}