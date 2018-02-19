import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;


public class Week11_ProblemB {
	
	static int n, c;
	static int[] coinsAvailable;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//System.setIn(new FileInputStream("2.in"));	
	
		InputStreamReader r = new InputStreamReader ( System . in );
		BufferedReader in = new BufferedReader ( r );
		int testCasesCount = Integer.parseInt(in.readLine());
		String[] tokens;
		
		for (int i = 1; i <= testCasesCount; i++) {
			tokens = in.readLine().split(" ");
			c = Integer.parseInt(tokens[0]);
			n = Integer.parseInt(tokens[1]);
			
			coinsAvailable = new int[c];
			
			tokens = in.readLine().split(" ");
			
			int j = 0;
			for (String token : tokens) {
				coinsAvailable[j++] = Integer.parseInt(token);
			}
			
			System.out.println(buildResult(i));
			
			if (i != testCasesCount)
				in.readLine();
		}
		
	}
	
	public static StringBuilder buildResult(int caseNo) {
		
		int[] minCoins = new int[n + 1];		
		minCoins[0] = 0;
	    
		int[] prev = new int[n + 1];
		Arrays.fill(prev, -1);
		
		for(int val = 1; val < minCoins.length; val++){
			int min = Integer.MAX_VALUE;
			
			for(int j = 0; j < coinsAvailable.length; j++){
				if(coinsAvailable[j] <= val){
					if ((minCoins[val - coinsAvailable[j]] + 1) < min){
						min = minCoins[val - coinsAvailable[j]] + 1;
						prev[val] = val - coinsAvailable[j];
					} 
				}
			}
			minCoins[val] = min;
		}
		
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(c * 2);
		
		int currIndex = n;
		int lastIndex = prev[currIndex];
		int coin = -1;
		
		while (lastIndex != -1) {
			coin = currIndex - lastIndex;
			
			if (map.containsKey(coin)) {
				map.put(coin, map.get(coin) + 1);
			} else {
				map.put(coin, 1);
			}
			
			currIndex = lastIndex;
			lastIndex = prev[currIndex];
		}
		
		StringBuilder sb = new StringBuilder(12);
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(":");
		
		for (int i : coinsAvailable) {
			if (map.containsKey(i)) {
				sb.append(" " + map.get(i));
			} else {
				sb.append(" " + 0);
			}
		}
		
		return sb;
	}
}
