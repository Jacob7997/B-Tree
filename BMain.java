import java.util.Scanner;
import java.util.Arrays;

public class BMain {

	public static void main(String[] args) {

		Scanner scn = new Scanner(System.in);
		System.out.println("Eneter your T :");
		int t = scn.nextInt();
		int num;
		BTree tree = new BTree(t);
		System.out.println("Press : 1 Insertion | 2 Search | 3 Deletion ");
		System.out.println("Following with the key you want that action.");
		System.out.println();


		while (true) {
			int cas = scn.nextInt();
			switch (cas) {
			case 1:
				num = scn.nextInt();
				tree.insert(num);
				System.out.println(tree.toString());
				break;

			case 2:
				num = scn.nextInt();
				System.out.println(Arrays.toString(tree.search(num).getKeys()));
				break;

			case 3:
				num = scn.nextInt();
				System.out.println(tree.delete(num));
				 System.out.println(tree.toString());
				break;
				
			case 4:
				num = scn.nextInt();
				System.out.println(Arrays.toString(tree.findPredecessorRec(num, tree.search(num)).getKeys()));
				break;
			case 5:
				num = scn.nextInt();
				System.out.println(Arrays.toString(tree.findSuccessorRec(num, tree.search(num)).getKeys()));
				break;

			default:
				break;
			}
		}

	}

}
