import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Week2_ProblemC {

	static int m;
	static int n;
	static int startIndex = 0;
	static int[] onTop;
	static int[] score;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		
		for (int i = 1; i <= testCasesCount; i++) {
			
			List<Integer>[] sticks = readInput(in);
			System.out.println(buildResult(i, sticks));
			
			if (i != testCasesCount)
				in.readLine();
		}
		
	}
	
	public static List<Integer>[] readInput(BufferedReader in) throws IOException {
		
		startIndex = 0;
		
		String line = in.readLine();
		String[] values = line.split(" ");
		
		n = Integer.parseInt(values[0]);
		m = Integer.parseInt(values[1]);
		int v, u;
		
		List<Integer>[] sticks = new ArrayList[n];
		onTop = new int[n];
		score = new int[n];
		
		line = in.readLine();
		values = line.split(" ");
		
		for (int i = 0; i < n; i++) {
			sticks[i] = new ArrayList<Integer>();
			score[i] = Integer.parseInt(values[i]);
		}
		
		for (int i = 1; i <= m; i++) {
			line = in.readLine();
			values = line.split(" ");
			
			v = Integer.parseInt(values[0]) - 1;
			u = Integer.parseInt(values[1]) - 1;
			
			sticks[v].add(u);
			onTop[u]++;
		}
		
		return sticks;
	}
	
	public static StringBuilder buildResult(int caseNo, List<Integer>[] sticks) {
		
		StringBuilder sb = new StringBuilder(12);
		
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		
		int currScore = 0;
		int index = nextIndex();
		
		while(index != -1) {
			currScore += score[index];
			score[index] = 0;
			
			for (int val : sticks[index])
				onTop[val]--;
			
			sticks[index].clear();
			index = nextIndex();
		}
		
		sb.append(currScore);
		
		return sb;
	}

	public static int nextIndex() {
		for (int i = startIndex; i < n; i++) {
			if (onTop[i] <= 0) {
				if (startIndex == i)
					startIndex++;
				if (score[i] > 0)
					return i;
			}
		}
		
		return - 1;
	}
}
