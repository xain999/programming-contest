import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Week6_ProblemC {
	static int[][] board;
	static ArrayList<Point> posToFill;
	static int BOARD_SIZE = 9;
	static int EMPTY = 0;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String input;
		char field;
		
		for (int i = 1; i <= testCasesCount; i++) {
			board = new int[BOARD_SIZE][BOARD_SIZE];
			posToFill = new ArrayList<Point>(20);
			
			for (int j = 0; j < BOARD_SIZE; j++) {
				input = in.readLine();
				for (int k = 0; k < BOARD_SIZE; k++) {
					field = input.charAt(k);
					if (field == '?') {
						board[j][k] = EMPTY;
						posToFill.add(new Point(k, j));
					} else {
						board[j][k] = Character.getNumericValue(field);
					}
				}
			}
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	// if a row, cloumn or inner box only misses one point
	public static boolean addSimpleValues() {
		boolean returnStatus = false;
		
		for (int i = 0; i < BOARD_SIZE; i++) {
			ArrayList<Point> refx = new ArrayList<Point>();
			ArrayList<Point> refy = new ArrayList<Point>();
			ArrayList<Point> refBox = new ArrayList<Point>();
			int box = 0;
			
			for (Point p : posToFill) {
				if (p.y == i)
					refx.add(p);
				if (p.x == i)
					refy.add(p);
				
				box = 3 * (p.y > 5 ? 2 : p.y > 2 ? 1 : 0) + 
							(p.x > 5 ? 2 : p.x > 2 ? 1 : 0);
				
				if (box == i)
					refBox.add(p);
			}
			
			Point p = refx.size() != 1 ? null : refx.get(0);
			if (p != null && board[p.y][p.x] == EMPTY) {
				posToFill.remove(p);
				int[] values = board[i].clone();
				int check = 1;
				Arrays.sort(values);
				if (values[8] != 9) {
					board[p.y][p.x] = 9;
					returnStatus = true;
				} else {
					for (int j = 1; j < values.length; j++, check++) {
						if (values[j] != check) {
							board[p.y][p.x] = check;
							returnStatus = true;
							break;
						}
					}
				}
			}
			
			p = refy.size() != 1 ? null : refy.get(0);
			if (p != null && board[p.y][p.x] == EMPTY) {
				posToFill.remove(p);
				int[] values = new int [BOARD_SIZE];
				
				for (int j = 0; j < BOARD_SIZE; j++)
					values[j] = board[j][i];
				
				int check = 1;
				Arrays.sort(values);
				if (values[8] != 9) {
					board[p.y][p.x] = 9;
					returnStatus = true;
				} else {
					for (int j = 1; j < values.length; j++, check++) {
						if (values[j] != check) {
							board[p.y][p.x] = check;
							returnStatus = true;
							break;
						}
					}
				}
			}
			
			p = refBox.size() != 1 ? null : refBox.get(0);
			if (p != null && board[p.y][p.x] == EMPTY) {
				posToFill.remove(p);

				int xBox = p.x > 5 ? 6 : p.x > 2 ? 3 : 0;
				int yBox = p.y > 5 ? 6 : p.y > 2 ? 3 : 0;
				int value = -1;
				
				for (int check = 1; check < 10; check++) {
					if ((board[yBox][xBox] != check && board[yBox][xBox + 1] != check && board[yBox][xBox + 2] != check
						&& board[yBox + 1][xBox] != check && board[yBox + 1][xBox + 1] != check && board[yBox + 1][xBox + 2] != check
						&& board[yBox + 2][xBox] != check && board[yBox + 2][xBox + 1] != check && board[yBox + 2][xBox + 2] != check)) {
						value = check;
						break;
					}
				}
				if (value != -1) {
					board[p.y][p.x] = value;
					returnStatus = true;
				}
			}
		}
		return returnStatus;
	}
	
	public static void displayMatrix() {
		for (int[] row : board) {
			System.out.print("[");
			for (int box : row)
				System.out.print(box + " ");
			System.out.println("]");
		}
	}
	
	public static boolean isValid(Point p, int value) {
		
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (board[p.y][i] == value || board[i][p.x] == value)
				return false;
		}
		
		int xBox = p.x > 5 ? 6 : p.x > 2 ? 3 : 0;
		int yBox = p.y > 5 ? 6 : p.y > 2 ? 3 : 0;
		
		if (board[yBox][xBox] == value || board[yBox][xBox + 1] == value || board[yBox][xBox + 2] == value
			|| board[yBox + 1][xBox] == value || board[yBox + 1][xBox + 1] == value || board[yBox + 1][xBox + 2] == value
			|| board[yBox + 2][xBox] == value || board[yBox + 2][xBox + 1] == value || board[yBox + 2][xBox + 2] == value) {
				return false;
		}
		
		return true;
	}
	
	public static boolean solve(Point[] points, int index) {
		if (index == points.length)
			return true;
					
		Point p = points[index];
		
		for (int i = 1; i < 10; i++) {
			if (isValid(p, i)) {
				board[p.y][p.x] = i;
				
				if (solve(points, index + 1)) {
					return true;
				}
				board[p.y][p.x] = EMPTY;
			}
		}
		
		return false;
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		while(addSimpleValues() && posToFill.size() > 0);
		
		if (posToFill.size() > 0)
			solve(posToFill.toArray(new Point[1]), 0);
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		sb.append(System.lineSeparator());
		
		for (int[] row : board) {
			for (int box : row)
				sb.append(box);
			sb.append(System.lineSeparator());
		}
		
		return sb;
	}
}
