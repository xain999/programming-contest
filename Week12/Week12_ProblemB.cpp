//
//  main.cpp
//  Week12_ProblemB
//
//  Created by Zain Ul Abideen on 27/01/2017.
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
    uint sum, leftIndex, rightIndex; // Both the indices are inclusive
    Node(int left, int right) :
    left(nullptr), right(nullptr), sum(0), leftIndex(left), rightIndex(right) {
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
        root = unique_ptr<Node>(new Node(1, n));
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

uint getValue(uint index) {
    auto curr = &root;
    uint result = curr->get()->sum;
    
    while (!(curr->get()->leftIndex == curr->get()->rightIndex && curr->get()->leftIndex == index)) {
        if (index <= curr->get()->left->rightIndex)
            curr = &curr->get()->left;
        else
            curr = &curr->get()->right;

        result += curr->get()->sum;
    }
    
    return result;
}

void addValue(unique_ptr<Node>* node, uint value, uint left, uint right) {
    if (node->get()->leftIndex == left && node->get()->rightIndex == right) {
        node->get()->sum += value;
        return;
    }
    
    int mid = (node->get()->leftIndex + node->get()->rightIndex) / 2;
    
    if (mid >= left)
        addValue(&node->get()->left, value, MAX(node->get()->leftIndex, left), MIN(node->get()->left->rightIndex, right));
    if (mid + 1 <= right)
        addValue(&node->get()->right, value, MAX(node->get()->right->leftIndex, left), MIN(node->get()->rightIndex, right));

}

void buildResult(int caseNo) {
    
    char type;
    uint left, right, value;
    uint remainder = 0;
    
    for (int j = 0; j < k; j++) {
        cin >> type;
        if (type == 'q') {
            cin >> value;
            remainder += getValue(value);
            remainder = remainder % MOD_CONST;
        } else if (type == 'i') {
            cin >> left >> right >> value;
            addValue(&root, value, left, right);
        }
    }
    
    cout << "Case #" << caseNo << ": " << remainder << endl;
}

