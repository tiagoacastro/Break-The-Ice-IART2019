package game;

import java.util.Scanner;
import java.util.InputMismatchException;
import heuristic.Heuristic;

/**
 * Main instance of the game, serves as UI and starting point.
 */
public class BreakTheIce 
{
    /**
     * The bot which is to realize the search.
     */
    private static Bot bot;
    
    /**
     * Launching point of the application.
     * @param args Arguments of the main function, none expected.
     */
    public static void main(String[] args)
    {
        menu();
    }

    /**
     * Main menu interface.
     */
    private static void menu()
    {
        int option;

        do
        {
            System.out.println("+----------------+");
            System.out.println("|  Break The Ice |");
            System.out.println("+----------------+");
            System.out.println("| 1 - Play (Bot) |");
            System.out.println("+----------------+");
            System.out.println("|    2 - Exit    |");
            System.out.println("+----------------+");
    
            option = getOption(2);

            switch(option)
            {
                case 1:
                    chooseLevel();
                    break;

                case 2:
                    return;

                default:
                    System.out.println("Option not recognized in main menu");
            }
        }
        while(true);
    }

    /**
     * Level selection interface.
     */
    private static void chooseLevel()
    {
        int option;

        System.out.println("+-----------------+");
        System.out.println("| Level Selection |");
        System.out.println("+-----------------+");
        System.out.println("|   1 - Level 1   |");
        System.out.println("+-----------------+");
        System.out.println("|   2 - Level 2   |");
        System.out.println("+-----------------+");
        System.out.println("|   3 - Level 3   |");
        System.out.println("+-----------------+");
        System.out.println("|   4 - Level 4   |");
        System.out.println("+-----------------+");
        System.out.println("|   5 - Level 5   |");
        System.out.println("+-----------------+");
        System.out.println("|   6 - Level 6   |");
        System.out.println("+-----------------+");

        option = getOption(6);

        bot = new Bot(new GameNode(null, 0, 0, "root", 0, getLevelSelected(option)));
        chooseMode();
    }

    /**
     * Returns a new GameBoard instance based on the level chosen previously.
     * @param option The option chosen.
     * @return The new GameBoard.
     */
    public static GameBoard getLevelSelected(int option)
    {
        int maxPlays, optimalMoves;
        char[][] board;
        int blocks, colors;

        switch(option)
        {
            case 1:
                maxPlays = 3;
                board = new char[][]
                {
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', 'y', '_', '_', '_', '_', '_'},
                    {'_', 'g', 'y', 'y', '_', '_', '_'},
                    {'_', 'y', 'y', 'b', 'b', '_', '_'},
                    {'_', 'g', 'r', 'r', 'b', '_', '_'},
                    {'_', 'g', 'b', 'r', 'r', 'b', '_'},
                    {'y', 'b', 'r', 'b', 'r', 'b', 'b'}
                };
                blocks = 24;
                colors = 4;
                break;

            case 2:
                maxPlays = 3;
                board = new char[][]
                {
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', 'b', 'o', '_'},
                    {'_', '_', '_', 'b', 'o', 'b', '_'},
                    {'_', '_', 'b', 'b', 'y', 'b', '_'},
                    {'_', '_', 'b', 'y', 'g', 'g', '_'},
                    {'_', '_', 'y', 'b', 'g', 'b', 'o'},
                    {'_', 'b', 'g', 'g', 'b', 'g', 'b'}
                };
                blocks = 24;
                colors = 4;
                break;

            case 3:
                maxPlays = 3;
                board = new char[][]
                {
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', 'b', '_', 'y', 'p', '_', '_'},
                    {'_', 'r', '_', 'y', 'p', '_', '_'},
                    {'_', 'r', 'b', 'b', 'y', '_', '_'},
                    {'b', 'b', 'p', 'p', 'b', 'p', 'p'},
                    {'b', 'r', 'b', 'p', 'b', 'p', 'p'}
                };
                blocks = 24;
                colors = 4;
                break;

            case 4:
                maxPlays = 4;
                board = new char[][] 
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
                    {'_', '_', 'r', 'g', 'p', '_', '_'},
                    {'_', 'o', 'g', 'r', 'b', 'p', '_'},
                    {'o', 'g', 'o', 'b', 'r', 'b', 'b'}
                };
                blocks = 16;
                colors = 5;
                break;

            case 5:
                maxPlays = 5;
                board = new char[][] 
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
                    {'_', '_', '_', 'p', '_', '_', '_'},
                    {'_', '_', '_', 'g', 'p', '_', '_'},
                    {'_', '_', 'g', 'p', 'g', 'p', 'p'}
                };
                blocks = 8;
                colors = 2;
                break;

            case 6:
                maxPlays = 6;
                board = new char[][] 
                {
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', 'g', '_', '_', '_'},
                    {'_', '_', '_', 'r', '_', '_', 'y'},
                    {'_', '_', '_', 'r', 'y', 'r', 'r'},
                    {'_', 'g', 'g', 'b', 'y', 'b', 'b'}
                };
                blocks = 13;
                colors = 2;
                break;

            default:
                System.out.println("Option not recognized in level selection");
                return null;
        }

        optimalMoves = maxPlays - 2;

        return new GameBoard(board, maxPlays, optimalMoves, blocks, colors);
    }

    /**
     * Mode selection menu.
     */
    private static void chooseMode()
    {
        int option;

        System.out.println("+-------------------------+");
        System.out.println("|   Algorithm Selection   |");
        System.out.println("+-------------------------+");
        System.out.println("|      1 - Breadth First  |");
        System.out.println("+-------------------------+");
        System.out.println("|     2 - Depth First     |");
        System.out.println("+-------------------------+");
        System.out.println("| 3 - Iterative Deepening |");
        System.out.println("+-------------------------+");
        System.out.println("|     4 - Uniform Cost    |");
        System.out.println("+-------------------------+");
        System.out.println("|        5 - Greedy       |");
        System.out.println("+-------------------------+");
        System.out.println("|          6- A*          |");
        System.out.println("+-------------------------+");

        option = getOption(6);

        if(option == 5 || option == 6) {
            chooseHeuristic();
        }
        bot.getRoot().setHeuristic();
        bot.search(option, false);
    }

    /**
     * Heuristic selection menu.
     */
    private static void chooseHeuristic()
    {
        int option;

        System.out.println("+----------------------------------+");
        System.out.println("|        Heuristic Selection       |");
        System.out.println("+----------------------------------+");
        System.out.println("|       1 - Number of blocks       |");
        System.out.println("+----------------------------------+");
        System.out.println("|       2 - Number of colors       |");
        System.out.println("+----------------------------------+");
        System.out.println("|     3 - Chains one move away     |");
        System.out.println("+----------------------------------+");

        option = getOption(3);
        Heuristic.setCurrentHeuristic(option);
    }

    /**
     * Gets an option from the user and tests its validity.
     * @param maxValue The maximum number of options allowed in this instance.
     * @return The option chosen.
     */
    private static int getOption(int maxValue)
    {
        Scanner scanner = new Scanner(System.in);
        int option;

        try
        {
            option = scanner.nextInt();
        }
        catch(InputMismatchException e)
        {
            System.out.println("Invalid input");
            return 0;
        }


        while(option < 1 && option > maxValue)
        {
            System.out.println("Invalid option.\nPlease try again");
            
            try
            {
                option = scanner.nextInt();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Invalid input");
                return 0;
            }
        }

        return option;
    }
}