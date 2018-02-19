import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Week13_ProblemF {
	static int n;
	static Point min;
	static Point[] points;
	
	static class Point {
		int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		int minIndex = 0;
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			n = Integer.parseInt(in.readLine());
			
			points = new Point[n];
			min = null;
			int x, y;
			
			for (int j = 0; j < n; j++) {
				tokens = in.readLine().split(" ");
				x = Integer.parseInt(tokens[0]);
				y = Integer.parseInt(tokens[1]);
				
				points[j] = new Point(x, y);
				
				if (min == null || y < min.y || (y == min.y && x < min.x)) {
					min = points[j];
					minIndex = j;
				}
			}
			
			Point temp = points[0];
			points[0] = min;
			points[minIndex] = temp;
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static int compare(Point a, Point b) {
		double aSlope = a.x == min.x ? 1001 : (double)(a.y - min.y) / (double)(a.x - min.x);
		double bSlope = b.x == min.x ? 1001 : (double)(b.y - min.y) / (double)(b.x - min.x);
		
		if (aSlope < 0)
			aSlope = -(1 / aSlope) + 1001;
		if (bSlope < 0)
			bSlope = -(1 / bSlope) + 1001;
		
		if (aSlope == bSlope) {
			double aDist = Math.sqrt((double)((a.x - min.x) * (a.x - min.x) + (a.y - min.y) * (a.y - min.y)));
			double bDist = Math.sqrt((double)((b.x - min.x) * (b.x - min.x) + (b.y - min.y) * (b.y - min.y)));
			
			if (aDist > bDist)
				aSlope++;
			else
				bSlope++;
			
		}
		
		return Double.compare(aSlope, bSlope);
	}
	
	public static int ccw(Point p1, Point p2, Point p3) {
		return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Arrays.sort(points, 1, points.length, (a, b) -> compare(a, b));
		
		int k = 1;
		for (int i=1; i<n; i++) {
			while (i < n-1 && ccw(points[0], points[i], points[i+1]) == 0)
		          i++;
		 
		    points[k] = points[i];
		    k++;
		}
		
		int m = 1;
		for (int i = 2; i < k; i++) {
			
			// formula copied from wikipedia
			while (ccw(points[m - 1], points[m], points[i]) <= 0) {
				if (m > 1)
					m--;
				else if (i == points.length)
		            break;
		        else
		            i++;
			}
			m++;
			
			Point temp = points[m];
			points[m] = points[i];
			points[i] = temp;
		}
		
		double area = 0.0f;
		for (int i = 0; i < m; i ++) {
			// copied from http://stackoverflow.com/a/717367
			area += points[i].x * points[i + 1].y - points[i].y * points[i + 1].x;
		}

		area += (points[m].x * points[0].y - points[m].y * points[0].x);
		area = Math.abs(area);

		StringBuilder sb = new StringBuilder(20);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(area);
		
		return sb;
	}
}
