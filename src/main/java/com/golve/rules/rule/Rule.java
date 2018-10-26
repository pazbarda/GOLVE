package com.golve.rules.rule;

import com.golve.universe.Universe;

/**
 * implementation of a GOL rule.
 * intended to define the rule and evaluate whether it applies on a cell in a given universe
 * @author pazb
 *
 */
public class Rule {
	private final boolean initialState;
	private final NeighborType neighborType;
	private final EqualityType equalityType;
	private final int liveNeighborsLimit;

	public Rule(boolean initialState, NeighborType neighborType, EqualityType equalityType, int liveNeighborsLimit) {
		super();
		this.initialState = initialState;
		this.neighborType = neighborType;
		this.equalityType = equalityType;
		this.liveNeighborsLimit = liveNeighborsLimit;
	}

	public boolean doesApply(Universe universe, int i, int j) {
		if (universe.isAlive(i, j)!=initialState) {
			return false;
		}
		int liveNeighborsSum = universe.getTotalLiveNeighbors(i, j, neighborType);
		return equalityType.evaluate(liveNeighborsSum, liveNeighborsLimit);
	}
}
