import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

public class Week10_ProblemD {
	static int n;
	static int[][] connections;
	static Integer[] sort;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//System.setIn(new FileInputStream("2.in"));
		InputStreamReader r = new InputStreamReader (  System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			n = Integer.parseInt(tokens[0]);
			
			connections = new int[n][];
			sort = new Integer[n];
			
			for (int j = 0; j < n; j++) {
				sort[j] = j;
				
				tokens = in.readLine().split(" ");
				connections[j] = new int[Integer.parseInt(tokens[0])];
				
				for (int k = 0; k < connections[j].length; k++)
					connections[j][k] = Integer.parseInt(tokens[k + 1]) - 1;
			}
			
			System.out.println(buildResult(i));	
			
			if (i != testCasesCount)
				in.readLine();
		}
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		Arrays.sort(sort, (a, b) -> Integer.compare(connections[b].length, connections[a].length));
		
		HashSet<Integer> set = new HashSet<Integer>(n);
		
		int[] roads = new int[n];
		int total = 0;
		
		for (int i = 0; i < n; i++) {
			int city = sort[i];
			
			int outgoing = 0;
			for (int toCity : connections[city]) {
				if (!set.contains(toCity))
					outgoing++;
			}
			
			if (outgoing > roads[city]) {
				total -= roads[city];
				total += outgoing;
				roads[city] = 0;
				
				for (int toCity : connections[city]) {
					if (!set.contains(toCity))
						roads[toCity]++;
				}
				set.add(city);
			}
		}
		
		Integer[] result = set.toArray(new Integer[set.size()]);
		
		Arrays.sort(result);
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		sb.append(System.lineSeparator());
		
		for (Integer i : result)
			sb.append((i + 1) + " ");
		
		return sb;
	}
}
