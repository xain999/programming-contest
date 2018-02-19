import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Week10_ProblemA {
	static int n, m;
	static int[] length;
	static int[] score;
	static Integer[] index;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//System.setIn(new FileInputStream("2.in"));
		InputStreamReader r = new InputStreamReader (  System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			m = Integer.parseInt(tokens[0]);
			n = Integer.parseInt(tokens[1]);
			
			length = new int[n];
			score = new int[n];
			index = new Integer[n];
			
			for (int j = 0; j < n; j++) {
				index[j] = j;
				tokens = in.readLine().split(" ");
				
				length[j] = Integer.parseInt(tokens[0]);
				score[j] = Integer.parseInt(tokens[1]);
			}
			
			System.out.println(buildResult(i));	
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Arrays.sort(index, (a, b) -> Double.compare((double)score[b] / length[b], (double)score[a] / length[a]));
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		
		int spaceLeft = m;
		for (int i = 0; i < n; i++) {
			int course = index[i];
			
			while (length[course] <= spaceLeft) {
				sb.append(" " + (course + 1));
				spaceLeft -= length[course];
			}
			
			if (spaceLeft <= 0)
				break;
		}
		
		return sb;
	}
}
