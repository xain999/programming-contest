//
//  main.cpp
//  Week13_ProblemC
//
//  Created by Zain Ul Abideen on 02/02/2017.
//  Copyright © 2017 Zain Ul Abideen. All rights reserved.
//

#include <iostream>
#include <climits>
#include <vector>
#include <cmath>
#include <queue>
#include <algorithm>

using namespace std;

struct Node {
    int to, weight;
    Node(int to, int weight) {
        this->to = to;
        this->weight = weight;
    }
};

void buildResult(int i, vector<vector<Node>>&);

int V;

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    int n, m, b;
    int favM, favB;
    int start, end;
    
    cin >> testCases;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> n >> m >> b;
        
        V = n + m + m + b + 2;
        vector<vector<Node>> graph(V);
        
        for (int j = 1; j <= n; j++) {
            cin >> favM >> favB;
            
            // from source to person node
            graph[0].push_back(Node(j, 1));
            
            // from person to meal node
            graph[j].push_back(Node(n + favM, 1));
            
            // from meal to temp meal node
            start = n + favM;
            end = n + m + favM;
            
            if (find_if(graph[start].begin(), graph[start].end(), [&end] (const Node& node) { return node.to == end; }) == graph[start].end())
                graph[start].push_back(Node(end, 1));
            
            if (find_if(graph[end].begin(), graph[end].end(), [&start] (const Node& node) { return node.to == start; }) == graph[end].end())
                graph[end].push_back(Node(start, 0));
            
            // from temp meal to beverage node
            start = end;
            end = n + m + m + favB;
            
            if (find_if(graph[start].begin(), graph[start].end(), [&end] (const Node& node) { return node.to == end; }) == graph[start].end())
                graph[start].push_back(Node(end, 1));
            
            if (find_if(graph[end].begin(), graph[end].end(), [&start] (const Node& node) { return node.to == start; }) == graph[end].end())
                graph[end].push_back(Node(start, 0));
            
            // from beverage to sink node
            start = end;
            end = V - 1;
            
            if (find_if(graph[start].begin(), graph[start].end(), [&end] (const Node& node) { return node.to == end; }) == graph[start].end())
                graph[start].push_back(Node(end, 1));
            
            if (find_if(graph[end].begin(), graph[end].end(), [&start] (const Node& node) { return node.to == start; }) == graph[end].end())
                graph[end].push_back(Node(start, 0));
        }
        
        buildResult(i, graph);
    }
    
    return 0;
}

bool bfs(int s, int t, vector<int>& parent, vector<vector<Node>>& graph)
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
        
        for (Node& v : graph[u])
        {
            if (!visited[v.to] && v.weight > 0)
            {
                queue.push(v.to);
                parent[v.to] = u;
                visited[v.to] = true;
            }
        }
    }
    
    // If we reached sink in BFS starting from source, then
    // return true, else false
    return (visited[t] == true);
}

static int fordFulkerson(int s, int t, vector<vector<Node>>& graph)
{
    int u, v;
    vector<int> parent(V, 0);
    
    int maxFlow = 0;
    
    while (bfs(s, t, parent, graph)) {
        int pathFlow = 1;
        
        // update residual capacities of the edges and
        // reverse edges along the path
        int path_flow = INT_MAX;
        for (v = t; v != s; v = parent[v])
        {
            u = parent[v];
            
            auto it = find_if(graph[u].begin(), graph[u].end(), [&v](const Node& node) { return node.to == v; });
            path_flow = min(path_flow, it->weight);
        }
        
        // update residual capacities of the edges and
        // reverse edges along the path
        for (v=t; v != s; v=parent[v])
        {
            u = parent[v];
            
            auto it = find_if(graph[u].begin(), graph[u].end(), [&v](const Node& node) { return node.to == v; });
            it->weight -= path_flow;
            
            it = find_if(graph[v].begin(), graph[v].end(), [&u](const Node& node) { return node.to == u; });
            it->weight += path_flow;
        }
        
        maxFlow += pathFlow;
    }
    return maxFlow;
}

void buildResult(int caseNo, vector<vector<Node>>& graph) {
    
    cout << "Case #" << caseNo << ": " << fordFulkerson(0, V - 1, graph) << endl;
}
