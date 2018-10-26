package com.golve.gamesession;

import java.util.TimerTask;

import com.golve.clientdelegate.IClientDelegate;
import com.golve.rules.applier.RulesApplier;
import com.golve.universe.Universe;

public class GenerationRunner extends TimerTask{

	private final IClientDelegate clientDelegate;
	private final RulesApplier rulesApplier;
	private Universe universe;
	private final int lastGeneration;
	// to prevent executing the run() logic (by directly by calling run() or re-scheduling the task) after lastGeneration passed
	private boolean done;

	public GenerationRunner(IClientDelegate clientDelegate, RulesApplier rulesApplier, Universe seedUniverse, int lastGeneration) {
		this.clientDelegate = clientDelegate;
		this.rulesApplier = rulesApplier;
		this.universe = seedUniverse;
		this.lastGeneration = lastGeneration;
		// in case the universe was already run by another runner
		this.done = universe.getGeneration() >= lastGeneration;
	}

	@Override
	public void run() {
		if (done) {
			return;
		}
		
		universe.newGeneration(rulesApplier);
		clientDelegate.generationUpdated(universe);
		
		if (universe.getGeneration() >= lastGeneration) {
			this.cancel();
			done = true;
		}
	}

}
