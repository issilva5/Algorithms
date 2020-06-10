package containers.queue;

import java.util.Iterator;

public class QueueLinkedList<Item> implements Queue<Item> {

	private Node head = null;
	private Node tail = null;
	
	@Override
	public Iterator<Item> iterator() {
		return new QueueIterator();
	}

	@Override
	public void enqueue(Item item) {
		
		if (this.isEmpty()) {
			
			this.head = new Node();
			this.head.value = item;
			this.head.next = null;
			this.tail = this.head;
			
		}
		
		Node aux = new Node();
		aux.value = item;
		aux.next = null;
		this.tail.next = aux;
		this.tail = aux;
		
	}

	@Override
	public Item dequeue() {

		if (this.isEmpty()) {
			throw new UnsupportedOperationException("Queue is empty!");
		}
		
		Item i = this.front();
		this.head = this.head.next;
		
		if (this.isEmpty()) {
			this.tail = null;
		}
		
		return i;
	}

	@Override
	public Item front() {
		return this.head.value;
	}

	@Override
	public boolean isEmpty() {
		return this.head == null;
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
	
	private class Node {
		
		public Item value;
		public Node next;
		
	}
	
	private class QueueIterator implements Iterator<Item> {
		 
		 private Node current = head;
		 
		 public boolean hasNext() { return current != null; }
		 
		 public void remove() { /* not supported */ }
		 
		 public Item next() {
			 Item item = current.value;
			 current = current.next;
			 return item;
		 }
		 
	 }
	
}
