#include <iostream>
using namespace std;

float d, p, u, v;

void buildResult(int caseNo) {
    
    float max = d / p;				//doesn't fit
    float min = (d - (v - u)) / p;	//fit
    float mid = (max + min) / 2;
    
    float start = 0;
    int factor = 0;
    
    while (max - min > 0.0001f) {
        
        factor = (int) (v / mid);
        start += (v - factor * mid);
        start += (mid * (p + (factor - (int) (u / mid) - 1)));
        
        if (start > d)
            max = mid;
        else
            min = mid;
        
        mid = (max + min) / 2;
        start = 0;
    }
    
    cout << "Case #" << caseNo << ": " << mid << endl;
}


int main(int argc, const char * argv[]) {
    
    std::ios::sync_with_stdio(false);
    
    int n;
    
    cin >> n;
    
    for (int i = 1; i <= n; i++) {
        cin >> d >> p >> u >> v;
        p--;
        buildResult(i);
    }
    
    return 0;
}
