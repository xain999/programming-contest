nclude <iostream>
#include <climits>
#include <vector>
#include <cmath>
#include <queue>

using namespace std;

void buildResult(int i, vector<vector<int>>&);

int V;

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    int n, m, b;
    
    cin >> testCases;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> n >> m >> b;
        
        V = n + m + m + b + 2;
        vector<vector<int>> graph(V, vector<int>(V));
        
        int favM, favB;
        
        for (int j = 1; j <= n; j++) {
            cin >> favM >> favB;
            
            graph[0][j] = 1;
            graph[j][n + favM] = 1;
            graph[n + favM][n + m + favM] = 1;
            
            graph[n + m + favM][n + m + m + favB] = 1;
            graph[n + m + m + favB][V - 1] = 1;
        }
        
        buildResult(i, graph);
    }
    
    return 0;
}

bool bfs(int s, int t, vector<int>& parent, vector<vector<int>>& graph)
{
    // Create a visited array and mark all vertices as not visited
    vector<bool> visited(V, false);
    
    // Create a queue, enqueue source vertex and mark
    // source vertex as visited
    queue<int> queue;
    queue.push(s);
    visited[s] = true;
    parent[s] = -1;
    
    // Standard BFS Loop
    while (queue.size() != 0)
    {
        int u = queue.front();
        queue.pop();
        
        for (int v = 0; v < V; v++)
        {
            if (!visited[v] && graph[u][v] > 0)
            {
                queue.push(v);
                parent[v] = u;
                visited[v] = true;
            }
        }
    }
    
    // If we reached sink in BFS starting from source, then
    // return true, else false
    return (visited[t] == true);
}

static int fordFulkerson(int s, int t, vector<vector<int>>& graph)
{
    int u, v;
    vector<int> parent(V, 0);
    
    int maxFlow = 0;
    
    while (bfs(s, t, parent, graph))
    {
        int pathFlow = INT_MAX;
        for (v = t; v != s; v = parent[v]) {
            u = parent[v];
            pathFlow = min(pathFlow, graph[u][v]);
        }
        
        // update residual capacities of the edges and
        // reverse edges along the path
        for (v=t; v != s; v=parent[v])
        {
            u = parent[v];
            graph[u][v] -= pathFlow;
            graph[v][u] += pathFlow;
        }
        
        maxFlow += pathFlow;
    }
    return maxFlow;
}

void buildResult(int caseNo, vector<vector<int>>& graph) {
    
    cout << "Case #" << caseNo << ": " << fordFulkerson(0, V - 1, graph) << endl;
}


