package sort;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sort.fastest.*;

public class SortTest {
	
	Sort sort = new MergeSort();
	int sz = 1000000;
	Integer[] array;

	@BeforeEach
	void setUp() throws Exception {
		
		this.array = new Integer[this.sz];
		for (int i = 0; i < this.sz; i++) {
			this.array[i] = (int) (Math.random() * sz);
		}
		
	}

	@Test
	void test() {
		sort.sort(array);
		assertTrue(sort.isSorted(array));
	}

}
