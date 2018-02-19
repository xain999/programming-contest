import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Week2_ProblemB {
	static double d, p, u, v;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		
		for (int i = 1; i <= testCasesCount; i++) {
			String line = in.readLine();
			String[] values = line.split(" ");
			d = Integer.parseInt(values[0]);
			p = Integer.parseInt(values[1]) - 1;
			u = Integer.parseInt(values[2]);
			v = Integer.parseInt(values[3]);
					
			buildResult(i);
		}
		
	}
	
	public static void buildResult(int caseNo) {
		
		double max = d / p;				//doesn't fit
		double min = (d - (v - u)) / p;	//fit
		double mid = (max + min) / 2;
		
		double start = 0;
		int factor;
		
		while (max - min > 0.001f) {
			
			factor = (int) (v / mid);
			start += (v - factor * mid);
			start += (mid * (p + (factor - (int) (u / mid) - 1)));
			
			if (start > d)
				max = mid;
			else
				min = mid;
			
			mid = (max + min) / 2;
			start = 0;
		}
		
		System.out.println("Case #" + caseNo + ": " + mid);
	}
}
