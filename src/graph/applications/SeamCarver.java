package graph.applications;
import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

	private Picture current;
	private int[][] edgeTo;
	private double[][] distTo;
	private double[][] energy;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		
		if (picture == null)
			throw new IllegalArgumentException();

		this.current = new Picture(picture);
		this.edgeTo = new int[this.width()][this.height()];
		this.distTo = new double[this.width()][this.height()];
		this.energy = new double[this.width()][this.height()];
		
	}

	// current picture
	public Picture picture() {
		return new Picture(this.current);
	}

	// width of current picture
	public int width() {
		return this.current.width();
	}

	// height of current picture
	public int height() {
		return this.current.height();
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		
		if (x < 0 || x >= this.width() || y < 0 || y >= this.height())
			throw new IllegalArgumentException();
		
		if (x == 0 || x == this.width() - 1 || y == 0 || y == this.height() - 1)
			return 1000.0;
		
		return Math.sqrt(this.xgradient(x, y) + this.ygradient(x, y));
	}

	private int xgradient(int x, int y) {
		
		Color previous = this.current.get(x-1, y);
		Color next = this.current.get(x+1, y);
		
		int deltaRed = next.getRed() - previous.getRed();
		int deltaGreen = next.getGreen() - previous.getGreen();
		int deltaBlue = next.getBlue() - previous.getBlue();
		
		int gradient = deltaRed * deltaRed + deltaGreen * deltaGreen + deltaBlue * deltaBlue;
		
		return gradient;
	}
	
	private int ygradient(int x, int y) {
		
		Color previous = this.current.get(x, y-1);
		Color next = this.current.get(x, y+1);
		
		int deltaRed = next.getRed() - previous.getRed();
		int deltaGreen = next.getGreen() - previous.getGreen();
		int deltaBlue = next.getBlue() - previous.getBlue();
		
		int gradient = deltaRed * deltaRed + deltaGreen * deltaGreen + deltaBlue * deltaBlue;
		
		return gradient;
	}

	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		
		int[] seam = new int[this.width()];
		this.resetDist();
		this.resetEnergy(false);
		
		int edgeToRight = -1;
		double distToRight = Double.POSITIVE_INFINITY;
		
		for (int row = 0; row < this.height(); row++)
			distTo[0][row] = energy[0][row];
		
		for (int col = 0; col < this.width() - 1; col++)
			for (int row = 0; row < this.height(); row++) {
				
				this.relax(col+1, row-1, col, row, false);
				this.relax(col+1, row, col, row, false);
				this.relax(col+1, row+1, col, row, false);
				
			}
		
		int lastCol = this.width() - 1;
		for (int row = 0; row < this.height(); row++) {
						
			if (distTo[lastCol][row] < distToRight) {
				
				distToRight = distTo[lastCol][row];
				edgeToRight = row;
				
			}
			
		}
		
		int col = lastCol;
		seam[col--] = edgeToRight;
		for (; col > -1; col--) {
			seam[col] = edgeTo[col+1][seam[col+1]];
		}
		
		return seam;
	}

	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		
		int[] seam = new int[this.height()];
		this.resetDist();
		this.resetEnergy(false);
		
		int edgeToTail = -1;
		double distToTail = Double.POSITIVE_INFINITY;
		
		for (int col = 0; col < this.width(); col++)
			distTo[col][0] = energy[col][0];
		
		for (int row = 0; row < this.height() - 1; row++)
			for (int col = 0; col < this.width(); col++) {
				
				this.relax(col-1, row+1, col, row, true);
				this.relax(col, row+1, col, row, true);
				this.relax(col+1, row+1, col, row, true);
				
			}
		
		int lastRow = this.height() - 1;
		for (int col = 0; col < this.width(); col++) {
						
			if (distTo[col][lastRow] < distToTail) {
				
				distToTail = distTo[col][lastRow];
				edgeToTail = col;
				
			}
			
		}
		
		int row = lastRow;
		seam[row--] = edgeToTail;
		for (; row > -1; row--) {
			seam[row] = edgeTo[seam[row+1]][row+1];
		}
		
		return seam;
		
	}
	
	private void resetEnergy(boolean transposed) {
		
		if (!transposed)
			for (int col = 0; col < this.width(); col++)
				for (int row = 0; row < this.height(); row++)
					this.energy[col][row] = this.energy(col, row);
		else
			for (int col = 0; col < this.width(); col++)
				for (int row = 0; row < this.height(); row++)
					this.energy[col][row] = this.energy(row, col);
		
	}

	private void resetDist() {
		
		for (int col = 0; col < this.width(); col++)
			for (int row = 0; row < this.height(); row++)
				this.distTo[col][row] = Double.POSITIVE_INFINITY;
		
	}

	private void relax(int colTo, int rowTo, int colFrom, int rowFrom, boolean vertical) {
		
		if (colTo < 0 || colTo >= this.width() || rowTo < 0 || rowTo >= this.height())
			return;
				
		if (distTo[colTo][rowTo] > distTo[colFrom][rowFrom] + energy[colTo][rowTo]) {
			
			distTo[colTo][rowTo] = distTo[colFrom][rowFrom] + energy[colTo][rowTo];
			edgeTo[colTo][rowTo] = vertical ? colFrom : rowFrom;
			
		}
		
	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		
		if (seam == null)
			throw new IllegalArgumentException();
		
		if (seam.length != this.width())
			throw new IllegalArgumentException();
		
		for (int i = 0; i < seam.length; i++)
			if (seam[i] < 0 || seam[i] >= this.height())
				throw new IllegalArgumentException();
			else if (i > 0 && Math.abs(seam[i] - seam[i-1]) > 1)
				throw new IllegalArgumentException();
		
		if (this.height() <= 1)
			throw new IllegalArgumentException();
		
		Picture cuted = new Picture(this.width(), this.height() - 1);

		int cRow = 0;
		for (int col = 0; col < this.width(); col++) {
			cRow = 0;
			for (int row = 0; row < this.height(); row++)
				if (row != seam[col]) {
					cuted.set(col, cRow, this.current.get(col, row));
					cRow++;
				}
		}
		
		this.current = cuted;

	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {

		if (seam == null)
			throw new IllegalArgumentException();
		
		if (seam.length != this.height())
			throw new IllegalArgumentException();
		
		for (int i = 0; i < seam.length; i++)
			if (seam[i] < 0 || seam[i] >= this.width())
				throw new IllegalArgumentException();
			else if (i > 0 && Math.abs(seam[i] - seam[i-1]) > 1)
				throw new IllegalArgumentException();
		
		if (this.width() <= 1)
			throw new IllegalArgumentException();
		
		Picture cuted = new Picture(this.width() - 1, this.height());

		int cCol = 0;
		for (int row = 0; row < this.height(); row++) {
			cCol = 0;
			for (int col = 0; col < this.width(); col++)
				if (col != seam[row]) {
					cuted.set(cCol, row, this.current.get(col, row));
					cCol++;
				}
		}
		
		this.current = cuted;
		
	}

	/*
	 * EmptyMethodBody
	 */
	public static void main(String[] args) {

	}

}
