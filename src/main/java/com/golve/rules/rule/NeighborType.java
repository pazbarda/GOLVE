package com.golve.rules.rule;

import java.text.ParseException;

/**
 * enum representation of neighborhood relations on a 2D grid (or a {@link Universe})
 * @author pazb
 *
 */
public enum NeighborType {
	ORTOGONAL("ortogonal", new int[][] {{-1,0},{0,1},{1,0},{0,-1}}),//
	DIAGONAL ("diagonal", new int[][] {{-1,-1},{-1,1},{1,1},{1,-1}}),//
	ALL ("all", new int[][] {{-1,0},{0,1},{1,0},{0,-1},{-1,-1},{-1,1},{1,1},{1,-1}}); //

	private final String stringValue;
	private final int[][] offsets;

	private NeighborType(String stringValue, int[][] offsets) {
		this.stringValue = stringValue;
		this.offsets = offsets;
	}

	/**
	 * get an array of offsets on the grid that correspond with the {@link NeighborType}.
	 * @return
	 */
	public int[][] getOffsets() {
		return offsets;
	}
	
	public String toString() {
		return stringValue;
	}
	
	public static NeighborType fromString(String str) throws ParseException {
		for (NeighborType type : NeighborType.values()) {
			if (str.equals(type.toString())) {
				return type;
			}
		}
		throw new ParseException(str + " does not correspond to a valid neighbor type", 0);
	}
}
