package com.golve.clientdelegate;

import com.golve.universe.Universe;

/**
 * client delegate represents a client of the application.
 * its main responsibility is to act upon a new generation on behalf of the client.
 * 
 * (example - for a local client, the delegate is expected to print the generation output to console)
 * 
 * @author pazb
 *
 */
public interface IClientDelegate {
	
	/**
	 * delegate's reaction to an update of universe
	 */
	public void generationUpdated(Universe universe);
	
	public String getClientName();
}
