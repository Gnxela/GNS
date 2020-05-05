package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.InvalidTypeException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.*;
import me.alexng.gns.tokens.value.NullValue;
import me.alexng.gns.tokens.value.Value;

import java.util.HashMap;
import java.util.Map;

public class Scope {

	private Environment environment;
	private Scope parentScope;
	private boolean globalScope, objectScope;
	private Map<String, Token> values;

	public Scope(Environment environment, Scope parentScope, boolean globalScope, boolean objectScope) {
		this.environment = environment;
		this.parentScope = parentScope;
		this.globalScope = globalScope;
		this.objectScope = objectScope;
		this.values = new HashMap<>();
	}

	public static Scope createGlobalScope(Environment environment) {
		return new Scope(environment, null, true, false);
	}

	public static Scope createObjectScope(Scope parentScope) {
		return new Scope(parentScope.getEnvironment(), parentScope, false, true);
	}

	public Scope createChildScope() {
		return new Scope(environment, this, false, false);
	}

	public Token getLocal(IdentifierToken identifier) throws RuntimeException {
		return values.get(identifier.getName());
	}

	private Token get(IdentifierToken identifier) throws RuntimeException {
		Token value = getLocal(identifier);
		if (value != null) {
			return value;
		}
		if (parentScope != null) {
			return parentScope.get(identifier);
		}
		return NullValue.INTERNAL;
	}

	private Scope findValue(IdentifierToken identifier) throws RuntimeException {
		Token value = getLocal(identifier);
		if (value != null) {
			return this;
		}
		if (parentScope != null) {
			return parentScope.findValue(identifier);
		}
		return null;
	}

	public Value getValue(IdentifiedToken identifiedToken) throws RuntimeException {
		Token token = get(identifiedToken.getIdentifier());
		if (token instanceof Value) {
			return token.execute(this);
		} else {
			throw new InvalidTypeException(identifiedToken, Value.class, token);
		}
	}

	public FunctionToken getFunction(IdentifiedToken identifiedToken) throws RuntimeException {
		Token token = get(identifiedToken.getIdentifier());
		if (token instanceof FunctionToken) {
			return (FunctionToken) token;
		} else {
			throw new InvalidTypeException(identifiedToken, FunctionToken.class, token);
		}
	}

	public TemplateToken getTemplate(IdentifiedToken identifiedToken) throws RuntimeException {
		Token token = get(identifiedToken.getIdentifier());
		if (token instanceof TemplateToken) {
			return (TemplateToken) token;
		} else {
			throw new InvalidTypeException(identifiedToken, TemplateToken.class, token);
		}
	}

	public void set(IdentifiedToken identifiedToken, Token value) throws RuntimeException {
		Scope scope = findValue(identifiedToken.getIdentifier());
		if (scope != null) {
			scope.setLocal(identifiedToken, value);
		} else {
			setLocal(identifiedToken, value);
		}
	}

	public void setLocal(IdentifiedToken identifiedToken, Token value) throws RuntimeException {
		values.put(identifiedToken.getIdentifier().getName(), value);
	}

	public Scope getGlobalScope() throws RuntimeException {
		if (isGlobalScope()) {
			return this;
		}
		if (parentScope == null) {
			// This should only occur in the event of an error, as if the parent scope is null by definition it is a global scope.
			throw new RuntimeException(FileIndex.NULL_INDEX, "Global scope not found");
		}
		return parentScope.getGlobalScope();
	}

	public Scope getObjectScope() throws RuntimeException {
		if (isObjectScope()) {
			return this;
		}
		if (parentScope == null) {
			// This should only occur in the event of an error, as if the parent scope is null by definition it is a global scope.
			throw new RuntimeException(FileIndex.NULL_INDEX, "Object scope not found");
		}
		return parentScope.getObjectScope();
	}

	public boolean isObjectScope() {
		return objectScope;
	}

	public boolean isGlobalScope() {
		return globalScope;
	}

	public Environment getEnvironment() {
		return environment;
	}

	@Override
	public String toString() {
		return "<Scope global=" + globalScope + " object=" + objectScope + " var=" + values.toString() + ">";
	}
}
