import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Week7_ProblemD {
	static int n;
	static Point2D.Double[] points;
	static Point2D.Double gravity;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			n = Integer.parseInt(in.readLine());
			
			points = new Point2D.Double[n];
			gravity = new Point2D.Double(0, 0);
			double x, y;
			
			for (int j = 0; j < n; j++) {
				tokens = in.readLine().split(" ");
				x = Double.parseDouble(tokens[0]);
				y = Double.parseDouble(tokens[1]);
				
				gravity.x += x;
				gravity.y += y;
				
				points[j] = new Point2D.Double(x, y);
			}
			
			gravity.x = gravity.x / n;
			gravity.y = gravity.y / n;
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static double ccw(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
		return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
	}
	
	//Help from Sharjeel
	public static Point2D.Double getPerpendicular(Point2D.Double p1, Point2D.Double p2, Point2D.Double q){
		double px = p2.x - p1.x;
		double py = p2.y - p1.y;
		
		double dist2 = px * px + py*py;
		double u = ((q.x - p1.x) * px + (q.y - p1.y) * py) / dist2;
		
		return new Point2D.Double(p1.x + u * px, p1.y + u * py);
	}
	
	public static boolean checkCenterOfGravity(Point2D.Double p1, Point2D.Double p2) {
		Point2D.Double perp = getPerpendicular(p1, p2, gravity);
		
		double xmin = p1.x < p2.x ? p1.x : p2.x;
		double ymin = p1.y < p2.y ? p1.y : p2.y;
		double xmax = p1.x > p2.x ? p1.x : p2.x;
		double ymax = p1.y > p2.y ? p1.y : p2.y;
		
		if (perp.x >= xmin && perp.x <= xmax && perp.y >= ymin && perp.y <= ymax)
			return true;
		
		return false;
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Point2D.Double p1 = null;
		Point2D.Double p2 = points[n - 1];
		
		int result = 0;

		for (int i = 0; i < n; i++) {
			p1 = p2;
			p2 = points[i];
			
			if (!checkCenterOfGravity(p1, p2))
				continue;
			
			Point2D.Double testPoint = null;
			boolean isValid = true;
			boolean isClockwise = false;
			boolean isCounterClockwise = false;
			
			for (int j = 0; j < n; j++) {
				testPoint = points[j];
				if (p1 == testPoint || p2 == testPoint)
					continue;
				double ccw = ccw(p1, p2, testPoint);
				
				if (ccw == 0) {
					isValid = false;
					break;
				}
				if (ccw < 0) {
					if (isCounterClockwise) {
						isValid = false;
						break;
					}
					else
						isClockwise = true;
				} else if (ccw > 0) {
					if (isClockwise) {
						isValid = false;
						break;
					}
					else
						isCounterClockwise = true;
				}
			}
		    
			if (isValid)
				result++;
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(result);
		
		return sb;
	}
}
