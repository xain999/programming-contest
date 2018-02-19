//
//  main.cpp
//  Week13_ProblemB
//
//  Created by Zain Ul Abideen on 02/02/2017.
//  Copyright © 2017 Zain Ul Abideen. All rights reserved.
//

#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>
#include <memory>

using namespace std;

void buildResult(int i);

int n, k;

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    
    cin >> testCases;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> n >> k;
        buildResult(i);
    }
    
    return 0;
}

void buildResult(int caseNo) {
    vector<int> list(n);
    
    for (int i = 0; i < n; i++)
        list[i] = i + 1;
    
    int curr = 0;
    k--;
    
    while (list.size() > 1) {
        curr += k;
        curr = curr % list.size();
        
        list.erase(list.begin() + curr);
    }
    
    cout << "Case #" << caseNo << ": " << list[0] << endl;
}

