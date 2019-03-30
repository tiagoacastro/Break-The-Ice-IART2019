package game;

public class CloseChainHeuristic extends Heuristic
{
    public void update(GameBoard gameBoard)
    {
        char[][] board = gameBoard.getBoard();
        int[] pieceCoords = new int[2];

        System.out.println("smt");

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

                System.out.println("smt2");
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
            if(board[movedPieceCoords[0]][movedPieceCoords[1]] == '_')
            {
                movedPieceCoords = gameBoard.getDroppedPieceCoords(movedPieceCoords[1], board);
                movedPieceCoords[0] = movedPieceCoords[0] - 1;
            }
            
            return testCloseChain(board, movedPieceCoords, color);
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
            if(board[movedPieceCoords[0]][movedPieceCoords[1]] == '_')
            {
                movedPieceCoords = gameBoard.getDroppedPieceCoords(movedPieceCoords[1], board);
                movedPieceCoords[0] = movedPieceCoords[0] - 1;
            }
            
            return testCloseChain(board, movedPieceCoords, color);
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

    public int compareTo(Heuristic h)
    {
        return this.value - h.value;
    }
}