package me.alexng.gns.env;

import java.util.HashMap;
import java.util.Map;

public class Scope {

	// TODO: Think about how we're storing this data. A map is a lot of overhead
	private Map<String, Value> variables;

	Scope() {
		variables = new HashMap<>();
	}

	public void setVariable(String name, Value value) {
		variables.put(name, value);
	}

	@Override
	public String toString() {
		return variables.toString();
	}
}
