package containers.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private class Node {
		
		Item value;
		Node next;
		Node previous;
		
	}
	
	private Node front;
	private Node back;
	private int length;
	
    // construct an empty deque
    public Deque() {
    	
    	this.front = null;
    	this.back = null;
    	this.length = 0;
    	
    }

    // is the deque empty?
    public boolean isEmpty() {
		return this.size() == 0;
    	
    }

    // return the number of items on the deque
    public int size() {
		return this.length;
    	
    }

    // add the item to the front
    public void addFirst(Item item) {
    	
    	if (item == null)
    		throw new IllegalArgumentException();
    	
    	Node aux = new Node();
    	aux.value = item;
    	aux.next = this.front;
    	aux.previous = null;
    	
    	if (this.front != null)
    		this.front.previous = aux;
    	
    	this.front = aux;
    	
    	if (this.back == null)
    		this.back = this.front;
    	
    	this.length++;
    	
    }

    // add the item to the back
    public void addLast(Item item) {
    	
    	if (item == null)
    		throw new IllegalArgumentException();
    	
    	Node aux = new Node();
    	aux.value = item;
    	aux.next = null;
    	aux.previous = this.back;
    	
    	if (this.back != null)
    		this.back.next = aux;
    	
    	this.back = aux;
    	
    	if (this.front == null)
    		this.front = this.back;
    	
    	this.length++;
    	
    }

    // remove and return the item from the front
    public Item removeFirst() {
		
    	if (this.isEmpty())
    		throw new NoSuchElementException();
    	
    	Item i = this.front.value;
    	this.front = this.front.next;
    	this.length--;
    	
    	if (this.isEmpty()) {
    		this.back = this.front;
    	} else {
    		this.front.previous = null;
    	}
    	
    	return i;
    	
    }

    // remove and return the item from the back
    public Item removeLast() {
    	
    	if (this.isEmpty())
    		throw new NoSuchElementException();
    	
    	Item i = this.back.value;
    	this.back = this.back.previous;
    	this.length--;
    	
    	if (this.isEmpty()) {
    		this.front = this.back;
    	} else {
    		this.back.next = null;
    	}
    	
		return i;
    	
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
		return new DequeIterator();
    	
    }
    
    private class DequeIterator implements Iterator<Item> {

    	private Node current = front;
    	
		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public Item next() {
			
			if (this.current == null)
		    	throw new NoSuchElementException();
			
			Item i = this.current.value;
			this.current = this.current.next;
			return i;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
    	
    }

}
