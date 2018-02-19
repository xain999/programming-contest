//
//  main.cpp
//  Week12_ProblemC
//
//  Created by Zain Ul Abideen on 30/01/2017.
//  Copyright © 2017 Zain Ul Abideen. All rights reserved.
//

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
#include <memory>

using namespace std;

#define MIN(a,b) ((a) < (b) ? (a) : (b))
#define MAX(a,b) ((a) > (b) ? (a) : (b))

struct Hallway {
    int room, waterLevel;
    Hallway (int room, int waterLevel): room(room), waterLevel(waterLevel) {}
};

struct Node {
    vector<Hallway> connections;
    bool done;
    bool isControlRoom;
    int minWaterLevel;
    Node() : done(false), isControlRoom(false), minWaterLevel(0) {}
};

void buildResult(int i);

uint n, hallwayCount, controlRoomsCount, waterLevel;
vector<unique_ptr<Node>> rooms;

int main(int argc, const char * argv[]) {
    ios_base :: sync_with_stdio ( false );
    
    int testCases;
    int a, b, level;
    
    cin >> testCases;
    
    for (int i = 1; i <= testCases; i++) {
        cin >> n >> hallwayCount >> controlRoomsCount >> waterLevel;
        
        rooms.clear();
        rooms.resize(n);
        
        for (int j = 0; j < n; j++)
            rooms[j] = unique_ptr<Node>(new Node);
        
        for (int j = 0; j < hallwayCount; j++) {
            cin >> a >> b >> level;
            a--;
            b--;
            rooms[a]->connections.push_back(Hallway(b, level));
            rooms[b]->connections.push_back(Hallway(a, level));
        }
        
        for (int j = 0; j < controlRoomsCount; j++) {
            cin >> a >> level;
            a--;
            rooms[a]->isControlRoom = true;
            rooms[a]->minWaterLevel = level;
        }

        buildResult(i);
    }
    
    return 0;
}

void buildResult(int caseNo) {
    
    priority_queue<Hallway, vector<Hallway>, std::function<bool(Hallway&, Hallway&)>> que([](Hallway& a, Hallway& b) -> bool { return a.waterLevel < b.waterLevel; });
    que.push(Hallway(0, waterLevel));
    
    priority_queue<Hallway, vector<Hallway>, std::function<bool(Hallway&, Hallway&)>> rest([](Hallway& a, Hallway& b) -> bool { return a.waterLevel < b.waterLevel; });
    
    uint roomsVisited = 0;
    Hallway curr(0, 0);
    uint currWaterLevel = waterLevel;
    uint minWaterLevel = waterLevel;
    
    while (!que.empty() && roomsVisited != n) {
        curr = que.top();
        que.pop();
        
        if (rooms[curr.room]->done)
            continue;
        
        if (rooms[curr.room]->isControlRoom && rooms[curr.room]->minWaterLevel < minWaterLevel) {
            minWaterLevel = rooms[curr.room]->minWaterLevel;
        
            if (!rest.empty()) {
                auto other = rest.top();
            
                while (other.waterLevel >= minWaterLevel) {
                    que.push(other);
                    rest.pop();
                    
                    if (!rest.empty())
                        other = rest.top();
                    else
                        break;
                }
            }
        }
        
        if (curr.waterLevel < currWaterLevel)
            currWaterLevel = curr.waterLevel;
            
        rooms[curr.room]->done = true;
        roomsVisited++;
        
        for (int i = 0; i < rooms[curr.room]->connections.size(); i++) {
            if (rooms[rooms[curr.room]->connections[i].room]->done)
                continue;
            if (rooms[curr.room]->connections[i].waterLevel >= minWaterLevel)
                que.push(rooms[curr.room]->connections[i]);
            else
                rest.push(rooms[curr.room]->connections[i]);
        }
    }
    
    
    cout << "Case #" << caseNo << ": ";
    
    if (roomsVisited == n)
        cout << currWaterLevel << endl;
    else
        cout << "impossible" << endl;
}

