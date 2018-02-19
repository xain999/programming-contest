//
//  main.cpp
//  Week12_ProblemE
//
//  Created by Zain Ul Abideen on 30/01/2017.
//  Copyright © 2017 Zain Ul Abideen. All rights reserved.
//


#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>
#include <memory>

using namespace std;

// Alphabet size (# of symbols)
#define ALPHABET_SIZE (26)
#define LEA true
#define BEA false

// Converts key current character into index
// use only 'a' through 'z' and lower case
#define CHAR_TO_INDEX(c) ((int)c - (int)'a')

struct Node {
    vector<Node*> children;
    bool wordEnds;
	bool player;
    Node() :
    children(ALPHABET_SIZE, nullptr), wordEnds(false), player(false) {
    }
};

void buildResult(int i);
void insert(Node* node, const char* word, int length);

int n;
Node* root;
bool result[2];

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    
    cin >> testCases;
    
    string word;
	int w;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> n >> w;
        root = new Node();
        
        for (int j = 0; j < w; j++) {
            cin >> word;
            insert(root, word.c_str(), static_cast<int>(word.length()));
        }
        
        buildResult(i);
    }
    
    return 0;
}

void insert(Node* node, const char* word, int length) {
    
    int level;
    int index;
    
    for (level = 0; level < length; level++)
    {
        index = CHAR_TO_INDEX(word[level]);
        if (!node->children[index]) {
            node->children[index] = new Node();
        }
        node = node->children[index];
    }
    
    // mark the end of the word
    node->wordEnds = true;
}

bool forceWinPossible(Node* node, bool isLea) {
	bool leaWins = false;
	bool beaWins = false;
	
	bool player = !node->player;
	for (int i = 0; i < ALPHABET_SIZE; i++) {
		if (node->children[i] == nullptr)
			continue;
		
		node->children[i]->player = isLea;
		player = forceWinPossible(node->children[i], !isLea);
		if (player == BEA)
			beaWins = true;
		else
			leaWins = true;
	}
	
	if (node->wordEnds) {
		if (node->player == LEA)
			beaWins = true;
		else
			leaWins = true;
	}
	
	if (leaWins && beaWins) {
		if (node->player == BEA)
			player = LEA;
		else
			player = BEA;
	}
	else if (beaWins)
		player = BEA;
	else if (leaWins)
		player = LEA;

	return player;
}

bool forceLosePossible(Node* node, bool isLea) {
	bool leaWins = false;
	bool beaWins = false;
	
	bool player = !node->player;
	for (int i = 0; i < ALPHABET_SIZE; i++) {
		if (node->children[i] == nullptr)
			continue;
		
		node->children[i]->player = isLea;
		player = forceLosePossible(node->children[i], !isLea);
		if (player == BEA)
			beaWins = true;
		else
			leaWins = true;
	}
	
	if (node->wordEnds) {
		if (node->player == LEA)
			beaWins = true;
		else
			leaWins = true;
	}
	
	if (leaWins && beaWins) {
		if (node->player == BEA)
			player = BEA;
		else
			player = LEA;
	}
	else if (beaWins)
		player = BEA;
	else if (leaWins)
		player = LEA;
	
	return player;
}

bool checkWinner(bool canForceWin, bool canForceLose, bool winnerStarts, uint rounds) {
	if (winnerStarts) {
		if (canForceWin)
			return LEA;
		else {
			if (canForceLose) {
				if (rounds % 2 == 0)
					return LEA;
				else
					return BEA;
			}
			else
				return BEA;
		}
	} else {
		if (canForceWin) {
			if (canForceLose)
				return LEA;
			else {
				if (rounds % 2 == 0)
					return BEA;
				else
					return LEA;
			}
		}
		else
			return BEA;
	}
	return true;
}

void calculateWins() {
	root->player = BEA;
	
	bool canForceWin = forceWinPossible(root, true);
	bool canForceLose = !forceLosePossible(root, true);
	
	// case 1
	{
		if (checkWinner(canForceWin, canForceLose, true, n))
			result[0] = true;
		else
			result[0] = false;
	}
	
	// case 2
	{
		if (checkWinner(canForceWin, canForceLose, false, n))
			result[1] = true;
		else
			result[1] = false;
	}
}

void buildResult(int caseNo) {
	
	calculateWins();
	
    cout << "Case #" << caseNo << ":" << endl;
	
	for (bool& res : result) {
		if (res)
			cout << "victory" << endl;
		else
			cout << "defeat" << endl;
	}
	for (bool& res : result) {
		if (!res)
			cout << "victory" << endl;
		else
			cout << "defeat" << endl;
	}
}

