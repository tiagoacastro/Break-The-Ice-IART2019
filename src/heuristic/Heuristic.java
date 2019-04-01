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
	 * Calculates the heuristic's value based on a board.
	 * @param board The board to which the heuristic's value is calculated.
	 */
	public abstract void calculate(GameBoard board);

	/**
	 * Returns a new instance of this heuristic sub-class.
	 * @return A new instance of a heuristic sub-class.
	 */
	public abstract Heuristic getNewHeuristic();

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
			return true;
		}
		else
			return false;
	}
}