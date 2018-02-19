import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Week2_ProblemD {

	static int m;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		
		for (int i = 1; i <= testCasesCount; i++) {
			
			List<Point>[] stations = readInput(in);
			System.out.println(buildResult(i, stations));
			
			if (i != testCasesCount)
				in.readLine();
		}
		
	}
	
	public static List<Point>[] readInput(BufferedReader in) throws IOException {
		
		String line = in.readLine();
		String[] values = line.split(" ");
		
		int n = Integer.parseInt(values[0]);
		m = Integer.parseInt(values[1]);
		int v, u;
		
		List<Point>[] stations = new ArrayList[n];
		
		for (int i = 0; i < n; i++) {
			stations[i] = new ArrayList<Point>();
		}
		
		for (int i = 1; i <= m; i++) {
			line = in.readLine();
			values = line.split(" ");
			
			v = Integer.parseInt(values[0]) - 1;
			u = Integer.parseInt(values[1]) - 1;
			
			stations[v].add(new Point(u, i));
			stations[u].add(new Point(v, i));
		}
		
		return stations;
	}
	
	public static StringBuilder buildResult(int caseNo, List<Point>[] stations) {
		
		StringBuilder sb = new StringBuilder(12);
		
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		
		for (int skip = 1; skip <= m; skip++) {
			Stack<Point> toVisit = new Stack<Point>();
			int[] visited = new int[stations.length];
			int visitSum = 0;
			toVisit.add(stations[0].get(0));
			
			while (!toVisit.isEmpty()) {
				Point val = toVisit.pop();
				
				if (visited[val.x] != 0)
					continue;
				
				visited[val.x] = 1;
				visitSum += 1;
				
				if (visitSum == stations.length)
					break;
				
				for (Point i : stations[val.x]) {
					if (i.y != skip && visited[i.x] == 0) {
						toVisit.add(i);
					}
				}
			}
			
			if (visitSum < stations.length) {
				sb.append(" ");
				sb.append(skip);
			}
		}
		
		return sb;
	}
}
