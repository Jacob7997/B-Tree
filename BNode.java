
public class BNode {
		
	int t;
	int count;
	int [] keys;
	BNode [] children;
	boolean leaf;
	
	
	public BNode(int t) {
		this.t = t;
		this.count = 0;
		this.keys = new int[2*t -1];
		this.children = new BNode[2*t];
		this.leaf = true;	
	}



	
	// Insert
	
	// Remove
	
	
	
	// Prints
	
	// Getters & Setters
	
	public int getCount() {
		return count;
	}




	public int getT() {
		return t;
	}




	public void setT(int t) {
		this.t = t;
	}




	public int[] getKeys() {
		return keys;
	}
	
	public int getKey(int i) {
		return keys[i];
	}




	public void setKeys(int[] keys) {
		this.keys = keys;
	}

	public void setKey(int j, int key) {
		keys[j] = key;
	}


	public BNode[] getChildren() {
		return children;
	}

	public BNode getChild(int i) {
		return children[i];
	}


	public void setChildren(BNode[] children) {
		this.children = children;
	}
	public void setChild(int i, BNode r) {
		children[i] = r;
	}



	public boolean isLeaf() {
		return leaf;
	}




	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}




	public void setCount(int count) {
		this.count = count;
	}










	

}
