package heuristic;

import game.GameBoard;

/**
 * General heuristic class, serves as basis for more complex heuristics associated with a node.
 */
public abstract class Heuristic {
	/**
	 * The current value of the heuristic.
	 */
	int value;

	/**
	 *	Indicator of the current sub-heuristic being used.
	 */
	private static int currentHeuristic = 0;

	/**
	 * The possible colors in a board.
	 */
	int purple=0, orange=0, red=0, blue=0, green=0, yellow=0;
	
	/**
	 * Constructor of the class, initiates it's value to 0.
	 */
	Heuristic()
	{ 
		this.value = 0; 
	}

	/**
	 * Retrieves the number of the current heuristic.
	 * @return The number representing the current heuristic.
	 */
	public static int getCurrentHeuristic()
	{
		return currentHeuristic;
	}

	/**
	 * Sets the current heuristic.
	 * @param h The new value representing the current heuristic.
	 */
	public static void setCurrentHeuristic(int h)
	{
		currentHeuristic = h;
	}
	
	/**
	 * Calculates the heuristic's value based on a board.
	 * @param board The board to which the heuristic's value is calculated.
	 */
	public abstract void calculate(GameBoard board);

	/**
	 * CompareTo for comparation purposes
	 * @param h heuristic to compare to
	 * @return difference
	 */
	public int compareTo(Heuristic h) {
		return this.value - h.value;
	}

	/**
	 * Checks if the board has a possible solution and if not sets the current value to a large number.
	 * @param boardChar The board in question.
	 * @return True if the board has a solution and false otherwise.
	 */
	boolean notPossibleBoard(char[][] boardChar)
	{
		for (char[] line : boardChar)
		{
			for (char cell : line)
			{
				if(cell != '_')
				{
					this.value++;
					switch(cell)
					{
						case 'p':
							purple++;
							break;
						case 'o':
							orange++;
							break;
						case 'r':
							red++;
							break;
						case 'b':
							blue++;
							break;
						case 'g':
							green++;
							break;
						case 'y':
							yellow++;
							break;
					}
				}
			}
		}

		if((purple | orange | red | blue | green | yellow) < 3 && (purple | orange | red | blue | green | yellow) > 0)
		{
			this.value = Integer.MAX_VALUE;
			return false;
		}
		else
			return true;
	}

	/**
	 * Returns a new sub-heuristic depending on the current one selected.
	 * @return The new heuristic.
	 */
	public static Heuristic createCurrentHeuristic()
	{
		switch(Heuristic.currentHeuristic) {
            case 1:
                return new BlockNumHeuristic();
            case 2:
                return new ColorHeuristic();
            case 3:
				return new CloseChainHeuristic();
            default:
                return new BlockNumHeuristic();
        }
	}
}