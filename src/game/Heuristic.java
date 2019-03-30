package game;

public abstract class Heuristic {

	protected int value;
	public static int currentHeuristic = 0;
	
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
	
	public abstract void update(GameBoard board);
	public abstract int compareTo(Heuristic h); //for priority queues
}