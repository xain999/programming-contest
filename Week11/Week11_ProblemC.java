import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Node {
	int iq, weight;
	Node (int iq, int weight) {
		this.iq = iq;
		this.weight = weight;
	}
}

public class Week11_ProblemC {
	
	static int n;
	static Node[] nodes;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//System.setIn(new FileInputStream("2.in"));	
	
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			n = Integer.parseInt(in.readLine());
			
			nodes = new Node[n];
			
			for (int j = 0; j < n; j++) {
				tokens = in.readLine().split(" ");
				nodes[j] = new Node(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
			}
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
		
	}
	
	public static StringBuilder buildResult(int caseNo) {		
		
		Arrays.sort(nodes, (a, b) -> { int res = Integer.compare(a.iq, b.iq); return res == 0 ? Integer.compare(b.weight, a.weight) : res; });
		
		int[] currSum = new int[nodes.length];
		currSum[0] = 1;
		int maxSum = 1;
		
//		for (int x = 0; x < nodes.length; x++) {
//			System.out.println(nodes[x].iq + "\t" + nodes[x].weight);
//		}
		
		for(int x = 1; x < nodes.length; x++) {
			currSum[x] = 1;
			
			for (int y = 0; y < x; y++) {
				if (nodes[x].weight < nodes[y].weight && currSum[x] < currSum[y] + 1) {
					currSum[x] = currSum[y] + 1;
					
					if (currSum[x] > maxSum) {
						maxSum = currSum[x];
						break;
					}
				}
			}
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(maxSum);
		
		return sb;
	}
}
