package me.alexng.gns.env;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.lexer.tokens.IdentifierToken;

import java.util.HashMap;
import java.util.Map;

public class Scope {

	// TODO: Think about how we're storing this data. A map is a lot of overhead
	private Map<String, Value> variables;

	Scope() {
		variables = new HashMap<>();
	}

	public void setVariable(IdentifierToken identifierToken, Value value) {
		variables.put(identifierToken.getName(), value);
	}

	public Value getVariable(IdentifierToken identifierToken) throws RuntimeException {
		Value value = variables.get(identifierToken.getName());
		if (value == null) {
			throw new RuntimeException(identifierToken, "Undefined variable: " + identifierToken.getName());
		}
		return value;
	}

	@Override
	public String toString() {
		return variables.toString();
	}
}
