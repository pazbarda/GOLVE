package com.golve.rules.applier;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.golve.rules.rule.Rule;

// TODO [test] - write tests for this class
/**
 * Data structure to hold all rules data for the game.<br/>
 * Rules are grouped and added by start-generation.<br/>
 * Given a start-generation, this class will provide the relevant rules (see {@link #getRulesForGeneration(int)}).
 * 
 * @author pazb
 *
 */
public class RulesData {
	
	private List<Entry<Integer, List<Rule>>> entries = new ArrayList<Entry<Integer, List<Rule>>>();
	
	/**
	 * add start generation, with no rules.
	 * @param startGeneration
	 */
	public void addStartGeneration(int startGeneration) {
		if (entries.isEmpty()) {
			entries.add(new AbstractMap.SimpleEntry<Integer, List<Rule>>(startGeneration, new ArrayList<Rule>()));
			return;
		}
		List<Rule> rules = getRulesOfStartGeneration(startGeneration);
		if (null==rules) {
			int index = 0;
			Entry<Integer, List<Rule>> entry = entries.get(index);
			while (index<entries.size() && startGeneration < entry.getKey()) {
				index++;
				entry = entries.get(index);
			}
			entries.add(index, new AbstractMap.SimpleEntry<Integer, List<Rule>>(startGeneration, new ArrayList<Rule>()));			
		}
	}
	
	/**
	 * add a rule to a start-generation. will add the start-generation if needed.
	 * @param rule
	 * @param startGeneration
	 */
	public void addRule(Rule rule, int startGeneration) {
		addStartGeneration(startGeneration);
		List<Rule> rules = getRulesOfStartGeneration(startGeneration);
		// rules can't be null at this point
		rules.add(rule);
	}
	
	/**
	 * remove the given start-generation (if it exists), along with all related rules
	 * @param startGeneration
	 */
	public void removeStartGeneration(int startGeneration) {
		List<Entry<Integer, List<Rule>>> entriesCopy = new ArrayList<Entry<Integer, List<Rule>>>(entries);
		int index = 0;
		for (Entry<Integer, List<Rule>> entry : entriesCopy) {
			if (entry.getKey()==startGeneration) {
				entries.remove(index);
				return;
			}
			index++;
		}
	}
	
	/**
	 * remove a rule from a start-generation, if exists.
	 * @param rule
	 * @param startGeneration
	 */
	public void removeRule(Rule rule, int startGeneration) {
		List<Rule> rules = getRulesOfStartGeneration(startGeneration);
		if (null!=rules) {
			rules.remove(rule);
		}
	}
	
	/**
	 * get the rules corresponding to the given generation. <br/>
	 * the returned list of rules matches the first start-generation that's smaller/equal-to that the given generation 
	 * @param generation
	 * @return
	 */
	public List<Rule> getRulesForGeneration(int generation) {
		for (Entry<Integer, List<Rule>> entry : entries) {
			int startGen = entry.getKey();
			if (generation >= startGen) {
				return getRulesOfStartGeneration(startGen);
			}
		}
		return new ArrayList<Rule>();
	}
	
	private List<Rule> getRulesOfStartGeneration(int startGeneration) {
		for (Entry<Integer, List<Rule>> entry : entries) {
			if (entry.getKey()==startGeneration) {
				return entry.getValue();
			}
		}
		return null;
	}
}
 