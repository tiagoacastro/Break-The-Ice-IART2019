# Break-The-Ice
A console application based on the mobile game [Break The Ice](https://play.google.com/store/apps/details?id=com.bitmango.breaktheice&hl=en) developed in the Artificial Intelligence class. It serves primarly as a test bed for different search algorithms.

## Overview

### Rules
The game's rules are quite simple. In each level, there's a number of blocks (ice cubes) of different colors. The objective is to eliminate every block in that level with the minimum amount of moves possible. To do this, the player must move or switch blocks (which are affected by gravity) in order to arrange them in rows/columns of 3 or more of the same color. Each time this is done, the blocks belonging to that chain will break. <br>
Blocks can only move one space in each play and can only switch with other adjacent blocks.

### Gameplay
Upon starting the application, the user can choose from a selection of 8 default levels or load a custom one from a file. After this step, he is asked to choose the search algorithm to be used. Currently, there are 6 different algorithms to choose from:

* Breadth First
* Depth First
* Iterative Deepening
* Uniform Cost
* Greedy
* A*

In the cases of the greedy and A* algorithms, the user can also choose from 3 different heuristics:

* Number of blocks - The number of blocks remaining.
* Number of colors - The number of colors remaining.
* Chains one move away (admissible) - The number of chains (3 or more blocks of the same color in a row/column) that are one move away from forming.

After picking one (or not), the application will search for a solution. After it has found one, it will print to the screen each move it performed to get to that solution as well as the board in that instance (and each time something else changes, such as blocks falling or breaking). It will also indicate the number of stars the solution found has, i.e, how good is the solution compared to the minimum amount of moves of that level.

![image](https://user-images.githubusercontent.com/32617691/60543830-3aea1380-9d0f-11e9-9118-00d81fb409eb.png)

## Usage
To run the application you can use the BreakTheIce.jar file provided or run the game.BreakTheIce class with java (no arguments).
