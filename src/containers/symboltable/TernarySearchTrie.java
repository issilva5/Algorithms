package containers.symboltable;

import edu.princeton.cs.algs4.Queue;

public class TernarySearchTrie<Item> implements StringST<Item> {

	private Node root = null;

	private class Node {

		public Item value;
		public char character;
		public Node left;
		public Node middle;
		public Node right;
		
		public boolean isNill() {
			return (value == null) && (left == null) && (middle == null) && (right == null);
		}

	}

	public void put(String key, Item value) {

		root = this.put(key, value, root, 0);

	}

	private Node put(String key, Item value, Node node, int d) {

		char c = key.charAt(d);

		if (node == null) {

			node = new Node();
			node.character = c;

		}

		if (c < node.character) {

			node.left = this.put(key, value, node.left, d);

		} else if (c > node.character) {

			node.right = this.put(key, value, node.right, d);

		} else if (d < key.length() - 1) {

			node.middle = this.put(key, value, node.middle, d + 1);

		} else {

			node.value = value;
		}

		return node;

	}

	public Item get(String key) {

		Node aux = this.get(key, root, 0);
		return aux == null ? null : aux.value;

	}

	private Node get(String key, Node node, int d) {

		if (node == null)
			return null;

		char c = key.charAt(d);

		if (c < node.character) {

			return this.get(key, node.left, d);

		} else if (c > node.character) {

			return this.get(key, node.right, d);

		} else if (d < key.length() - 1) {

			return this.get(key, node.middle, d + 1);

		}

		return node;

	}
	
	public void delete(String key) {
		
		root = this.delete(key, root, 0);
		
	}

	private Node delete(String key, Node node, int d) {
		
		if (node == null)
			return null;

		char c = key.charAt(d);

		if (c < node.character) {

			node.left = this.delete(key, node.left, d);

		} else if (c > node.character) {

			node.right = this.delete(key, node.right, d);

		} else if (d < key.length() - 1) {

			node.middle = this.delete(key, node.middle, d + 1);

		} else {

			node.value = null;
			
		}
		
		if (node.isNill())
			return null;
		
		return node;
		
	}

	@Override
	public Iterable<String> keys() {
		
		Queue<String> keys = new Queue<>();
		collect("", root, keys);
		
		return keys;
	}

	private void collect(String prefix, Node node, Queue<String> keys) {
		
		if (node == null)
			return;
		
		if (node.value != null)
			keys.enqueue(prefix);
		
		collect(prefix, node.left, keys);
		collect(prefix + node.character, node.middle, keys);
		collect(prefix, node.right, keys);
		
	}

	@Override
	public Iterable<String> keysWithPrefix(String prefix) {
		
		Node x = this.get(prefix, root, 0);
		Queue<String> keys = new Queue<>();
		collect(prefix, x, keys);
		return keys;
	}

	@Override
	public String longestPrefixOf(String query) {
		
		int length = search(root, query, 0, 0);
		return query.substring(0, length);
		
	}
	
	private int search(Node node, String query, int d, int length) {
		
		if (node == null)
			return length;
		
		if (node.value != null)
			length = d;
		
		if (d == query.length())
			return length;
		
		char c = query.charAt(d);
		if (c < node.character)
			return search(node.left, query, d, length);
		
		if (c > node.character)
			return search(node.right, query, d, length);
		
		return search(node.middle, query, d + 1, length);
				
	}

}
