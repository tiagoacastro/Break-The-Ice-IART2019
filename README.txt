How to compile/run (if the source code is inside a java project and from inside the src folder):

Game: javac game/*.java
      javac heuristic/*.java
      java game.BreakTheIce

Testing: javac testing/*.java
         javac game/*.java
         javac heuristic/*.java
         java testing.testingSuite

Note: If you want to read custom levels from a file they must follow the following format

<Optimal Number of Moves>
<Map>

The Map must contain only the accepted characters (p, o, r, b, g, y, _) with no spaces in between.
Furthermore, it should be 12 characters high and 7 wide.
The path specified for the file is the relative path from where you opened the console.