package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.InvalidTypeException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.*;

import java.util.HashMap;
import java.util.Map;

public class Scope {

	private Environment environment;
	private Scope parentScope;
	private boolean globalScope, objectScope;
	private Map<String, Token> values;

	private Scope(Environment environment, Scope parentScope, boolean globalScope, boolean objectScope) {
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

	private Token get(IdentifierToken identifier) {
		Token value = values.get(identifier.getName());
		if (value != null) {
			return value;
		}
		if (parentScope != null) {
			return parentScope.get(identifier);
		}
		return ValueToken.NULL;
	}

	private Scope findValue(IdentifierToken identifier) {
		Token value = values.get(identifier.getName());
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
		if (token instanceof ValueToken) {
			return token.execute(this);
		} else {
			throw new InvalidTypeException(identifiedToken, ValueToken.class, token);
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

	public ClassToken getTemplate(IdentifiedToken identifiedToken) throws RuntimeException {
		Token token = get(identifiedToken.getIdentifier());
		if (token instanceof ClassToken) {
			return (ClassToken) token;
		} else {
			throw new InvalidTypeException(identifiedToken, ClassToken.class, token);
		}
	}

	public void set(IdentifiedToken identifiedToken, Token value) {
		Scope scope = findValue(identifiedToken.getIdentifier());
		if (scope != null) {
			scope.setLocal(identifiedToken, value);
		} else {
			setLocal(identifiedToken, value);
		}
	}

	private void setLocal(IdentifiedToken identifiedToken, Token value) {
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
