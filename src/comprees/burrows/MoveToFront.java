package comprees.burrows;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
	
	// apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
    	
    	int R = 256;
    	char[] code = new char[R];
    	
    	for (int i = 0; i < R; i++)
    		code[i] = (char) i;
    	
    	while(!BinaryStdIn.isEmpty()) {
    		
    		char c = BinaryStdIn.readChar();
    		
    		int p = -1;
    		for (int i = 0; i < 256; i++)
    			if (c == code[i]) {
    				BinaryStdOut.write(i, 8);
    				p = i;
    			}
    		
    		for (int i = p; i > 0; i--) {
	    		char aux = code[i-1];
	    		code[i-1] = code[i];
	    		code[i] = aux;
    		}
    		
    	}
    	
    	BinaryStdOut.flush();
    	
    	
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	
    	int R = 256;
    	char[] code = new char[R];
    	
    	for (int i = 0; i < R; i++)
    		code[i] = (char) i;
    	
    	int c;
    	while(!BinaryStdIn.isEmpty()) {
    		
    		c = (int) BinaryStdIn.readChar();
    		BinaryStdOut.write(code[c]);
    		
    		for (int i = c; i > 0; i--) {
	    		char aux = code[i-1];
	    		code[i-1] = code[i];
	    		code[i] = aux;
    		}
    		
    	}
    	
    	BinaryStdOut.flush();
    	
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
    	
    	if (args[0].equals("-"))
			MoveToFront.encode();

		else if (args[0].equals("+"))
			MoveToFront.decode();
    	
    }

}
