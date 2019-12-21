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
	private Environment environment;
	private Scope parentScope;
	private Scope globalScope;
	private Scope objectScope;

	private Scope(Environment environment, Scope parentScope, Scope objectScope, Scope globalScope) {
		this.variables = new HashMap<>();
		this.classes = new HashMap<>();
		this.functions = new LinkedList<>();
		this.environment = environment;
		this.parentScope = parentScope;
		this.objectScope = objectScope;
		this.globalScope = globalScope;
	}

	public static Scope createGlobalScope(Environment environment) {
		Scope globalScope = new Scope(environment, null, null, null);
		globalScope.setGlobalScope(globalScope);
		return globalScope;
	}

	public Scope createChildScope() {
		return new Scope(environment, this, objectScope, globalScope);
	}

	// TODO: May be used for nested classes, once I get around to it.
	public Scope createObjectScope(Scope parentScope) {
		Scope objectScope = new Scope(parentScope.getEnvironment(), parentScope, null, parentScope.getGlobalScope());
		objectScope.setObjectScope(objectScope);
		return objectScope;
	}

	public Scope createObjectScope() {
		return createObjectScope(globalScope);
	}

	public void addClass(ClassToken classToken) throws RuntimeException {
		try {
			// TODO: Not sure if I like the idea of just waiting for an exception, see later
			getClass(classToken.getIdentifier());
			throw new RuntimeException(classToken, "Class already defined");
		} catch (RuntimeException ignored) {
			classes.put(classToken.getIdentifier().getName(), classToken);
		}
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

	public void addFunction(FunctionToken functionToken) throws RuntimeException {
		if (getLocalFunction(functionToken.getName()) != null) {
			// TODO: This is only checking the local scope. We need to check up the stack.
			//  I'm still not sure if I want to allow nested functions etc. so I'll leave it for now.
			throw new RuntimeException(functionToken, "Function already defined");
		}
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
	public FunctionToken getLocalFunction(CharSequence identifier) {
		for (FunctionToken functionToken : functions) {
			if (functionToken.getName().contentEquals(identifier)) {
				return functionToken;
			}
		}
		return null;
	}

	public void setLocalVariable(String identifier, Value value) {
		variables.put(identifier, value);
	}

	public void setLocalVariable(IdentifierToken identifierToken, Value value) {
		setLocalVariable(identifierToken.getName(), value);
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

	public Environment getEnvironment() {
		return environment;
	}

	@Override
	public String toString() {
		return "<Scope var=" + variables.toString() + ". func={" + StringUtil.unrollIdentifiedListInline(functions) + "}. class={" + StringUtil.unrollIdentifiedMapInline(classes) + "}>";
	}
}
