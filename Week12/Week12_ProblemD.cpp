//
//  main.cpp
//  Week12_ProblemD
//
//  Created by Zain Ul Abideen on 29/01/2017.
//  Copyright © 2017 Zain Ul Abideen. All rights reserved.
//

#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>
#include <memory>

using namespace std;

#define MOD_CONST 1000000007

#define MIN(a,b) ((a) < (b) ? (a) : (b))
#define MAX(a,b) ((a) > (b) ? (a) : (b))

struct Node {
    unique_ptr<Node> left, right;
    uint value, leftIndex, rightIndex; // Both the indices are inclusive
    Node(int left, int right) :
    left(nullptr), right(nullptr), value(0), leftIndex(left), rightIndex(right) {
    }
};

void buildResult(int i);
void buildTree(unique_ptr<Node>& node);

uint n, k;
unique_ptr<Node> root;

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    cin >> testCases;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> n;
        cin >> k;
        root = unique_ptr<Node>(new Node(0, n - 1));
        buildTree(root);
        
        buildResult(i);
    }
    
    return 0;
}

void buildTree(unique_ptr<Node>& node) {
    if (node->leftIndex == node->rightIndex) {
        return;
    }
    node->left = unique_ptr<Node>(new Node(node->leftIndex, (node->leftIndex + node->rightIndex) / 2));
    node->right = unique_ptr<Node>(new Node((node->leftIndex + node->rightIndex) / 2 + 1, node->rightIndex));
    
    buildTree(node->left);
    buildTree(node->right);
}

uint getMaxValue(unique_ptr<Node>& node, uint start, uint end) {
    if (node->value != 0) {
        return node->value;
    }

    uint left = 0, right = 0;
    
    int mid = (node->leftIndex + node->rightIndex) / 2;
    
    if (mid >= start && node->left != nullptr)
        left = getMaxValue(node->left, MAX(node->leftIndex, start), MIN(node->left->rightIndex, end));
    if (mid + 1 <= end && node->right != nullptr)
        right = getMaxValue(node->right, MAX(node->right->leftIndex, start), MIN(node->rightIndex, end));
    
    return (left > right ? left : right);
}

void setValue(unique_ptr<Node>& node, uint value, uint left, uint right) {
    if (node->leftIndex == left && node->rightIndex == right) {
        node->value = value;
        return;
    }
    
    if (node->value != 0) {
        setValue(node->left, node->value, node->left->leftIndex, node->left->rightIndex);
        setValue(node->right, node->value, node->right->leftIndex, node->right->rightIndex);
        node->value = 0;
    }
    
    int mid = (node->leftIndex + node->rightIndex) / 2;
    
    if (mid >= left)
        setValue(node->left, value, MAX(node->leftIndex, left), MIN(node->left->rightIndex, right));
    if (mid + 1 <= right)
        setValue(node->right, value, MAX(node->right->leftIndex, left), MIN(node->rightIndex, right));
    
}

void buildResult(int caseNo) {
    
    uint w, h, p;
    uint max = 0;
    uint curr;
    
    cout << "Case #" << caseNo << ":";
    
    for (int j = 0; j < k; j++) {
        cin >> w >> h >> p;
        curr = getMaxValue(root, p, p + w - 1) + h;
        setValue(root, curr, p, p + w - 1);
        if (curr > max)
            max = curr;
        cout << " " << max;
    }
    
    cout << endl;
}

