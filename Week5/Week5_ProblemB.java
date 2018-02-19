import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Week5_ProblemB {
	static int n;
	static long[] armies;
	
public static void main(String[] args) throws NumberFormatException, IOException{
		
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		
		for (int i = 1; i <= testCasesCount; i++) {
			
			n = Integer.parseInt(in.readLine());
			armies = new long[n];
			String[] input = in.readLine().split(" ");
			
			for (int j = 0; j < input.length; j++)
				armies[j] = Long.parseLong(input[j]);
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
		
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		long gcd = armies[0];
		long[] r = new long[3];
		
		for (int i = 1; i < armies.length; i++) {
			r[0] = gcd;
			r[1] = armies[i];
			
			while (r[1] != 0) {
				r[2] = r[0] % r[1];
				r[0] = r[1]; r[1] = r[2];
			}
			gcd = r[0];
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(gcd);
		
		return sb;
	}
}
