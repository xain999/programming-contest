import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;


public class Week4_ProblemD {
	static class Node {
		int i, j, cost, dym;
		Node jumpPos;
		Node(int i, int j, int cost, int dym) {
			this.i = i;
			this.j = j;
			this.cost = cost;
			this.dym = dym;
		}
	}
	
	static int w, h;
	static Node start, end;
	static int[][][] dist;
	static boolean[][] map;
	static PriorityQueue<Node> queue;
	
public static void main(String[] args) throws NumberFormatException, IOException{
		
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		
		for (int i = 1; i <= testCasesCount; i++) {
			
			readInput(in);
			System.out.print(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
		
	}
	
	public static void readInput(BufferedReader in) throws IOException{
		
		String line = in.readLine();
		String[] tokens = line.split(" ");
		
		w = Integer.parseInt(tokens[0]);
		h = Integer.parseInt(tokens[1]);
		
		map = new boolean[h][w];
		dist = new int[h][w][4];
		
		queue = new PriorityQueue<Node>((a, b) -> Integer.compare(a.cost, b.cost));
		
		for (int i = 0; i < h; i++) {
			line = in.readLine();
			
			for (int j = 0; j < w; j++) {
				if (line.charAt(j) == '*') {
					start = new Node(i, j, 1, 0);
					map[i][j] = true;
					dist[i][j][0] = 1;
					continue;
				}
				map[i][j] = line.charAt(j) == '_';
				
				if ((i == 0 || i == h - 1 || j == 0 || j == w - 1) && map[i][j])
					end = new Node(i, j, Integer.MAX_VALUE, 0);
			}
		}
		queue.add(start);
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Node node = null;
		Node newNode = null;
		int jumpCount = -1;
		int cost = 0;
		int i = 0;
		int j = 0;
		boolean hasResult = false;
		
		while (!queue.isEmpty()) {
			
			node = queue.remove();
			
			//reached
			if (node.i == end.i && node.j == end.j) {
				hasResult = true;
				break;
			}
			
			cost = node.cost + 1;
			
			//left
			{
				i = node.i;
				j = node.j - 1;
				if (j >= 0 && map[i][j]) {
					if ((dist[i][j][node.dym] == 0 || cost < dist[i][j][node.dym]) &&
						 (node.dym == 0 || ((dist[i][j][node.dym - 1] == 0 || cost < dist[i][j][node.dym - 1]) &&
						  (node.dym == 1 || ((dist[i][j][node.dym - 2] == 0 || cost < dist[i][j][node.dym - 2]) &&
						   (node.dym == 2 || dist[i][j][node.dym - 3] == 0 || cost < dist[i][j][node.dym - 3]))))))
					 {
						dist[i][j][node.dym] = cost;
						
						newNode = new Node(i, j, cost, node.dym);
						newNode.jumpPos = node.jumpPos;
						queue.add(newNode);
					 }
					
				} else if (node.dym < 3) {
					jumpCount = -2;
					j = -1;
					if (node.j + jumpCount >= 0 && map[node.i][node.j + jumpCount]) {
						j = node.j + jumpCount;
					}
					if (j != -1 &&
						(dist[i][j][node.dym] == 0 || cost < dist[i][j][node.dym]) &&
						 (node.dym == 0 || ((dist[i][j][node.dym - 1] == 0 || cost < dist[i][j][node.dym - 1]) &&
						  (node.dym == 1 || ((dist[i][j][node.dym - 2] == 0 || cost < dist[i][j][node.dym - 2]) &&
						   (node.dym == 2 || dist[i][j][node.dym - 3] == 0 || cost < dist[i][j][node.dym - 3])))))
						) {
						newNode = new Node(i, j, cost, node.dym + 1);
						newNode.jumpPos = new Node(node.i, node.j - 1, node.cost, node.dym);
						newNode.jumpPos.jumpPos = node.jumpPos;
						queue.add(newNode);
						
						dist[newNode.i][newNode.j][newNode.dym] = newNode.cost;
					}
				}
			}
			
			//right
			{
				i = node.i;
				j = node.j + 1;
				if (j < w && map[i][j]) {
					if ((dist[i][j][node.dym] == 0 || cost < dist[i][j][node.dym]) &&
						 (node.dym == 0 || ((dist[i][j][node.dym - 1] == 0 || cost < dist[i][j][node.dym - 1]) &&
						  (node.dym == 1 || ((dist[i][j][node.dym - 2] == 0 || cost < dist[i][j][node.dym - 2]) &&
						   (node.dym == 2 || dist[i][j][node.dym - 3] == 0 || cost < dist[i][j][node.dym - 3]))))))
					 {
						dist[i][j][node.dym] = cost;
						
						newNode = new Node(i, j, cost, node.dym);
						newNode.jumpPos = node.jumpPos;
						queue.add(newNode);
					 }
					
				} else if (node.dym < 3) {
					jumpCount = 2;
					j = -1;
					if (node.j + jumpCount < w && map[node.i][node.j + jumpCount]) {
						j = node.j + jumpCount;
					}
					if (j != -1 &&
						(dist[i][j][node.dym] == 0 || cost < dist[i][j][node.dym]) &&
						 (node.dym == 0 || ((dist[i][j][node.dym - 1] == 0 || cost < dist[i][j][node.dym - 1]) &&
						  (node.dym == 1 || ((dist[i][j][node.dym - 2] == 0 || cost < dist[i][j][node.dym - 2]) &&
						   (node.dym == 2 || dist[i][j][node.dym - 3] == 0 || cost < dist[i][j][node.dym - 3])))))
						) {
						newNode = new Node(i, j, cost, node.dym + 1);
						newNode.jumpPos = new Node(node.i, node.j + 1, node.cost, node.dym);
						newNode.jumpPos.jumpPos = node.jumpPos;
						queue.add(newNode);
						
						dist[newNode.i][newNode.j][newNode.dym] = newNode.cost;
					}
				}
			}
			
			//top
			{
				i = node.i - 1;
				j = node.j;
				if (i >= 0 && map[i][j]) {
					if ((dist[i][j][node.dym] == 0 || cost < dist[i][j][node.dym]) &&
						 (node.dym == 0 || ((dist[i][j][node.dym - 1] == 0 || cost < dist[i][j][node.dym - 1]) &&
						  (node.dym == 1 || ((dist[i][j][node.dym - 2] == 0 || cost < dist[i][j][node.dym - 2]) &&
						   (node.dym == 2 || dist[i][j][node.dym - 3] == 0 || cost < dist[i][j][node.dym - 3]))))))
					 {
						dist[i][j][node.dym] = cost;
						
						newNode = new Node(i, j, cost, node.dym);
						newNode.jumpPos = node.jumpPos;
						queue.add(newNode);
					 }
					
				} else if (node.dym < 3) {
					jumpCount = -2;
					i = -1;
					if (node.i + jumpCount >= 0 && map[node.i + jumpCount][node.j]) {
						i = node.i + jumpCount;
					}
					if (i != -1 &&
						(dist[i][j][node.dym] == 0 || cost < dist[i][j][node.dym]) &&
						 (node.dym == 0 || ((dist[i][j][node.dym - 1] == 0 || cost < dist[i][j][node.dym - 1]) &&
						  (node.dym == 1 || ((dist[i][j][node.dym - 2] == 0 || cost < dist[i][j][node.dym - 2]) &&
						   (node.dym == 2 || dist[i][j][node.dym - 3] == 0 || cost < dist[i][j][node.dym - 3])))))
						) {
						newNode = new Node(i, j, cost, node.dym + 1);
						newNode.jumpPos = new Node(node.i - 1, node.j, node.cost, node.dym);
						newNode.jumpPos.jumpPos = node.jumpPos;
						queue.add(newNode);
						
						dist[newNode.i][newNode.j][newNode.dym] = newNode.cost;
					}
				}
			}
			
			//bottom
			{
				i = node.i + 1;
				j = node.j;
				if (i < h && map[i][j]) {
					if ((dist[i][j][node.dym] == 0 || cost < dist[i][j][node.dym]) &&
						 (node.dym == 0 || ((dist[i][j][node.dym - 1] == 0 || cost < dist[i][j][node.dym - 1]) &&
						  (node.dym == 1 || ((dist[i][j][node.dym - 2] == 0 || cost < dist[i][j][node.dym - 2]) &&
						   (node.dym == 2 || dist[i][j][node.dym - 3] == 0 || cost < dist[i][j][node.dym - 3]))))))
					 {
						dist[i][j][node.dym] = cost;
						
						newNode = new Node(i, j, cost, node.dym);
						newNode.jumpPos = node.jumpPos;
						queue.add(newNode);
					 }
					
				} else if (node.dym < 3) {
					jumpCount = 2;
					i = -1;
					if (node.i + jumpCount < h && map[node.i + jumpCount][node.j]) {
						i = node.i + jumpCount;
					}
					if (i != -1 &&
						(dist[i][j][node.dym] == 0 || cost < dist[i][j][node.dym]) &&
						 (node.dym == 0 || ((dist[i][j][node.dym - 1] == 0 || cost < dist[i][j][node.dym - 1]) &&
						  (node.dym == 1 || ((dist[i][j][node.dym - 2] == 0 || cost < dist[i][j][node.dym - 2]) &&
						   (node.dym == 2 || dist[i][j][node.dym - 3] == 0 || cost < dist[i][j][node.dym - 3])))))
						) {
						newNode = new Node(i, j, cost, node.dym + 1);
						newNode.jumpPos = new Node(node.i + 1, node.j, node.cost, node.dym);
						newNode.jumpPos.jumpPos = node.jumpPos;
						queue.add(newNode);
						
						dist[newNode.i][newNode.j][newNode.dym] = newNode.cost;
					}
				}
			}
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(System.lineSeparator());
		
		String[] result = { "unused", "unused", "unused" };
		i = 0;
		
		while (hasResult && node != null && node.dym > 0) {
			result[i++] = (node.jumpPos.j + 1) + " " + (node.jumpPos.i + 1);
			node = node.jumpPos;
		}
		
		for (String s : result) {
			sb.append(s);
			sb.append(System.lineSeparator());
		}
		
		return sb;
	}
}
