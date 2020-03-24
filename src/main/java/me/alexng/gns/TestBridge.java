package me.alexng.gns;

import me.alexng.gns.bridge.Expose;
import me.alexng.gns.env.value.NumberValue;

public class TestBridge {

	@Expose
	public NumberValue id;

	public TestBridge(int id) {
		this.id = new NumberValue(id);
	}

	@Expose
	public TestBridge(NumberValue numberValue) {
		this.id = numberValue;
	}

	@Expose
	public NumberValue getId() {
		System.out.println("called");
		return id;
	}
}
