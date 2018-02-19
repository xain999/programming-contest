import java.awt.Point;
import java.util.Scanner;

public class Week1_ProblemE {
	
	public static enum Direction {
		right,
		up,
		left,
		down;
	}

	public static class GameInfo {
		int gridSize;
		int steps;
		int foodEaten;
		Point head;
		Point tail;
		Direction headDirection;
		int[][] grid;
		String moves;
		int nextNum;
		
		GameInfo(int gridSize, Point head, int[][] grid, String moves) {
			this.gridSize = gridSize;
			this.steps = 0;
			this.foodEaten = 0;
			this.head = head;
			this.tail = new Point(head.x, head.y);
			this.headDirection = Direction.right;
			this.grid = grid;
			this.moves = moves;
			this.nextNum = 1;
			
			grid[head.x][head.y] = this.nextNum++;
		}
		
		void moveTail() {
			int newVal = grid[tail.x][tail.y] + 1;
			grid[tail.x][tail.y] = 0;
			
			if (grid[tail.x][(tail.y + 1) % gridSize] == newVal)
				tail.y = (tail.y + 1) % gridSize;
			else if (grid[tail.x][(tail.y - 1 + gridSize) % gridSize] == newVal)
				tail.y = (tail.y - 1 + gridSize) % gridSize;
			else if (grid[(tail.x + 1) % gridSize][tail.y] == newVal)
				tail.x = (tail.x + 1) % gridSize;
			else if (grid[(tail.x - 1 + gridSize) % gridSize][tail.y] == newVal)
				tail.x = (tail.x - 1 + gridSize) % gridSize;			
		}
		
		boolean moveForward() {
			int newVal = grid[head.x][head.y] + 1;
			
			switch(headDirection) {
			case right:
				head.x = (head.x + 1) % gridSize;
				break;			
			case up:
				head.y = (head.y - 1 + gridSize) % gridSize;
				break;
			case left:
				head.x = (head.x - 1 + gridSize) % gridSize;
				break;
			case down:
				head.y = (head.y + 1) % gridSize;
				break;
			}
			
			if (grid[head.x][head.y] > 0) {
				if (grid[head.x][head.y] != grid[tail.x][tail.y])
					return false;
				else {
					moveTail();
					grid[head.x][head.y] = newVal;
				}
					
			}
			else if (grid[head.x][head.y] == 0) {
				grid[head.x][head.y] = newVal;
				moveTail();
			}
			else {
				grid[head.x][head.y] = newVal;
				foodEaten++;
			}
			steps++;
			
			return true;
		}
		
		boolean turnLeft() {
			switch(headDirection) {
			case right:
				headDirection = Direction.up;
				break;			
			case up:
				headDirection = Direction.left;
				break;
			case left:
				headDirection = Direction.down;
				break;
			case down:
				headDirection = Direction.right;
				break;
			}
			return moveForward();
		}
		
		boolean turnRight() {
			switch(headDirection) {
			case right:
				headDirection = Direction.down;
				break;			
			case up:
				headDirection = Direction.right;
				break;
			case left:
				headDirection = Direction.up;
				break;
			case down:
				headDirection = Direction.left;
				break;
			}
			return moveForward();
		}
	}
	
public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in); 
		int testCasesCount = scanner.nextInt();
		
		for (int i = 1; i <= testCasesCount; i++) {
			if (i != 1) {
				scanner.nextLine();
				//scanner.nextLine();
			}
			
			GameInfo gameInfo = readInput(scanner);
			playGame(gameInfo);
			
			String result = buildResult(i, gameInfo.steps, gameInfo.foodEaten);		
			System.out.println(result);
		}
		
	}
	
	public static GameInfo readInput(Scanner scanner)
	{
		int gridSize = scanner.nextInt();
		int[][] grid = new int[gridSize][gridSize];
		
		int foodItems = scanner.nextInt();
		Point head = new Point(scanner.nextInt() - 1, scanner.nextInt() - 1);
		
		for (int i = 0; i < foodItems; i++) {
			Point foodstart = new Point(scanner.nextInt() - 1, scanner.nextInt() - 1);
			Point foodend = new Point(scanner.nextInt() + foodstart.x, scanner.nextInt() + foodstart.y);
			
			for (int j = foodstart.x; j < foodend.x; j++)
				for (int k = foodstart.y; k < foodend.y; k++)
					grid[j % gridSize][k % gridSize] = -1;
		}
		
		scanner.nextInt();
		String moves = scanner.next();
		
		return new GameInfo(gridSize, head, grid, moves);
	}
	
	public static String buildResult(int caseNumber, int steps, int foodEaten)
	{
		StringBuilder sb = new StringBuilder(50);
		
		sb.append("Case #");
		sb.append(caseNumber);
		sb.append(": ");
		sb.append(steps);
		sb.append(" ");
		sb.append(foodEaten);
		
		return sb.toString();
	}
	
	public static void playGame(GameInfo gameInfo)
	{
		char character;
		boolean gamePlaying = true;
		
		for (int i = 0; i < gameInfo.moves.length() && gamePlaying; i++) {
			character = gameInfo.moves.charAt(i);
			
			if (character == 'F')
				gamePlaying = gameInfo.moveForward();
			else if (character == 'R')
				gamePlaying = gameInfo.turnRight();
			else if (character == 'L')
				gamePlaying = gameInfo.turnLeft();
		}
	}
	
}
