package game;

import java.util.ArrayList;

public class GameNode extends Node
{
    private GameBoard board;
    private int moves;

    public GameNode(Node parentNode, int depth, int pathCost, String operator, int moves, GameBoard board)
    {
        super(parentNode, depth, pathCost, operator);

        this.board = board;
        this.moves = moves;
    }

    public GameNode(Node parentNode, String operator, int moves, GameBoard board)
    {
        super(parentNode, operator);

        this.board = board;
        this.moves = moves;
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
        if(!this.testGoal())
        {
            if(this.moves > board.getMaxMoves())
                return false;

            ArrayList<Node> children = this.expandNode();

            for(int i = 0; i < children.size(); i++)
            {
                if(children.get(i).depthSearch())
                {
                    Node.solution.add(this.operator);
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

    public boolean iterativeDepthSearch() {
        //maximum limit -> max number of moves ?

        int maxDepth = board.getMaxMoves();

        for (int i = 0; i < maxDepth; i++) {
            if (depthLimitedSearch(depth)) {
                return true;
            } 
        }

        return false;
    } 

    public boolean depthLimitedSearch(int depth) {

        if(!this.testGoal()) {
            Node.solution.add(this.operator);
            return true;
        }

        if(depth == 0) {
            return false;
        }

        ArrayList<Node> children = this.expandNode();

        for(int i = 0; i < children.size(); i++)
        {
            if(children.get(i).depthLimitedSearch(depth-1))
            {
                Node.solution.add(this.operator);
                return true;
            }
        }

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

            if(op.equals("move"))
                this.move(direction, pieceCoords);
            else
                this.switchBlock(direction, pieceCoords);
        }
    }

    public void traceSolutionUp()
    {
        if(!operator.equals("root"))
        {
            solution.add(0, operator);
            ((GameNode) parentNode).traceSolutionUp();
        }
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