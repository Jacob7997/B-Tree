
public class BTree {

	int t;
	BNode root;

	public BTree(int t) {
		this.t = t;
		root = new BNode(t);
	}

	public BNode search(int k) {
		return searchRec(k, root);
	}

	public void insert(int k) {
		BNode r = root;
		if (r.getCount() == 2 * t - 1) {
			BNode s = new BNode(t);
			root = s;
			s.setLeaf(false);
			s.setCount(0);
			s.setChild(0, r);
			splitChild(s, 0);
			insertNonFull(s, k);
		} else
			insertNonFull(r, k);

	}

	public String toString() {
		String str = stringRec(root, "", 0);
		return str.substring(0, str.length()-2);

	}

	public void delete(int key ) {
		
	}
	
	
	
	
	
	private String stringRec(BNode node, String str, int height) {
	
		if (node.isLeaf())
			for (int i = 0; i < node.getCount(); i++)
				str = str + node.getKey(i) + "_" + height + " , ";

		else
			for (int i = 0; i <= node.getCount(); i++) {
				if (i < node.getKeys().length && node.getKey(i) != 0)
					str = stringRec(node.getChild(i), str, height + 1) + node.getKey(i) + "_" + height + " , ";
				else
					str = stringRec(node.getChild(i), str, height + 1);
			}

		return str;
	}

	private BNode searchRec(int k, BNode node) {

		int i = 0;

		while (i < node.getCount() && k > node.getKey(i))
			i++;
		if (i <= node.count && k == node.getKey(i))
			return node;
		else if (node.isLeaf())
			return null;
		else
			return searchRec(k, node.getChild(i));
	}

	private void insertNonFull(BNode node, int k) {
		int i = node.getCount();
		if (node.isLeaf()) {
			while (i > 0 && k < node.getKey(i - 1)) {
				node.setKey(i, node.getKey(i - 1));
				i = i - 1;
			}
			node.setKey(i, k);
			node.setCount(node.getCount() + 1);
		} else {
			while (i > 0 && k < node.getKey(i - 1)) {
				i = i - 1;
			}
			i = i + 1;
			if (node.getChild(i - 1).getCount() == 2 * t - 1) {
				splitChild(node, i - 1);
				if (k > node.getKey(i - 1)) {
					i = i + 1;
				}
			}
			insertNonFull(node.getChild(i - 1), k);

		}

	}
 
	private void splitChild(BNode node, int index) {
		BNode y = node.getChild(index);
		BNode z = new BNode(t);
		z.setLeaf(y.isLeaf());
		z.setCount(t - 1);
		for (int j = 0; j < t - 1; j++) { // transfer keys
			z.setKey(j, y.getKey(j + t));
			y.setKey((j + t), 0);
		}
		if (!y.isLeaf())
			for (int j = 0; j < t; j++) { // transfer children
				z.setChild(j, y.getChild(j + t));
			}
		for (int j = node.getCount() + 1; j > index + 1; j--) { // make room for new baby
			node.setChild(j, node.getChild(j - 1));
		}
		node.setChild(index + 1, z);
		for (int j = node.getCount(); j > index; j--) {
			node.setKey(j, node.getKey(j - 1));
		}
		node.setKey(index, y.getKey(t - 1));
		y.setKey(t - 1, 0);
		node.setCount(node.getCount() + 1);
		y.setCount(t - 1);
	}

}
