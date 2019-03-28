package game;

import java.util.ArrayList;

public class Bot
{
    GameNode root;

    public Bot(GameNode root)
    {
        this.root = root;
    }

    public boolean search(int searchMode)
    {
        boolean solutionFound = false;

        Node.solution.clear();

        switch(searchMode)
        {
            case 0:
                solutionFound = root.depthSearch();
                break;

            case 1:
                solutionFound = this.widthSearch();
                break;
            case 2:
                solutionFound = root.iterativeDepthSearch();
                break;

            default:
                System.out.println("Invalid search option: " + searchMode);
                return false;
        }

        if(solutionFound)
        {
            System.out.println("Solution found\n");
            root.printBoard();
            root.traceSolution();
        }
        else
            System.out.println("No solution was found");

        return solutionFound;
    }

    public boolean widthSearch()
    {
        ArrayList<Node> activeNodes = new ArrayList<Node>(), childrenNodes = new ArrayList<Node>();
        
        activeNodes.add(root);

        for(int i = 0; i < root.getGameBoard().getMaxMoves(); i++)
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
}