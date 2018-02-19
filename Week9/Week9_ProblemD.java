import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.Set;

public class Week9_ProblemD {
	static int n, m;
	static int[][] graph;
	static int[] wins;
	static int[] matches;
	static HashMap<Integer, Integer> map;
	
	//Help from: www.ohio.edu/people/melkonia/math3050/slides/baseball.ppt
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//System.setIn(new FileInputStream("2.in"));
		InputStreamReader r = new InputStreamReader (  System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			n = Integer.parseInt(tokens[0]);
			m = Integer.parseInt(tokens[1]);
			
			wins = new int[n];
			matches = new int[n];
			map = new HashMap<Integer, Integer>(m * 2);
			int a, b, key;
			
			tokens = in.readLine().split(" ");
			
			a = 0;
			for (String token : tokens)
				wins[a++] = Integer.parseInt(token);
			
			for (int j = 0; j < m; j++) {
				tokens = in.readLine().split(" ");
				a = Integer.parseInt(tokens[0]);
				b = Integer.parseInt(tokens[1]);
				
				if (a > b) {
					int temp = a;
					a = b;
					b = temp;
				}
				
				matches[a - 1]++;
				matches[b - 1]++;
				
				key = a * 100 + b;
				
				if (map.containsKey(key))
					map.replace(key, map.get(key) + 1);
				else
					map.put(key, 1);
			}
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	// function from http://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
	static boolean bfs(int[][] rGraph, int s, int t, int parent[])
    {
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[rGraph.length];
 
        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;
 
        // Standard BFS Loop
        while (queue.size() != 0)
        {
            int u = queue.poll();
 
            for (int v = 0; v < rGraph.length; v++)
            {
                if (visited[v]==false && rGraph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                    
                    if (v == t)
                    	return true;
                }
            }
        }
        
        return false;
    }
 
	//function from http://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
    // Returns tne maximum flow from s to t in the given graph
    static int fordFulkerson(int[][] rGraph, int s, int t)
    {
        int u, v;
 
        // This array is filled by BFS and to store path
        int parent[] = new int[rGraph.length];
 
        int max_flow = 0;  // There is no flow initially
 
        // Augment the flow while tere is path from source
        // to sink
        while (bfs(rGraph, s, t, parent))
        {
            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }
 
            // update residual capacities of the edges and
            // reverse edges along the path
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }
 
            // Add path flow to overall flow
            max_flow += path_flow;
        }
 
        // Return the overall flow
        return max_flow;
    }
	
	public static StringBuilder buildResult(int caseNo) {
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		
		Set<Integer> keys = map.keySet();
		int size = keys.size();
		int a, b, c, winCount;
		
		graph = new int[n + size + 2][n + size + 2];
		
		c = 1;
		for (int key : keys) {
			a = key / 100;
			b = key % 100;
			
			graph[0][c] = map.get(key);
			graph[c][size + a] = graph[0][c];
			graph[c][size + b] = graph[0][c];
			
			c++;
		}
		
		for (int i = 1; i <= n; i++) {
			
			winCount = wins[i - 1] + matches[i - 1];
			
			boolean check = true;
			
			for (int j = 0; j < n; j++)
				if (j != i - 1 && winCount < wins[j]) {
					check = false;
					break;
				}
			
			if (!check)
				sb.append(" no");
			else {
				int[][] copyGraph = new int[graph.length][];
				
				c = 0;
				for (int[] g : graph) {
					copyGraph[c++] = g.clone();
				}
				
				for (int j = size + 1; j <= size + n; j++)
					copyGraph[j][copyGraph.length - 1] = winCount - wins[j - size - 1];
				
				int maxFlow = fordFulkerson(copyGraph, 0, graph.length - 1);
				
				if (maxFlow == m) {
					sb.append(" yes");
				}
				else
					sb.append(" no");
			}
		}
		
		return sb;
	}
}
