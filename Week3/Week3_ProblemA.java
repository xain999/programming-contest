import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Week3_ProblemA {
	public static class Point {
		int x, y, z;
		Point(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		int distance(Point p) {
			return (Math.abs(p.x - x) + Math.abs(p.y - y) + Math.abs(p.z - z));
		}
	}
	
	public static class Edge {
		int i, j, cost;
		Edge(int i, int j, int cost) {
			this.i = i;
			this.j = j;
			this.cost = cost;
		}
	}
	
	static int n, result;
	static Point[] pos;
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
		
		n = Integer.parseInt(in.readLine());
		
		weight = new ArrayList<Edge>(n * n / 2);
		pos = new Point[n];
		status = new int[n];
		Arrays.fill(status, -1);
		
		String line;
		String[] tokens;
		
		for (int i = 0; i < n; i++) {
			line = in.readLine();
			tokens = line.split(" ");
			
			pos[i] = new Point(
					Integer.parseInt(tokens[0]),
					Integer.parseInt(tokens[1]),
					Integer.parseInt(tokens[2]));
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		result = 0;
		
		for (int i = 1; i < n; i++)
			for (int j = 0; j < i; j++) {
					weight.add(new Edge(i, j, pos[i].distance(pos[j])));
			}
		
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
		sb.append(result);
		
		return sb;
	}
	
	public static void union(int a, int b) {
		a = unionFind(a);
		b = unionFind(b);
		if (a != b)
			status[b] = a;
	}
	
	public static int unionFind(int a) {
		while (true) {
			if (status[a] == -1 || status[a] == a)
				return a;
			a = status[a];
		}
	}
}
