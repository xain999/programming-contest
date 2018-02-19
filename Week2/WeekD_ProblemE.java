import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class WeekD_ProblemE {
	static int n;
	static int m;
	static int enemy;
	static int[] allies;
	static int[] value;
	static Queue<Point> enemiesLeft;
	
	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		int testCasesCount = in.nextInt();
		
		for (int i = 1; i <= testCasesCount; i++) {
			
			readInput(in);
			System.out.println(buildResult(i));
		}
		
	}
	
	public static void readInput(Scanner in){
		enemiesLeft = new ArrayDeque<Point>();
		enemy = -1;
		
		n = in.nextInt();
		m = in.nextInt();
		int v, u;
		
		allies = new int[n];
		value = new int[n];
		Arrays.fill(allies, -1);
		Arrays.fill(value, 1);
		
		for (int i = 1; i <= m; i++) {
			
			String input = in.next();
			v = in.nextInt() - 1;
			u = in.nextInt() - 1;
			
			int find0 = find(0);
			int findU = find(u);
			int findV = find(v);
			
			if (input.equals("F")) {
				if (findU == find0 || findV == find0) {
					union(0, u);
					union(0, v);
				} else if (enemy != -1 && (find(enemy) == findU || find(enemy) == findV)) {
					union(enemy, u);
					union(enemy, v);
				} else {
					union(u, v);
				}
			} else {
				if (findU == find0) {
					if (enemy == -1)
						enemy = v;
					else
						union(enemy, v);
				} else if (findV == find0) {
					if (enemy == -1)
						enemy = u;
					else
						union(enemy, u);
				} else if (enemy != -1 && findU == find(enemy)) {
					union(0, v);
				} else if (enemy != -1 && findV == find(enemy)) {
					union(0, u);
				} else {
					enemiesLeft.add(new Point(u, v));
				}
			}
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		StringBuilder sb = new StringBuilder(12);
		
		sb.append("Case #");
		sb.append(caseNo);
		
		int i = 0;
		int size = enemiesLeft.size() * 4;
		
		while (!enemiesLeft.isEmpty() && i++ < size) {
			Point p = enemiesLeft.poll();
			
			int find0 = find(0);
			int findX = find(p.x);
			int findY = find(p.y);
			
			if (findX == find0) {
				if (enemy == -1)
					enemy = p.y;
				else
					union(enemy, p.y);
			} else if (findY == find0) {
				if (enemy == -1)
					enemy = p.x;
				else
					union(enemy, p.x);
			} else if (enemy != -1 && findX == find(enemy)) {
				union(0, p.y);
			} else if (enemy != -1 && findY == find(enemy)) {
				union(0, p.x);
			} else {
				enemiesLeft.add(p);
			}
		}
		
		if (value[find(0)] > n / 2)
			sb.append(": yes");
		else
			sb.append(": no");
		
		return sb;
	}
	
	public static void union(int a, int b) {
		if (a != b) {
			a = find(a);
			b = find(b);
			
			if (a > b) {
				allies[a] = b;
				value[b] += value[a];
				value[a] = 0;
			} else if (a < b) {
				allies[b] = a;
				value[a] += value[b];
				value[b] = 0;
			}
		}
	}
	
	public static int find(int a) {
		while (true) {
			if (allies[a] == -1 || allies[a] == a)
				return a;
			a = allies[a];
		}
	}
}
