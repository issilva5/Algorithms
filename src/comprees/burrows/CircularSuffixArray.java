package comprees.burrows;
import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
	
	private class CircularSuffix implements Comparable<CircularSuffix> {

		public int index;
		
		public CircularSuffix(int index) {
			
			this.index = index;
			
		}
		
		public char charAt(int i) {
			
			int pos = (index + i) % CircularSuffixArray.this.original.length();
			return CircularSuffixArray.this.original.charAt(pos);
			
		}

		@Override
		public int compareTo(CircularSuffix that) {
			
			for (int i = 0; i < CircularSuffixArray.this.original.length(); i++) {
				
				if (this.charAt(i) > that.charAt(i))
					return 1;
				
				if (this.charAt(i) < that.charAt(i))
					return -1;
				
			}
			
			return 0;
			
		}
		
		@Override
		public String toString() {
			
			StringBuilder sb = new StringBuilder(CircularSuffixArray.this.original.substring(index));
			sb.append(CircularSuffixArray.this.original.substring(0, index));
			
			return sb.toString();
			
		}
		
	}
	
	private String original;
	private CircularSuffix[] array;
	private Integer[] indexes;
	
	// circular suffix array of s
    public CircularSuffixArray(String s) {
    	
    	if (s == null)
    		throw new IllegalArgumentException();
    	
    	original = s;
    	array = new CircularSuffix[s.length()];
    	indexes = new Integer[s.length()];
    	
    	for (int i = 0; i < this.length(); i++) {
    		array[i] = new CircularSuffix(i);
    		indexes[i] = i;
    	}
    	
    	Arrays.sort(indexes, new Comparator<Integer>() {

			@Override
			public int compare(Integer index1, Integer index2) {
				return array[index1].compareTo(array[index2]);
			}
    		
    	});

    }

    // length of s
    public int length() {
    	return original.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
    	
    	if (i < 0 || i >= this.length())
    		throw new IllegalArgumentException();
    	
    	return indexes[i];
    	
    }

    // unit testing (required)
    public static void main(String[] args) {
    	
    	String txt = args.length == 1 ? args[0] : "ABRACADABRA!";
    	
    	CircularSuffixArray cs = new CircularSuffixArray(txt);
    	
    	for (int i = 0; i < cs.length(); i++)
    		StdOut.println(cs.index(i));
    	
    }

}
