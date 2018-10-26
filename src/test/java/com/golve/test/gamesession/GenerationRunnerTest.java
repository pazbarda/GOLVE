package com.golve.test.gamesession;

import org.junit.Test;

import com.golve.gamesession.GenerationRunner;
import com.golve.rules.applier.RulesApplier;
import com.golve.rules.applier.RulesApplierFactory;
import com.golve.universe.Universe;

/**
 * test the functionality of {@link GenerationRunner} = make sure it changes the universe as expected
 * @author pazb
 *
 */
// TODO [improvement] - need more test cases
public class GenerationRunnerTest {

	/**
	 * test the simple case described in the requirements document
	 */
	@Test
	public void simpleRequirementsTest() {
		Universe seed = new Universe(3, 3, new int[] {0,0,0, 2,0,0, 4,0,8 });		
		Universe expectedGen1 = new Universe(3, 3, new int[] {0,0,0, 0,300,0, 0,1,0 });
		
		TestClientDelegate testDelegate = new TestClientDelegate();
		// we're using the traditional rules, since virus is still not spreader on the 1st generation
		RulesApplier rulesApplier = RulesApplierFactory.getTraditionalRulesApplier();
		GenerationRunner generationRunner = new GenerationRunner(testDelegate, rulesApplier, seed, 1);
		
		// run 1st generation
		generationRunner.run();
		
		testDelegate.assertUniverse(expectedGen1);
	}
}
