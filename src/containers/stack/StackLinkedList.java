package containers.stack;

import java.util.Iterator;

public class StackLinkedList<Item> implements Stack<Item> {

	private class Node {
		
		public Item value;
		public Node next;
		
	}
	
	private Node top = null;
	
	@Override
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		 
		 private Node current = top;
		 
		 public boolean hasNext() { return current != null; }
		 
		 public void remove() { /* not supported */ }
		 
		 public Item next() {
			 Item item = current.value;
			 current = current.next;
			 return item;
		 }
		 
	 }

	@Override
	public void push(Item item) {
		
		if(this.isEmpty()) {
			this.top = new Node();
			this.top.value = item;
			this.top.next = null;
		} else {
			Node aux = new Node();
			aux.value = item;
			aux.next = this.top;
			this.top = aux;
		}
		
	}

	@Override
	public Item pop() {
		
		if(isEmpty())
			throw new UnsupportedOperationException("Stack is empty!");
		
		Item it = this.top.value;
		
		this.top = this.top.next;
		
		return it;
		
	}

	@Override
	public boolean isEmpty() {
		return this.top == null;
	}

	@Override
	public int size() {
		
		int s = 0;
		
		Iterator<Item> i = this.iterator();
		
		while(i.hasNext()) {
			s++;
			i.next();
		}
		
		return s;
	}

}
