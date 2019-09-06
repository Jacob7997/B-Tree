import java.util.Scanner;

public class BMain {

	public static void main(String[] args) {
		
		
		Scanner scn = new Scanner(System.in);
		System.out.println("Eneter your T :");
		int t = scn.nextInt();
int num;
		BTree tree = new BTree(t);
		
		while(true) {
			int cas = scn.nextInt();
			switch (cas) {
			case 1:
				 num = scn.nextInt();
				tree.insert(num);
				System.out.println(tree.toString());
				break;
				
				
			case 2:
				 num = scn.nextInt();
				System.out.println(tree.search(num));


				break;

			default:
				break;
			}
		}









	}

}
