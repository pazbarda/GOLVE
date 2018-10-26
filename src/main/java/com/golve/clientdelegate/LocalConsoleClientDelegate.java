package com.golve.clientdelegate;

import com.golve.universe.Universe;

/**
 * represents a local client, whose UI is the console.
 * 
 * @author pazb
 *
 */
public class LocalConsoleClientDelegate implements IClientDelegate {
	
	private final String clientName;
	
	public LocalConsoleClientDelegate(String clientName) {
		this.clientName = clientName;
	}

	public void generationUpdated(Universe universe) {
		System.out.println("<" + clientName + "> " + universe.toString2D());
	}

	public String getClientName() {
		return clientName;
	}

}
