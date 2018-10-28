package com.golve.rules.applier;

import java.util.List;

import com.golve.rules.rule.Rule;
import com.golve.universe.Universe;

/**
 * holds the entire set of the game's rules, and applies the relevant ones on a provided universe.<br />
 * for each start generation, the matching rules are kept in an ordered list<br />
 * <b>NOTE: while applying rules on a universe::<br /></b>
 * 1 - this class assumes the lists of rules are ordered by starting generation (descending). there is no validation of that<br />
 * 2 - for a given start generation, inter-rules logic is assumed (there is no validation whether rules contradict each other)<br />
 * 
 * @author pazb
 *
 */

// TODO [test] - write tests for this class
//partial coverage is obtained by GenerationRunnerTest
public class RulesApplier {
	
	private RulesData rulesData;

	public RulesApplier(RulesData rulesData) {
		this.rulesData = rulesData;
	}
	
	public Universe apply(Universe universe, int generation) {
		
		// get the relevant list of rules, according to the current generation
		List<Rule> rules = rulesData.getRulesForGeneration(generation);
		// no rules apply for the current generation, no changes to universe
		if (null==rules || rules.isEmpty()) {
			return universe;
		}
		
		// do not make changes to the original universe
		Universe newUniverse = universe.duplicate();
		
		// found the list of rule that match the generation, scan and apply on the entire universe
		for (int i=0; i<universe.getHeight(); i++) {
			for (int j=0; j<universe.getWidth(); j++) {
				// apply the first rule that applies to the cell
				for (Rule rule : rules) {
					if (rule.doesApply(universe, i, j)) {
						newUniverse.toggle(i, j);
						break;
					}
				}
			}
		}
		
		return newUniverse;
	}
}
