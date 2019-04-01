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
        Bot bot;

        for(int i = 0; i < 6; i++)
        {
            bot = new Bot(new GameNode(null, 0, 0, "root", 0, BreakTheIce.getLevelSelected(i + 1))); 

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
                if(j == 5 || j == 6)
                    for(int k = 0; k < 3; k++)
                    {
                        Heuristic.setCurrentHeuristic(k + 1);
                        measureTime(bot, j, k+1);
                    }
                else
                    measureTime(bot, j, 0);
            }
        }
    }

    private static void measureTime(Bot bot, int j, int k)
    {
        HashMap<Integer, String> algTable = new HashMap<>();
        long startTime, endTime;
        String heuristic;

        algTable.put(1, "Breadth First");
        algTable.put(2, "Depth First");
        algTable.put(3, "Iterative Deepening");
        algTable.put(4, "Uniform Cost");
        algTable.put(5, "Greedy");
        algTable.put(6, "A*");

        startTime = System.currentTimeMillis();
        bot.search(j, true);
        endTime = System.currentTimeMillis();

        int actualMoves = GameNode.getSolution().size();

            if(GameNode.getSolution().get(0).equals("root"))
                actualMoves -= 1;

        switch(k)
        {
            case 1:
                heuristic = " - Number of blocks";
                break;

            case 2:
                heuristic = " - Number of Colors";
                break;

            case 3:
                heuristic = " - Number of chains";
                break;

            default:
                heuristic = "";
                break;
        }

        System.out.println(algTable.get(j) + heuristic + "(ms): " + (endTime - startTime) + "\n"
                + GameNode.analyzedNodes + " nodes visited (" + actualMoves + " moves performed - "
                + (actualMoves == bot.getRoot().getGameBoard().getOptimalMoves() ? " optimal solution)\n"
                        : " non-optimal solution)\n"));
    }
}