package containers.queue;

public interface Queue<Item> extends Iterable<Item> {

	public void enqueue(Item item);
	
	public Item dequeue();
	
	public Item front();
	
	public boolean isEmpty();
	
	public int size();
	
}
