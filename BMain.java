import java.util.Scanner;
import java.util.Arrays;

public class BMain {

	public static void main(String[] args) {

		Scanner scn = new Scanner(System.in);
		System.out.println("Eneter your T :");
		int t = scn.nextInt();
		int num;
		BTree tree = new BTree(t);

		tree.insert(5);
		tree.insert(10);
		tree.insert(30);
		tree.insert(40);
		tree.insert(50);
		tree.insert(60);
		System.out.println(tree.toString());

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

			default:
				break;
			}
		}

	}

}
