package testing;

import java.util.HashMap;

import game.*;
import heuristic.*;

/**
 * Suite designed to run statistical analysis tests or just plain, random ones.
 */
public class testingSuite
{
    /**
     * Point of entry for the tests.
     * @param args Command line arguments, none expected.
     */
    public static void main(String args[])
    {
        testLevels();
        //randomTest();
    }

    /**
     * Runs random tests for debug purposes.
     */
    public static void randomTest()
    {
        GameBoard board = new GameBoard(new char[][]
        {
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', 'p', '_', '_', '_'},
            {'_', '_', 'p', 'p', '_', '_', '_'},
            {'_', '_', '_', 'g', 'p', '_', 'p'},
            {'_', '_', 'g', 'p', 'g', 'p', 'p'}
        }, 5, 3, 11, 2);
        int res = 0;

        Heuristic.setCurrentHeuristic(4);
        
        new GameNode(null, 0, 0, "root", 0, board);
    
        System.out.println(res);
    }

    /**
     * Used to get statistical data regarding the different algorythms.
     */
    private static void testLevels()
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

            try {
                System.out.println("----- Testing with " + BreakTheIce.getLevelSelected(i + 1).getMaxMoves()
                        + " move(s) solution, " + GameBoard.getBlocksLeft(BreakTheIce.getLevelSelected(i + 1).getBoard())
                        + " blocks -----\n");
            } catch(NullPointerException e){
                System.out.println("Null pointer exception\n");
                return;
            }

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

    /**
     * Calculates the average of a set of values.
     * @param values The set of values.
     * @return The average of the values.
     */
    private static long calculateAverage(long[] values)
    {
        long sum = 0;

        for(long value : values)
            sum += value;

        return sum / values.length;
    }
}