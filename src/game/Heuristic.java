package game;

public abstract class Heuristic {

	protected int value;
	public static int currentHeuristic = 0;
	protected int purple=0, orange=0, red=0, blue=0, green=0, yellow=0;
	
	public Heuristic() { 
		this.value = 0; 
	}

	public Heuristic(int value) {
		this.value = value; 
	}

	public void setValue(int value) { 
		this.value = value; 
	}
	
	public double getValue() { 
		return value; 
	}

	public static void setCurrentHeuristic(int h)
	{
		currentHeuristic = h;
	}
	
	public abstract void update(GameBoard board);
	public abstract int compareTo(Heuristic h); //for priority queues

	protected void auxiliar(char[][] boardChar) {
		for (int i = 0; i < boardChar.length; i++) {
			for (int j = 0; j < boardChar[i].length; j++) {
				if(boardChar[i][j] != '_') {
					this.value++;
					switch(boardChar[i][j]){
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
	}
}