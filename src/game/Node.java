package game;
import java.util.ArrayList;

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

        switch(Heuristic.currentHeuristic) {
            case 1:
                this.heuristic = new BlockNumHeuristic();
                break;
            case 2:
                this.heuristic = new ColorHeuristic();
                break;
            case 3:
                this.heuristic = new CloseChainHeuristic();
                break;
            default:
                this.heuristic = new BlockNumHeuristic();
        }
    }

    public Node(Node parentNode, String operator)
    {
        this.depth = parentNode.depth + 1;
        this.pathCost = 1;
        this.parentNode = parentNode;
        this.operator = operator;

        switch(Heuristic.currentHeuristic) {
            case 1:
                this.heuristic = new BlockNumHeuristic();
                break;
            case 2:
                this.heuristic = new ColorHeuristic();
                break;
            case 3:
                this.heuristic = new CloseChainHeuristic();
                break;
            default:
                this.heuristic = new BlockNumHeuristic();
        }
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

    public void updateHeuristic() {
        switch(Heuristic.currentHeuristic) {
            case 1:
                this.heuristic = new BlockNumHeuristic();
                break;
            case 2:
                this.heuristic = new ColorHeuristic();
                break;

            case 3:
                this.heuristic = new CloseChainHeuristic();
                
            default:
                this.heuristic = new BlockNumHeuristic();
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