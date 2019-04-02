package heuristic;

import game.GameBoard;

/**
 * Heuristic based on the number of chains that are one move away from forming and the number of blocks remaining.
 */
public class CloseChainHeuristic extends Heuristic
{
    private int blocksInChains = 0;
    /**
     * Calculates the heuristic's value based on the number of chains that are able to be formed with just one move
     * and the number of blocks remaining.
     * @param gameBoard The gameBoard to which apply the heuristic.
     */
    public void calculate(GameBoard gameBoard)
    {
        char[][] board = gameBoard.getBoard();
        int[] pieceCoords = new int[2];
        int nChains = 0, blocksLeft = GameBoard.getBlocksLeft(board);
        blocksInChains = 0;

        if(notPossibleBoard(board))
            return;

        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++)
            {
                pieceCoords[0] = i;
                pieceCoords[1] = j;

                if(board[i][j] != '_') 
                    if(testMoveLeftChain(gameBoard, pieceCoords) || testMoveRightChain(gameBoard, pieceCoords)
                        || testSwitchLeftChain(gameBoard, pieceCoords) || testSwitchRightChain(gameBoard, pieceCoords)
                        || testSwitchUpChain(gameBoard, pieceCoords) || testSwitchDownChain(gameBoard, pieceCoords))
                        nChains++;
            }

        if(blocksLeft == 0)
            value = 0;
        else
        {
            if(nChains == 0)
                value = 2;
            else 
            {
                value = blocksLeft / (blocksInChains);

                if(value == 0)
                    value = 1;

                if (value > gameBoard.getMaxMoves())
                    value = gameBoard.getMaxMoves();
            }
        }

        //System.out.println(blocksInChains + " - " + value);
    }

    /**
    * Returns a new instance of this heuristic.
    * @return New instance of CloseChainsHeuristic.
    */
    public Heuristic getNewHeuristic()
    {
    return new CloseChainHeuristic();
    }

    /**
     * Checks if there are any chains formed by a switch down of a piece.
     * @param gameBoard The board to analyse.
     * @param pieceCoords The piece's coordinates.
     * @return True if there were chains formed and false otherwise.
     */
    private boolean testSwitchDownChain(GameBoard gameBoard, int[] pieceCoords)
    {
        char[][] board = gameBoard.getBoard();
        char color = board[pieceCoords[0]][pieceCoords[1]];
        int[] movedPieceCoords = new int[] {pieceCoords[0] + 1, pieceCoords[1]};

        if(gameBoard.validateSwitchDown(pieceCoords))
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[movedPieceCoords[0]][movedPieceCoords[1]])
                return false;

            board[pieceCoords[0]][pieceCoords[1]] = board[movedPieceCoords[0]][movedPieceCoords[1]];
            board[movedPieceCoords[0]][movedPieceCoords[1]] = color;
            
            return findPatternInColumn(board, pieceCoords[1]) || findPatternInLine(board, pieceCoords[0]) 
                || findPatternInLine(board, movedPieceCoords[0]);
        } 

        return false;
    }

        /**
     * Checks if there are any chains formed by a switch up of a piece.
     * @param gameBoard The board to analyse.
     * @param pieceCoords The piece's coordinates.
     * @return True if there were chains formed and false otherwise.
     */
    private boolean testSwitchUpChain(GameBoard gameBoard, int[] pieceCoords)
    {
        char[][] board = gameBoard.getBoard();
        char color = board[pieceCoords[0]][pieceCoords[1]];
        int[] movedPieceCoords = new int[] {pieceCoords[0] - 1, pieceCoords[1]};

        if(gameBoard.validateSwitchUp(pieceCoords))
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[movedPieceCoords[0]][movedPieceCoords[1]])
                return false;

            board[pieceCoords[0]][pieceCoords[1]] = board[movedPieceCoords[0]][movedPieceCoords[1]];
            board[movedPieceCoords[0]][movedPieceCoords[1]] = color;
            
            return findPatternInColumn(board, pieceCoords[1]) || findPatternInLine(board, pieceCoords[0]) 
                || findPatternInLine(board, movedPieceCoords[0]);
        } 

        return false;
    }

    /**
     * Checks if there are any chains formed by a switch left of a piece.
     * @param gameBoard The board to analyse.
     * @param pieceCoords The piece's coordinates.
     * @return True if there were chains formed and false otherwise.
     */
    private boolean testSwitchLeftChain(GameBoard gameBoard, int[] pieceCoords)
    {
        char[][] board = gameBoard.getBoard();
        char color = board[pieceCoords[0]][pieceCoords[1]];
        int[] movedPieceCoords = new int[] {pieceCoords[0], pieceCoords[1] - 1};

        if(gameBoard.validateSwitchLeft(pieceCoords))
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[movedPieceCoords[0]][movedPieceCoords[1]])
                return false;

            board[pieceCoords[0]][pieceCoords[1]] = board[movedPieceCoords[0]][movedPieceCoords[1]];
            board[movedPieceCoords[0]][movedPieceCoords[1]] = color;
            
            return findPatternInLine(board, pieceCoords[0]) || findPatternInColumn(board, pieceCoords[1]) 
            || findPatternInColumn(board, movedPieceCoords[1]);
        } 

        return false;
    }

    /**
     * Checks if there are any chains formed by a switch right of a piece.
     * @param gameBoard The board to analyse.
     * @param pieceCoords The piece's coordinates.
     * @return True if there were chains formed and false otherwise.
     */
    private boolean testSwitchRightChain(GameBoard gameBoard, int[] pieceCoords)
    {
        char[][] board = gameBoard.getBoard();
        char color = board[pieceCoords[0]][pieceCoords[1]];
        int[] movedPieceCoords = new int[] {pieceCoords[0], pieceCoords[1] + 1};

        if(gameBoard.validateSwitchRight(pieceCoords))
        {
            if(board[pieceCoords[0]][pieceCoords[1]] == board[movedPieceCoords[0]][movedPieceCoords[1]])
                return false;

            board[pieceCoords[0]][pieceCoords[1]] = board[movedPieceCoords[0]][movedPieceCoords[1]];
            board[movedPieceCoords[0]][movedPieceCoords[1]] = color;
            
            return findPatternInLine(board, pieceCoords[0]) || findPatternInColumn(board, pieceCoords[1]) 
                || findPatternInColumn(board, movedPieceCoords[1]);
        } 

        return false;
    }

    /**
     * Checks if there are any chains formed by a move right of a piece.
     * @param gameBoard The board to analyse.
     * @param pieceCoords The piece's coordinates.
     * @return True if there were chains formed and false otherwise.
     */
    private boolean testMoveRightChain(GameBoard gameBoard, int[] pieceCoords)
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

    /**
     * Checks if there are any chains formed by a move left of a piece.
     * @param gameBoard The board to analyse.
     * @param pieceCoords The piece's coordinates.
     * @return True if there were chains formed and false otherwise.
     */
    private boolean testMoveLeftChain(GameBoard gameBoard, int[] pieceCoords)
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

                

                if(findPatternAround(board, pieceCoords))
                    return true;
            }

            Boolean b = aux(gameBoard, pieceCoords[0] < board.length - 1 && board[movedPieceCoords[0] + 1][movedPieceCoords[1]] == '_', board, movedPieceCoords);
            if (b != null) return b;

        } 

        return false;
    }

    /**
     * auxiliar function
     * @param gameBoard board
     * @param b boolean
     * @param board actual board
     * @param movedPieceCoords moved piece coordenates
     * @return boolean
     */
    private Boolean aux(GameBoard gameBoard, boolean b, char[][] board, int[] movedPieceCoords) {
        if(b)
        {
            board = gameBoard.dropPiece(board, movedPieceCoords);

            int[] droppedPieceCoords = gameBoard.getDroppedPieceCoords(movedPieceCoords[1], board);

            if(findPatternAround(board, droppedPieceCoords))
                return true;
        }
        else
            return findPatternInColumn(board, movedPieceCoords[1]);
        return null;
    }

    /**
     * Checks if there is a chain around a particular piece.
     * @param board The board to analyse.
     * @param coords The piece's coordinates.
     * @return True if there were chains formed and false otherwise.
     */
    private boolean findPatternAround(char[][] board, int[] coords)
    {
        return findPatternInLine(board, coords[0]) || findPatternInColumn(board, coords[1]);
    }

    /**
     * Checks if there is a chain in a line.
     * @param board The board which contains the line.
     * @param line The line to analyse.
     * @return True if there were chains formed and false otherwise.
     */
    private boolean findPatternInLine(char[][] board, int line)
    {
        char currentColor = '_';
        int counter = 1;
        boolean chainFound = false;

        for(int i = 0; i < board[line].length; i++)
        {
            if(board[line][i] == currentColor && board[line][i] != '_')
            {
                counter++;   

                if(counter == 3)
                {
                    chainFound = true;
                    blocksInChains += 3;
                }  
                else
                    if(counter > 3)
                    {
                        blocksInChains++;
                        counter++;
                    }
            }
            else
            {
                counter = 1;
                currentColor = board[line][i];
            }
        }

        if(chainFound)
            return true;
        
        return false;
    }

    /**
     * Checks if there is a chain in a column.
     * @param board The board which contains the column.
     * @param column The column to analyse.
     * @return True if there were chains formed and false otherwise.
     */
    private boolean findPatternInColumn(char[][] board, int column)
    {
        char currentColor = '_';
        int counter = 1;
        boolean chainFound = false;

        for(int i = board.length - 1; i >= 0; i--)
        {
            if(board[i][column] == currentColor && board[i][column] != '_')
            {
                counter++; 

                if(counter == 3)
                {
                    chainFound = true;
                    blocksInChains += 3;
                }
                else
                    if(counter > 3)
                    {
                        blocksInChains++;
                        counter++;
                    }           
            }
            else 
            {
                counter = 1;
                currentColor =  board[i][column];
            }
        }

        if(chainFound)
            return true;
        
        return false;
    }
}