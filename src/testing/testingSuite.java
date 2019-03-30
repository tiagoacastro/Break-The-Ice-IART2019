package testing;

import java.util.ArrayList;
import java.util.HashMap;

import game.*;

public class testingSuite
{
    public static void main(String args[])
    {
        //testLevels();
        randomTest();
    }

    public static void randomTest()
    {
        GameBoard board = BreakTheIce.getLevelSelected(5);
        GameNode root = new GameNode(null, 0, 0, "root", 0, board); //review this
        
        root.getHeuristic().setCurrentHeuristic(3);
        root.updateHeuristic();
        root.getHeuristic().update(board);
        
        System.out.println(root.getHeuristic().getValue());
    }

    public static void testLevels()
    {
        ArrayList<GameBoard> levelList = new ArrayList<GameBoard>();
        HashMap<Integer, String> algTable = new HashMap<>();
        long[] times = new long[10];
        long startTime, endTime;
        Bot bot;

        levelList.add(BreakTheIce.getLevelSelected(3));
        levelList.add(BreakTheIce.getLevelSelected(4));
        levelList.add(BreakTheIce.getLevelSelected(5));

        algTable.put(1, "Breadth First");
        algTable.put(2, "Depth First");
        algTable.put(3, "Iterative Deepening");
        algTable.put(4, "Uniform Cost");
        algTable.put(5, "Greedy");
        algTable.put(6, "A*");

        for(int i = 0; i < levelList.size(); i++)
        {
            bot = new Bot(new GameNode(null, 0, 0, "root", 0, levelList.get(i))); //review this

            System.out.println("----- Testing with " + (i + 1) + " move(s) solution, " 
                + GameBoard.getBlocksLeft(levelList.get(i).getBoard()) + " blocks -----\n");

            for(int j = 1; j <= 6; j++)
            {
                for(int k = 0; k < 10; k++) //Average
                {
                    startTime = System.currentTimeMillis();
                    bot.search(j, true);
                    endTime = System.currentTimeMillis();
                    times[k] = endTime - startTime;
                }

                System.out.println(algTable.get(j) + " average(ms): " + calculateAverage(times) + "\n");
            }
        }

    }

    public static long calculateAverage(long[] values)
    {
        long sum = 0;

        for(int i = 0; i < values.length; i++)
            sum += values[i];

        return sum / values.length;
    }
}