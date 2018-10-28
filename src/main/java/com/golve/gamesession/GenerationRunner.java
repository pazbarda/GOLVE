package com.golve.gamesession;

import java.util.TimerTask;

import com.golve.clientdelegate.IClientDelegate;
import com.golve.rules.applier.RulesApplier;
import com.golve.universe.Universe;

/**
 * Runs generation on given universe, namely applying the rules on it repeatedly.<br/>
 * This class is a {@link TimerTask} and as such will kick-in once scheduled by a {@link Timer}.<br/>
 * Typically a generation runner is owned by a {@link GameSession}.<br/>
 * The runner will run all generations until the last one (unless cancelled by the Timer), and then will execute<br/>
 * the owning {@link GameSession}'s cancel() method to shut it down.
 * 
 * @author pazb
 *
 */
public class GenerationRunner extends TimerTask{

	private GameSession owningSession;
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
			done = true;
			if (null!=owningSession) {
				owningSession.cancel();
			}
			this.cancel();
		}
	}

	public boolean isDone() {
		return done;
	}

	public IClientDelegate getClientDelegate() {
		return clientDelegate;
	}

	public GameSession getOwningSession() {
		return owningSession;
	}

	public void setOwningSession(GameSession owningSession) {
		this.owningSession = owningSession;
	}
}
