package me.alexng.gns.env;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.FunctionToken;
import me.alexng.gns.tokens.IdentifiedToken;
import me.alexng.gns.tokens.IdentifierToken;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Scope {

	// TODO: Think about how we're storing this data. A map is a lot of overhead
	private Map<String, Value> variables;
	private List<FunctionToken> functions;
	private Scope parentScope;
	private Scope globalScope;

	private Scope(Scope parentScope, Scope globalScope) {
		this.variables = new HashMap<>();
		this.functions = new LinkedList<>();
		this.parentScope = parentScope;
		this.globalScope = globalScope;
	}

	private Scope(Scope parentScope) {
		this(parentScope, null);
		this.globalScope = this;
	}

	public static Scope createGlobalScope() {
		return new Scope(null);
	}

	public Scope createChildScope() {
		return new Scope(this, globalScope);
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
			if (parentScope == null) {
				throw new RuntimeException(identifierToken, "Undefined variable: " + identifierToken.getName());
			}
			return parentScope.getVariable(identifierToken);
		}
		return value;
	}

	public void addFunction(FunctionToken functionToken) {
		functions.add(functionToken);
	}

	public FunctionToken getFunction(IdentifiedToken identifiedToken) throws RuntimeException {
		FunctionToken function = getLocalFunction(identifiedToken);
		if (function != null) {
			return function;
		}
		if (parentScope != null) {
			return parentScope.getFunction(identifiedToken);
		}
		throw new RuntimeException(identifiedToken, "Function not found: " + identifiedToken.getIdentifier().getName());
	}

	private FunctionToken getLocalFunction(IdentifiedToken identifierToken) {
		for (FunctionToken functionToken : functions) {
			if (functionToken.getName().equals(identifierToken.getIdentifier().getName())) {
				return functionToken;
			}
		}
		return null;
	}

	public void setLocalVariable(IdentifierToken identifierToken, Value value) {
		variables.put(identifierToken.getName(), value);
	}

	private Scope findScopeWithVariable(IdentifierToken identifierToken) {
		if (variables.containsKey(identifierToken.getName())) {
			return this;
		}
		if (parentScope == null) {
			return null;
		}
		return parentScope.findScopeWithVariable(identifierToken);
	}

	public Scope getGlobalScope() {
		return globalScope;
	}

	private boolean isGlobalScope() {
		return parentScope == null;
	}

	@Override
	public String toString() {
		return variables.toString();
	}
}
