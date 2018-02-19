import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Week4_ProblemA {
	
	static class Edge {
		int i, j, cost;
		Edge(int i, int j, int cost) {
			this.i = i;
			this.j = j;
			this.cost = cost;
		}
	}
	
	static int n, m;
	static boolean[] visited;
	static int[] dist;
	static int[] pred;
	static ArrayList<Edge>[] graph;
	static PriorityQueue<Edge> queue;
	
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
		
		visited = new boolean[n];
		dist = new int[n];
		pred = new int[n];
		graph = new ArrayList[n];
		
		Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(pred, -1);
		
		dist[0] = 0;
		visited[0] = true;
		
		queue = new PriorityQueue<Edge>((a, b) -> Integer.compare(a.cost, b.cost));
		Edge edge;
		
		for (int i = 0; i < m; i++) {
			line = in.readLine();
			tokens = line.split(" ");
			
			edge = new Edge(
					Integer.parseInt(tokens[0]) - 1,
					Integer.parseInt(tokens[1]) - 1, 
					Integer.parseInt(tokens[2]));
			
			if (graph[edge.i] == null)
				graph[edge.i] = new ArrayList<Edge>();
			if (graph[edge.j] == null)
				graph[edge.j] = new ArrayList<Edge>();
			
			graph[edge.i].add(edge);
			graph[edge.j].add(edge);
		}
		
		if (graph[0] != null)
			queue.addAll(graph[0]);
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Edge edge;
		int i, j;
		
		while (!queue.isEmpty() && pred[n - 1] == -1) {
			
			edge = queue.remove();
			i = edge.i;
			j = edge.j;
			
			if (visited[i] && visited[j])
				continue;
			
			if (visited[j]) {
				i = edge.j;
				j = edge.i;
			}
			
			if (edge.cost < dist[j]) {
				dist[j] = edge.cost;
				pred[j] = i;
			}
			
			visited[j] = true;
			
			for (Edge e : graph[j]) {
				queue.add(new Edge(e.i, e.j, e.cost + dist[j]));
			}
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(dist[n - 1]);
		
		return sb;
	}
}
