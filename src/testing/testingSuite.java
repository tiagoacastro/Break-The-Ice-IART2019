package testing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import game.*;
import heuristic.*;

/**
 * Suite designed to run statistical analysis tests or just plain, random ones.
 */
public class testingSuite
{
    private static ArrayList<GameBoard> boards;
    /**
     * Point of entry for the tests.
     * @param args Command line arguments, none expected.
     */
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|  Input the file names separated by space without extension (ENTER to skip) |");
        System.out.println("+----------------------------------------------------------------------------+");
        String files = sc.nextLine();

        boards = new ArrayList<>();

        if(files.length() > 0) {
            String[] fileNames = files.split(" ");

            for (String fileName : fileNames) {
                File file = new File("levels/" + fileName);
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }

                String line;
                char[][] board = new char[12][7];
                int n = 0;
                int optimalMoves=0, maxPlays, blocks = 0, colors = 0;
                boolean purple = false, orange = false, red = false, blue = false, green = false, yellow = false;

                try {
                    optimalMoves = Integer.parseInt(br.readLine());
                    while ((line = br.readLine()) != null) {
                        char[] row = new char[7];
                        for (int i = 0; i < 7; i++) {
                            char c = line.charAt(i);
                            row[i] = c;
                            if (c != '_') {
                                blocks++;
                                switch (c) {
                                    case 'p':
                                        purple = true;
                                        break;
                                    case 'o':
                                        orange = true;
                                        break;
                                    case 'r':
                                        red = true;
                                        break;
                                    case 'b':
                                        blue = true;
                                        break;
                                    case 'g':
                                        green = true;
                                        break;
                                    case 'y':
                                        yellow = true;
                                        break;
                                }
                            }
                        }
                        board[n] = row;
                        n++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }

                if (purple) colors++;
                if (orange) colors++;
                if (red) colors++;
                if (blue) colors++;
                if (green) colors++;
                if (yellow) colors++;

                maxPlays = optimalMoves + 2;

                boards.add(new GameBoard(board, maxPlays, optimalMoves, blocks, colors));
            }
        }

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
            {'_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', 'y', 'b', '_', '_'},
            {'_', '_', '_', 'b', 'y', '_', '_'},
            {'_', '_', '_', 'y', 'b', '_', '_'}
        }, 5, 3, 11, 2);
        int res = 0;
        int[] pieceCoords = new int[] {10, 4};
        
        GameNode root = new GameNode(null, 0, 0, "root", 4, new BlockNumHeuristic(), 1, board);

        root.getGameBoard().switchPieceLeft(pieceCoords).printBoard();
    }

    /**
     * Used to get statistical data regarding the different algorithms.
     */
    private static void testLevels()
    {        
        Bot bot;

        if(boards.size() == 0)
        for(int i = 0; i < 8; i++)
        {
            bot = new Bot(new GameNode(null, 0, 0, "root", 4, new BlockNumHeuristic(), 0, 
                BreakTheIce.getLevelSelected(i + 1))); 

            try {
                System.out.println("----- Testing with " + BreakTheIce.getLevelSelected(i + 1).getMaxMoves()
                        + " move(s) solution, " + GameBoard.getBlocksLeft(BreakTheIce.getLevelSelected(i + 1).getBoard())
                        + " blocks -----\n");
            } catch(NullPointerException e){
                System.out.println("Null pointer exception\n");
                return;
            }

            if (aux(bot)) return;
        }
        else
        for(GameBoard board : boards){
            bot = new Bot(new GameNode(null, 0, 0, "root", 4, new BlockNumHeuristic(), 0,
                    board));

            try {
                System.out.println("----- Testing with " + board.getMaxMoves()
                        + " move(s) solution, " + GameBoard.getBlocksLeft(board.getBoard())
                        + " blocks -----\n");
            } catch(NullPointerException e){
                System.out.println("Null pointer exception\n");
                return;
            }

            if (aux(bot)) return;
        }
    }

    /**
     * auxiliar method
     * @param bot bot
     * @return boolean result
     */
    private static boolean aux(Bot bot) {
        for(int j = 1; j <= 6; j++)
        {
            bot.getRoot().setSearchOption(j);

            if(j == 5 || j == 6)
                for(int k = 0; k < 3; k++)
                {
                    switch(k)
                    {
                        case 0:
                            bot.getRoot().setHeuristic(new BlockNumHeuristic());
                            break;

                        case 1:
                            bot.getRoot().setHeuristic(new ColorHeuristic());
                            break;

                        case 2:
                            bot.getRoot().setHeuristic(new CloseChainHeuristic());
                            break;

                        default:
                            System.out.println("k value doesn't match heuristic options");
                            return true;
                    }
                    measureTime(bot, j, k+1);
                }
            else
                measureTime(bot, j, 0);
        }
        return false;
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

            if(actualMoves > 0 && GameNode.getSolution().get(0).equals("root"))
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