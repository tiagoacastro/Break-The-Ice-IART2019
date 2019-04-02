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
     * @param root  root node
     */
    public Bot(GameNode root)
    {
        this.root = root;
    }

    /**
     * Initiates a type of search.
     * @param searchMode Identifier for the type of search to conduct.
     * @param test Flag indicating if the search being conducted is for statistical purposes (test) only.
     */
    public void search(int searchMode, boolean test)
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
                solutionFound = this.greedySearch();
                break;
            case 6:
                solutionFound = this.AStarSearch();
                break;

            default:
                System.out.println("Invalid search option: " + searchMode);
        }

        if(!test)
        {
            if(solutionFound)
            {
                System.out.print("\nSolution found: ");

                movesDiff = GameNode.getSolution().size() - root.getGameBoard().getOptimalMoves();

                if(GameNode.getSolution().get(0).equals("root"))
                    movesDiff -= 1;

                if(movesDiff > 3)
                    movesDiff = 3;
                
                if(movesDiff < 0)
                    movesDiff = 0;

                System.out.println(3 - movesDiff + "/3 stars\n");

                root.printBoard();
                root.traceSolution();
            }
            else
                System.out.println("No solution was found");
        }
    }

    /**
     * Breadth search.
     * @return Flag indicating if the search was successful or not.
     */
    private boolean breadth()
    {
        ArrayList<Node> activeNodes = new ArrayList<>(), childrenNodes = new ArrayList<>();
        
        activeNodes.add(root);

        for(int i = 0; i <= root.getGameBoard().getMaxMoves(); i++)
        {
            childrenNodes.clear();
            childrenNodes.trimToSize();

            for(Node n : activeNodes)
            {
                if(n.testGoal())
                {
                    n.traceSolutionUp();
                    return true;
                }

                if (!n.isImpossibleState())
                    childrenNodes.addAll(n.expandNode());
            }

            activeNodes.clear();
            activeNodes.trimToSize();
            activeNodes.addAll(childrenNodes);
        }

        return false;
    }

    /**
     * Uniform cost search.
     * @return Flag indicating if the search was successful or not.
     */
    private boolean uniformCostSearch() {
        return aux(4);
    }

    /**
     * Performs a greedy search on this node.
     * @return Flag indicating if the search was successful or not.
     */
    private boolean greedySearch() {
        return aux(5);
    }

    /**
     * A* search.
     * @return Flag indicating if the search was successful or not.
     */
    private boolean AStarSearch()
    {
        PriorityQueue<Node> activeNodes = new PriorityQueue<>(); 
        ArrayList<Node> children = new ArrayList<>();
        ArrayList<String> visitedNodes = new ArrayList<>();
        Node currentNode;
        boolean active = false, visited = false;

        root.setSearchOption(6);
        activeNodes.add(root);

        while (!activeNodes.isEmpty()) 
        {
            currentNode = activeNodes.peek();

            //System.out.println("Analyzed nodes: " + GameNode.analyzedNodes + " | Current id: " + currentNode.id + "\n");

            if (currentNode.testGoal()) 
            {
                activeNodes.poll().traceSolutionUp();
                return true;
            }

            activeNodes.poll();
            visitedNodes.add(currentNode.id);

            if(currentNode.depth < root.getGameBoard().getMaxMoves())
            {
                children = currentNode.expandNode();

                for(Node child: children)
                {
                    for(String id: visitedNodes)
                        if(id.equals(child.id))
                        {
                            visited = true;
                            break;
                        }
    
                    if(visited)
                    {
                        visited = false;
                        continue;
                    }
    
                    for(Node n: activeNodes)
                        if(n.id.equals(child.id))
                        {
                            active = true;
                            break;
                        }
    
                    if(!active)
                        activeNodes.add(child);
    
                    active = false;
                }
            }
            

            children.clear();
            children.trimToSize();

        }

        return false;
    }

    /**
     * Auxiliary function to searches using priority queues.
     * @return Flag indicating if the search was successful or not.
     */
    private boolean aux(int i) 
    {
        PriorityQueue<Node> activeNodes = new PriorityQueue<>(); 
        ArrayList<Node> activeNodesAR = new ArrayList<>();
        ArrayList<String> visitedNodes = new ArrayList<>();
        Node currentNode;
        boolean visitedNode = false;

        root.setSearchOption(i);
        activeNodes.add(root);

        while (!activeNodes.isEmpty()) 
        {
            currentNode = activeNodes.peek();

            //System.out.println("Analyzed nodes: " + root.analyzedNodes + "\n");

            if (currentNode.testGoal()) 
            {
                activeNodes.poll().traceSolutionUp();
                return true;
            }

            for (int j = 0; j < visitedNodes.size(); j++)
                if (currentNode.id.equals(visitedNodes.get(j))) 
                {
                    visitedNode = true;
                    break;
                }

            if (!visitedNode && currentNode.getDepth() < root.getGameBoard().getMaxMoves() && !currentNode.isImpossibleState()) 
            {
                activeNodesAR.addAll(currentNode.expandNode());

                for (Node child : activeNodesAR) 
                {
                    activeNodes.add(child);
                        
                }

                activeNodesAR.clear();
                activeNodesAR.trimToSize();

                visitedNodes.add(currentNode.id);
            } 

            visitedNode = false;
            activeNodes.poll();
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