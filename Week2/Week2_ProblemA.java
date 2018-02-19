import java.util.Scanner;
import java.util.Arrays;

public class Week2_ProblemA {
	
	static int[] relations;
	static int[] marriageStatus;
	static int[] money;
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in); 
		int testCasesCount = scanner.nextInt();
		
		for (int i = 1; i <= testCasesCount; i++) {
			int ppl = scanner.nextInt();
			int relCount = scanner.nextInt();
			int marriageCount = scanner.nextInt();
			
			money = new int[ppl - 1];
			relations = new int[ppl];
			marriageStatus = new int[ppl];
			
			Arrays.fill(relations, -1);
			Arrays.fill(marriageStatus, -1);
			
			readInput(scanner, relCount, marriageCount);
					
			System.out.println(buildResult(i));
		}
		
	}
	
	public static void readInput(Scanner scanner, int relCount, int marriageCount) {
		
		for (int i = 0; i < money.length; i++)
			money[i] = scanner.nextInt();
		
		for (int i = 0; i < relCount; i++) {
			int d = scanner.nextInt() - 1;
			int e = scanner.nextInt() - 1;
			union(d, e);
		}
		
		for(int i = 0; i < marriageCount; i++) {
			int f = scanner.nextInt() - 1;
			marriageStatus[f] = scanner.nextInt() - 1;
			marriageStatus[marriageStatus[f]] = f;
			union(f, marriageStatus[f]);
		}
	}
	
	public static String buildResult(int caseNo) {
		
		StringBuilder sb = new StringBuilder(12);
		
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		
		int jacob = find(relations.length - 1);
		int mate = -1;
		
		for (int i = 0; i < relations.length - 1; i++) {
			if (marriageStatus[i] != -1 || jacob == find(i))
				continue;
			
			if (mate == -1 || money[mate] < money[i])
				mate = i;
		}
		
		if (mate == -1)
			sb.append("impossible");
		else
			sb.append(money[mate]);
		
		return sb.toString();
	}
	
	public static void union(int a, int b) {
		if (a != b)
			relations[find(a)] = find(b);
	}
	
	public static int find(int a) {
		while (true) {
			if (relations[a] == -1 || relations[a] == a)
				return a;
			a = relations[a];
		}
	}
}
