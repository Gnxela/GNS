package me.alexng.gns.env.scope;

import me.alexng.gns.env.Environment;

public class Scope {

	public VariableProvider variableProvider;
	public ClassProvider classProvider;
	public FunctionProvider functionProvider;
	private Environment environment;
	private Scope parentScope;
	private Scope objectScope;
	private Scope globalScope;

	private Scope(Environment environment, Scope parentScope, Scope objectScope, Scope globalScope, FunctionProvider functionProvider, ClassProvider classProvider, VariableProvider variableProvider) {
		this.environment = environment;
		this.parentScope = parentScope;
		this.objectScope = objectScope;
		this.globalScope = globalScope;
		this.variableProvider = variableProvider;
		this.classProvider = classProvider;
		this.functionProvider = functionProvider;
	}

	public static Scope createGlobalScope(Environment environment) {
		Scope globalScope = new Scope(environment,
				null, null, null,
				new FunctionProvider(null),
				new ClassProvider(null),
				new VariableProvider(null));
		globalScope.setGlobalScope(globalScope);
		return globalScope;
	}

	public Scope createChildScope() {
		return new Scope(environment,
				this, objectScope, globalScope,
				new FunctionProvider(functionProvider),
				new ClassProvider(classProvider),
				new VariableProvider(variableProvider));
	}

	private Scope createObjectScope(Scope parentScope) {
		Scope objectScope = new Scope(parentScope.getEnvironment(),
				parentScope,
				null,
				parentScope.getGlobalScope(),
				new FunctionProvider(parentScope.functionProvider),
				new ClassProvider(parentScope.classProvider),
				new VariableProvider(parentScope.variableProvider));
		objectScope.setObjectScope(objectScope);
		return objectScope;
	}

	public Scope createObjectScope() {
		return createObjectScope(globalScope);
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
