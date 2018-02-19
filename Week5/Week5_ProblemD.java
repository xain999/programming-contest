import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Week5_ProblemD {
	static int[] remainder = new int[148];
	static int[] result = { 13, 1, 2, 3, 4, 5, 6, 7, 0, 0, 8, 9, 10, 11, 12 };
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		init(); 
	
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		
		for (int i = 0; i < testCasesCount; i++)
			System.out.println(buildResult(i + 1, in.readLine()));
	}

	public static void init() {
		for (int i = 1, base8 = 0; base8 < 148; i++) {
			base8 = Integer.parseInt(Integer.toOctalString(i));
			if (base8 > 147)
				break;
			remainder[base8] = Integer.parseInt(Integer.toOctalString(i % 13));
		}
	}
	
	public static StringBuilder buildResult(int caseNo, String input) {
		
		int i = 2;
		int len = input.length();
		int num = Integer.parseInt(input.substring(0, len > 1 ? 2 : 1));
		int rem = remainder[num];
		
		while (i < len) {
			num = rem * 10 + Integer.parseInt(input.substring(i, i + 1));
			rem = remainder[num];
			i++;
		}
		
		rem = result[rem];
		rem += 2;
		rem = rem % 13;
		rem += 1;
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(rem);
		
		return sb;
	}
}
