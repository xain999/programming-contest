import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;

public class Week1_ProblemD {
	
	public static void main1(String[] args) {
		
		Scanner scanner = new Scanner(System.in); 
		int testCasesCount = scanner.nextInt();
		
		for (int i = 1; i <= testCasesCount; i++) {
			if (i != 1) {
				scanner.nextLine();
				//scanner.nextLine();
			}
			
			int ppl = scanner.nextInt();
			int relCount = scanner.nextInt();
			int marriageCount = scanner.nextInt();
			
			int[] money = new int[ppl - 1];
			List<Integer>[] relations = new LinkedList[ppl];
			int[] marriageStatus = new int[ppl];
			
			readInput(scanner, money, relations, relCount, marriageStatus, marriageCount);
					
			System.out.print(buildResult(i, money, relations, marriageStatus));
		}
		
	}
	
	public static void readInput(Scanner scanner, int[] money, List<Integer>[] relations, int relCount,
			int[] marriageStatus, int marriageCount) {
		
		for (int i = 0; i < money.length; i++)
			money[i] = scanner.nextInt();
		
		for (int i = 0; i < relCount; i++) {
			int d = scanner.nextInt();
			int e = scanner.nextInt();
			
			if (relations[d] == null)
				relations[d] = new LinkedList<Integer>();
			if (relations[e] == null)
				relations[e] = new LinkedList<Integer>();
			
			relations[d].add(e);
			relations[e].add(d);
		}
		
		for(int i = 0; i < marriageCount; i++) {
			int f = scanner.nextInt();
			marriageStatus[f] = scanner.nextInt();
			marriageStatus[marriageStatus[f]] = f;
		}
	}
	
	public static String buildResult(int caseNo, int[] money, List<Integer>[] relations, 
			int[] marriageStatus) {
		
		StringBuilder sb = new StringBuilder(12);
		
		sb.append("Case #");
		sb.append(caseNo);
		sb.append(": ");
		
		int person = relations.length - 1;
		int mate = -1;
		//int mateValue = -1;
		
		for (int i = 0; i < person; i++) {
			if (marriageStatus[i] != 0 || relations[person].contains(i))
				continue;
			
			//check for spouse relations here		
			//for (int j : relations[person])
			//	if (marriage)
			
			//int val = relations[i].size();

			if (mate != -1 && money[mate] < money[i])
				mate = i;
		}
		
		if (mate == -1)
			sb.append("impossible");
		else
			sb.append(mate);
		
		return sb.toString();
	}
}
