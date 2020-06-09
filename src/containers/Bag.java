package containers;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {

	private Stack<Item> bag;

	public Bag() {
		this.bag = new StackArray<>();
	}
	
	public void add(Item item) {
		this.bag.push(item);
	}

	public int size() {
		return this.bag.size();
	}
	
	@Override
	public Iterator<Item> iterator() {
		return this.bag.iterator();
	}
	
}
