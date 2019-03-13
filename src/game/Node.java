package game;
import java.util.ArrayList;

public abstract class Node
{
    protected Node parentNode;
    protected int depth;
    protected int pathCost;
    protected String operator;
    protected static ArrayList<String> solution = new ArrayList<String>(); //Prolly vai-se mudar isto

    public Node(Node parentNode, int depth, int pathCost, String operator)
    {
        this.parentNode = parentNode;
        this.depth = depth;
        this.pathCost = pathCost;
        this.operator = operator;
    }

    public Node(Node parentNode, String operator)
    {
        this.depth = parentNode.depth + 1;
        this.pathCost = 1;
    }

    public abstract ArrayList<Node> expandNode();
    public abstract boolean depthSearch();
    public abstract boolean testGoal();

    public Node getParentNode()
    {
        return parentNode;
    }

    public int getDepth()
    {
        return depth;
    }

    public int getPathCost()
    {
        return pathCost;
    }

    public String getOperator()
    {
        return operator;
    }

    public static ArrayList<String> getSolution()
    {
        return solution;
    }

}