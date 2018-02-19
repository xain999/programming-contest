import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Week3_ProblemB {
	
	public static class Edge {
		int i, j, cost;
		Edge(int i, int j, int cost) {
			this.i = i;
			this.j = j;
			this.cost = cost;
		}
	}
	
	static int n, m, result, maxGraphSize;
	static int[] setValue;
	static int[] status;
	static ArrayList<Edge> weight;
	
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
		m = Integer.parseInt(tokens[1]);
		
		weight = new ArrayList<Edge>(m);
		status = new int[n];
		setValue = new int[n];
		Arrays.fill(status, -1);
		Arrays.fill(setValue, 1);
		
		for (int i = 0; i < m; i++) {
			line = in.readLine();
			tokens = line.split(" ");
			
			weight.add(new Edge(
					Integer.parseInt(tokens[0]) - 1,
					Integer.parseInt(tokens[1]) - 1,
					Integer.parseInt(tokens[2])
					));
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		result = 0;
		maxGraphSize = -1;
		
		weight.sort((a, b) -> Integer.compare(a.cost, b.cost));
		int size = weight.size();
		
		for (int i = 0; i < size; i++) {
			Edge e = weight.get(i);
			
			if (unionFind(e.i) != unionFind(e.j)) {
				union(e.i, e.j);
				result += e.cost;
			}
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		
		if (maxGraphSize == n)
			sb.append(result);
		else
			sb.append("impossible");
		
		return sb;
	}
	
	public static void union(int a, int b) {
		a = unionFind(a);
		b = unionFind(b);
		if (a != b) {
			status[b] = a;
			setValue[a] += setValue[b];
			setValue[b] = 0;
			
			if (maxGraphSize < setValue[a])
				maxGraphSize = setValue[a];
		}
	}
	
	public static int unionFind(int a) {
		while (true) {
			if (status[a] == -1 || status[a] == a)
				return a;
			a = status[a];
		}
	}
}
