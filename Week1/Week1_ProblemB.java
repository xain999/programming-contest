import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;

public class Week1_ProblemB {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in); 
		int n = input.nextInt();
		BigInteger c2 = new BigInteger("89875517873681764");
		
		for (int i = 1; i <= n; i++) {
			BigInteger mass = new BigInteger(input.next());
			BigInteger result = mass.multiply(c2);
			System.out.println("Case #" + i + ": " + result);
		}
	}	
	
}
