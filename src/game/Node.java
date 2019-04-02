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
    protected static ArrayList<String> solution = new ArrayList<>();

    protected String id;

    /**
     * Constructor of the class with most members.
     * @param parentNode The parent node of this node.
     * @param depth The depth at which this node is at.
     * @param pathCost The path cost to reach this node.
     * @param operator This node's operator.
     * @param searchOption This node's search option.
     * @param heuristic This node's heuristic.
     */
    Node(Node parentNode, int depth, int pathCost, String operator, int searchOption, Heuristic heuristic)
    {
        this.parentNode = parentNode;
        this.depth = depth;
        this.pathCost = pathCost;
        this.searchOption = 0;
        this.operator = operator;
        this.heuristic = heuristic;

        if(parentNode == null)
            this.id = operator;
        else
            this.id = parentNode.id + " -> " + operator;
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
        this.searchOption = parentNode.getSearchOption();
        if (parentNode.heuristic != null) {
            this.heuristic = parentNode.getHeuristic().getNewHeuristic();
        }

        this.id = parentNode.id + " -> " + operator;
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
     * Tests if the goal of game has been accomplished in this node.
     * @return True if the game has been won and false otherwise.
     */
    public abstract boolean testGoal();

    public abstract GameBoard getGameBoard();

    /**
     * Executes the moves indicated by the operators in the Solution array.
     */
    public abstract void traceSolution();

    /**
     * Adds the correspondent node's operators to the Solution array all the way from the final/acceptance node.
     */
    public abstract void traceSolutionUp();

    /**
     * Calculates the heuristic value.
     */
    public abstract void calculateHeuristic();

    public abstract boolean isRepeated();

    public abstract boolean isImpossibleState();

    /**
     * Retrieves the depth.
     * @return The depth this node's at.
     */
    public int getDepth()
    {
        return depth;
    }

    /**
     * Used to compare this node to another depending on the current search function.
     * @param o The node to compare to.
     * @return The numerical difference between them.
     */
    public int compareTo(Node o) 
    {
        double comparation;

        switch(this.searchOption) 
        {
            case 4:
                comparation = this.depth - o.depth;
                break;
            case 5:
                comparation = this.heuristic.compare(o.heuristic);
                break;
            case 6:
                return this.heuristic.compareTo(o.heuristic) + (this.depth - o.depth);
            default:
                return 0;
        }

        if(comparation < 0)
            return -1;
        else if(comparation > 0)
            return 1;
        return 0;
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
     * Retrieves the search option.
     * @return The search option.
     */
    public int getSearchOption()
    {
        return searchOption;
    }

    /**
     * Retrieves the current heuristic.
     * @return The current heuristic.
     */
    public Heuristic getHeuristic()
    {
        return this.heuristic;
    }

    /**
     * Sets this node heuristic's engine.
     * @param heuristic The new heuristic.
     */
    public void setHeuristic(Heuristic heuristic) 
    {
        this.heuristic = heuristic;
        this.calculateHeuristic();
    } 
}