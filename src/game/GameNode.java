package game;

import java.util.ArrayList;
import heuristic.*;

/**
 * Represents an instance of a node in a search graph of a solution for a level in the game.
 */
public class GameNode extends Node 
{
    /**
     * The state of the board in this node.
     */
    private GameBoard board;

    /**
     * The number of moves performed to get to this node.
     */
    private int moves;

    /**
     * The number of nodes analyzed in the graph.
     */
    public static int analyzedNodes = 0;

    /**
     * Constructor of the class with most members.
     * @param parentNode The parent node of this node.
     * @param depth The depth at which this node is at.
     * @param pathCost The path cost to reach this node.
     * @param operator This node's operator.
     * @param heuristic This node's heuristic.
     * @param searchOption This node's search option
     * @param moves The number of mobes performed to get to this node.
     * @param board The state of the board in this node.
     */
    public GameNode(Node parentNode, int depth, int pathCost, String operator,  
        int searchOption, Heuristic heuristic, int moves, GameBoard board)
    {
        super(parentNode, depth, pathCost, operator, searchOption, heuristic);

        this.board = board;
        this.moves = moves;

        if(this.heuristic != null)
            this.calculateHeuristic();
    }

    /**
     * Constructor of the class with only a few members, the ones remaining being instantiated automatically as if
     * this node was a regular child of the parent node indicated.
     * @param parentNode The parent node of this node.
     * @param operator This node's operator.
     * @param moves The number of mobes performed to get to this node.
     * @param board The state of the board in this node.
     */
    private GameNode(Node parentNode, String operator, int moves, GameBoard board)
    {
        super(parentNode, operator);

        this.board = board;
        this.moves = moves;
        
        if(this.heuristic != null)
            this.calculateHeuristic();
    }

    /**
     * Expands a node, i.e, returns its possible children.
     * @return The children of this node.
     */
    public ArrayList<Node> expandNode()
    {
        ArrayList<Node> nodeList = new ArrayList<>();
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

    public void calculateHeuristic()
    {
        this.heuristic.calculate(board);
    }

    /**
     * Performs a deapth first search in this node.
     * @return Flag indicating if the search was successfull or not.
     */
    public boolean depthSearch()
    {
        if(this.moves >= board.getMaxMoves())
            return false;

        if(!this.testGoal())
        {
            ArrayList<Node> children = this.expandNode();

            for(Node n : children)
            {
                if(n != null && n.depthSearch())
                {
                    Node.solution.add(0, this.operator);
                    return true;
                }
                    
            }

            return false;
        }
        else
        {
            Node.solution.add(this.operator);
            return true;
        }
    }

    /**
     * Performs an iterative deapth first search in this node.
     * @return Flag indicating if the search was successfull or not.
     */
    public boolean iterativeDepthSearch() 
    {
        int maxDepth = board.getMaxMoves();
        int depth = 0;

        for (int i = 0; i <= maxDepth; i++) 
        {
            if(depthLimitedSearch(depth)) 
            {
                return true;
            } 

            depth++;
        }

        return false;
    } 

    /**
     * Auxiliary function to the iterative depth first search.
     * @return Flag indicating if the search was successfull or not.
     */
    public boolean depthLimitedSearch(int depth) 
    {
        if(this.depth == depth)
        {
            if(this.testGoal()) 
            {
                Node.solution.add(this.operator);
                return true;
            }
        }
        else {

            analyzedNodes++;

            if(this.depth < depth)
            {
                ArrayList<Node> children = this.expandNode();

                for(Node n : children)
                {
                    if(n.depthLimitedSearch(depth))
                    {
                        Node.solution.add(0, this.operator);
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * Tests if the goal of game has been accomplished in this node.
     * @return True if the game has been won and false otherwise.
     */
    public boolean testGoal()
    {
        char[][] board = getBoard();

        analyzedNodes++;

        for(char[] line : board)
            for(char cell : line)
                if(cell != '_')
                    return false;

        return true;
    }

    /**
     * Moves a piece in the board.
     * @param direction The direction in which to move the piece.
     * @param pieceCoords The coordinates of the piece.
     * @return A new node representing the board if the move was succesfull or null if not. 
     */
    private GameNode move(String direction, int[] pieceCoords)
    {
        GameBoard newBoard ;
        String op;
        
        if(board.testPieceCoords(pieceCoords))
        {
            System.out.println("Invalid piece in move " + direction);
            return null;
        }

        switch(direction)
        {
            case "left":
                newBoard = board.movePieceLeft(pieceCoords);
                op = "move left " + pieceCoords[0] + " " +  pieceCoords[1];
                break;

            case "right":
                newBoard = board.movePieceRight(pieceCoords);
                op = "move right " + pieceCoords[0] + " " + pieceCoords[1];
                break;

            default:
                System.out.println("Invalid move direction: " + direction);
                return null;
        }

        return new GameNode(this, op, this.moves + 1, newBoard);
    }

    /**
     * Switches a block with another one.
     * @param direction The direction in which to switch the block.
     * @param pieceCoords The piece's coordinates.
     * @return A new node representing the board if the move was succesfull or null if not.
     */
    private GameNode switchBlock(String direction, int[] pieceCoords)
    {
        GameBoard newBoard;
        String op;

        if(board.testPieceCoords(pieceCoords))
        {
            System.out.println("Invalid piece in move " + direction);
            return null;
        }

        switch(direction)
        {
            case "left":
                newBoard = board.switchPieceLeft(pieceCoords);
                op = "switch left " + pieceCoords[0] + " " + pieceCoords[1];
                break;

            case "right":
                newBoard = board.switchPieceRight(pieceCoords);
                op = "switch right " + pieceCoords[0] + " " + pieceCoords[1];
                break;

            case "up":
                newBoard = board.switchPieceUp(pieceCoords);
                op = "switch up " + pieceCoords[0] + " " + pieceCoords[1];
                break;

            case "down":
                newBoard = board.switchPieceDown(pieceCoords);
                op = "switch down " + pieceCoords[0] + " " + pieceCoords[1];
                break;

            default:
                System.out.println("Invalid switch direction: " + direction);
                return null;
        }

        return new GameNode(this, op, this.moves + 1, newBoard);
    }

    /**
     * Executes the moves indicated by the operators in the Solution array.
     */
    public void traceSolution()
    {
        String[] move;
        String op, direction;
        int[] pieceCoords = new int[2];

        GameBoard.setShowMove(true);

        for(int i = 0; i < Node.solution.size(); i++)
        {
            move = Node.solution.get(i).split("\\s+");

            if(move.length != 4)
            {
                if(!move[0].equals("root"))
                    System.out.println("Invalid Solution: " + Node.solution.get(i));
                    
                continue;
            }
            
            op = move[0];
            direction = move[1];
            pieceCoords[0] = Integer.parseInt(move[2]);
            pieceCoords[1] = Integer.parseInt(move[3]);

            System.out.println("\n" + op + " " + direction + "(" + move[2] + "," + move[3] + ")\n");

            try {
                if (op.equals("move"))
                    this.board = this.move(direction, pieceCoords).getGameBoard();
                else
                    this.board = this.switchBlock(direction, pieceCoords).getGameBoard();
            } catch(NullPointerException e){
                System.out.println("Null pointer exception\n");
                return;
            }
        }
    }

    /**
     * Adds the correspondent node's operators to the Solution array all the way from the final/acceptance node.
     */
    public void traceSolutionUp()
    {
        if(!operator.equals("root"))
        {
            solution.add(0, operator);
            parentNode.traceSolutionUp();
        }
    }

    /**
     * Prints the current board.
     */
    public void printBoard()
    {
        board.printBoard();
    }

    /**
     * Retrieves the current game board.
     * @return The current game board.
     */
    public GameBoard getGameBoard()
    {
        return this.board;
    }

    /**
     * Retrieves the matrix instance of the board.
     * @return The matrix representing the actual board.
     */
    private char[][] getBoard()
    {
        return board.getBoard();
    }
}