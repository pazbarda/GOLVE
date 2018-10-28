package com.golve.test.rules;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.golve.rules.rule.EqualityType;
import com.golve.rules.rule.NeighborType;
import com.golve.rules.rule.Rule;
import com.golve.universe.Universe;

/**
 * test {@link Rule} functionality, mainly its {@link Rule#doesApply(Universe, int, int)} method which is crucial to the game
 * @author pazb
 *
 */
// TODO [improvement] - consider using more universe patterns other than checkerboard (may cover more corner cases)

@RunWith(Parameterized.class)
public class RuleTest {
	
	private static final Universe checkerBoard = new Universe(4, 4, new int[]{0,1,0,1, 1,0,1,0, 0,1,0,1, 1,0,1,0});
	
	private Universe universe;
	private NeighborType neighborType;
	private EqualityType equalityType;
	private boolean expectToApplyByState;
	private boolean expectToApplyByValue;
	
	public RuleTest(Universe universe, NeighborType neighborType, EqualityType equalityType,
			boolean expectToApplyBySTate, boolean expectToApplyByValue) {
		this.universe = universe;
		this.neighborType = neighborType;
		this.equalityType = equalityType;
		this.expectToApplyByState = expectToApplyBySTate;
		this.expectToApplyByValue = expectToApplyByValue;
	}

	/**
	 * we use 
	 * @return
	 */
	@Parameters
   public static List<Object[]> params() {
		Universe[] universes = new Universe[] {checkerBoard};
		boolean[] byState = new boolean[] {true, false};
		boolean[] byValue = new boolean[] {true, false};
		List<Object[]> retList = new ArrayList<Object[]>();
		for (Universe universe : universes) {
			for (NeighborType neighborType : NeighborType.values()) {
				for (EqualityType equalityType : EqualityType.values()) {
					for (boolean bs : byState) {
						for (boolean bv : byValue) {
							Object[] arr = new Object[] {universe, neighborType, equalityType, bs, bv};
							retList.add(arr);
						}
					}
				}
			}
		}
		
		return retList;
	}

	@Test
	public void doesApplyTest() {
		for (int i=0; i<universe.getHeight(); i++) {
			for (int j=0; j<universe.getWidth(); j++) {
				genericDoesApplyTest(universe, i, j, neighborType, equalityType, expectToApplyByState, expectToApplyByValue);
			}
		}
	}
	
	private static void genericDoesApplyTest(Universe universe, int i, int j, NeighborType neighborType, EqualityType equalityType, boolean expectedToApplyByState,boolean expectedToApplyByLiveNeighbors) {
		int actualLiveNeighbors = universe.getTotalLiveNeighbors(i, j, neighborType);
		int testedLiveNeighbors = actualLiveNeighbors;
		
		switch (equalityType) {
		case EQUALS:
			testedLiveNeighbors += (expectedToApplyByLiveNeighbors ? 0 : 1);
			break;
		case GREATER:
			testedLiveNeighbors += (expectedToApplyByLiveNeighbors ? -1 : 0);
			break;
		case LESS:
			testedLiveNeighbors += (expectedToApplyByLiveNeighbors ? 1 : 0); 
		}
		
		boolean testedInitialState = (expectedToApplyByState == universe.isAlive(i, j));
		
		Rule rule = new Rule(testedInitialState, neighborType, equalityType, testedLiveNeighbors);
		boolean expectedToApply = expectedToApplyByState && expectedToApplyByLiveNeighbors;
		assertTrue(expectedToApply==rule.doesApply(universe, i, j));
	}
}
