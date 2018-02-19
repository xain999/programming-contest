//
//  main.cpp
//  Week11_ProblemA
//
//  Created by Zain Ul Abideen on 24/01/2017.
//  Copyright © 2017 Zain Ul Abideen. All rights reserved.
//

#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>

using namespace std;

int m, n, rows;

struct Topic {
    int l, s, id;
    Topic(int id, int l, int s) {
        this->l = l;
        this->s = s;
        this->id = id;
    }
};

vector<Topic> topics;

void buildResult(int i);

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    
    cin >> testCases;
    
    int repeat = 0;
    int l, s;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> m;
        cin >> n;
        
        rows = 0;
        topics.clear();
        topics.push_back(Topic(0, 0, 0));
        
        for (int j = 0; j < n; j++) {
            cin >> repeat;
            cin >> l;
            cin >> s;
            rows += repeat;
            
            for (int k = 0; k < repeat; k++)
                topics.push_back(Topic(j + 1, l, s));
        }
        
        buildResult(i);
    }
    
    return 0;
}

void buildResult(int caseNo) {
    
    vector<vector<int>> table(m + 1, vector<int>(rows + 1, 0));
    vector<vector<bool>> prev(m + 1, vector<bool>(rows + 1, false));
    
    int a, b;
    
    for (int i = 1; i <= m; i++) {
        
        for (int j = 1; j <= rows; j++) {
            
            a = table[i][j - 1];
            b = 0;
            
            if (i >= topics[j].l) {
                int k = i - topics[j].l;
                b = table[k][j - 1] + topics[j].s;
            }
            
            if (b > a) {
                table[i][j] = b;
                prev[i][j] = true;
                
            } else
                table[i][j] = a;
        }
    }
    
    a = m, b = rows;
    stack<int> stack;
    
    while (b > 0 && a > 0) {
        if (prev[a][b]) {
            stack.push(topics[b].id);
            a = a - topics[b].l;
        }
        b--;
    }
    
    
    cout << "Case #" << caseNo << ":";
    
    while (stack.size() > 0) {
        cout << " " << stack.top();
        stack.pop();
    }
    cout << endl;
}





















