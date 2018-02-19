//
//  main.cpp
//  Week11_ProblemC
//
//  Created by Zain Ul Abideen on 23/01/2017.
//  Copyright © 2017 Zain Ul Abideen. All rights reserved.
//

#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

void buildResult(int i);

struct Node {
    int iq, weight;
};

int n;
vector<Node> nodes;

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    
    cin >> testCases;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> n;
        nodes.resize(n);
        
        for (int j = 0; j < n; j++) {
            nodes[j] = Node();
            cin >> nodes[j].iq;
            cin >> nodes[j].weight;
        }
        
        buildResult(i);
        
//        if (i != testCases)
//            in.readLine();
    }
    
    return 0;
}

void buildResult(int caseNo) {
    
    sort(nodes.begin(), nodes.end(), [](Node a, Node b) { return a.iq == b.iq ? b.weight < a.weight : a.iq < b.iq; });
    
    int* currSum = new int[nodes.size()];
    currSum[0] = 1;
    int maxSum = 1;
    
    //		for (int x = 0; x < nodes.length; x++) {
    //			System.out.println(nodes[x].iq + "\t" + nodes[x].weight);
    //		}
    
    int size = nodes.size();
    
    for(int x = 1; x < size; x++) {
        currSum[x] = 1;
        
        for (int y = 0; y < x; y++) {
            if (nodes[x].weight < nodes[y].weight && currSum[x] < currSum[y] + 1) {
                currSum[x] = currSum[y] + 1;
                
                if (currSum[x] > maxSum) {
                    maxSum = currSum[x];
                    break;
                }
            }
        }
    }
    
    cout << "Case #" << caseNo << ": " << maxSum << endl;
}
