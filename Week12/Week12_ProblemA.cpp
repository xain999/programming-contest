//
//  main.cpp
//  Week12_ProblemA
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

// Help from: http://www.geeksforgeeks.org/trie-insert-and-search/

// Alphabet size (# of symbols)
#define ALPHABET_SIZE (26)

// Convert Letters to lower case
#define TO_LOWER_CASE(c) ((int)c + ((int)'a' - (int)'A'))

// Converts key current character into index
// use only 'a' through 'z' and lower case
#define CHAR_TO_INDEX(c) ((int)c - (int)'a')

struct Node {
    vector<unique_ptr<Node>> children;
    bool wordEnds, hasChildren;
    Node() :
        children(ALPHABET_SIZE), wordEnds(false), hasChildren(false) {
    }
};

void buildResult(int i);
void insert(unique_ptr<Node>* node, const char* word, int length);

int n;
unique_ptr<Node> root;

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    
    cin >> testCases;
    
    string word;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> n;
        root = unique_ptr<Node>(new Node());
        
        for (int j = 0; j < n; j++) {
            cin >> word;
            word[0] = TO_LOWER_CASE(word[0]);
            insert(&root, word.c_str(), static_cast<int>(word.length()));
        }
        
        buildResult(i);
    }
    
    return 0;
}

void insert(unique_ptr<Node>* node, const char* word, int length) {
    
    int level;
    int index;
    
    for (level = 0; level < length; level++)
    {
        index = CHAR_TO_INDEX(word[level]);
        if (!node->get()->children[index]) {
            node->get()->children[index] = unique_ptr<Node>(new Node());
        }
        
        node->get()->hasChildren = true;
        node = &node->get()->children[index];
    }
    
    // mark the end of the word
    node->get()->wordEnds = true;
}

int findOverlappingWords(unique_ptr<Node>& node) {
    if (!node->hasChildren)
        return 0;
    
    int result = 0;
    
    for (int i = 0; i < ALPHABET_SIZE; i++) {
        if (node->children[i] != nullptr)
            result += findOverlappingWords(node->children[i]);
    }
    return node->wordEnds ? 1 + result : result;
}

void buildResult(int caseNo) {
    
    cout << "Case #" << caseNo << ": " << findOverlappingWords(root) << endl;
}

