import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Week8_ProblemC {
	static class Station {
		double x, y, z;
		Station(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		@Override
		public int hashCode() {
			return (x + " " + y).hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			return this.hashCode() == obj.hashCode();
		}
		void normalise() {
			x = x / z;
			y = y / z;
			z = 1;
		}
		boolean isValid() {
			if (z == 0 || (x == 0 && y == 0))
				return false;
			return true;
		}
	}
	
	public static Station cross(Station a, Station b) {
		return new Station(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
	}
	
	static HashMap<Station, HashSet<Integer>> connections;
	static HashSet<Integer>[] neighbours;
	static ArrayList<Station>[] lines;
	static boolean[] done;
	static Station start, end;
	static Set<Integer> startSet;
	static Set<Integer> endSet;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			int n = Integer.parseInt(tokens[0]);
			
			connections = new HashMap<Station, HashSet<Integer>>(n * n);
			neighbours = new HashSet[n];
			lines = new ArrayList[n];
			done = new boolean[n];
			
			start = new Station(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]), 1);
			end = new Station(Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]), 1);
			startSet = null;
			endSet = null;
			
			Station st = null;
			
			for (int j = 0; j < n ; j++) {
				tokens = in.readLine().split(" ");
				int m = Integer.parseInt(tokens[0]);
				
				lines[j] = new ArrayList<Station>(m);
				neighbours[j] = new HashSet<Integer>();
				
				for (int k = 1; k < 2 * m; k += 2) {
					st = new Station(Double.parseDouble(tokens[k]),
							Double.parseDouble(tokens[k + 1]), 1);
					lines[j].add(st);
					
					if (!connections.containsKey(st))
						connections.put(st, new HashSet<Integer>());
					
					connections.get(st).add(j);
				}
			}
			
			System.out.println(buildResult(i));
			
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static void updateStations() {

		startSet = connections.get(start);
		endSet = connections.get(end);
		
		Set<Station> keys = connections.keySet();
		HashSet<Integer> values;
		
		for (Station st : keys) {
			values = connections.get(st);
			
			for (int val : values)
				neighbours[val].addAll(values);
		}
		
		for (int i = 0; i < lines.length; i++) {
			for (int j = i + 1; j < lines.length; j++) {
				addStations(lines[i], lines[j], i, j);
				//System.out.println(i + " " + j);
			}
		}
	}
	
	public static boolean validIntersection(Station a, Station b, Station intr) {
		double minx = a.x < b.x ? a.x : b.x;
		double miny = a.y < b.y ? a.y : b.y;
		double maxx = a.x > b.x ? a.x : b.x;
		double maxy = a.y > b.y ? a.y : b.y;
		
		if (intr.x >= minx && intr.x <= maxx && intr.y >= miny && intr.y <= maxy)
			return true;
		
		return false;
	}
	
	public static void addStations(ArrayList<Station> line1, ArrayList<Station> line2, int ind1, int ind2) {
		Station st1, st2, st3, st4;
		Station first, second, result;
		
		HashSet<Integer> set = null;
		
		for (int i = 1; i < line1.size(); i++) {
			st1 = line1.get(i - 1);
			st2 = line1.get(i);
			
			for (int j = 1; j < line2.size(); j++) {
				
				st3 = line2.get(j - 1);
				st4 = line2.get(j);
				
				first = cross(st1, st2);
				second = cross(st3, st4);
				
				result = cross(first, second);
				
				if (result.isValid()) {
					result.normalise();
					
					if (validIntersection(st1, st2, result) && validIntersection(st3, st4, result)) {
						
						if (start.equals(result)) {
							if (startSet == null)
								startSet = new HashSet<Integer>();
							startSet.add(ind1);
							startSet.add(ind2);
						}
						
						if (end.equals(result)) {
							if (endSet == null)
								endSet = new HashSet<Integer>();
							endSet.add(ind1);
							endSet.add(ind2);
						}
						
						set = connections.get(result);
						
						if (set == null) {
							set = new HashSet<Integer>(10);
							connections.put(result, set);
						} else {
							for (int ele : set) {
								neighbours[ind1].add(ele);
								neighbours[ind2].add(ele);
								neighbours[ele].add(ind1);
								neighbours[ele].add(ind2);
							}
						}
						
						set.add(ind1);
						set.add(ind2);
						
						//System.out.println("\tx: " + result.x + " y: " + result.y);
						
						neighbours[ind1].add(ind2);
						neighbours[ind2].add(ind1);
					}
				}
			}
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		//double startTime = System.currentTimeMillis();
		updateStations();
		//System.out.println("intersections: " + (System.currentTimeMillis() - startTime));
		
		boolean gotResult = false;
		int result = 0;
		
		Queue<Integer> queue = new ArrayDeque<Integer>(lines.length * 2);
		
		queue.addAll(startSet);
		queue.add(-1);

		
		while (!queue.isEmpty()) {
			int val = queue.poll();
			
			if (val == -1) {
				result++;
				
				if(!queue.isEmpty())
					queue.add(-1);
				continue;
			}
			
			if (done[val])
				continue;
			
			done[val] = true;
			
			if (endSet.contains(val)) {
				gotResult = true;
				break;
			}
			
			for (int next : neighbours[val]) {
				if (!done[next])
					queue.add(next);
			}
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		
		if (gotResult)
			sb.append(result);
		else
			sb.append("impossible");
		
		//System.out.println("search: " + (System.currentTimeMillis() - startTime));
		
		return sb;
	}
}
