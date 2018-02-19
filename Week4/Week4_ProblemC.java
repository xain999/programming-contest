import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;

public class Week4_ProblemC {
	
	static int n, s, l;
	static int[] snake;
	static int[] ladder;
	static ArrayDeque<Integer> deque;
	
public static void main(String[] args) throws NumberFormatException, IOException{
		
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		
		for (int i = 1; i <= testCasesCount; i++) {
			
			readInput(in);
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
		
	}
	
	public static void readInput(BufferedReader in) throws IOException{
		
		String line = in.readLine();
		String[] tokens = line.split(" ");
		
		n = Integer.parseInt(tokens[0]);
		s = Integer.parseInt(tokens[1]);
		l = Integer.parseInt(tokens[2]);
		
		snake = new int[n + 1];
		ladder = new int[n + 1];
		
		for (int i = 0; i < s; i++) {
			line = in.readLine();
			tokens = line.split(" ");
			
			snake[Integer.parseInt(tokens[0])] = Integer.parseInt(tokens[1]);
		}
		
		for (int i = 0; i < l; i++) {
			line = in.readLine();
			tokens = line.split(" ");
			
			ladder[Integer.parseInt(tokens[0])] = Integer.parseInt(tokens[1]);
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		int min = -1;
		int got = -1;
		boolean[] result = null;
		
		for (int dice = 1; dice <= 6; dice++) {
			got = stepCounter(dice);
			
			if (got == -1)
				continue;
			if (min == -1 || got < min) {
				min = got;
				result = new boolean[7];
				result[dice] = true;
			} else if (got == min)
				result[dice] = true;
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		
		if (result != null) {
			for (int i = 1; i <= 6; i++)
				if (result[i])
					sb.append(" " + i);	
		} else
			sb.append(" impossible");
		
		return sb;
	}
	
	public static int stepCounter(int step) {
		deque = new ArrayDeque<Integer>();
		deque.add(1);
		int[] cost = new int[n + 7];
		Arrays.fill(cost, Integer.MAX_VALUE);
		cost[1] = 0;
		
		int item = 0;
		int result = Integer.MAX_VALUE;
		
		while (!deque.isEmpty()) {
			
			item = deque.remove();
			
			if (item >= n) {
				if (result > cost[item])
					result = cost[item];
				continue;
			}
			
			if (snake[item] > 0) {
				if (cost[snake[item]] > cost[item]) {
					deque.addFirst(snake[item]);
					cost[snake[item]] = cost[item];
				}
				continue;
			}	
			else if (ladder[item] > 0 && cost[ladder[item]] > cost[item]) {
				deque.addFirst(ladder[item]);
				cost[ladder[item]] = cost[item];
			}
			
			if (cost[item + step] > cost[item] + 1) {
				cost[item + step] = cost[item] + 1;
				deque.add(item + step);
			}
		}
		
		return result == Integer.MAX_VALUE ? - 1 : result;
	}
}
