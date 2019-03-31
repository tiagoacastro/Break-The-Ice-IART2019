package game;
import java.util.ArrayList;
import heuristic.*;

public abstract class Node implements Comparable<Node>
{
    protected Node parentNode;
    protected int depth;
    protected int pathCost; //priority (always 1 since from one depth level to the other all possible plays exhaust 1 move)
    protected Heuristic heuristic; 
    protected int searchOption;
    protected String operator;
    protected static ArrayList<String> solution = new ArrayList<String>(); //Prolly vai-se mudar isto

    public Node(Node parentNode, int depth, int pathCost, String operator)
    {
        this.parentNode = parentNode;
        this.depth = depth;
        this.pathCost = pathCost;
        this.searchOption = 0;
        this.operator = operator;
        this.heuristic = Heuristic.createCurrentHeuristic();
    }

    public Node(Node parentNode, String operator)
    {
        this.depth = parentNode.depth + 1;
        this.pathCost = 1;
        this.parentNode = parentNode;
        this.operator = operator;
        
        if(parentNode != null)
            this.heuristic = parentNode.getHeuristic();
        else
            this.heuristic = Heuristic.createCurrentHeuristic();
    }

    public abstract ArrayList<Node> expandNode();
    public abstract boolean depthSearch();
    public abstract boolean iterativeDepthSearch();
    public abstract boolean depthLimitedSearch(int depth);
    public abstract boolean greedySearch();
    public abstract boolean AStarSearch();
    public abstract boolean testGoal();
    public abstract void traceSolution();
    public abstract void traceSolutionUp();

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

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int compareTo(Node o) {
        switch(this.searchOption) {
            case 4:
                return o.pathCost - this.pathCost;
            case 5:
                return this.heuristic.compareTo(o.heuristic);
            case 6:
                return this.heuristic.compareTo(o.heuristic) + o.pathCost - this.pathCost;
            default:
                return 0;
        }
    }

    public static ArrayList<String> getSolution()
    {
        return solution;
    }

    public void setSearchOption(int searchOption) {
        this.searchOption = searchOption;
    }

    public Heuristic getHeuristic()
    {
        return this.heuristic;
    }

}