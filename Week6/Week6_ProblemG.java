import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Week6_ProblemG {
	static ArrayList<Integer>[] neighbours;
	static int[] colors;
	static int n, m, k;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		int node1, node2;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			n = Integer.parseInt(tokens[0]);
			m = Integer.parseInt(tokens[1]);
			k = Integer.parseInt(tokens[2]);
			
			neighbours = new ArrayList[n];
			colors = new int[n];
			
			for (int j = 0; j < m; j++) {
				tokens = in.readLine().split(" ");
				node1 = Integer.parseInt(tokens[0]) - 1;
				node2 = Integer.parseInt(tokens[1]) - 1;
				
				if (neighbours[node1] == null)
					neighbours[node1] = new ArrayList<Integer>();
				if (neighbours[node2] == null)
					neighbours[node2] = new ArrayList<Integer>();
				
				neighbours[node1].add(node2);
				neighbours[node2].add(node1);
			}
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static boolean isValid(int index, int color) {
		if (neighbours[index] != null)
			for (int node : neighbours[index]) {
				if (colors[node] == color)
					return false;
			}
		return true;
	}
	
	public static boolean setColors(int index) {
		for (int i = 1; i <= k; i++) {
			if (isValid(index, i)) {
				colors[index] = i;
				if (index == n - 1 || setColors(index + 1))
					return true;
				
				colors[index] = 0;
			}
		}
		return false;
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		
		if (setColors(0)) {
			for (int color : colors)
				sb.append(" " + color);
		}
		else
			sb.append("impossible");
		
		return sb;
	}
}
