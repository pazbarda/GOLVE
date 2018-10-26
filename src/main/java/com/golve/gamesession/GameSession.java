package com.golve.gamesession;

import java.util.Timer;

import com.golve.clientdelegate.IClientDelegate;
import com.golve.rules.applier.RulesApplier;
import com.golve.universe.Universe;

public class GameSession {
	
	private Timer timer;
	private final IClientDelegate clientDelegate;
	private final RulesApplier rulesApplier;
	private final long interval;
	private final Universe seed;
	private final int lastGeneration;

	public GameSession(IClientDelegate clientDelegate, RulesApplier rulesApplier, long interval, Universe seed, int lastGeneration) {
		super();
		this.clientDelegate = clientDelegate;
		this.rulesApplier = rulesApplier;
		this.interval = interval;
		this.seed = seed;
		this.lastGeneration = lastGeneration;
	}

	public void start() {
		clientDelegate.generationUpdated(seed);
		timer = new Timer(clientDelegate.getClientName());
		timer.schedule(new GenerationRunner(clientDelegate, rulesApplier, seed, lastGeneration), 0, interval);
	}
	
	public void cancel() {
		if (null!=timer) {
			timer.cancel();
		}
	}
	
}
