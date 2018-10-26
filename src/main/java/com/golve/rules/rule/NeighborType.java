package com.golve.rules.rule;

public enum NeighborType {
	ORTOGONAL(new int[][] {{-1,0},{0,1},{1,0},{0,-1}}),//
	DIAGONAL (new int[][] {{-1,-1},{-1,1},{1,1},{1,-1}}),//
	ALL (new int[][] {{-1,0},{0,1},{1,0},{0,-1},{-1,-1},{-1,1},{1,1},{1,-1}}); //
//	UNKNOWN (new int[][] {{}});
	
	private final int[][] offsets;

	private NeighborType(int[][] offsets) {
		this.offsets = offsets;
	}

	public int[][] getOffsets() {
		return offsets;
	}
	
	
}
