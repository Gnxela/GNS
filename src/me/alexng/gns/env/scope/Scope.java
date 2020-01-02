package me.alexng.gns.env.scope;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Value;
import me.alexng.gns.tokens.*;
import me.alexng.gns.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class Scope {

	FunctionProvider functionProvider;
	private Environment environment;
	private Scope parentScope;
	private Scope globalScope;

	// TODO: Think about how we're storing this data. A map is a lot of overhead
	private Map<String, Value> variables;
	private Map<String, ClassToken> classes;
	private Scope objectScope;

	private Scope(Environment environment, Scope parentScope, Scope objectScope, Scope globalScope, FunctionProvider parentFunctionProvider) {
		this.variables = new HashMap<>();
		this.classes = new HashMap<>();
		this.functionProvider = new FunctionProvider(parentFunctionProvider);
		this.environment = environment;
		this.parentScope = parentScope;
		this.objectScope = objectScope;
		this.globalScope = globalScope;
	}

	public static Scope createGlobalScope(Environment environment) {
		Scope globalScope = new Scope(environment, null, null, null, null);
		globalScope.setGlobalScope(globalScope);
		return globalScope;
	}

	public Scope createChildScope() {
		return new Scope(environment, this, objectScope, globalScope, functionProvider);
	}

	// TODO: May be used for nested classes, once I get around to it.
	public Scope createObjectScope(Scope parentScope) {
		Scope objectScope = new Scope(parentScope.getEnvironment(), parentScope, null, parentScope.getGlobalScope(), parentScope.functionProvider);
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
		Value value = getLocalVariable(identifierToken);
		if (value != null) {
			return value;
		}
		if (parentScope != null) {
			return parentScope.getVariable(identifierToken);
		}
		return Value.NULL;
	}

	public Value getLocalVariable(IdentifierToken identifierToken) {
		return variables.getOrDefault(identifierToken.getName(), null);
	}

	public void addFunction(FunctionToken functionToken) throws RuntimeException {
		functionProvider.setLocal(functionToken);
	}

	public FunctionToken getFunction(IdentifiedToken identifiedToken) throws RuntimeException {
		return functionProvider.get(identifiedToken);
	}

	public FunctionToken getLocalFunction(Token caller, CharSequence identifier) {
		return functionProvider.getLocal(new IdentifierToken(identifier.toString(), caller.getFileIndex()));
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
		return "<Scope var=" + variables.toString() + ". func={}. class={" + StringUtil.unrollIdentifiedMapInline(classes) + "}>";
	}
}
