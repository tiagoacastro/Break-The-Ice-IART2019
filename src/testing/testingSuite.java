package testing;

import java.util.ArrayList;
import java.util.HashMap;

import game.*;

public class testingSuite
{
    public static void main(String args[])
    {
        testLevels();
    }

    public static void testLevels()
    {
        ArrayList<char[][]> levelList = new ArrayList<char[][]>();
        HashMap<Integer, String> algTable = new HashMap<Integer, String>();
        long[] times = new long[10];
        long startTime, endTime;
        Bot bot;

        levelList.add(BreakTheIce.getLevelSelected(5));
        levelList.add(BreakTheIce.getLevelSelected(1));
        levelList.add(BreakTheIce.getLevelSelected(2));

        algTable.put(1, "Breadcrumbs");
        algTable.put(2, "Depth First");
        algTable.put(3, "Iterative Deepening");
        algTable.put(4, "Uniform Cost");
        algTable.put(5, "Greedy");
        algTable.put(6, "A*");

        for(int i = 0; i < levelList.size(); i++)
        {
            bot = new Bot(new GameNode(null, 0, 0, "root", 0, new GameBoard(levelList.get(i), 3)));

            System.out.println("-----Testing with " + (i + 1) + " move(s) solution, " 
                + GameBoard.getBlocksLeft(levelList.get(i)) + " blocks-----\n");

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