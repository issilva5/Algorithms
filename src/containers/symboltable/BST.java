package containers.symboltable;

import containers.queue.Queue;
import containers.queue.QueueArray;

public class BST<Key extends Comparable<Key>, Value> implements KeyOrderedST<Key, Value> {

	protected class Node {
		
		public Key key;
		public Value value;
		public Node left;
		public Node right;
		public int count;
		
		public Node(Key k, Value v) {
			
			this.key = k;
			this.value = v;
			this.left = null;
			this.right = null;
			this.count = 1;
			
		}
		
	}
	
	protected Node root;
	
	@Override
	public void put(Key k, Value v) {
		
		this.root = this.put(this.root, k, v);
		
	}

	private Node put(Node x, Key k, Value v) {
		
		if (x == null) return new Node(k, v);
		int cmp = k.compareTo(x.key);
		if (cmp < 0)
			x.left = put(x.left, k, v);
		else if (cmp > 0)
			x.right = put(x.right, k, v);
		else
			x.value = v;
		
		x.count = 1 + size(x.left) + size(x.right);
		return x;
		
	}

	private int size(Node x) {
		
		if (x == null)
			return 0;
		
		return x.count;
	}

	@Override
	public Value get(Key k) {
		return this.get(this.root, k);
	}

	private Value get(Node x, Key k) {
		
		if (x == null)
			return null;
		
		int cmp = k.compareTo(x.key);
		if (cmp < 0) return this.get(x.left, k);
		if (cmp > 0) return this.get(x.right, k);
		return x.value;
		
	}

	@Override
	public void delete(Key k) {
		
		this.root = this.delete(this.root, k);
		
	}

	private Node delete(Node x, Key k) {
		
		if (x == null)
			return null;
		
		int cmp = k.compareTo(x.key);
		if (cmp < 0) x.left = this.delete(x.left, k);
		else if (cmp > 0) x.right = this.delete(x.right, k);
		else {

			if (x.left == null)
				return x.right;
			
			if (x.right == null)
				return x.left;
			
			Node t = x;
			x = this.sucessor(t.right);
			x.right = this.deleteMin(t.right);
			x.left = t.left;
			
		}
		x.count = this.size(x.left) + this.size(x.right) + 1;
		return x;
		
	}

	private Node sucessor(Node x) {
		
		if (x.left == null)
			return x;
		
		return this.sucessor(x.left);
	}

	@Override
	public boolean contains(Key k) {
		return this.contains(this.root, k);
	}

	private boolean contains(Node x, Key k) {
		
		if (x == null)
			return false;
		
		int cmp = k.compareTo(x.key);
		if (cmp < 0) return this.contains(x.left, k);
		if (cmp > 0) return this.contains(x.right, k);
		return true;
		
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public int size() {
		return this.size(this.root);
	}

	@Override
	public Iterable<Key> keys() {
		return this.inOrder();
	}

	@Override
	public Key min() {
		return this.min(this.root);
	}

	private Key min(Node x) {
		
		if (x == null)
			return null;
		
		if (x.left != null) return this.min(x.left);
		return x.key;
		
	}

	@Override
	public Key max() {
		return this.max(this.root);
	}

	private Key max(Node x) {
		
		if (x == null)
			return null;
		
		if (x.right != null) return this.max(x.right);
		return x.key;
		
	}

	@Override
	public Key floor(Key key) {
		Node x = this.floor(this.root, key);
		if (x == null)
			return null;
		return x.key;
	}

	private Node floor(Node x, Key key) {
		
		if (x == null)
			return null;
		
		int cmp = key.compareTo(x.key);
		
		if (cmp == 0) return x;
		
		if (cmp < 0)
			return this.floor(x.left, key);
		
		Node t = this.floor(x.right, key);
		if (t == null) return x;
		return t;
		
	}

	@Override
	public Key ceiling(Key key) {
		Node x = this.ceiling(this.root, key);
		if (x == null)
			return null;
		return x.key;
	}

	private Node ceiling(Node x, Key key) {
		
		if (x == null)
			return null;
		
		int cmp = key.compareTo(x.key);
		
		if (cmp == 0) return x;
		
		if (cmp > 0) return this.ceiling(x.right, key);
		
		Node t = this.ceiling(x.left, key);
		if (t == null)
			return x;
		return t;
	}

	@Override
	public Key select(int rank) {
		return this.select(this.root, rank);
	}

	private Key select(Node x, int rank) {
		
		if (x == null)
			return null;
		
		if (this.rank(x.key) == rank)
			return x.key;
		
		if (rank > x.count)
			return this.select(x.right, rank);
		
		return this.select(x.left, rank);
	}

	@Override
	public Iterable<Key> keys(Key start, Key end) {
		
		Queue<Key> q = new QueueArray<>();
		
		for (Key k : this.inOrder())
			if (k.compareTo(start) >= 0 && k.compareTo(end) <= 0)
				q.enqueue(k);
		
		return q;
	}

	@Override
	public int rank(Key key) {
		return this.rank(this.root, key);
	}

	private int rank(Node x, Key key) {
		
		if (x == null) return 0;
		int cmp = key.compareTo(x.key);
		if (cmp < 0) return this.rank(x.left, key);
		else if (cmp > 0) return 1 + size(x.left) + this.rank(x.right, key);
		return size(x.left); 

	}

	@Override
	public int size(Key start, Key end) {
		
		if (start.compareTo(end) > 0) {
			Key aux = end;
			end = start;
			start = aux;
		}
		
		if (this.contains(end)) return this.rank(end) - this.rank(start) + 1;
		else return this.rank(end) - this.rank(start);
	}

	@Override
	public void deleteMin() {
		
		this.root = this.deleteMin(this.root);
		
	}

	private Node deleteMin(Node x) {
		
		if (x.left == null) return x.right;
		
		x.left = this.deleteMin(x.left);
		x.count = 1 + this.size(x.left) + this.size(x.right);
		
		return x;
		
	}

	@Override
	public void deleteMax() {
		
		this.root = this.deleteMax(this.root);
		
	}
	
	private Node deleteMax(Node x) {
		
		if (x.right == null) return x.left;
		
		x.right = this.deleteMin(x.right);
		x.count = 1 + this.size(x.left) + this.size(x.right);
		
		return x;
		
	}
	
	public Iterable<Key> inOrder() {
		
		Queue<Key> q = new QueueArray<>();
		this.inOrder(this.root, q);
		return q;
		
	}
	
	private void inOrder(Node x, Queue<Key> q) {
		
		if (x == null)
			return;
		
		this.inOrder(x.left, q);
		q.enqueue(x.key);
		this.inOrder(x.right, q);
		
	}

	public Iterable<Key> preOrder() {
		
		Queue<Key> q = new QueueArray<>();
		this.preOrder(this.root, q);
		return q;
		
	}
	
	private void preOrder(Node x, Queue<Key> q) {
		
		if (x == null)
			return;
		
		q.enqueue(x.key);
		this.preOrder(x.left, q);
		this.preOrder(x.right, q);
		
	}

	public Iterable<Key> postOrder() {
		
		Queue<Key> q = new QueueArray<>();
		this.postOrder(this.root, q);
		return q;
		
	}
	
	private void postOrder(Node x, Queue<Key> q) {
		
		if (x == null)
			return;
		
		this.postOrder(x.left, q);
		this.postOrder(x.right, q);
		q.enqueue(x.key);
		
	}

	public Iterable<Key> levelOrder() {
		
		Queue<Node> p = new QueueArray<>();
		Queue<Key> q = new QueueArray<>();
		
		p.enqueue(this.root);
		
		while (!p.isEmpty()) {
			
			Node top = p.dequeue();
			q.enqueue(top.key);
			if (top.left != null) p.enqueue(top.left);
			if (top.right != null) p.enqueue(top.right);
			
		}
		
		return q;
		
	}
	
	public static void main(String[] args) {
		
		BST<String, Integer> bst = new BST<>();
		bst.put("J", 1);
		bst.put("P", 26);
		bst.put("B", 10);
		bst.put("A", 2);
		
		System.out.println(bst.rank("B"));
		
		for (String k : bst.keys("A", "D"))
			System.out.println(k);
		System.out.println();
		
		for (String k : bst.inOrder())
			System.out.println(k.toString());
		System.out.println();
		
		for (String k : bst.preOrder())
			System.out.println(k.toString());
		System.out.println();
		
		for (String k : bst.postOrder())
			System.out.println(k.toString());
		System.out.println();
		
		for (String k : bst.levelOrder())
			System.out.println(k.toString());
			
		
	}

}
