package com.golve.clientdelegate;

import com.golve.universe.Universe;

/**
 * represents a local client, whose UI is the console.
 * when notified on a new generation, this delegate will print the flat representation of the new universe to the console
 * this client implementation supports definition of custom rules (json format)
 * 
 * @author pazb
 *
 */
public class LocalConsoleClientDelegate implements IClientDelegate {
	
	private final String clientName;
	private final String customRulesFilePath;
	
	/**
	 * construct a local console client delegate with custom rules
	 * @param clientName
	 * @param customRulesFilePath
	 */
	public LocalConsoleClientDelegate(String clientName, String customRulesFilePath) {
		this.clientName = clientName;
		this.customRulesFilePath = customRulesFilePath;
	}
	
	/**
	 * construct a client delegate with no custom rules, will use the GOL virus rules
	 * @param clientName
	 */
	public LocalConsoleClientDelegate(String clientName) {
		this(clientName, Thread.currentThread().getContextClassLoader().getResource("virus-rules.json").getPath());
	}

	/**
	 * react on a generation update, print the new universe to console
	 */
	public void generationUpdated(Universe universe) {
		System.out.println("<" + clientName + "> " + universe.toString());
	}

	/**
	 * get the client name
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @return the path to the custom rules file
	 */
	public String getRulesFilePath() {
		return customRulesFilePath;
	}
}
