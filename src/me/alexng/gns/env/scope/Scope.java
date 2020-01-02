package me.alexng.gns.env.scope;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Value;
import me.alexng.gns.tokens.*;

public class Scope {

	VariableProvider variableProvider;
	ClassProvider classProvider;
	FunctionProvider functionProvider;
	private Environment environment;
	private Scope parentScope;
	private Scope objectScope;
	private Scope globalScope;

	private Scope(Environment environment, Scope parentScope, Scope objectScope, Scope globalScope, FunctionProvider parentFunctionProvider, ClassProvider parentClassProvider, VariableProvider parentVariableProvider) {
		this.variableProvider = new VariableProvider(parentVariableProvider);
		this.classProvider = new ClassProvider(parentClassProvider);
		this.functionProvider = new FunctionProvider(parentFunctionProvider);
		this.environment = environment;
		this.parentScope = parentScope;
		this.objectScope = objectScope;
		this.globalScope = globalScope;
	}

	public static Scope createGlobalScope(Environment environment) {
		Scope globalScope = new Scope(environment, null, null, null, null, null, null);
		globalScope.setGlobalScope(globalScope);
		return globalScope;
	}

	public Scope createChildScope() {
		return new Scope(environment, this, objectScope, globalScope, functionProvider, classProvider, variableProvider);
	}

	// TODO: May be used for nested classes, once I get around to it.
	public Scope createObjectScope(Scope parentScope) {
		Scope objectScope = new Scope(parentScope.getEnvironment(), parentScope, null, parentScope.getGlobalScope(), parentScope.functionProvider, parentScope.classProvider, parentScope.variableProvider);
		objectScope.setObjectScope(objectScope);
		return objectScope;
	}

	public Scope createObjectScope() {
		return createObjectScope(globalScope);
	}

	public void addClass(ClassToken classToken) throws RuntimeException {
		classProvider.set(classToken);
	}

	public ClassToken getClass(IdentifierToken identifierToken) throws RuntimeException {
		return classProvider.get(identifierToken);
	}

	public void setVariable(IdentifierToken identifierToken, Value value) throws RuntimeException {
		variableProvider.set(identifierToken, value);
	}

	public Value getVariable(IdentifierToken identifierToken) throws RuntimeException {
		return variableProvider.get(identifierToken);
	}

	public Value getLocalVariable(IdentifierToken identifierToken) {
		return variableProvider.getLocal(identifierToken);
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
		variableProvider.setLocal(new IdentifierToken(identifier, FileIndex.INTERNAL_INDEX), value);
	}

	public void setLocalVariable(IdentifierToken identifierToken, Value value) {
		setLocalVariable(identifierToken.getName(), value);
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
		return "<Scope var={}. func={}. class={}>";
	}
}
