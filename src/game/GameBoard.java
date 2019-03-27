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
            int[] newPieceCoords = pieceCoords.clone(), droppedPieceCoords = new int[2];
            boolean searchDrop = false;

            newPieceCoords[1] += 1;

            newBoard[pieceCoords[0]][pieceCoords[1]] = '_'; // Old position
            newBoard[newPieceCoords[0]][newPieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; // Switch place

            // Make piece in question fall if that's the case
            if (newPieceCoords[0] < this.board.length - 1 && this.board[newPieceCoords[0] + 1][newPieceCoords[1]] == '_')
            {
                newBoard = dropPiece(newBoard, newPieceCoords);
                droppedPieceCoords = getDroppedPieceCoords(newPieceCoords[1]);
                searchDrop = true;
            }
                

            // Make the other pieces above the original one fall if they exist
            if (pieceCoords[0] > 0 && newBoard[pieceCoords[0] - 1][pieceCoords[1]] != '_') 
                newBoard = dropColumn(newBoard, pieceCoords[1]); 

            newBoard = explodeAround(pieceCoords[0], pieceCoords[1], newBoard);

            if(searchDrop)
                newBoard = explodeAround(droppedPieceCoords[0], droppedPieceCoords[1], newBoard);
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
            int[] newPieceCoords = pieceCoords.clone(), droppedPieceCoords = new int[2];
            boolean searchDrop = false;

            newPieceCoords[1] -= 1;

            newBoard[pieceCoords[0]][pieceCoords[1]] = '_'; //Old position
            newBoard[newPieceCoords[0]][newPieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; //Switch place

            //Make piece in question fall if that's the case
            if(newPieceCoords[0] < this.board.length - 1 && this.board[newPieceCoords[0] + 1][newPieceCoords[1]] == '_')
            {
                newBoard = dropPiece(newBoard, newPieceCoords);
                droppedPieceCoords = getDroppedPieceCoords(newPieceCoords[1]);
                searchDrop = true;
            }
        
            //Make the other pieces above the original one fall if they exist
            if(pieceCoords[0] > 0 && newBoard[pieceCoords[0] - 1][pieceCoords[1]] != '_')    
                newBoard = dropColumn(newBoard, pieceCoords[1]);


            newBoard = explodeAround(pieceCoords[0], pieceCoords[1], newBoard);

            if(searchDrop) 
                newBoard = explodeAround(droppedPieceCoords[0], droppedPieceCoords[1], newBoard);
            
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
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0]][pieceCoords[1] - 1])
                return new GameBoard(newBoard);

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] - 1]; 
            newBoard[pieceCoords[0]][pieceCoords[1] - 1] = board[pieceCoords[0]][pieceCoords[1]]; 
        }

        newBoard = explodeAround(pieceCoords[0], pieceCoords[1], newBoard);
        newBoard = explodeAround(pieceCoords[0], pieceCoords[1] - 1, newBoard);

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
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0]][pieceCoords[1] + 1])
                return new GameBoard(newBoard);

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1] + 1];
            newBoard[pieceCoords[0]][pieceCoords[1] + 1] = board[pieceCoords[0]][pieceCoords[1]]; 
        }

        newBoard = explodeAround(pieceCoords[0], pieceCoords[1], newBoard);
        newBoard = explodeAround(pieceCoords[0], pieceCoords[1] + 1, newBoard);

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
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0] - 1][pieceCoords[1]])
                return new GameBoard(newBoard);

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0] - 1][pieceCoords[1]];
            newBoard[pieceCoords[0] - 1][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; 
        }

        newBoard = explodeAround(pieceCoords[0], pieceCoords[1], newBoard);
        newBoard = explodeAround(pieceCoords[0] - 1, pieceCoords[1], newBoard);

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
            if(board[pieceCoords[0]][pieceCoords[1]] == board[pieceCoords[0] + 1][pieceCoords[1]])
                return new GameBoard(newBoard);

            newBoard[pieceCoords[0]][pieceCoords[1]] = board[pieceCoords[0] + 1][pieceCoords[1]];
            newBoard[pieceCoords[0] + 1][pieceCoords[1]] = board[pieceCoords[0]][pieceCoords[1]]; 
        }

        newBoard = explodeAround(pieceCoords[0], pieceCoords[1], newBoard);
        newBoard = explodeAround(pieceCoords[0] + 1, pieceCoords[1], newBoard);

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

    public int[] getDroppedPieceCoords(int column)
    {
        int[] droppedPieceCoords = new int[2];

        for(int i = 0; i < board.length; i++)
            if(board[i][column] != '_')
            {
                droppedPieceCoords[0] = i;
                droppedPieceCoords[1] = column;

                return droppedPieceCoords;
            }

        droppedPieceCoords[0] = -1;
        droppedPieceCoords[1] = -1;

        return droppedPieceCoords;
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

                break;
            }
                
        return newBoard;
    }

    public char[][] explodeAll(char[][] board)
    {
        char[][] newBoard = copyBoard(board), testBoard;
        
        for(int i = 0; i < board.length; i++)
        {
            testBoard = explodeLine(i, newBoard);

            if(testBoard != null)
            {
                newBoard = copyBoard(testBoard);
                printBoard(newBoard);
                i = 0;
                continue;
            }
            else
            {
                if(i < newBoard[i].length)
                {
                    testBoard = explodeColumn(i, newBoard);

                    if(testBoard != null)
                    {
                        newBoard = copyBoard(testBoard);
                        printBoard(newBoard);
                        i = 0;
                        continue;
                    }
                }
            }        
        }

        return newBoard;
    }

    public char[][] explodeAround(int line, int column, char[][] board)
    {
        char[][] newBoard = copyBoard(board), testBoard;

        System.out.print("" + line + " | ");
        System.out.println(column);

        testBoard = explodeLine(line, newBoard);
        
        if(testBoard != null)
            return explodeAll(testBoard);
        else
        {
            testBoard = explodeColumn(column, newBoard);

            if(testBoard != null)
                return explodeAll(testBoard);
            else
                return board;
        }
        
    }

    public char[][] explodeLine(int line, char[][] board)
    {
        char[][] newBoard = copyBoard(board);
        char currentColor = 'X';
        int counter = 1;
        boolean foundPattern = false;

        for(int i = 0; i < newBoard[line].length; i++)
        {
            if(newBoard[line][i] != '_')
            {
                if(newBoard[line][i] == currentColor)
                {
                    counter++;

                    if(counter == 3)
                    {
                        foundPattern = true;

                        //Destroy previous blocks
                        for(int j = 0; j < 3; j++)
                        {
                            newBoard[line][i - 2 + j] = '_';

                            if(line > 0 && newBoard[line - 1][i - 2 + j] != '_')
                                newBoard = dropColumn(newBoard, i - 2 + j);
                        }
                    }
                    else
                        if(counter > 3)
                        {
                            newBoard[line][i] = '_';

                            if(line > 0 && newBoard[line - 1][i] != '_')
                                newBoard = dropColumn(newBoard, i);
                        }
                }
                else
                {
                    currentColor = newBoard[line][i];
                    counter = 1;
                }
            }
            else
            {
                currentColor = 'X';
            }
        }

        if(foundPattern)
            return newBoard;
        else
            return null;
    }

    public char[][] explodeColumn(int column, char[][] board)
    {
        char[][] newBoard = copyBoard(board);
        char currentColor = 'X';
        int counter = 1;
        boolean foundPattern = false;

        for(int i = newBoard.length - 1; i >= 0; i--)
            if(newBoard[i][column] != '_')
            {
                if(newBoard[i][column] == currentColor)
                {
                    counter++;

                    if(counter == 3)
                    {
                        foundPattern = true;
                        newBoard[i + 2][column] = '_';
                        newBoard[i + 1][column] = '_';
                        newBoard[i][column] = '_';
                    }
                    else
                        if(counter > 3)
                            newBoard[i][column] = '_';
                        
                }
                else
                {
                    if(counter >= 3)
                    {
                        printBoard(newBoard);
                        newBoard = dropColumn(newBoard, column);
                        i += counter;
                        printBoard(newBoard);
                    }

                    counter = 1;
                    currentColor =  newBoard[i][column];
                }

                
            }
            else
            {
                currentColor = 'X';
            }

        if(foundPattern)
            return newBoard;
        else
            return null;

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

    public boolean testPieceCoords(int[] pieceCoords)
    {
        return !(pieceCoords[0] >= this.board.length || pieceCoords[1] >= this.board[0].length 
        || pieceCoords[0] < 0 || pieceCoords[1] < 0 || this.board[pieceCoords[0]][pieceCoords[1]] == '_');
    }
}