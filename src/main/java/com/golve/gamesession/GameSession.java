package com.golve.gamesession;

import java.util.Timer;

import com.golve.clientdelegate.IClientDelegate;
import com.golve.universe.Universe;

/**
 * a game session manages a single GOL session for a specific {@link IClientDelegate},<br/>
 * based on the client's rules and program arguments.<br/>
 * a game session is started on-demand by calling {@link #start()}) method and will auto-stop once final generation has passed.<br/>
 * it can also be shutdown by calling {@link #cancel()};
 * @author pazb
 *
 */
public class GameSession {
	
	private Timer timer;
	private final Universe seed;
	private final IClientDelegate clientDelegate;
	private final long interval;
	private GenerationRunner runner;

	/**
	 * constructor
	 * @param seed
	 * @param clientDelegate
	 * @param runner
	 * @param interval
	 */
	public GameSession(Universe seed, IClientDelegate clientDelegate, GenerationRunner runner, long interval) {
		super();
		this.seed = seed;
		this.clientDelegate = clientDelegate;
		this.interval = interval;
		this.runner = runner;
		this.runner.setOwningSession(this);
	}

	/**
	 * start the session. will auto-stop once final generation has passed (see {@link GenerationRunner})
	 */
	public void start() {
		clientDelegate.generationUpdated(seed);
		timer = new Timer(clientDelegate.getClientName());
		timer.schedule(runner, 0, interval);
	}
	
	/**
	 * cancel (stop) the session. current generation and universe state are kept.
	 */
	public void cancel() {
		if (null!=timer) {
			timer.cancel();
		}
	}
	
}
