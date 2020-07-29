package containers.symboltable;

public interface StringST<Item> {
	
	public void put(String key, Item value);
	
	public Item get(String key);
	
	public void delete(String key);
	
	public Iterable<String> keys();
	
	public Iterable<String> keysWithPrefix(String prefix);
		
	public String longestPrefixOf(String s);

}
