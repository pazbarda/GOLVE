package com.golve;

import java.util.ArrayList;
import java.util.Arrays;

import com.golve.clientdelegate.LocalConsoleClientDelegate;
import com.golve.gamesession.GameSession;
import com.golve.gamesession.GenerationRunner;
import com.golve.rules.applier.RulesApplier;
import com.golve.rules.applier.RulesApplierFactory;
import com.golve.universe.Universe;

/**
 * Application main class.
 * On execution, the application will: <br/>
 * - read  and validate the input arguments<br/>
 * - create a {@link GameSession} that will execute the Game Of Life based on program arguments<br/>
 * 
 * @author pazb
 *
 */
public class App {
	
	/**
	 * @param args format: [width height infect-after max-generation seed(space delimited)] generation-interval
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// read input and validate
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		int maxGeneration = Integer.parseInt(args[3]);
		String[] rawSeed = args[4].split(" ");
		int intervalMiliSec = Integer.parseInt(args[5]) * 1000;
		if (width * height != rawSeed.length) {
			System.err.println("universe dimensions are inconsisted with seed length. aborting");
			return;
		}
		
		// create a client delegate (currently just the local console)
		LocalConsoleClientDelegate localClient = new LocalConsoleClientDelegate("Local");
		@SuppressWarnings("serial")
		ArrayList<String> userInput = new ArrayList<String>() {{
		    add(args[2]); // generation to start virus
		}};
		
		// create a rules applier based on rules set by the client
		RulesApplier rulesApplier = RulesApplierFactory.getCustomRulesApplier(localClient.getRulesFilePath(), userInput);
		
		// build a seed universe
		int[] flatSeed = Arrays.stream(rawSeed).mapToInt(Integer::parseInt).toArray();
		Universe seed = new Universe(height, width, flatSeed);
		
		// build a generation runner, that will apply the rules each generation
		GenerationRunner runner = new GenerationRunner(localClient, rulesApplier, seed, maxGeneration); 
		
		// finally - generate a game session and run it. session will auto-stop when final generation has completed
		GameSession gameSession = new GameSession(seed, localClient, runner, intervalMiliSec);
		gameSession.start();
	}
}
