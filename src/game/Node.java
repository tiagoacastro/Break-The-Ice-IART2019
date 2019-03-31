package game;
import java.util.ArrayList;
import heuristic.*;

/**
 * Represents a regular node in a search problem.
 */
public abstract class Node implements Comparable<Node>
{
    /**
     * The parent node of this node.
     */
    Node parentNode;

    /**
     * The depth at which this node is at.
     */
    int depth;

    /**
     * The path cost to reach this node (always 1 since from one depth level to the other all possible plays 
     * exhaust 1 move)
     */
    private int pathCost;

    /**
     * The heuristic used.
     */
    protected Heuristic heuristic; 

    /**
     * The type of search being performed.
     */
    private int searchOption;

    /**
     * This node's operator.
     */
    String operator;

    /**
     * List containing the solution to a level in order.
     */
    protected static ArrayList<String> solution = new ArrayList<String>();

    /**
     * Constructor of the class with most members.
     * @param parentNode The parent node of this node.
     * @param depth The depth at which this node is at.
     * @param pathCost The path cost to reach this node.
     * @param operator This node's operator.
     */
    Node(Node parentNode, int depth, int pathCost, String operator)
    {
        this.parentNode = parentNode;
        this.depth = depth;
        this.pathCost = pathCost;
        this.searchOption = 0;
        this.operator = operator;
        this.heuristic = Heuristic.createCurrentHeuristic();
    }

    /**
     * Constructor of the class with only a few members, the ones remaining being instantiated automatically as if
     * this node was a regular child of the parent node indicated.
     * @param parentNode The parent node of this node.
     * @param operator This node's operator.
     */
    Node(Node parentNode, String operator)
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

    /**
     * Expands a node, i.e, returns its possible children.
     * @return The children of this node.
     */
    public abstract ArrayList<Node> expandNode();

    /**
     * Performs a deapth first search in this node.
     * @return Flag indicating if the search was successfull or not.
     */
    public abstract boolean depthSearch();

    /**
     * Performs an iterative deapth first search in this node.
     * @return Flag indicating if the search was successfull or not.
     */
    public abstract boolean iterativeDepthSearch();

    /**
     * Auxiliary function to the iterative depth first search.
     * @return Flag indicating if the search was successfull or not.
     */
    public abstract boolean depthLimitedSearch(int depth);

    /**
     * Performs a greedy search on this node.
     * @return Flag indicating if the search was successfull or not.
     */
    public abstract boolean greedySearch();

    /**
     * Tests if the goal of game has been accomplished in this node.
     * @return True if the game has been won and false otherwise.
     */
    public abstract boolean testGoal();

    /**
     * Executes the moves indicated by the operators in the Solution array.
     */
    public abstract void traceSolution();

    /**
     * Adds the correspondent node's operators to the Solution array all the way from the final/acceptance node.
     */
    public abstract void traceSolutionUp();

    /**
     * Retrieves the parent node.
     * @return The parent node.
     */
    public Node getParentNode()
    {
        return parentNode;
    }

    /**
     * Retrieves the depth.
     * @return The depth this node's at.
     */
    public int getDepth()
    {
        return depth;
    }

    /**
     * Retrieves the path cost.
     * @return The path cost to get to this node.
     */
    public int getPathCost()
    {
        return pathCost;
    }

    /**
     * Retrieves the operator.
     * @return The node's operator.
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * Sets the path cost of this node.
     * @param pathCost The new path cost of this node.
     */
    public void setPathCost(int pathCost) 
    {
        this.pathCost = pathCost;
    }

    /**
     * Used to compare this node to another depending on the current search function.
     * @param o The node to compare to.
     * @return The numerical difference between them.
     */
    public int compareTo(Node o) 
    {
        switch(this.searchOption) 
        {
            case 4:
                return this.depth - o.depth;
            case 5:
                return this.heuristic.compareTo(o.heuristic);
            case 6:
                return this.heuristic.compareTo(o.heuristic) + this.depth - o.depth;
            default:
                return 0;
        }
    }

    /**
     * Retrieves the current solution.
     * @return The list containing the solution to a level.
     */
    public static ArrayList<String> getSolution()
    {
        return solution;
    }

    /**
     * Sets the current search option.
     * @param searchOption The new search option.
     */
    public void setSearchOption(int searchOption) {
        this.searchOption = searchOption;
    }

    /**
     * Retrieves the current heuristic.
     * @return The current heuristic.
     */
    public Heuristic getHeuristic()
    {
        return this.heuristic;
    }
}