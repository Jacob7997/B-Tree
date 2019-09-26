
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
		return str.substring(0, str.length() - 2);

	}

	public boolean delete(int key) {
		BNode node = search(key);
		if (node == null)
			return false;
		else {
			if (node == root) {
				if (node.isLeaf())
					deleteSimpleLeaf(node, key);
				else
					deleteRoot(node, key);
			} else {
				BNode parent = searchParentRec(key, root, null);
				if (node.isLeaf())
					deleteLeaf(node, parent, key);
				else
					deleteBranch(node, parent, key);
			}
			checkRoot();
		}
		return true;
	}

	private void checkRoot() {
		if (root.getCount() < t - 1) {
			root = root.getChild(0);
		}
	}

	// Search helper functions
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

	// Insert helper functions
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

	// ToString helper functions
	private String stringRec(BNode node, String str, int height) {

		if (node != null && node.isLeaf())
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

	public BNode searchParentRec(int key, BNode node, BNode parent) {
		checkCapacity(node, parent);
		for (int i = 0; i <= node.getCount(); i++) {
			if (node.getKey(i) > key) {
				return searchParentRec(key, node.getChild(i), node);
			} else if (i == node.getCount())
				return searchParentRec(key, node.getChild(node.getCount()), node);
			else if (node.getKey(i) == key)
				return parent;
		}
		return parent;
	}

	private void checkCapacity(BNode node, BNode parent) {
		if (node.getCount() == t - 1 && node != root) {
			int index = getNumChild(node, parent);
			if (index != 0 && parent.getChild(index - 1).getCount() >= t) // shift right
				shiftingRight(parent.getChild(index - 1), parent, node, index);

			else if (index != parent.getCount() && parent.getChild(index + 1).getCount() >= t) // shift left
				shiftingLeft(node, parent, parent.getChild(index + 1), index);

			else if (index != parent.getCount()) { // merge left
				merge(node, parent, parent.getChild(index + 1), index);
			} else { // merge right
				merge(parent.getChild(index - 1), parent, node, index);

			}

		}
	}

	private void shiftingRight(BNode brother, BNode parent, BNode node, int index) {
		shiftKeysRight(node);
		node.setKey(0, parent.getKey(index - 1));
		node.setCount(node.getCount() + 1);
		shiftChildsRight(node);
		node.setChild(0, brother.getChild(brother.getCount()));

		parent.setKey(index - 1, brother.getKey(brother.getCount() - 1));
		brother.setKey(brother.getCount() - 1, 0);
		brother.setCount(brother.getCount() - 1);

	}

	private void shiftChildsRight(BNode node) {
		for (int i = node.getCount() + 1; i > 0; i--) {
			node.setChild(i, node.getChild(i - 1));
		}
		node.setChild(0, null);
	}

	private void shiftKeysRight(BNode node) {
		for (int i = node.getCount(); i > 0; i--) {
			node.setKey(i, node.getKey(i - 1));
		}
		node.setKey(0, 0);
	}

	private void shiftingLeft(BNode node, BNode parent, BNode brother, int index) {
		node.setKey(node.getCount(), parent.getKey(index));
		node.setCount(node.getCount() + 1);
		node.setChild(node.getCount(), brother.getChild(0));

		parent.setKey(index, brother.getKey(0));
		shiftKeys(brother, 0);
		shiftChilds(brother, 0);

	}

	private void merge(BNode node, BNode parent, BNode brother, int index) {
		node.setKey(node.getCount(), parent.getKey(index));
		node.setCount(node.getCount() + 1);

		for (int i = 0; i < t - 1; i++) {
			node.setKey(node.getCount() + i, brother.getKey(i));
			node.setChild(node.getCount() + i, brother.getChild(i));
		}
		node.setChild((2 * t) - 1, brother.getChild(t - 1));
		shiftChilds(parent, index + 1);
		shiftKeys(parent, index);

		node.setCount((2 * t) - 1);

	}

	private int getNumChild(BNode node, BNode parent) {
		for (int i = 0; i <= parent.getCount(); i++) {
			if (parent.getChild(i) == node) {
				return i;
			}
		}
		return -1;
	}

	private void deleteRoot(BNode node, int key) {
	}

	private void deleteSimpleLeaf(BNode node, int key) {
		for (int i = 0; i < node.getCount(); i++) {
			if (node.getKey(i) == key) {
				node.setKey(i, 0);
				shiftKeys(node, i);
			}
		}

	}

	private void deleteBranch(BNode node, BNode parent, int key) {
		// TODO Auto-generated method stub

	}

	private void deleteLeaf(BNode node, BNode parent, int key) {
		if (node.getCount() == t - 1)
			fixNodeRec(root, null, key);
		deleteSimpleLeaf(node, key);
	}

	private BNode fixNodeRec(BNode node, BNode parent, int key) {
		checkCapacity(node, parent);
		int i = 0;
		while (i < node.getCount() && key > node.getKey(i))
			i++;
		if (i <= node.count && key == node.getKey(i))
			return node;
		else if (node.isLeaf())
			return null;
		else
			return fixNodeRec(node.getChild(i), node, key);
	}

	private void shiftKeys(BNode node, int i) {
		for (int j = i; j < node.getCount() - 1; j++) {
			node.setKey(j, node.getKey(j + 1));
		}
		node.setKey(node.getCount() - 1, 0);
		node.setCount(node.getCount() - 1);
	}

	private void shiftChilds(BNode node, int i) {
		for (int j = i; j < node.getCount(); j++) {
			node.setChild(j, node.getChild(j + 1));
		}
		node.setChild(node.getCount(), null);
	}

	public BNode getRoot() {
		return root;
	}

}
