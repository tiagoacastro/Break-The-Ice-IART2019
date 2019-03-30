package game;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Bot
{
    GameNode root;

    public Bot(GameNode root)
    {
        this.root = root;
    }

    public boolean search(int searchMode, boolean test)
    {
        boolean solutionFound = false;
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
                System.out.println("\nSolution found\n");
                root.printBoard();
                root.traceSolution();
            }
            else
                System.out.println("No solution was found");
        }
        
        return solutionFound;
    }

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
                GameNode.analyzedNodes++;

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

    public boolean uniformCostSearch() {

        PriorityQueue<Node> activeNodes = new PriorityQueue<Node>();
        PriorityQueue<Node> childrenNodes = new PriorityQueue<Node>();
        ArrayList<Node> childrenNodesAR = new ArrayList<Node>();
        
        activeNodes.add(root);

        for(int i = 0; i <= root.getGameBoard().getMaxMoves(); i++)
        {

            childrenNodes.clear();
            
            while(activeNodes.size() != 0) {
                GameNode.analyzedNodes++;
    
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
}