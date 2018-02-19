import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Week1_ProblemA {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(System.in)); 
		int n = Integer.parseInt(buffReader.readLine());

		for (int i = 1; i <= n; i++) {
			String name = buffReader.readLine();
			System.out.println("Case #" + i + ": Hello " + name + "!");
		}
	}
	
}
