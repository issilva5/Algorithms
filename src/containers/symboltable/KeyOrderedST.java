package containers.symboltable;

public interface KeyOrderedST<Key extends Comparable<Key>, Value> extends SymbolTable<Key, Value>{

	public Key min();
	
	public Key max();
	
	public Key floor(Key key);
	
	public Key ceiling(Key key);
	
	public Key select(int rank);
	
	public Iterable<Key> keys(Key start, Key end);
	
	public int rank(Key key);
	
	public int size(Key start, Key end);
	
	public void deleteMin();
	
	public void deleteMax();

}
