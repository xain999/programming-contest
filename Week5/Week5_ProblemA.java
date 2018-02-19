import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Week5_ProblemA {
	
	static int[] input;
	static boolean[] prime;
	
public static void main(String[] args) throws NumberFormatException, IOException{
		
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		input = new int[testCasesCount];
		int max = 0;
		int i, j;
		
		for (i = 0; i < testCasesCount; i++) {
			input[i] = Integer.parseInt(in.readLine());
			if (input[i] > max)
				max = input[i];
		}
		
		prime = new boolean[max - 1];
		Arrays.fill(prime, 2, max - 1, Boolean.TRUE);;
		
		for (i = 2; i < prime.length; i++) {
			if (prime[i])
				for (j = 2; j * i < prime.length; j++)
					prime[j*i] = false;
		}
		
		for (i = 1; i <= testCasesCount; i++)
			System.out.println(buildResult(i, input[i - 1]));
	}
	
	public static StringBuilder buildResult(int caseNo, int num) {
		boolean even = num % 2 == 0;
		boolean stop = false;
		int[] result = new int[even ? 2 : 3];
		
		for (int i = 2; i < prime.length && !stop; i++) {
			if (prime[i]) {
				if (even) {
					if (prime[num - i]) {
						result[0] = i;
						result[1] = num - i;
						stop = true;
					}
				} else {
					int k = num - i;
					for (int j = 2; j < k; j++) {
						if (prime[j] && prime[k - j]) {
							result[0] = i;
							result[1] = j;
							result[2] = k - j;
							stop = true;
							break;
						}
					}
				}
			}
		}
		
		Arrays.sort(result);
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		
		for (int i : result) {
			sb.append(" ");
			sb.append(i);
		}
		
		return sb;
	}
}
