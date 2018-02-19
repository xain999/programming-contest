//
//  main.cpp
//  Week13_ProblemE
//
//  Created by Zain Ul Abideen on 02/02/2017.
//  Copyright © 2017 Zain Ul Abideen. All rights reserved.
//

#include <iostream>
#include <iomanip>
#include <vector>
#include <cmath>

using namespace std;

#define MIN 0.0000001

void buildResult(int i);

int n, b;
vector<int> list(n);

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    
    cin >> testCases;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> n >> b;
        
        list.resize(n);
        
        for (int j = 0; j < n; j++)
            cin >> list[j];
        
        buildResult(i);
    }
    
    return 0;
}

double calculateProb(double prob) {
    double result = 0;
    
    int i = 1;
    
    for (int lottery : list)
        result += (lottery * pow(prob, i++));
    
    return result;
}

void buildResult(int caseNo) {
    
    double left = 0, right = 1;
    double mid = 0;
    
    while (right - left > MIN) {
        mid = (left + right) / 2;
        if (calculateProb(mid) > b)
            right = mid;
        else
            left = mid;
    }
    
    cout << "Case #" << caseNo << ": " << std::fixed << setprecision(7) << (left + right) / 2 << endl;
}

