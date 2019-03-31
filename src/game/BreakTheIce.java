package game;

import java.util.Scanner;
import java.util.InputMismatchException;
import heuristic.Heuristic;

public class BreakTheIce 
{
    private static Bot bot;
    public static void main(String[] args) throws Exception 
    {
        menu();
    }

    public static void menu()
    {
        int option = 0;

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
                    continue;
            }
        }
        while(option != 2);
    }

    public static void chooseLevel()
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

        option = getOption(5);

        bot = new Bot(new GameNode(null, 0, 0, "root", 0, getLevelSelected(option)));
        chooseMode();
    }

    /**
     * 
     * @param option
     * @return
     */
    public static GameBoard getLevelSelected(int option)
    {
        int maxPlays, optimalMoves;
        char[][] board;

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
                break;

            default:
                System.out.println("Option not recognized in level selection");
                return null;
        }

        optimalMoves = maxPlays - 2;

        return new GameBoard(board, maxPlays, optimalMoves);
    }

    public static void chooseMode()
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

    public static int getOption(int maxValue)
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

    public static void chooseHeuristic() {
        int option;

        System.out.println("+----------------------------------+");
        System.out.println("|        Heuristic Selection       |");
        System.out.println("+----------------------------------+");
        System.out.println("|       1 - Number of blocks       |");
        System.out.println("+----------------------------------+");
        System.out.println("|       2 - Number of colors       |");
        System.out.println("+----------------------------------+");
        System.out.println("|  3 - Chains one move away        |");
        System.out.println("+----------------------------------+");

        option = getOption(3);
        Heuristic.setCurrentHeuristic(option);
    }
}