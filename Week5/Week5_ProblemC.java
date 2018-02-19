import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Week5_ProblemC {
	static BigInteger n;
	static BigInteger two = new BigInteger("2");
	static BigInteger six = new BigInteger("6");
	
public static void main(String[] args) throws NumberFormatException, IOException{
		
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		
		for (int i = 1; i <= testCasesCount; i++) {
			n = new BigInteger(in.readLine());
			System.out.println(buildResult(i));
		}
		
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		n = n.multiply(six);
		BigInteger min = new BigInteger("1");
		BigInteger max = new BigInteger("10000000000");
		BigInteger mid = new BigInteger("-1");
		BigInteger result;
		BigInteger lastMid;
		int comp = 0;
		
		while (true) {
			lastMid = mid;
			mid = max.add(min).divide(two);
			
			if (mid.compareTo(lastMid) == 0)
				break;
			
			result = mid.multiply(mid.add(BigInteger.ONE));
			result = result.multiply(mid.multiply(two).add(BigInteger.ONE));
			
			comp = result.compareTo(n);
			
			if (comp == -1)
				min = mid;
			else if (comp == 1)
				max = mid;
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(mid.add(two));
		
		return sb;
	}
}
