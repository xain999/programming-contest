import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Week8_ProblemE {
	static int n, d;
	static double a;
	static StringBuilder s;
	static HashMap<String, String> map;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			
			map = new HashMap<String, String>(2 * n);
			
			n = Integer.parseInt(tokens[0]);
			d = Integer.parseInt(tokens[1]);
			a = Integer.parseInt(tokens[2]);
			s = new StringBuilder(tokens[3]);
			
			for (int j = 0; j < n; j++) {
				tokens = in.readLine().split("=>");
				map.put(tokens[0], tokens[1]);
			}
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		String p;
		
		for (int j = 0; j < d; j++) {
			for (int i = s.length() - 1; i >= 0; i--) {
				p = s.substring(i, i + 1);
				
				if (!p.equals("+") && !p.equals("-"))
					s.replace(i, i + 1, map.get(p));
			}
		}
		
		double x = 0, y = 0;
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		sb.append(System.lineSeparator());
		sb.append(x);
		sb.append(" ");
		sb.append(y);
		
		double currAngle = 0;
		char[] array = new char[s.length()];
		s.getChars(0, s.length(), array, 0);
		
		a /= 180;
		
		for (char c : array) {
			
			if (c == '+') {
				currAngle += a;
			} else if (c == '-') {
				currAngle -= a;
			} else {
				x += Math.cos(currAngle * Math.PI);
				y += Math.sin(currAngle * Math.PI);
				sb.append(System.lineSeparator());
				sb.append(x);
				sb.append(" ");
				sb.append(y);
			}
		}
		
		return sb;
	}
}
