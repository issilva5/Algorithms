package containers.symboltable;

public class RedBlackBST<Key extends Comparable<Key>, Value> extends BST<Key, Value> {

	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private RBNode root;
	
	private class RBNode extends Node {

		public boolean color; //color of parent link
		public RBNode left;
		public RBNode right;
		
		public RBNode(Key k, Value v) {
			super(k, v);
		}
		
	}
	
	private boolean isRed(RBNode x) {
		
		if (x == null) return false;
		return x.color == RED;
		
	}
	
	private RBNode rotateLeft(RBNode h) {
		
		assert this.isRed(h.right);
		RBNode x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		return x;
		
	}
	
	private RBNode rotateRight(RBNode h) {
		
		assert this.isRed(h.left);
		RBNode x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		return x;
		
	}
	
	private void flipColors(RBNode h) {
		
		assert !this.isRed(h);
		assert this.isRed(h.right);
		assert this.isRed(h.left);
		
		h.color = RED;
		h.right.color = BLACK;
		h.left.color = BLACK;
		
	}
	
	@Override
	public void put(Key k, Value v) {
		
		this.root = this.put(this.root, k, v);
		
	}
	
	private RBNode put(RBNode x, Key k, Value v) {
		
		if (x == null) {
			RBNode h = new RBNode(k, v);
			h.color = BLACK;
			return h;
		}
		
		int cmp = k.compareTo(x.key);
		if (cmp < 0) x.left = this.put(x, k, v);
		else if (cmp > 0) x.right = this.put(x, k, v);
		else x.value = v;

		if (this.isRed(x.right) && !this.isRed(x.left)) x = this.rotateLeft(x);
		if (this.isRed(x.left) && this.isRed(x.left.left)) x = this.rotateRight(x);
		if (this.isRed(x.left) && this.isRed(x.right)) this.flipColors(x);
		
		return x;
		
	}
	
}
