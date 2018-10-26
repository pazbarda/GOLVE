package com.golve.rules.applier;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.golve.rules.rule.EqualityType;
import com.golve.rules.rule.NeighborType;
import com.golve.rules.rule.Rule;

/**
 * factory of {@link RulesApplier} objects
 * @author pazb
 *
 */
// TODO [test] - write tests for this class
// TODO [improvement] - read the rules from external file
public class RulesApplierFactory {
	
	public static RulesApplier getTraditionalRulesApplier() {
		return new RulesApplier(getTraditionalRulesData());
	}
	
	public static RulesApplier getVirusRulesApplier(int virusStartGeneration) {
		return new RulesApplier(getVirusRulesData(virusStartGeneration));
	}
	
	private static List<Entry<Integer,List<Rule>>> getTraditionalRulesData() {
		
		List<Entry<Integer,List<Rule>>> ret = new ArrayList<Map.Entry<Integer,List<Rule>>>();
		List<Rule> rules = new ArrayList<Rule>();
		
		rules.add(new Rule(true, NeighborType.ALL, EqualityType.LESS, 2));
		rules.add(new Rule(true, NeighborType.ALL, EqualityType.GREATER, 3));
		rules.add(new Rule(false, NeighborType.ALL, EqualityType.EQUALS, 3));
		
		ret.add(new AbstractMap.SimpleEntry<Integer, List<Rule>>(0, rules));
		
		return ret;
	}
	
	private static List<Entry<Integer,List<Rule>>> getVirusRulesData(int virusStartGeneration) {
		List<Entry<Integer,List<Rule>>> ret = new ArrayList<Map.Entry<Integer,List<Rule>>>();
		List<Rule> rules = new ArrayList<Rule>();
		
		rules.add(new Rule(false, NeighborType.ALL, EqualityType.EQUALS, 1));
		rules.add(new Rule(true, NeighborType.ORTOGONAL, EqualityType.EQUALS, 0));
		
		ret.add(new AbstractMap.SimpleEntry<Integer, List<Rule>>(virusStartGeneration, rules));
		
		ret.addAll(getTraditionalRulesData());
		
		return ret;
	}
	
}
