import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Week9_ProblemB {
	static int l, n, m;
	static int[][] graph;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//System.setIn(new FileInputStream("2.in"));
		InputStreamReader r = new InputStreamReader (  System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			l = Integer.parseInt(tokens[0]);
			n = Integer.parseInt(tokens[1]);
			m = Integer.parseInt(tokens[2]);

			graph = new int[n][n];
			int a, b, c;
			
			for (int j = 0; j < m; j++) {
				tokens = in.readLine().split(" ");
				a = Integer.parseInt(tokens[0]) - 1;
				b = Integer.parseInt(tokens[1]) - 1;
				c = Integer.parseInt(tokens[2]);
				
				if (a > b) {
					int temp = a;
					a = b;
					b = temp;
				}
				
				graph[a][b] += c;
				graph[b][a] += c;
			}
			
			System.out.println(buildResult(i));
			
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	// function from http://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
	static boolean bfs(int s, int t, int parent[])
    {
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[n];
 
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
 
            for (int v = 0; v < n; v++)
            {
                if (visited[v]==false && graph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
 
        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);
    }
 
	//function from http://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
    // Returns tne maximum flow from s to t in the given graph
    static int fordFulkerson(int s, int t)
    {
        int u, v;
 
        // This array is filled by BFS and to store path
        int parent[] = new int[n];
 
        int max_flow = 0;  // There is no flow initially
 
        // Augment the flow while tere is path from source
        // to sink
        while (bfs(s, t, parent))
        {
            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, graph[u][v]);
            }
 
            // update residual capacities of the edges and
            // reverse edges along the path
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                graph[u][v] -= path_flow;
                graph[v][u] += path_flow;
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
		sb.append(": ");
		
		if (fordFulkerson(0, n - 1) > l)
			sb.append("no");
		else
			sb.append("yes");
		
		return sb;
	}
}
