package testing;

import java.util.HashMap;

import game.*;
import heuristic.*;

public class testingSuite
{
    public static void main(String args[])
    {
        testLevels();
        //randomTest();
    }

    public static void randomTest()
    {
        GameBoard board = BreakTheIce.getLevelSelected(5);

        Heuristic.setCurrentHeuristic(3);
        
        GameNode root = new GameNode(null, 0, 0, "root", 0, board); 
    
        root.getHeuristic().calculate(board);
        System.out.println(root.getHeuristic().getValue());
    }

    public static void testLevels()
    {
        HashMap<Integer, String> algTable = new HashMap<>();
        long[] times = new long[10];
        long startTime, endTime;
        Bot bot;

        algTable.put(1, "Breadth First");
        algTable.put(2, "Depth First");
        algTable.put(3, "Iterative Deepening");
        algTable.put(4, "Uniform Cost");
        algTable.put(5, "Greedy");
        algTable.put(6, "A*");

        for(int i = 0; i < 5; i++)
        {
            bot = new Bot(new GameNode(null, 0, 0, "root", 0, BreakTheIce.getLevelSelected(i + 1))); //review this

            System.out.println("----- Testing with " + BreakTheIce.getLevelSelected(i + 1).getMaxMoves() 
                + " move(s) solution, " + GameBoard.getBlocksLeft(BreakTheIce.getLevelSelected(i + 1).getBoard()) 
                + " blocks -----\n");

            for(int j = 1; j <= 6; j++)
            {
                for(int k = 0; k < 10; k++) //Average
                {
                    startTime = System.currentTimeMillis();
                    bot.search(j, true);
                    endTime = System.currentTimeMillis();
                    times[k] = endTime - startTime;
                }

                System.out.println(algTable.get(j) + " average(ms): " + calculateAverage(times) + "\n" + GameNode.analyzedNodes + " nodes visited\n");
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