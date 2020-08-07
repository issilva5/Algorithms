package comprees.burrows;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

	// apply Burrows-Wheeler transform,
	// reading from standard input and writing to standard output
	public static void transform() {

		String s = BinaryStdIn.readString();
		CircularSuffixArray cs = new CircularSuffixArray(s);

		int first = -1;
		for (int i = 0; first == -1; i++) {

			if (cs.index(i) == 0)
				first = i;

		}
		
		BinaryStdOut.write(first);
		int pos, L = s.length();
		for (int i = 0; i < L; i++) {

			pos = (cs.index(i) - 1 + L) % L;
			BinaryStdOut.write(s.charAt(pos));

		}
		
		BinaryStdOut.flush();

	}

	// apply Burrows-Wheeler inverse transform,
	// reading from standard input and writing to standard output
	public static void inverseTransform() {

		int first = BinaryStdIn.readInt();
		String s = BinaryStdIn.readString();
		
		char[] ord = s.toCharArray();
		int[] next = sort(ord);
		
		int pos = first;
		
		for (int i = 0; i < ord.length; i++) {
			
			BinaryStdOut.write(ord[pos]);
			pos = next[pos];
			
		}
		
		BinaryStdOut.flush();

	}

	private static int[] sort(char[] a) {

		int N = a.length;
		int R = 256;

		int[] ind = new int[N];
		int[] aux = new int[N];
		char[] auxC = new char[N];
		
		for (int i = 0; i < N; i++)
			ind[i] = i;
		
		int[] count = new int[R + 1];
		for (int i = 0; i < N; i++)
			count[a[i] + 1]++;
		
		for (int r = 0; r < R; r++)
			count[r + 1] += count[r];
		
		for (int i = 0; i < N; i++) {
			aux[count[a[i]]] = ind[i];
			auxC[count[a[i]]++] = a[i];
		}
		
		for (int i = 0; i < N; i++)
			a[i] = auxC[i];
		
		return aux;

	}

	// if args[0] is "-", apply Burrows-Wheeler transform
	// if args[0] is "+", apply Burrows-Wheeler inverse transform
	public static void main(String[] args) {

		if (args[0].equals("-"))
			BurrowsWheeler.transform();

		else if (args[0].equals("+"))
			BurrowsWheeler.inverseTransform();

	}

}