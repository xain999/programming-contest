import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Queue;

public class Week4_ProblemB {
	
	static int n;
	static int[] dist;
	static int[] pred;
	static int[] cost;
	static int[][] graph;
	static Queue<Integer> queue;
	
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
		
		String line;
		String[] tokens;
		
		n = Integer.parseInt(in.readLine());
		
		dist = new int[n];
		pred = new int[n];
		cost = new int[n];
		graph = new int[n][];
		
		Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(pred, -1);
		
		int successors;
		
		for (int i = 0; i < n; i++) {
			line = in.readLine();
			tokens = line.split(" ");
			
			cost[i] = -1 * Integer.parseInt(tokens[0]);
			successors = Integer.parseInt(tokens[1]);
			graph[i] = new int [successors];
			
			for (int j = 0; j < successors; j++)
				graph[i][j] = Integer.parseInt(tokens[j + 2]) - 1;
		}
		
		dist[0] = cost[0];
		queue = new ArrayDeque<Integer>();
		queue.add(0);
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		int item;
		
		while (!queue.isEmpty()) {
			
			item = queue.remove();
						
			for (int succ : graph[item]) {
				if (dist[succ] > cost[succ] + dist[item]) {
					dist[succ] = cost[succ] + dist[item];
					pred[succ] = item;
					queue.add(succ);
				}
			}
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(dist[n - 1] * -1);
		
		return sb;
	}
}
