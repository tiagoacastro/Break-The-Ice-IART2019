package game;

public class CloseChainHeuristic extends Heuristic
{
    public void update(GameBoard gameBoard)
    {
        char[][] board = gameBoard.getBoard();
        int[] pieceCoords = new int[2];

        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++)
            {
                pieceCoords[0] = i;
                pieceCoords[1] = j;

                if(board[i][j] != '_') 
                    if(testMoveLeftChain(gameBoard, pieceCoords) || testMoveRightChain(gameBoard, pieceCoords)
                        || testSwitchLeftChain(gameBoard, pieceCoords) || testSwitchRightChain(gameBoard, pieceCoords)
                        || testSwitchUpChain(gameBoard, pieceCoords) || testSwitchDownChain(gameBoard, pieceCoords))
                        value++;
            }    
    }

    public boolean testSwitchDownChain(GameBoard gameBoard, int[] pieceCoords)
    {
        char[][] board = gameBoard.getBoard();
        char color = board[pieceCoords[0]][pieceCoords[1]];
        int[] movedPieceCoords = new int[] {pieceCoords[0] + 1, pieceCoords[1]};

        if(gameBoard.validateSwitchDown(pieceCoords))
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[movedPieceCoords[0]][movedPieceCoords[1]])
                return false;
            
            return testCloseChain(board, movedPieceCoords, color);
        } 

        return false;
    }

    public boolean testSwitchUpChain(GameBoard gameBoard, int[] pieceCoords)
    {
        char[][] board = gameBoard.getBoard();
        char color = board[pieceCoords[0]][pieceCoords[1]];
        int[] movedPieceCoords = new int[] {pieceCoords[0] - 1, pieceCoords[1]};

        if(gameBoard.validateSwitchUp(pieceCoords))
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[movedPieceCoords[0]][movedPieceCoords[1]])
                return false;
            
            return testCloseChain(board, movedPieceCoords, color);
        } 

        return false;
    }

    public boolean testSwitchLeftChain(GameBoard gameBoard, int[] pieceCoords)
    {
        char[][] board = gameBoard.getBoard();
        char color = board[pieceCoords[0]][pieceCoords[1]];
        int[] movedPieceCoords = new int[] {pieceCoords[0], pieceCoords[1] - 1};

        if(gameBoard.validateSwitchLeft(pieceCoords))
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[movedPieceCoords[0]][movedPieceCoords[1]])
                return false;
            
            return testCloseChain(board, movedPieceCoords, color);
        } 

        return false;
    }

    public boolean testSwitchRightChain(GameBoard gameBoard, int[] pieceCoords)
    {
        char[][] board = gameBoard.getBoard();
        char color = board[pieceCoords[0]][pieceCoords[1]];
        int[] movedPieceCoords = new int[] {pieceCoords[0], pieceCoords[1] + 1};

        if(gameBoard.validateSwitchRight(pieceCoords))
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[movedPieceCoords[0]][movedPieceCoords[1]])
                return false;
            
            return testCloseChain(board, movedPieceCoords, color);
        } 

        return false;
    }

    public boolean testMoveRightChain(GameBoard gameBoard, int[] pieceCoords)
    {
        char[][] board = gameBoard.getBoard();
        char color = board[pieceCoords[0]][pieceCoords[1]];
        int[] movedPieceCoords = new int[] {pieceCoords[0], pieceCoords[1] + 1};

        if(gameBoard.validateMoveRight(pieceCoords))
        {
            board[pieceCoords[0]][pieceCoords[1]] = '_';
            board[movedPieceCoords[0]][movedPieceCoords[1]] = color;

            if(pieceCoords[0] > 0 && board[pieceCoords[0] - 1][pieceCoords[1]] != '_')
            {
                board = gameBoard.dropColumn(board, pieceCoords[1]);

                if(findPatternAround(board, new int[] {pieceCoords[0], pieceCoords[1]}))
                    return true;
            }

            if(pieceCoords[0] < board.length - 1 && board[movedPieceCoords[0] + 1][movedPieceCoords[1]] == '_')
            {
                board = gameBoard.dropPiece(board, movedPieceCoords);

                int[] droppedPieceCoords = gameBoard.getDroppedPieceCoords(movedPieceCoords[1], board);

                if(findPatternAround(board, droppedPieceCoords))
                    return true;
            }
            else
                return findPatternInColumn(board, movedPieceCoords[1]);
        } 

        return false;
    }

    public boolean testMoveLeftChain(GameBoard gameBoard, int[] pieceCoords)
    {
        char[][] board = gameBoard.getBoard();
        char color = board[pieceCoords[0]][pieceCoords[1]];
        int[] movedPieceCoords = new int[] {pieceCoords[0], pieceCoords[1] - 1};
        
        if(gameBoard.validateMoveLeft(pieceCoords))
        {
            board[pieceCoords[0]][pieceCoords[1]] = '_';
            board[movedPieceCoords[0]][movedPieceCoords[1]] = color;

            if(pieceCoords[0] > 0 && board[pieceCoords[0] - 1][pieceCoords[1]] != '_')
            {
                board = gameBoard.dropColumn(board, pieceCoords[1]);

                if(findPatternAround(board, new int[] {pieceCoords[0], pieceCoords[1]}))
                    return true;
            }

            if(pieceCoords[0] < board.length - 1 && board[movedPieceCoords[0] + 1][movedPieceCoords[1]] == '_')
            {
                board = gameBoard.dropPiece(board, movedPieceCoords);

                int[] droppedPieceCoords = gameBoard.getDroppedPieceCoords(movedPieceCoords[1], board);

                if(findPatternAround(board, droppedPieceCoords))
                    return true;
            }
            else
                return findPatternInColumn(board, movedPieceCoords[1]);

        } 

        return false;
    }

    public boolean testCloseChain(char[][] board, int[] position, char color)
    {
        if(position[0] + 2 <= board.length - 1)
            if(board[position[0] + 1][position[1]] == color && board[position[0] + 2][position[1]] == color)
                return true;
        
        if(position[0] - 2 >= 0)
            if(board[position[0] - 1][position[1]] == color && board[position[0] - 2][position[1]] == color)
                return true;

        if(position[1] - 2 >= 0)
            if(board[position[0]][position[1] - 1] == color && board[position[0]][position[1] - 2] == color)
                return true;

        if(position[1] + 2 <= board[0].length - 1)
            if(board[position[0]][position[1] + 1] == color && board[position[0]][position[1] + 2] == color)
                    return true;
    
        return false;
    }

    public boolean findPatternAround(char[][] board, int[] coords)
    {
        return findPatternInLine(board, coords[0]) || findPatternInColumn(board, coords[1]);
    }

    public boolean findPatternInLine(char[][] board, int line)
    {
        char currentColor = '_';
        int counter = 1;

        for(int i = 0; i < board[line].length; i++)
        {
            if(board[line][i] == currentColor && board[line][i] != '_')
            {
                counter++;

                if(counter == 3)
                    return true;   
            }
            else
            {
                counter = 1;
                currentColor = board[line][i];
            }
        }

        return false;
    }

    public boolean findPatternInColumn(char[][] board, int column)
    {
        char currentColor = '_';
        int counter = 1;

        for(int i = board.length - 1; i >= 0; i--)
        {
            if(board[i][column] == currentColor && board[i][column] != '_')
            {
                counter++;

                if(counter == 3)
                    return true;
            }
            else 
            {
                counter = 1;
                currentColor =  board[i][column];
            }
        }

        return false;
    }

    public int compareTo(Heuristic h)
    {
        return this.value - h.value;
    }
}