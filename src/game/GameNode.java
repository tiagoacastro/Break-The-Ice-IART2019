package game;

import java.util.ArrayList;

public class GameNode extends Node
{
    private char[][] board;

    public GameNode(Node parentNode, int depth, int pathCost, String operator, char[][] board)
    {
        super(parentNode, depth, pathCost, operator);
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


}