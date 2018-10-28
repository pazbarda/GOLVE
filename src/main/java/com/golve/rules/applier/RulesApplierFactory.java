package com.golve.rules.applier;

import java.util.List;

import com.golve.rules.parser.JsonRulesFileParser;
import com.golve.rules.rule.EqualityType;
import com.golve.rules.rule.NeighborType;
import com.golve.rules.rule.Rule;

/**
 * factory of {@link RulesApplier} objects. currently supported:<br/>
 * traditional-rules applier (predefined rules, no need for rules json file)
 * virus-mode-rules applier (predefined rules, no need for rules json file)
 * custom-rules applier (rules are read from an external json file)
 * 
 * @author pazb
 *
 */
public class RulesApplierFactory {
	
	/**
	 * get a {@link RulesApplier} object based on rules written in an external file<br/>
	 * <b> only .json file supported at the moment</b>
	 * @param externalJsonFilePath path to custom rules file on disk
	 * @return
	 * @throws Exception 
	 */
	public static RulesApplier getCustomRulesApplier(String externalJsonFilePath, List<String> userInput) throws Exception {
		RulesData rulesData = new JsonRulesFileParser(userInput).getRulesData(externalJsonFilePath);
		return new RulesApplier(rulesData);
	}
	
	public static RulesApplier getTraditionalRulesApplier() {
		return new RulesApplier(getTraditionalRulesData());
	}
	
	public static RulesApplier getVirusRulesApplier(int virusStartGeneration) {
		return new RulesApplier(getVirusRulesData(virusStartGeneration));
	}
	
	private static RulesData getTraditionalRulesData() {
		
		RulesData ret = new RulesData();
		
		ret.addRule(new Rule(true, NeighborType.ALL, EqualityType.LESS, 2), 0);
		ret.addRule(new Rule(true, NeighborType.ALL, EqualityType.GREATER, 3), 0);
		ret.addRule(new Rule(false, NeighborType.ALL, EqualityType.EQUALS, 3), 0);
		
		return ret;
	}
	
	private static RulesData getVirusRulesData(int virusStartGeneration) {
		RulesData ret = new RulesData();
		
		ret.addRule(new Rule(false, NeighborType.ALL, EqualityType.EQUALS, 1), virusStartGeneration);
		ret.addRule(new Rule(true, NeighborType.ORTOGONAL, EqualityType.EQUALS, 0), virusStartGeneration);
		
		ret.addRule(new Rule(true, NeighborType.ALL, EqualityType.LESS, 2), 0);
		ret.addRule(new Rule(true, NeighborType.ALL, EqualityType.GREATER, 3), 0);
		ret.addRule(new Rule(false, NeighborType.ALL, EqualityType.EQUALS, 3), 0);
				
		return ret;
	}
	
}
