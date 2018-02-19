import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Week6_ProblemB {
	
	static int EMPTY = 0;
	static int TOOL = 1;
	static int BLOCKED = 2;
	static int VISITED = 3;
	static int w, h;
	static int[][] cave;
	static Point start;
	static int tools;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String input;
		String[] tokens;
		char field;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			w = Integer.parseInt(tokens[0]);
			h = Integer.parseInt(tokens[1]);
			tools = 0;
			cave = new int[h][w];
			
			for (int j = 0; j < h; j++) {
				input = in.readLine();
				for (int k = 0; k < w; k++) {
					field = input.charAt(k);
					if (field == '#')
						cave[j][k] = BLOCKED;
					else if (field == 'L') {
						start = new Point(k, j);
					} else if (field == 'T') {
						cave[j][k] = TOOL;
						tools++;
					}
				}
			}
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static boolean hasPath(int x, int y, int[][] cave, int gotTools) {
		
		boolean isTool = false;
		if (cave[y][x] == TOOL) {
			gotTools++;
			isTool = true;
		}
		
		if (tools == gotTools)
			return true;
		
		cave[y][x] = VISITED;
		
		if (x - 1 >= 0 && (cave[y][x - 1] <= TOOL) && hasPath(x - 1, y, cave, gotTools))
			return true;
		if (x + 1 < w && (cave[y][x + 1] <= TOOL) && hasPath(x + 1, y, cave, gotTools))
			return true;
		if (y - 1 >= 0 && (cave[y - 1][x] <= TOOL) && hasPath(x, y - 1, cave, gotTools))
			return true;
		if (y + 1 < h && (cave[y + 1][x] <= TOOL) && hasPath(x, y + 1, cave, gotTools))
			return true;
		
		if (isTool)
			cave[y][x] = TOOL;
		else
			cave[y][x] = EMPTY;
		
		return false;
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		
		if (hasPath(start.x, start.y, cave, 0))
			sb.append("yes");
		else
			sb.append("no");
		
		return sb;
	}
}
