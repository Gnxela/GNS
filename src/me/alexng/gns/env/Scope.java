package me.alexng.gns.env;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.ClassToken;
import me.alexng.gns.tokens.FunctionToken;
import me.alexng.gns.tokens.IdentifiedToken;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.util.StringUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Scope {

	// TODO: Think about how we're storing this data. A map is a lot of overhead
	private Map<String, Value> variables;
	private Map<String, ClassToken> classes;
	private List<FunctionToken> functions;
	private Scope parentScope;
	private Scope objectScope;
	private Scope globalScope;

	private Scope(Scope parentScope, Scope objectScope, Scope globalScope) {
		this.variables = new HashMap<>();
		this.classes = new HashMap<>();
		this.functions = new LinkedList<>();
		this.parentScope = parentScope;
		this.objectScope = objectScope;
		this.globalScope = globalScope;
	}

	public static Scope createGlobalScope() {
		Scope globalScope = new Scope(null, null, null);
		globalScope.setGlobalScope(globalScope);
		return globalScope;
	}

	public Scope createChildScope() {
		return new Scope(this, objectScope, globalScope);
	}

	public Scope createObjectScope(Scope parentScope) {
		Scope objectScope = new Scope(parentScope, null, globalScope);
		objectScope.setObjectScope(objectScope);
		return objectScope;
	}

	public Scope createObjectScope() {
		return createObjectScope(globalScope);
	}

	public void addClass(ClassToken classToken) {
		classes.put(classToken.getIdentifier().getName(), classToken);
	}

	public ClassToken getClass(IdentifierToken identifierToken) throws RuntimeException {
		ClassToken classToken = classes.get(identifierToken.getName());
		if (classToken != null) {
			return classToken;
		}
		if (parentScope != null) {
			return parentScope.getClass(identifierToken);
		}
		throw new RuntimeException(identifierToken, "Undefined class: " + identifierToken.getName());
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
		if (value != null) {
			return value;
		}
		if (parentScope != null) {
			return parentScope.getVariable(identifierToken);
		}
		return Value.NULL;
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
		throw new RuntimeException(identifiedToken, "Undefined function: " + identifiedToken.getIdentifier().getName());
	}

	// TODO: Do I want to use char sequences? If so replace all, if not think of alternative for getting constructor.
	public FunctionToken getLocalFunction(CharSequence charSequence) {
		for (FunctionToken functionToken : functions) {
			if (functionToken.getName().contentEquals(charSequence)) {
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

	public Scope getObjectScope() {
		return objectScope;
	}

	private void setObjectScope(Scope objectScope) {
		this.objectScope = objectScope;
	}

	public Scope getGlobalScope() {
		return globalScope;
	}

	public boolean isGlobalScope() {
		return globalScope == this;
	}

	private void setGlobalScope(Scope globalScope) {
		this.globalScope = globalScope;
	}

	@Override
	public String toString() {
		return "<Scope var=" + variables.toString() + ". func={" + StringUtil.unrollIdentifiedListInline(functions) + "}. class={" + StringUtil.unrollIdentifiedMapInline(classes) + "}>";
	}
}
