package containers.queue;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class QueueArray<Item> implements Queue<Item> {

	private Item[] queue = (Item[]) new Object[1];
	private int head = 0;
	private int tail = 0;

	@Override
	public void enqueue(Item item) {
		
		if (this.size() == this.queue.length) {
			this.resizeArray(2 * this.queue.length);
		}           
		
		this.queue[this.tail] = item;
		this.tail++;
		this.tail %= this.queue.length;
		
	}

	@Override
	public Item dequeue() {
		
		if(this.isEmpty())
			throw new UnsupportedOperationException("Queue is empty!");
		
		Item it =  this.queue[this.head];
		this.queue[this.head] = null;
		this.head++;
		this.head %= this.queue.length;
		
		if(this.size() > 0 && this.size() == this.queue.length / 4)
			this.resizeArray(this.queue.length / 2);
		
		return it;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public int size() {
		
		int s;
		
		if(this.tail == this.head)
			s = this.queue[this.head] == null ? 0 : this.queue.length;
		else if(this.tail > this.head)
			s = this.tail - this.head;
		else
			s = this.queue.length - (this.head - this.tail);
		
		return s;
	}

	@Override
	public Item front() {
		return this.queue[this.head];
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new QueueIterator();
	}
	
	private void resizeArray(int length) {
		
		Item[] copy = (Item[]) new Object[length];
		int j = 0;
		
		if(this.tail > this.head) {
			
			for(int i = this.head; i < this.tail; i++, j++) {
				
				copy[j] = this.queue[i];
				
			}
			
		} else {
			
			for(int i = this.head; i < this.queue.length; i++, j++) {
				
				copy[j] = this.queue[i];
				
			}
			
			for(int i = 0; i < this.tail; i++, j++) {
				
				copy[j] = this.queue[i];
				
			}
			
		}
		
		this.head = 0;
		this.tail = j;
		this.queue = copy;
		
	}
	
	private class QueueIterator implements Iterator<Item> {

		private int current = head;
		private int id = 0;
		
		@Override
		public void remove() { /* not supported */ }
		
		@Override
		public boolean hasNext() {
			
			return id < size();
			
		}

		@Override
		public Item next() {
			
			Item i = queue[current];
			current++;
			current %= queue.length;
			id++;
			
			return i;
			
		}
		
	}

}
