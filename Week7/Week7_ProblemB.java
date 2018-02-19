import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class Week7_ProblemB {	
	public static class Point {
		int x, y;
		boolean done;
		LinkedList<Point> other;
		Point(int x, int y) {
			this.x = x;
			this.y = y;
			done = false;
			other = new LinkedList<Point>();
		}
		@Override
		public int hashCode() {
			return (x + 2001) * 1000 + (y + 2001);
		}
		@Override
		public boolean equals(Object obj) {
			return this.hashCode() == obj.hashCode();
		}
	}
	
	static int n;
	static Point[] left;
	static Point[] right;
	static Point impact;
	static HashMap<Point, Point> neighbour;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			neighbour = new HashMap<Point, Point>();
			tokens = in.readLine().split(" ");
			
			impact = new Point(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
			int sides = Integer.parseInt(tokens[2]);
			
			Point p1 = null;
			Point p2 = null;
			Point p3 = null;
			Point p4 = null;
			
			for (int j = 0; j < sides; j++) {
				tokens = in.readLine().split(" ");
				p1 = new Point(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
				p2 = new Point(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
				
				p3 = neighbour.get(p1);
				p4 = neighbour.get(p2);
				
				if (p3 == null) {
					p3 = p1;
					neighbour.put(p3, p3);
				}
				if (p4 == null) {
					p4 = p2;
					neighbour.put(p4, p4);
				}
				p3.other.add(p4);
				p4.other.add(p3);
			}
			
			System.out.println(buildResult(i, p3));
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static StringBuilder buildResult(int caseNo, Point start) {
		
		Polygon polygon = new Polygon();
		Stack<Point> stack = new Stack<Point>();
		stack.push(start);
		
		Point p = null;
		
		while (!stack.isEmpty()) {
			p = stack.pop();
			
			if (p.done == true)
				continue;
			
			p.done = true;
			polygon.addPoint(p.x, p.y);
			
			for (Point point : p.other) {
				stack.push(point);
			}
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
			
		if (polygon.contains(impact.x, impact.y))
			sb.append("jackpot");
		else
			sb.append("too bad");
		
		return sb;
	}
}
