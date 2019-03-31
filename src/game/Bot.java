package game;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A Bot is responsible for initiating and/or conducting a type of search begining in a root node.
 */
public class Bot
{
    /**
     * The root node in which the search is initiated.
     */
    private GameNode root;

    /**
     * Constructor of the class.
     * @param root
     */
    public Bot(GameNode root)
    {
        this.root = root;
    }

    /**
     * Initiates a type of search.
     * @param searchMode Identifier for the type of search to conduct.
     * @param test Flag indicating if the search being conducted is for statistical purposes (test) only.
     * @return
     */
    public boolean search(int searchMode, boolean test)
    {
        boolean solutionFound = false;
        int movesDiff;

        GameBoard.setShowMove(false);

        Node.solution.clear();

        GameNode.analyzedNodes = 0;

        switch(searchMode)
        {
            case 1:
                solutionFound = this.breadth();
                break;

            case 2:
                solutionFound = root.depthSearch();
                break;
            case 3:
                solutionFound = root.iterativeDepthSearch();
                break;
            case 4:
                solutionFound = this.uniformCostSearch();
                break;
            case 5:
                solutionFound = root.greedySearch();
                break;
            case 6:
                solutionFound = root.AStarSearch();
                break;

            default:
                System.out.println("Invalid search option: " + searchMode);
                return false;
        }

        if(!test)
        {
            if(solutionFound)
            {
                System.out.print("\nSolution found: ");

                movesDiff = GameNode.getSolution().size() - root.getGameBoard().getOptimalMoves();

                if(movesDiff > 3)
                    movesDiff = 3;

                System.out.println(3 - movesDiff + "/3 stars\n");

                root.printBoard();
                root.traceSolution();
            }
            else
                System.out.println("No solution was found");
        }
        
        return solutionFound;
    }

    /**
     * Breadth search.
     * @return Flag indicating if the search was successfull or not.
     */
    public boolean breadth()
    {
        ArrayList<Node> activeNodes = new ArrayList<Node>(), childrenNodes = new ArrayList<Node>();
        
        activeNodes.add(root);

        for(int i = 0; i <= root.getGameBoard().getMaxMoves(); i++)
        {
            childrenNodes.clear();
            childrenNodes.trimToSize();

            for(int j = 0; j < activeNodes.size(); j++)
            {
                if(activeNodes.get(j).testGoal())
                {
                    activeNodes.get(j).traceSolutionUp();
                    return true;
                }

                childrenNodes.addAll(activeNodes.get(j).expandNode());
            }

            activeNodes.clear();
            activeNodes.trimToSize();
            activeNodes.addAll(childrenNodes);
        }

        return false;
    }

    /**
     * Uniform cost search.
     * @return Flag indicating if the search was successfull or not.
     */
    public boolean uniformCostSearch() {

        PriorityQueue<Node> activeNodes = new PriorityQueue<Node>();
        PriorityQueue<Node> childrenNodes = new PriorityQueue<Node>();
        ArrayList<Node> childrenNodesAR = new ArrayList<Node>();
        
        activeNodes.add(root);

        for(int i = 0; i <= root.getGameBoard().getMaxMoves(); i++)
        {

            childrenNodes.clear();
            
            while(activeNodes.size() != 0) {
    
                if(activeNodes.peek().testGoal()) {
                    activeNodes.poll().traceSolutionUp();
                    return true;
                }
    
                childrenNodesAR.addAll(activeNodes.poll().expandNode());
            }
            
            for(Node child: childrenNodesAR) {
                child.setSearchOption(4);
                activeNodes.add(child);
            }

        }

        return false;
    }

    /**
     * Returns the root node.
     * @return The root node.
     */
    public GameNode getRoot()
    {
        return root;
    }
}