import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Week3_ProblemC {
	public static class Edge {
		int i, j, cost;
		Edge(int i, int j, int cost) {
			this.i = i;
			this.j = j;
			this.cost = cost;
		}
	}
	
	static int n, result, maxGraphSize;
	static int[] setValue;
	static int[] status;
	static int[] power;
	static Point[] pos;
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
		
		String line;
		String[] tokens;
		
		n = Integer.parseInt(in.readLine());
		
		weight = new ArrayList<Edge>(n * 4);
		pos = new Point[n + 1];
		power = new int[n + 1];
		status = new int[n + 1];
		setValue = new int[n + 1];
		Arrays.fill(status, -1);
		Arrays.fill(setValue, 1);
		
		power[0] = Integer.MAX_VALUE;
		pos[0] = new Point(0, 0);
		
		int powerReq = 0;
		
		for (int i = 1; i <= n; i++) {
			line = in.readLine();
			tokens = line.split(" ");
			
			pos[i] = new Point(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
			power[i] = Integer.parseInt(tokens[2]);
			
			for (int j = 0; j < i; j++) {
				powerReq = (int) pos[i].distanceSq(pos[j]);
				
				if (powerReq <= power[i] && powerReq <= power[j]) {
					weight.add(new Edge(i, j, powerReq));
				}
			}
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
		
		if (maxGraphSize == n + 1)
			sb.append(result * 2);
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
