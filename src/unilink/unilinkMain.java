package unilink;
import java.util.Scanner;

public class unilinkMain {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("*****UniLink System*****");
		System.out.println("1. Log in");
		System.out.println("2. Quit");
		System.out.print("Enter your choice: ");
		int choice = sc.nextInt();
		
		sc.close();
	}
}
