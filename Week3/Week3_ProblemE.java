import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Week3_ProblemE {
	static int n;
	static int[][] graph;
	
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
		graph = new int[n][n];	
		int j = 0;
		int temp = 0;
		
		for (int i = 0; i < n; i++) {
			line = in.readLine();
			tokens = line.split(" ");
			j = 0;
			
			for (String token : tokens) {
				temp = Integer.parseInt(token);
				if (graph[0][n - 1] == 0 || temp < graph[0][n - 1])
					graph[i][j] = temp;
				j++;
			}
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		LinkedList<Integer> result = getPath(0, n - 1, graph[0][n - 1]);
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		
		for (int i : result) {
			sb.append(" ");
			sb.append(i + 1);
		}
		
		return sb;
	}
	
	public static LinkedList<Integer> getPath(int start, int end, int distance) {
		LinkedList<Integer> selected = new LinkedList<Integer>();
		LinkedList<Integer> other = null;
		selected.add(end);
		
		for (int i = 1; i < end; i++) {
			if (graph[start][i] == 0 || graph[i][end] == 0 || distance != graph[start][i] + graph[i][end])
				continue;
			
			other = getPath(i, end, distance - graph[start][i]);
			if (other.size() > selected.size())
				selected = other;
		}
		
		selected.add(0, start);
		return selected;
	}
}
