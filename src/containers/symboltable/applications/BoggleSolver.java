package containers.symboltable.applications;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

public class BoggleSolver {

	private TST<Integer> dictionary;

	// Initializes the data structure using the given array of strings as the
	// dictionary.
	// (You can assume each word in the dictionary contains only the uppercase
	// letters A through Z.)
	public BoggleSolver(String[] dictionary) {

		this.dictionary = new TST<>();

		for (String s : dictionary) {

			if (s.length() >= 3)
				this.dictionary.put(s, computeScore(s));

		}

	}

	private int computeScore(String s) {

		int score = 0;
		int L = s.length();

		if (L == 3 || L == 4)
			score = 1;
		else if (L == 5)
			score = 2;
		else if (L == 6)
			score = 3;
		else if (L == 7)
			score = 5;
		else
			score = 11;

		return score;

	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {

		Set<String> bag = new TreeSet<>();
		int n = board.rows();
		int m = board.cols();

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				searchAll(i, j, "" + getLetter(i, j, board), bag, board, new boolean[n][m]);
			}

		return bag;
	}
	
	private boolean[][] copy(boolean[][] array) {
		
		int n = array.length;
		int m = array[0].length;
		
		boolean[][] aux = new boolean[n][m];
		for (int k = 0; k < n; k++)
			for (int l = 0; l < m; l++)
				aux[k][l] = array[k][l];
		
		return aux;
		
	}

	private String getLetter(int i, int j, BoggleBoard board) {
		
		char c = board.getLetter(i, j);
		
		return c == 'Q' ? "QU" : (c + "");
	}
	
	private boolean hasKeysWithPrefix(String text) {
		Iterable<String> it = dictionary.keysWithPrefix(text);
		
		return ((Queue<String>) it).size() != 0;
	}
	
	private void searchAll(int i, int j, String text, Set<String> bag, BoggleBoard board, boolean[][] visited) {
		
		if (visited[i][j])
			return;

		visited[i][j] = true;

		if (text.length() >= 3)
			if (dictionary.get(text) != null) bag.add(text);
			else if (!hasKeysWithPrefix(text)) return;

		int n = board.rows();
		int m = board.cols();

		if (j + 1 < m) {

			searchAll(i, j + 1, text + getLetter(i, j + 1, board), bag, board, copy(visited));
			
			if (i + 1 < n)
				searchAll(i + 1, j + 1, text + getLetter(i + 1, j + 1, board), bag, board, copy(visited));
			
			if (i - 1 > -1)
				searchAll(i - 1, j + 1, text + getLetter(i - 1, j + 1, board), bag, board, copy(visited));
			
		}

		if (j - 1 > -1) {
			
			searchAll(i, j - 1, text + getLetter(i, j - 1, board), bag, board, copy(visited));

			if (i + 1 < n)
				searchAll(i + 1, j - 1, text + getLetter(i + 1, j - 1, board), bag, board, copy(visited));
			
			if (i - 1 > -1)
				searchAll(i - 1, j - 1, text + getLetter(i - 1, j - 1, board), bag, board, copy(visited));
			
		}

		if (i + 1 < n)
			searchAll(i + 1, j, text + getLetter(i + 1, j, board), bag, board, copy(visited));
		
		if (i - 1 > -1)
			searchAll(i - 1, j, text + getLetter(i - 1, j, board), bag, board, copy(visited));
		
	}

	// Returns the score of the given word if it is in the dictionary, zero
	// otherwise.
	// (You can assume the word contains only the uppercase letters A through Z.)
	public int scoreOf(String word) {

		Integer i = dictionary.get(word);

		return i == null ? 0 : i;
	}

	public static void main(String[] args) {

		String dictF = "boggleTest/dictionary-algs4.txt";
		String boardF = "boggleTest/board-q.txt";
		In in = new In(dictF);
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);
		BoggleBoard board = new BoggleBoard(boardF);

		StdOut.println(board);

		int score = 0;
		for (String word : solver.getAllValidWords(board)) {
			StdOut.println(word);
			score += solver.scoreOf(word);
		}
		StdOut.println("Score = " + score);

	}

}