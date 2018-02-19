import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Week8_ProblemB {
	static class Point {
		double x, y, z;
		Point(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		void normalise() {
			x = x / z;
			y = y / z;
			z = 1;
		}
		void orthogonal() {
			double temp = x;
			x = y;
			y = -temp;
		}
	}
	
	public static Point cross(Point a, Point b) {
		return new Point(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
	}
	
	static Point p1, p2, p3;
	static final double INF = 1000000000;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			
			p1 = new Point(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]), 1);
			tokens = in.readLine().split(" ");
			p2 = new Point(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]), 1);
			tokens = in.readLine().split(" ");
			p3 = new Point(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]), 1);
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static Point getPerpendicular(Point p1, Point p2, Point q){
		double px = p2.x - p1.x;
		double py = p2.y - p1.y;
		
		double dist2 = px * px + py * py;
		double u = ((q.x - p1.x) * px + (q.y - p1.y) * py) / dist2;
		
		return new Point(p1.x + u * px, p1.y + u * py, 1);
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Point orthocenter, centroid, circumcenter;
		Point[] mid = new Point[3];
		
		orthocenter = cross(getPerpendicular(p1, p2, p3), p3);
		orthocenter = cross(cross(getPerpendicular(p2, p3, p1), p1), orthocenter);
		orthocenter.normalise();
		
		mid[0] = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2, 1);
		mid[1] = new Point((p3.x + p2.x) / 2, (p3.y + p2.y) / 2, 1);
		mid[2] = new Point((p1.x + p3.x) / 2, (p1.y + p3.y) / 2, 1);
		
		centroid = cross(mid[0], p3);
		centroid = cross(centroid, cross(mid[1], p1));
		centroid.normalise();
		
		double m1 = p2.x == p1.x ? INF : (p2.y - p1.y) / (p2.x - p1.x); //mid[0]
		double m2 = p2.x == p3.x ? INF : (p2.y - p3.y) / (p2.x - p3.x); //mid[1]
		m1 = m1 == 0 ? INF : (-1 / m1);
		m2 = m2 == 0 ? INF : (-1 / m2);
		
		double x = mid[0].x * m1 - mid[1].x * m2 + mid[1].y - mid[0].y;
		x = x / (m1 - m2 == 0 ? INF : m1 - m2);
		double y = m1 * (x - mid[0].x) + mid[0].y;
		
		circumcenter = new Point(x, y, 1);
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(System.lineSeparator());
		sb.append(centroid.x + " " + centroid.y);
		sb.append(System.lineSeparator());
		sb.append(orthocenter.x + " " + orthocenter.y);
		sb.append(System.lineSeparator());
		sb.append(circumcenter.x + " " + circumcenter.y);
		
		return sb;
	}
}
