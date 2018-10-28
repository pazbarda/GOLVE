package com.golve.rules.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.golve.rules.applier.RulesData;
import com.golve.rules.rule.EqualityType;
import com.golve.rules.rule.NeighborType;
import com.golve.rules.rule.Rule;
import com.golve.utils.AbstractFileParser;

// TODO [test] - write test for this class
// partial coverage is obtained by GenerationRunnerTest

/**
 * file parser to parse json rules file.
 * example of such a file: /GameOfLifeVirusEdition/src/main/resources/default-rules.json
 * @author pazb
 *
 */
public class JsonRulesFileParser extends AbstractFileParser<RulesData>{

	private final List<String> userInput;
	
	private static final String JSON = "json";
	private static final String SPC = " ";

	private static final String RULES_BY_START_GEN_KEY = "rulesByStartGeneneration";
	private static final String START_GENERATION_KEY = "startGeneration";
	private static final String RULES_KEY = "rules";
	private static final String INITIAL_STATE_KEY = "initialState";
	private static final String NEIGHBOR_TYPE_KEY = "neighborType";
	private static final String CONDITION_KEY = "condition";
	
	private static final String INPUT_VALUE = "input";	
	private static final String ALIVE_STATE_VALUE = "alive";
	private static final String DEAD_STATE_VALUE = "dead";
	
	public JsonRulesFileParser(List<String> userInput) {
		super(JSON);
		this.userInput = userInput;
	}

	@Override
	protected RulesData parse(String path) throws FileNotFoundException, IOException, java.text.ParseException, ParseException {
        RulesData ret = new RulesData();
		
		int parseExceptionOffset = 0;

		JSONParser parser = new JSONParser();
        JSONObject entireJson = (JSONObject) parser.parse(new FileReader(path));
        
        JSONArray rulesByStartGen = (JSONArray) getValueFromJsonObj(entireJson, RULES_BY_START_GEN_KEY, parseExceptionOffset);

        int userInputIndex = 0;
        
        for (Object obj : rulesByStartGen) {
        	
        	parseExceptionOffset++;
        	
        	JSONObject jsonObj = (JSONObject) obj;
        	
        	// get the start generation of the rules
        	long startGenArg;
        	Object startGen = getValueFromJsonObj(jsonObj, START_GENERATION_KEY, parseExceptionOffset);
        	if (startGen.equals(INPUT_VALUE)) {
        		// user input should go here
        		startGenArg = Integer.parseInt(userInput.get(userInputIndex));
        		userInputIndex++;
        	} else {
        		// specified value is used
        		startGenArg = (long) startGen;
        	}
        	
        	// get the rules
        	JSONArray rulesArr = (JSONArray) getValueFromJsonObj(jsonObj, RULES_KEY, parseExceptionOffset);
        	for (Object ruleObj : rulesArr) {
        		JSONObject ruleJsonObj = (JSONObject) ruleObj;
        		// initial state
        		String initialStateStr = (String) getValueFromJsonObj(ruleJsonObj, INITIAL_STATE_KEY, parseExceptionOffset);
        		boolean initialStateArg = getInitialStateFromString(initialStateStr, parseExceptionOffset);
        		// neighbor type
        		String neighborTypeStr = (String) getValueFromJsonObj(ruleJsonObj, NEIGHBOR_TYPE_KEY, parseExceptionOffset);
        		NeighborType neighborTypeArg = NeighborType.fromString(neighborTypeStr);
        		// condition (equality and limit)
        		String conditionStr = (String) getValueFromJsonObj(ruleJsonObj, CONDITION_KEY, parseExceptionOffset);
        		String[] condParts = conditionStr.split(SPC);
        		if (condParts.length!=2) {
        			throw new java.text.ParseException("invalid condition string", parseExceptionOffset);
        		}
        		EqualityType equalityTypeArg = EqualityType.fromString(condParts[0]);
        		int limitArg = Integer.parseInt(condParts[1]);
        		
        		// add the rule:
        		ret.addRule(new Rule(initialStateArg, neighborTypeArg, equalityTypeArg, limitArg), (int) startGenArg);
        	}
        }
		return ret;
	}
	
	private Object getValueFromJsonObj(JSONObject jsonObj, String key, int exceptionOffset) throws java.text.ParseException {
		Object ret = jsonObj.get(key);
		if (null==ret) {
        	throw new java.text.ParseException("did not find key + " + key, exceptionOffset);
        }
		return ret;
	}
	
	private boolean getInitialStateFromString(String str, int exceptionOffset) throws java.text.ParseException {
		if (str.equals(ALIVE_STATE_VALUE)) {
			return true;
		} else if (str.equals(DEAD_STATE_VALUE)) {
			return false;
		}
		throw new java.text.ParseException("unidentified state + " + str, exceptionOffset);
	}	
}
