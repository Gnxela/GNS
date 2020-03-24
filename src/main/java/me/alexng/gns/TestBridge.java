package me.alexng.gns;

import me.alexng.gns.bridge.Expose;
import me.alexng.gns.env.value.NumberValue;

public class TestBridge {

	@Expose
	public NumberValue id;

	// TODO: This is temporary!
	public TestBridge() {
		this.id = new NumberValue(5);
	}

	public TestBridge(int id) {
		this.id = new NumberValue(id);
	}

	@Expose
	public TestBridge(NumberValue numberValue) {
		this.id = numberValue;
	}
}
