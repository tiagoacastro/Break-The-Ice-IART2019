package game;

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
}