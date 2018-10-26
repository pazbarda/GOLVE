package com.golve.rules.applier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.golve.rules.rule.Rule;
import com.golve.universe.Universe;

// TODO - revisit this doc
/**
 * holds the entire set of the game's rules, and applies the relevant ones on a provided universe.<br />
 * for each start generation, the matching rules are kept in an ordered list<br />
 * <b>NOTE: while applying rules on a universe, this class:<br /></b>
 * 1 - assumes the lists of rules are ordered by starting generation (descending). there is no validation of that<br />
 * 2 - within the same start generation, the first rule that applies to a cell is the one to take effect. once a cell is effected by a rule, 
 * rest of the rules for that starting generation will not be evaluated. order of the rules in the list should reflect that.<br />
 * 3 - for a given start generation, inter-rules logic is assumed (there is no validation whether rules contradict each other)<br />
 * 
 * @author pazb
 *
 */

// TODO [test] - write tests for this class
public class RulesApplier {
	
	// TODO - make rulesData structure its own class
	private List<Entry<Integer, List<Rule>>> rulesData = new ArrayList<Entry<Integer,List<Rule>>>();

	public RulesApplier(List<Entry<Integer, List<Rule>>> rulesData) {
		this.rulesData = rulesData;
	}
	
	public Universe apply(Universe universe, int generation) {
		
		// get the relevant list of rules, according to the current generation
		List<Rule> rules = null;
		for (Entry<Integer, List<Rule>> entry : rulesData) {
			if (generation >= entry.getKey()) {
				rules = entry.getValue();
				break;
			}
		}
		// no rules apply for the current generation, no changes to universe
		if (null==rules) {
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
