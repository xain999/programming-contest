import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Week6_ProblemE {
	static int n;
	static int[][] cities;
	static boolean[] visited;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			n = Integer.parseInt(in.readLine());
			
			cities = new int[n][n];
			visited = new boolean[n];
			
			for (int j = 0; j < n; j++) {
				tokens = in.readLine().split(" ");
				for (int k = 0; k < n; k++) {
					cities[j][k] = Integer.parseInt(tokens[k]);
				}
			}
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static int getDistance(int index, int prev, ArrayList<Integer> list) {
		int minDistance = Integer.MAX_VALUE;
		ArrayList<Integer> minDistList = new ArrayList<Integer>();
		int distance = 0;
		visited[index] = true;
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				ArrayList<Integer> checkList = new ArrayList<Integer>();
				distance = getDistance(i, index, checkList);
				
				if (distance < minDistance) {
					minDistance = distance;
					minDistList = checkList;
				}
			}
		}
		visited[index] = false;
		list.add(index);
		list.addAll(minDistList);
		
		return minDistance == Integer.MAX_VALUE ? cities[prev][index] + cities[0][index] : minDistance + cities[prev][index];
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		getDistance(0, 0, list);
			
		for (int i : list) {
			sb.append(" " + (i + 1));
		}
		
		return sb;
	}
}
