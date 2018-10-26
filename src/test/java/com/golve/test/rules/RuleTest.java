package com.golve.test.rules;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.golve.rules.rule.EqualityType;
import com.golve.rules.rule.NeighborType;
import com.golve.rules.rule.Rule;
import com.golve.universe.Universe;

// TODO [improvement] - use parameterized testing (it's practically there...)
// TODO [improvement] - consider using more universe patterns other than checkerboard (may cover more corner cases)
public class RuleTest {
	
	private static final Universe checkerBoard = new Universe(4, 4, new int[]{0,1,0,1, 1,0,1,0, 0,1,0,1, 1,0,1,0});
	
	@Test
	public void test() {
		for (int i=0; i<checkerBoard.getHeight(); i++) {
			for (int j=0; j<checkerBoard.getWidth(); j++) {
				for (NeighborType neighborType : NeighborType.values()) {
					for (EqualityType equalityType : EqualityType.values()) {
						for (boolean[] boolRow : new boolean[][]{{true,true},{true,false},{false, true},{false, false}}) {
							boolean byState = boolRow[0];
							boolean byValue = boolRow[1];
							genericRuleTest(checkerBoard, i, j, neighborType, equalityType, byState, byValue);
						}
					}
				}
			}
		}
	}
	
	private static void genericRuleTest(Universe universe, int i, int j, NeighborType neighborType, EqualityType equalityType, boolean expectedToApplyByState,boolean expectedToApplyByLiveNeighbors) {
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
