package containers;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] rdqueue;
	private int id;
	
    // construct an empty randomized queue
    public RandomizedQueue() {
    	
    	this.rdqueue = (Item[]) new Object[1];
    	this.id = 0;
    	
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
		return this.size() == 0;
    	
    }

    // return the number of items on the randomized queue
    public int size() {
		return this.id;
    	
    }

    // add the item
    public void enqueue(Item item) {
    	
    	if (item == null)
    		throw new IllegalArgumentException();
    	
    	if (this.id == this.rdqueue.length)
    		this.resize(2 * this.rdqueue.length);
    	
    	this.rdqueue[this.id++] = item;
    	
    }

	// remove and return a random item
    public Item dequeue() {
    	
    	if (this.isEmpty())
    		throw new NoSuchElementException();
    	
    	int v = StdRandom.uniform(this.id);
    	Item aux = this.rdqueue[v];
    	this.rdqueue[v] = this.rdqueue[--this.id];
    	
    	if (this.id > 0 && this.id == this.rdqueue.length / 4)
    		this.resize(this.rdqueue.length / 2);
    	
    	return aux;
    	
    }
    
    private void resize(int size) {
		
		Item[] copy = (Item[]) new Object[size];
    	for(int i = 0; i < this.id; i++)
    		copy[i] = this.rdqueue[i];
    	
    	this.rdqueue = copy;
		
	}

	// return a random item (but do not remove it)
    public Item sample() {
    	
    	if (this.isEmpty())
    		throw new NoSuchElementException();

		return this.rdqueue[StdRandom.uniform(this.id)];
    	
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
		return new RQIterator();
    	
    }
    
    private class RQIterator implements Iterator<Item> {

    	private int size = id;
    	private Item[] copy = (Item[]) new Object[size];
    	
    	public RQIterator() {
			
    		for (int i = 0; i < size; i++) copy[i] = rdqueue[i];
    		
		}
    	
		@Override
		public boolean hasNext() {
			return size > 0;
		}

		@Override
		public Item next() {
			
			if (size <= 0)
	    		throw new NoSuchElementException();
			
			int v = StdRandom.uniform(this.size);
	    	
			Item aux = copy[v];
	    	copy[v] = copy[--this.size];
			
	    	return aux;
	    	

		}
    	
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
    	
    }

}