import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

class Edge {
	int i, j, cost;
	Edge(int i, int j, int cost) {
		this.i = i;
		this.j = j;
		this.cost = cost;
	}
}

public class Week10_ProblemE {
	static int n, m, s;
	//static boolean[] visited;
	static int[] dist;
	static ArrayList<Edge>[] graph;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		System.setIn(new FileInputStream("1.in"));
		InputStreamReader r = new InputStreamReader (  System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			n = Integer.parseInt(tokens[0]);
			m = Integer.parseInt(tokens[1]);
			s = Integer.parseInt(tokens[2]) - 1;
			
			//visited = new boolean[n];
			dist = new int[n];
			graph = new ArrayList[n];
			
			Arrays.fill(dist, Integer.MAX_VALUE);
			
			int a, b, c;
			
			for (int j = 0; j < m; j++) {
				tokens = in.readLine().split(" ");
				a = Integer.parseInt(tokens[0]) - 1;
				b = Integer.parseInt(tokens[1]) - 1;
				c = Integer.parseInt(tokens[2]);
				
				if (graph[a] == null)
					graph[a] = new ArrayList<Edge>();
				if (graph[b] == null)
					graph[b] = new ArrayList<Edge>();
				
				graph[a].add(new Edge(a, b, c));
				graph[b].add(new Edge(b, a, -c));
			}
			
			System.out.println(buildResult(i));	
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		ArrayDeque<Edge> queue = new ArrayDeque<Edge>();
		queue.addAll(graph[s]);
		//queue.add(new Edge(0, -s, cost))
		dist[s] = 0;
		
		boolean[] status = new boolean[n];
		
		Edge item;
		int distance = 0;
		int minDist = Integer.MAX_VALUE;
		boolean hasMinDist = false;
		
		while (!queue.isEmpty()) {
			item = queue.remove();
			
			if (item.j < 0) {
				distance--;
				item.j = -item.j;
				status[item.j] = !status[item.j];
				continue;
			}
			
			if (item.j == s && distance + item.cost != 0) {
				hasMinDist = true;
			
				if (minDist > distance + item.cost)
					minDist = distance + item.cost;
			}
				
			if (status[item.j])
				continue;
			
			ArrayList<Edge> list = graph[item.j];
			
			status[item.j] = !status[item.j];
			
			queue.addFirst(new Edge(item.i, -item.j, item.cost));
			distance++;
			
			for (Edge edge2 : list)
				if (edge2.j == s || !status[edge2.j])
					queue.addFirst(new Edge(edge2.i, edge2.j, edge2.cost + item.cost));
			
		}
		
//		while (!queue.isEmpty()) {
//			
//			edge = queue.remove();
//			i = edge.i;
//			j = edge.j;
//			cost = edge.cost;
//			
//			if (j == s && edge.cost != 0) {
//				hasMinDist = true;
//				
//				if (minDist > edge.staircases)
//					minDist = edge.staircases;
//			}
//			
////			if (visited[i] && visited[j])
////				continue;
//			
//			//if (edge.cost < dist[j])
//			//	dist[j] = edge.cost;
//			
//			//visited[j] = true;
//			
//			for (Edge e : graph[j]) {
//				if (e.j == s || dist[e.i] > dist[e.j] + edge.cost) { // || !visited[e.j])
//					queue.add(new Edge(e.i, e.j, e.cost + dist[j], e.staircases + 1));
//					dist[e.i] = dist[e.j] + edge.cost;
//				}
//			}
//		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		
		if (hasMinDist)
			sb.append(minDist);
		else
			sb.append("possible");
		
		return sb;
	}
}
