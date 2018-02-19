import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Week9_ProblemC {
	static int n, m;
	static int[][] graph;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//System.setIn(new FileInputStream("3.in"));
		InputStreamReader r = new InputStreamReader (  System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			n = Integer.parseInt(tokens[0]);
			m = Integer.parseInt(tokens[1]);

			if (n > m) {
				System.out.println("Case #" + i + ": no");
				
				for (int j = 0; j < n; j++)
					in.readLine();
				
				if (i != testCasesCount)
					in.readLine();
				continue;
			}
			
			graph = new int[n + m + 2][n + m + 2];
			int a, b;
			String[] seq;
			
			for (int j = m + 1; j <= n + m; j++) {
				tokens = in.readLine().split(",");
				
				for (String token : tokens) {
					if (token.indexOf('-') >= 0) {
						try {
							seq = token.split("-");
							a = Integer.parseInt(seq[0]);
							b = Integer.parseInt(seq[1]);
							
							for (int k = a; k <= b; k++)
								graph[k][j] = 1;
						} catch (Exception e) {
							// TODO: handle exception
						}
					} else {
						try {
							a = Integer.parseInt(token);
							graph[a][j] = 1;
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
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
        boolean visited[] = new boolean[graph.length];
 
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
 
            for (int v = 0; v < graph.length; v++)
            {
                if (visited[v]==false && graph[u][v] > 0)
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
    static int fordFulkerson(int s, int t)
    {
        int u, v;
 
        // This array is filled by BFS and to store path
        int parent[] = new int[graph.length];
 
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
		
		for (int i = 1; i <= m; i++)
			graph[0][i] = 1;
		
		for (int i = m + 1; i <= n + m; i++)
			graph[i][graph.length - 1] = 1;
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		
		if (fordFulkerson(0, graph.length - 1) != n)
			sb.append("no");
		else
			sb.append("yes");
		
		return sb;
	}
}
