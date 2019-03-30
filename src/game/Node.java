package game;
import java.util.ArrayList;

public abstract class Node implements Comparable<Node>
{
    protected Node parentNode;
    protected int depth;
    protected int pathCost; //priority (always 1 since from one depth level to the other all possible plays exhaust 1 move)
    protected BlockNumHeuristic heuristic; //number of blocks left
    protected int searchOption;
    protected String operator;
    protected static ArrayList<String> solution = new ArrayList<String>(); //Prolly vai-se mudar isto

    public Node(Node parentNode, int depth, int pathCost, String operator)
    {
        this.parentNode = parentNode;
        this.depth = depth;
        this.pathCost = pathCost;
        this.heuristic = new BlockNumHeuristic();
        this.searchOption = 0;
        this.operator = operator;
    }

    public Node(Node parentNode, String operator)
    {
        this.depth = parentNode.depth + 1;
        this.pathCost = 1;
        this.parentNode = parentNode;
        this.heuristic = new BlockNumHeuristic();
        this.operator = operator;
    }

    public abstract ArrayList<Node> expandNode();
    public abstract boolean depthSearch();
    public abstract boolean iterativeDepthSearch();
    public abstract boolean depthLimitedSearch(int depth);
    public abstract boolean greedySearch();
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
        }

        return o.pathCost - this.pathCost;
    }

    public static ArrayList<String> getSolution()
    {
        return solution;
    }

    public void setSearchOption(int searchOption) {
        this.searchOption = searchOption;
    }

}