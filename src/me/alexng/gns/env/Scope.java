package me.alexng.gns.env;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.lexer.tokens.FunctionToken;
import me.alexng.gns.lexer.tokens.IdentifierToken;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Scope {

	// TODO: Think about how we're storing this data. A map is a lot of overhead
	private Map<String, Value> variables;
	private List<FunctionToken> functions;
	private Scope parent;

	public Scope(Scope parent) {
		this.variables = new HashMap<>();
		this.functions = new LinkedList<>();
		this.parent = parent;
	}

	Scope() {
		this(null);
	}

	public void setVariable(IdentifierToken identifierToken, Value value) {
		Scope scope = findScopeWithVariable(identifierToken);
		if (scope == null) {
			scope = this;
		}
		scope.setLocalVariable(identifierToken, value);
	}

	public Value getVariable(IdentifierToken identifierToken) throws RuntimeException {
		Value value = variables.get(identifierToken.getName());
		if (value == null) {
			if (parent == null) {
				throw new RuntimeException(identifierToken, "Undefined variable: " + identifierToken.getName());
			}
			return parent.getVariable(identifierToken);
		}
		return value;
	}

	public void addFunction(FunctionToken functionToken) {
		functions.add(functionToken);
	}

	private void setLocalVariable(IdentifierToken identifierToken, Value value) {
		variables.put(identifierToken.getName(), value);
	}

	private Scope findScopeWithVariable(IdentifierToken identifierToken) {
		if (variables.containsKey(identifierToken.getName())) {
			return this;
		}
		if (parent == null) {
			return null;
		}
		return parent.findScopeWithVariable(identifierToken);
	}

	@Override
	public String toString() {
		return variables.toString();
	}
}
