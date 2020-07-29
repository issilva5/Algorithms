package containers.symboltable;

import edu.princeton.cs.algs4.Queue;

public class TrieST<Item> implements StringST<Item> {

	private static final int R = 256;
	private Node root = new Node();

	private static class Node {

		public Object value;
		public Node[] next = new Node[R];

		public boolean isNill() {

			if (value == null) {
				for (int i = 0; i < R; i++)
					if (next[i] != null)
						return false;
				return true;
			}

			return false;
		}

	}

	public void put(String key, Item value) {

		root = this.put(key, value, root, 0);

	}

	private Node put(String key, Item value, Node node, int d) {

		if (node == null)
			node = new Node();
		if (d == key.length()) {
			node.value = value;
			return node;
		}

		char c = key.charAt(d);
		node.next[c] = put(key, value, node.next[c], d + 1);
		return node;

	}

	@SuppressWarnings("unchecked")
	public Item get(String key) {

		Node n = this.get(key, root, 0);

		if (n == null)
			return null;

		return (Item) n.value;

	}

	private Node get(String key, Node node, int d) {

		if (node == null)
			return null;

		if (d == key.length())
			return node;

		char c = key.charAt(d);

		return get(key, node.next[c], d + 1);
	}

	public void delete(String key) {

		root = this.delete(key, root, 0);

	}

	private Node delete(String key, Node node, int d) {

		if (node == null)
			return null;

		if (d == key.length()) {
			node.value = null;

			if (node.isNill())
				return null;

			return node;
		}

		char c = key.charAt(d);
		node.next[c] = delete(key, node.next[c], d + 1);

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

		for (char i = 0; i < R; i++)
			collect(prefix + i, node.next[i], keys);

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
		return search(node.next[c], query, d + 1, length);
	}

}
