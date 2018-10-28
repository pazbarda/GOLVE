package com.golve.universe;

import java.util.Arrays;

import com.golve.rules.applier.RulesApplier;
import com.golve.rules.rule.NeighborType;

//TODO [test] - write tests for this class

/**
 * implementation of a GOL universe.
 * @author pazb
 *
 */
public class Universe {
	
	private static final String SPC = " ";
	private static final String LIVE = "1";
	private static final String DEAD = "0";
	
	private int[][] cells;
	private final int height;
	private final int width;
	private int generation;
	
	public Universe(int height, int width,  int[] flatData) {
		if (height==0 || width==0) {
			throw new IllegalArgumentException("found a 0-dimension, cannot create universe");
		}
		if (flatData.length==0) {
			throw new IllegalArgumentException("flat data is empty, cannot create a universe");
		}
		if (width*height != flatData.length) {
			throw new IllegalArgumentException("width and height are inconsistent to raw data length, cannot create universe");
		}
		
		this.height = height;
		this.width = width;
		cells = new int[this.height][this.width];
		int flatDataIndex = 0;
		for (int i=0; i<height;i++) {
			for (int j=0;j<width;j++) {
				cells[i][j] = flatData[flatDataIndex] > 0 ? 1 : 0;
				flatDataIndex++;
			}
		}
		this.generation = 0;
	}
	
	/**
	 * use a {@link RulesApplier} to apply rules on this universe.
	 * @param rulesApplier
	 */
	public void newGeneration(RulesApplier rulesApplier) {
		cells = rulesApplier.apply(this, ++generation).cells;
	}
	
	/**
	 * duplicate the universe
	 * @return
	 */
	public Universe duplicate() {
		int height = cells.length;
		int width = cells[0].length;
		int[] flatData = new int[height*width];
		int flatDataIndex = 0;
		for (int i=0; i<cells.length; i++) {
			for (int j=0;j<cells[i].length;j++) {
				flatData[flatDataIndex] = cells[i][j];
				flatDataIndex++;
			}
		}
		return new Universe(height, width, flatData);
	}
	
	public boolean isAlive(int i, int j) {
		if (!isValidLocation(i, j)) {
			return false;
		}
		return cells[i][j]>0;
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	/**
	 * get the univer's current generation
	 * @return
	 */
	public int getGeneration() {
		return generation;
	}

	/**
	 * get the total number of live neighbors of cell
	 * @param i
	 * @param j
	 * @param neighborType
	 * @return get the total number of live neighbors of the cell at [i,j]
	 */
	public int getTotalLiveNeighbors(int i, int j, NeighborType neighborType) {
		int liveSum = 0;
		int[][] neighborOffsets = neighborType.getOffsets();
		for (int[] offsetsPair : neighborOffsets) {
			int currI = i + offsetsPair[0];
			int currJ = j + offsetsPair[1];
			if (!isValidLocation(currI, currJ)) {
				continue;
			}
			liveSum += isAlive(currI, currJ) ? 1 : 0;
		}
		return liveSum;
	}
	
	/**
	 * flip the state of a cell (alive->dead or dead->alive)
	 * @param i
	 * @param j
	 */
	public void toggle(int i, int j) {
		if (cells[i][j] > 0) {
			cells[i][j]=0;
		} else {
			cells[i][j]=1;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("[generation=");
		ret.append(generation);
		ret.append("] [");
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				ret.append(isAlive(i, j) ? LIVE : DEAD);
				ret.append(SPC);
			}
		}
		ret.append("]");
		return ret.toString();
	}
	
	public String toString2D() {
		StringBuilder ret = new StringBuilder();
		ret.append("[generation=");
		ret.append(generation);
		ret.append("] \n");
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				ret.append(isAlive(i, j) ? LIVE : DEAD);
				ret.append(SPC);
			}
			ret.append("\n");
		}
		return ret.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(cells);
		result = prime * result + generation;
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Universe other = (Universe) obj;
		if (!Arrays.deepEquals(cells, other.cells))
			return false;
		return true;
	}

	private boolean isValidLocation(int i, int j) {
		return (i>=0 && j>=0 && i<height && j<width);
	}
}
