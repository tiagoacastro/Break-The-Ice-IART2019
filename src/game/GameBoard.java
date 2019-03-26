package game;

public class GameBoard
{
    private char[][] board;

    public GameBoard(char[][] board)
    {
        this.board = board;
    }

    public GameBoard movePieceRight(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board);

        if (pieceCoords[1] == board[0].length - 1 || this.board[pieceCoords[0]][pieceCoords[1] + 1] != '_') 
        {
            System.out.println("Can't move right");
            return null;
        } 
        else 
        {
            int[] newPieceCoords = pieceCoords.clone();

            newPieceCoords[1] += 1;

            newBoard[pieceCoords[0]][pieceCoords[1]] = '_'; // Old position
            newBoard[newPieceCoords[0]][newPieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; // Switch place

            // Make piece in question fall if that's the case
            if (newPieceCoords[0] < this.board.length - 1 && this.board[newPieceCoords[0] + 1][newPieceCoords[1]] == '_')
                newBoard = dropPiece(newBoard, newPieceCoords);

            // Make the other pieces above the original one fall if they exist
            if (pieceCoords[0] > 0 && newBoard[pieceCoords[0] - 1][pieceCoords[1]] != '_') 
                newBoard = dropColumn(newBoard, pieceCoords[1]); 
            
        }

        return new GameBoard(newBoard);
    }

    public GameBoard movePieceLeft(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board);

        if(pieceCoords[1] == 0 || this.board[pieceCoords[0]][pieceCoords[1] - 1] != '_')
        {
            System.out.println("Can't move left");
            return null;
        }
        else
        {
            int[] newPieceCoords = pieceCoords.clone();

            newPieceCoords[1] -= 1;

            newBoard[pieceCoords[0]][pieceCoords[1]] = '_'; //Old position
            newBoard[newPieceCoords[0]][newPieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; //Switch place

            //Make piece in question fall if that's the case
            if(newPieceCoords[0] < this.board.length - 1 && this.board[newPieceCoords[0] + 1][newPieceCoords[1]] == '_')
                newBoard = dropPiece(newBoard, newPieceCoords);

            //Make the other pieces above the original one fall if they exist
            if(pieceCoords[0] > 0 && newBoard[pieceCoords[0] - 1][pieceCoords[1]] != '_')    
                newBoard = dropColumn(newBoard, pieceCoords[1]);
        }

        return new GameBoard(newBoard);
    }

    public GameBoard switchPieceLeft(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board);

        if(pieceCoords[1] == 0 || this.board[pieceCoords[0]][pieceCoords[1] - 1] == '_')
        {
            System.out.println("Can't switch left");
            return null;
        }
        else
        {
            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] - 1]; 
            newBoard[pieceCoords[0]][pieceCoords[1] - 1] = board[pieceCoords[0]][pieceCoords[1]]; 
        }

        return new GameBoard(newBoard);
    }

    public GameBoard switchPieceRight(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board);

        if(pieceCoords[1] == board[0].length - 1 || this.board[pieceCoords[0]][pieceCoords[1] + 1] == '_')
        {
            System.out.println("Can't switch right");
            return null;
        }
        else
        {
            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] + 1];
            newBoard[pieceCoords[0]][pieceCoords[1] + 1] = board[pieceCoords[0]][pieceCoords[1]]; 
        }

        return new GameBoard(newBoard);
    }

    public GameBoard switchPieceUp(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board);

        if(pieceCoords[0] == 0 || this.board[pieceCoords[0] - 1][pieceCoords[1]] == '_')
        {
            System.out.println("Can't switch up");
            return null;
        }
        else
        {
            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0] - 1][pieceCoords[1]];
            newBoard[pieceCoords[0] - 1][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; 
        }

        return new GameBoard(newBoard);
    }

    public GameBoard switchPieceDown(int[] pieceCoords)
    {
        char[][] newBoard = copyBoard(this.board);

        if(pieceCoords[0] == board.length - 1|| this.board[pieceCoords[0] + 1][pieceCoords[1]] == '_')
        {
            System.out.println("Can't switch down");
            return null;
        }
        else
        {
            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0] + 1][pieceCoords[1]];
            newBoard[pieceCoords[0] + 1][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; 
        }

        return new GameBoard(newBoard);
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

    public char[][] explodeBlocks()
    {
        char[][] newBoard = copyBoard(board);

        for(int i = 0; i < newBoard.length; i++)
            for(int j = 0; j < newBoard[j].length; j++)
            {
                
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

    public char[][] getBoard()
    {
        return board;
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

        System.out.println("-------------------------------------");
    }

    public boolean testPieceCoords(int[] pieceCoords)
    {
        return !(pieceCoords[0] >= this.board.length || pieceCoords[1] >= this.board[0].length 
        || pieceCoords[0] < 0 || pieceCoords[1] < 0 || this.board[pieceCoords[0]][pieceCoords[1]] == '_');
    }
}