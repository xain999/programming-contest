import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Week10_ProblemB {
	static int l, n, d;
	static int[] lights;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//System.setIn(new FileInputStream("2.in"));
		InputStreamReader r = new InputStreamReader (  System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			l = Integer.parseInt(tokens[0]);
			n = Integer.parseInt(tokens[1]);
			d = Integer.parseInt(tokens[2]);
			
			lights = new int[n];
			tokens = in.readLine().split(" ");
				
			if (n > 0) {
				int k = 0;
				for (String token : tokens)
					lights[k++] = Integer.parseInt(token);
			}
			System.out.println(buildResult(i));	
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Arrays.sort(lights);
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		
		int result = 0;
		int illuminationDone = 0;
		int illuminationTemp = 0;
		
		for (int light : lights) {
			if (light - d > illuminationTemp) {
				sb.append("impossible");
				return sb;
			} else {
				if (light - d > illuminationDone) {
					illuminationDone = illuminationTemp;
					result++;
				}
				
				illuminationTemp = light + d;
				
				if (illuminationTemp >= l) {
					result++;
					sb.append(result);
					return sb;
				}
			}
		}
		
		sb.append("impossible");
		
		return sb;
	}
}
