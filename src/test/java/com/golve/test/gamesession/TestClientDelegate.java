package com.golve.test.gamesession;

import org.junit.Assert;

import com.golve.clientdelegate.IClientDelegate;
import com.golve.universe.Universe;

public class TestClientDelegate implements IClientDelegate {

	private Universe universe;
	
	public void generationUpdated(Universe universe) {
		this.universe = universe;
	}
	
	public void assertUniverse(Universe expectedUniverse) {
		Assert.assertEquals(expectedUniverse, universe);
	}

	public String getClientName() {
		return "TestClient";
	}

}
