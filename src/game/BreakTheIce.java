package game;

import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import heuristic.*;

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
     * level selection interface.
     */
    private static void chooseLevel()
    {
        int option;

        System.out.println("+-----------------+");
        System.out.println("| level Selection |");
        System.out.println("+-----------------+");
        System.out.println("|   1 - level 1   |");
        System.out.println("+-----------------+");
        System.out.println("|   2 - level 2   |");
        System.out.println("+-----------------+");
        System.out.println("|   3 - level 3   |");
        System.out.println("+-----------------+");
        System.out.println("|   4 - level 4   |");
        System.out.println("+-----------------+");
        System.out.println("|   5 - level 5   |");
        System.out.println("+-----------------+");
        System.out.println("|   6 - level 6   |");
        System.out.println("+-----------------+");
        System.out.println("|   7 - level 7   |");
        System.out.println("+-----------------+");
        System.out.println("|   8 - level 8   |");
        System.out.println("+-----------------+");
        System.out.println("|  9 - Load level |");
        System.out.println("+-----------------+");

        option = getOption(9);
        GameBoard board = null;

        if(option != 9)
            board = getLevelSelected(option);
        else
            board = loadLevel();

        bot = new Bot(new GameNode(null, 0, 0, "root", 0, null, 0, board));
        chooseMode();
    }

    /**
     * Loads level from input file
     * @return board from the file
     */
    private static GameBoard loadLevel(){
        Scanner sc = new Scanner(System.in);

        System.out.println("+----------------------------------------+");
        System.out.println("|  Input the file name without extension |");
        System.out.println("+----------------------------------------+");
        String fileName = sc.nextLine();
        File file = new File(System.getProperty("user.dir") + "/levels/" + fileName);
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(file));
        } catch(FileNotFoundException e){
            e.printStackTrace();
            System.exit(-1);
        }

        String line;
        char[][] board= new char[12][7];
        int n = 0;
        int optimalMoves, maxPlays=0, blocks=0, colors=0;
        boolean purple=false, orange=false, red=false, blue=false, green=false, yellow=false;

        try{
            maxPlays = Integer.parseInt(br.readLine());
            while ((line = br.readLine()) != null) {
                char[] row = new char[7];
                for(int i = 0; i < 7; i++) {
                    char c = line.charAt(i);
                    row[i] = c;
                    if(c != '_'){
                        blocks++;
                        switch(c)
                        {
                            case 'p': purple = true; break;
                            case 'o': orange = true; break;
                            case 'r': red = true; break;
                            case 'b': blue = true; break;
                            case 'g': green = true; break;
                            case 'y': yellow = true; break;
                        }
                    }
                }
                board[n] = row;
                n++;
            }
        } catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

        if(purple) colors++;
        if(orange) colors++;
        if(red) colors++;
        if(blue) colors++;
        if(green) colors++;
        if(yellow) colors++;

        optimalMoves = maxPlays - 2;

        return new GameBoard(board, maxPlays, optimalMoves, blocks, colors);
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
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', 'y', 'b', '_', '_'},
                    {'_', '_', '_', 'b', 'y', '_', '_'},
                    {'_', '_', '_', 'y', 'b', '_', '_'}
                };
                blocks = 6;
                colors = 2;
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
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', 'y', '_', '_', '_'},
                    {'_', '_', 'p', 'p', 'y', '_', '_'},
                    {'_', 'p', 'p', 'y', 'p', 'p', '_'},
                    {'y', 'y', 'y', 'p', 'p', 'y', 'y'}
                };
                blocks = 16;
                colors = 2;
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
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', 'b', '_', 'r', '_', '_'},
                    {'_', 'b', 'b', '_', 'r', 'r', '_'}
                };
                blocks = 6;
                colors = 2;
                break;

            case 5:
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

            case 6:
                maxPlays = 4;
                board = new char[][] 
                {
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', 'p', '_', '_', '_'},
                    {'_', '_', '_', 'y', 'p', '_', '_'},
                    {'_', '_', '_', 'p', 'y', 'p', '_'},
                    {'_', '_', 'r', 'g', 'p', 'y', '_'},
                    {'_', 'o', 'g', 'r', 'b', 'p', '_'},
                    {'o', 'g', 'o', 'b', 'r', 'b', 'b'}
                };
                blocks = 23;
                colors = 6;
                break;

            case 7:
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

            case 8:
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
                    {'_', '_', '_', '_', '_', '_', '_'},
                    {'_', '_', '_', '_', '_', 'r', '_'},
                    {'_', '_', '_', '_', '_', 'r', 'r'},
                    {'_', 'b', '_', '_', '_', 'b', 'b'}
                };
                blocks = 6;
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

        bot.getRoot().setSearchOption(option);

        if(option == 5 || option == 6)
            chooseHeuristic(bot);
            
        bot.search(option, false);
    }

    /**
     * Heuristic selection menu.
     */
    private static void chooseHeuristic(Bot bot)
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

        switch(option)
        {
            case 1:
                bot.getRoot().setHeuristic(new BlockNumHeuristic());
                break;

            case 2:
                bot.getRoot().setHeuristic(new ColorHeuristic());
                break;

            case 3:
                bot.getRoot().setHeuristic(new CloseChainHeuristic());
                break;

            default:
                System.out.println("Unknown heuristic");
                return;
        }
        
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