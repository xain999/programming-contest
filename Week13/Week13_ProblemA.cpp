//
//  main.cpp
//  Week13_ProblemA
//
//  Created by Zain Ul Abideen on 02/02/2017.
//  Copyright © 2017 Zain Ul Abideen. All rights reserved.
//

#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <cstring>

using namespace std;

#define MIN(a,b) ((a) < (b) ? (a) : (b))
#define MAX(a,b) ((a) > (b) ? (a) : (b))

int* tree;
int* leftIndex;
int* rightIndex;

void buildResult(int i);

uint n, k;
size_t size;

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    
    cin >> testCases;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> n >> k;
        
        size = pow(2, ceil(log2(n)) + 2);
        tree = new int[size];
        leftIndex = new int[size];
        rightIndex = new int[size];
        
        // using leftIndex also as lazy
        
        leftIndex[1] = 1;
        leftIndex[2] = -1;
        leftIndex[3] = -1;
        rightIndex[1] = n;
        
        buildResult(i);
    }
    
    return 0;
}

uint getValue(int node, uint left, uint right) {
    uint result = 0;
    
    if (leftIndex[node] == left && rightIndex[node] == right) {
        result = tree[node];
        tree[node] = 0;
        
        if (left != right) {
            leftIndex[node * 2] = -1;
            leftIndex[node * 2 + 1] = -1;
        }
        
        return result;
    }
    
    int mid = (leftIndex[node] + rightIndex[node]) / 2;
    
    if (mid >= left && leftIndex[node * 2] != -1)
        result += getValue(node * 2, MAX(left, leftIndex[node]), MIN(rightIndex[node * 2], right));
    
    if (mid + 1 <= right && leftIndex[node * 2 + 1] != -1)
        result += getValue(node * 2 + 1, MAX(left, leftIndex[node * 2 + 1]), MIN(rightIndex[node], right));
    
    return result;
}

void addValue(int node, int value, uint index) {
    if (leftIndex[node] == index && leftIndex[node] == rightIndex[node]) {
        if (value < 0) {
            if (tree[node] > 0)
                tree[node]--;
        } else
            tree[node] += value;
        
        return;
    }
    
    int mid = (leftIndex[node] + rightIndex[node]) / 2;
        
    if (mid >= index) {
        if (leftIndex[node * 2] == -1) {
            int child = node * 2;
            
            leftIndex[child] = leftIndex[node];
            rightIndex[child] = mid;
            tree[child] = 0;
            
            if (leftIndex[child] != rightIndex[child]) {
                leftIndex[child * 2] = -1;
                leftIndex[child * 2 + 1] = -1;
            }
        }
        addValue(node * 2, value, index);
    } else if (mid + 1 <= index) {
        if (leftIndex[node * 2 + 1] == -1) {
            int child = node * 2 + 1;
            
            rightIndex[child] = rightIndex[node];
            leftIndex[child] = mid + 1;
            tree[child] = 0;
            
            if (leftIndex[child] != rightIndex[child]) {
                leftIndex[child * 2] = -1;
                leftIndex[child * 2 + 1] = -1;
            }
        }
        
        addValue(node * 2 + 1, value, index);
    }
    
    tree[node] = leftIndex[node * 2] != -1 ? tree[node * 2] : 0;
    tree[node] += (leftIndex[node * 2] != -1 ? tree[node * 2 + 1] : 0);
}

void buildResult(int caseNo) {
    
    char type;
    uint left, right, index;
    uint result = 0;
    
    for (int j = 0; j < k; j++) {
        cin >> type;
        
        switch (type) {
            case 's':
                cin >> index;
                addValue(1, 1, index);
                break;
            case 'd':
                cin >> index;
                addValue(1, -1, index);
                break;
            case 'c':
                cin >> left >> right;
                result += getValue(1, left, right);
                break;
        }
    }
    
    cout << "Case #" << caseNo << ": " << result << endl;
}
