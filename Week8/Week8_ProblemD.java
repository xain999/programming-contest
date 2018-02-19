import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Week8_ProblemD {
	static long num;
	static long den;
	static Point[] vertices;
	static int n, m;
	
	// help from http://www.mathopenref.com/coordpolygonarea.html
	public static long doubleArea(Point[] points) {
		long area = 0;
		for (int i = 1; i <= points.length; i++)
			area += (points[i - 1].x * points[i % points.length].y - points[i - 1].y * points[i % points.length].x);
		return Math.abs(area);
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			
			n = Integer.parseInt(tokens[0]);
			m = Integer.parseInt(tokens[1]);
			
			vertices = new Point[n];
			
			for (int j = 0; j < n; j++) {
				tokens = in.readLine().split(" ");
				vertices[j] = new Point(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
			}
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	// help from http://stackoverflow.com/questions/13673600/how-to-write-a-simple-java-program-that-finds-the-greatest-common-divisor-betwee
	static long gcd(long a, long b) {
	  if(b == 0)
		  return a; // base case
	  return gcd(b, a % b);
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		den = doubleArea(vertices) * m * m;
		num = 0;
		
		for (int i = 1; i <= vertices.length; i++) {
			num += doubleArea(new Point[] 
					{ vertices[i - 1], vertices[i % vertices.length], vertices[(i + 1) % vertices.length] });
		}
		
		long gcd = gcd(num, den);
		num = num / gcd;
		den = den / gcd;
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(num);
		sb.append("/");
		sb.append(den);
		
		return sb;
	}
}
