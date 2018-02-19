import java.util.Scanner;

public class Week1_ProblemC {

	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in); 
		int n = reader.nextInt();
		reader.nextLine();
	
		for (int i = 1; i <= n; i++) {
			String line = reader.nextLine();
			String[] tokens = line.split("#", 2);

			int pos = Integer.parseInt(tokens[0]);
			
			String result = tokens[1].substring(pos) + tokens[1].substring(0, pos);		
			System.out.println("Case #" + i + ": " + result);
		}
		
	}

}

