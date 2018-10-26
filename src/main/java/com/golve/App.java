package com.golve;

import java.util.Arrays;

import com.golve.clientdelegate.LocalConsoleClientDelegate;
import com.golve.gamesession.GameSession;
import com.golve.rules.applier.RulesApplier;
import com.golve.rules.applier.RulesApplierFactory;
import com.golve.universe.Universe;

// TODO - DOC. for everything public.
// TODO [improvement] - use spring
// TODO [improvement] - implenment main for multiple clients
public class App {
	
	/**
	 * @param args format: [width height infect-after max-generation seed(space delimited)]
	 */
	public static void main(String[] args) {
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		int virusStartGeneration = Integer.parseInt(args[2]);
		int maxGeneration = Integer.parseInt(args[3]);
		String[] rawSeed = args[4].split(" ");
		int intervalMiliSec = Integer.parseInt(args[5]) * 1000;
		
		if (width * height != rawSeed.length) {
			System.err.println("universe dimensions are inconsisted with seed length. aborting");
			return;
		}
		
		LocalConsoleClientDelegate localClient = new LocalConsoleClientDelegate("Local");
		RulesApplier rulesApplier = RulesApplierFactory.getVirusRulesApplier(virusStartGeneration);
		
		int[] flatSeed = Arrays.stream(rawSeed).mapToInt(Integer::parseInt).toArray();
		Universe seed = new Universe(height, width, flatSeed);
		
		GameSession gameSession = new GameSession(localClient, rulesApplier, intervalMiliSec, seed, maxGeneration);
		
		gameSession.start();		
	}
}
