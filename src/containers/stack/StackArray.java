package containers.stack;

import java.util.Iterator;

public class StackArray<Item> implements Stack<Item> {
	
	@SuppressWarnings("unchecked")
	private Item[] stack = (Item[]) new Object[1];
	private int id = 0;
	
	@Override
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		 
		 private int current = id;
		 
		 public boolean hasNext() { return current > 0; }
		 
		 public void remove() { /* not supported */ }
		 
		 public Item next() {
			 return stack[--current];
		 }
		 
	 }

	@Override
	public void push(Item item) {
		
		if (this.stack.length == id) {
			
			this.resizeArray(2 * this.stack.length);
			
		}
		
		this.stack[this.id++] = item;
		
	}

	@SuppressWarnings("unchecked")
	private void resizeArray(int length) {
		
		Item[] copy = (Item[]) new Object[length];
		
		for (int i = 0; i < this.id; i++) {
			copy[i] = this.stack[i];
			
		}
		
		this.stack = copy;
		
	}

	@Override
	public Item pop() {
		
		if(this.isEmpty())
			throw new UnsupportedOperationException("Stack is empty!");
		
		Item it = this.stack[--this.id];
		this.stack[this.id] = null;
		
		if (this.size() > 0 && this.size() == this.stack.length / 4) {
			
			this.resizeArray(this.stack.length / 2);
			
		}
		
		return it;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public int size() {
		return this.id;
	}

	@Override
	public Item peek() {
		return this.stack[this.id - 1];
	}

}
