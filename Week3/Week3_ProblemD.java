import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Week3_ProblemD {
	
	static int n, result;
	static int[] value;
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
		value = new int[n];
		
		int trust;
		int j = 0;
		
		in.readLine();
		
		for (int i = 1; i < n; i++) {
			line = in.readLine();
			tokens = line.split(" ");
			j = 0;
			
			for (String token : tokens) {
				trust = Integer.parseInt(token);
				if (trust > 0) {
					graph[j][i] = trust;
				}
				j++;
			}
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Stack<Integer> toVisit = new Stack<Integer>();
		toVisit.add(0);
		int item = 0;
		
		while (!toVisit.isEmpty()) {
			item = toVisit.pop();
			for (int j = 0; j < n; j++) {
				if (value[j] == 0 && graph[item][j] > 0) {
					toVisit.push(j);
				}
				if (value[j] < graph[item][j])
					value[j] = graph[item][j];
			}
		}
		
		result = 0;
		
		for (int i = 0; i < n; i++)
			result += value[i];
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");		
		sb.append(result);
		
		return sb;
	}
}
