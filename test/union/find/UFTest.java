package union.find;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UFTest {

	private UF uf;
	
	@Test
	void testQu() {
		
		this.uf = new QuickUnion(10);
		
		this.uf.union(1, 5);
		this.uf.union(2, 7);
		this.uf.union(8, 7);
		this.uf.union(4, 3);
		this.uf.union(1, 2);
		this.uf.union(0, 9);
		this.uf.union(9, 8);
		
		assertTrue(this.uf.connected(0, 2));
		assertTrue(this.uf.connected(0, 5));
		assertTrue(this.uf.connected(1, 2));
		assertTrue(this.uf.connected(0, 7));
		assertTrue(this.uf.connected(4, 3));
		
		assertFalse(this.uf.connected(6, 0));
		assertFalse(this.uf.connected(6, 5));
		assertFalse(this.uf.connected(5, 3));
		
	}
	
	@Test
	void testQf() {
		
		this.uf = new QuickFind(10);
		
		this.uf.union(1, 5);
		this.uf.union(2, 7);
		this.uf.union(8, 7);
		this.uf.union(4, 3);
		this.uf.union(1, 2);
		this.uf.union(0, 9);
		this.uf.union(9, 8);
		
		assertTrue(this.uf.connected(0, 2));
		assertTrue(this.uf.connected(0, 5));
		assertTrue(this.uf.connected(1, 2));
		assertTrue(this.uf.connected(0, 7));
		assertTrue(this.uf.connected(4, 3));
		
		assertFalse(this.uf.connected(6, 0));
		assertFalse(this.uf.connected(6, 5));
		assertFalse(this.uf.connected(5, 3));
		
	}

}
