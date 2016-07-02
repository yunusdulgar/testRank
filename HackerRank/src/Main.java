import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		int n = in.nextInt();
		
		int prisonCount = in.nextInt();
		int sweetsCount = in.nextInt();
		int startId = in.nextInt();
		
		int[][] prisons = new int[prisonCount][1];

		for (int i = 0; i < prisons.length; i++) {
			prisons[i][0] = i+1 ;
		}
		
		int left = prisonCount%sweetsCount;

		 for (int i = 0; i < prisons.length; i++) {
			System.out.println("heyo");
		}
		
	}

}
