import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Week8_ProblemA {
	static class Point {
		float x, y, z;
		Point(float x, float y, float z) {
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
			float temp = x;
			x = y;
			y = -temp;
		}
	}
	
	public static Point cross(Point a, Point b) {
		return new Point(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
	}
	
	static Point p11, p12, p13, p21, p22, p23;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			
			p11 = new Point(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), 1);
			p12 = new Point(Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]), 1);
			p13 = new Point(Float.parseFloat(tokens[4]), Float.parseFloat(tokens[5]), 1);
			p21 = new Point(Float.parseFloat(tokens[6]), Float.parseFloat(tokens[7]), 1);
			p22 = new Point(Float.parseFloat(tokens[8]), Float.parseFloat(tokens[9]), 1);
			p23 = new Point(Float.parseFloat(tokens[10]), Float.parseFloat(tokens[11]), 1);
			
			System.out.println(buildResult(i));
		}
	}
	
	public static Point getPerpendicular(Point p1, Point p2, Point q){
		float px = p2.x - p1.x;
		float py = p2.y - p1.y;
		
		float dist2 = px * px + py*py;
		float u = ((q.x - p1.x) * px + (q.y - p1.y) * py) / dist2;
		
		return new Point(p1.x + u * px, p1.y + u * py, 1);
	}
	
	public static int ccw(Point p1, Point p2, Point p3) {
		float result = (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
		return result > 0 ? 1 : result < 0 ? -1 : 0;
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Point midP1 = getPerpendicular(p11, p12, p13);
		Point midP2 = getPerpendicular(p21, p22, p23);
		
		//help taken from classmate
		Point line1 = cross(p11, p12);
		line1 = cross(line1, new Point(0, 0, 1));
		line1.orthogonal();
		line1 = cross(p13, line1);
		
		Point line2 = cross(p21, p22);
		line2 = cross(line2, new Point(0, 0, 1));
		line2.orthogonal();
		line2 = cross(p23, line2);
		
		//Point line1 = cross(p13, midP1);
		//Point line2 = cross(p23, midP2);
		
		Point intersection = cross(line1, line2);
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		
		if (intersection.z == 0) {
			sb.append("strange");
			return sb;
		}
		
		intersection.normalise();
				
		if (intersection.y < midP1.y || intersection.y < midP2.y) {
			sb.append("strange");
			return sb;
		}
		
		sb.append(intersection.x);
		sb.append(" ");
		sb.append(intersection.y);
		
		return sb;
	}
}
