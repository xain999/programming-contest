import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Week4_ProblemE {
	static class Edge {
		int i, j, cost;
		Edge(int i, int j, int cost) {
			this.i = i;
			this.j = j;
			this.cost = cost;
		}
	}
	
	static int n, m, d;
	static boolean[] visited;
	static boolean[] hasNinja;
	static ArrayList<Edge>[] graph;
	static PriorityQueue<Edge> queue;
	static int[][] distance;
	
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
		d = Integer.parseInt(tokens[2]);
		
		distance = new int[n][n];
		graph = new ArrayList[n];
		hasNinja = new boolean[n];
		queue = new PriorityQueue<Edge>((a, b) -> Integer.compare(a.cost, b.cost));
		Edge edge;
		
		for (int i = 0; i < d; i++)
			hasNinja[Integer.parseInt(in.readLine()) - 1] = true;
		
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
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Edge edge;
		
		for (int index = 0; index < n; index++) {
			if (!hasNinja[index] && index != 0)
				continue;
			
			visited = new boolean[n];
			boolean[] ninjaVisited = new boolean[n];
			queue.clear();
			if (graph[index] != null)
				queue.addAll(graph[index]);
			
			visited[index] = true;
			
			int i, j;
			int ninjaCount = hasNinja[index] ? 1 : 0;
			
			while (!queue.isEmpty()) {
				
				edge = queue.remove();
				i = edge.i;
				j = edge.j;
				
				if (visited[i] && visited[j])
					continue;
				
				if (visited[j]) {
					i = edge.j;
					j = edge.i;
				}
				
				if (distance[index][j] == 0 || edge.cost < distance[index][j]) {
					distance[index][j] = edge.cost;
					distance[j][index] = edge.cost;
				}
				
				visited[j] = true;
				
				if (hasNinja[j] && !ninjaVisited[j]) {
					ninjaVisited[j] = true;
					if (++ninjaCount == d)
						break;
				}
				
				for (Edge e : graph[j]) {
					if (!visited[e.i] || !visited[e.j])
						queue.add(new Edge(e.i, e.j, e.cost + distance[index][j]));
				}
			}
		}
		
		queue.clear();
		visited = new boolean[n];
		int result = Integer.MAX_VALUE;
		int index = 0;
		
		for (int i = 0; i < n; i++)
			if (hasNinja[i] && result > distance[0][i]) {
				index = i;
				result = distance[0][i];
			}
		
		visited[index] = true;
		visited[0] = true;
		
		for (int j = 0; j < n; j++) {
			if (hasNinja[j] && !visited[j])
				queue.add(new Edge(index, j, distance[index][j]));
		}
		
		d--;
		while  (d > 0) {
			edge = queue.poll();
			if (visited[edge.j]) {
				continue;
			}
			
			result += edge.cost;
			visited[edge.j] = true;
			
			for (int j = 0; j < n; j++) {
				if (hasNinja[j] && !visited[j])
					queue.add(new Edge(edge.j, j, distance[edge.j][j]));
			}
			d--;
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(result);
		
		return sb;
	}
}
