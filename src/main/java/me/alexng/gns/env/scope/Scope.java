package me.alexng.gns.env.scope;

import me.alexng.gns.env.Environment;

public class Scope {

	public NameProvider nameProvider;
	public VariableProvider variableProvider;
	public ClassProvider classProvider;
	public FunctionProvider functionProvider;
	public FunctionProvider operatorFunctionProvider;
	private Environment environment;
	private Scope parentScope;
	private Scope objectScope;
	private Scope globalScope;

	private Scope(Environment environment,
				  Scope parentScope,
				  Scope objectScope,
				  Scope globalScope,
				  NameProvider nameProvider,
				  FunctionProvider functionProvider,
				  ClassProvider classProvider,
				  VariableProvider variableProvider,
				  FunctionProvider operatorFunctionProvider) {
		this.environment = environment;
		this.parentScope = parentScope;
		this.objectScope = objectScope;
		this.globalScope = globalScope;
		this.nameProvider = nameProvider;
		this.variableProvider = variableProvider;
		this.classProvider = classProvider;
		this.functionProvider = functionProvider;
		this.operatorFunctionProvider = operatorFunctionProvider;
	}

	public static Scope createGlobalScope(Environment environment) {
		Scope globalScope = new Scope(environment,
				null, null, null,
				new NameProvider(""),
				new FunctionProvider(null),
				new ClassProvider(null),
				new VariableProvider(null),
				new FunctionProvider(null));
		globalScope.setGlobalScope(globalScope);
		return globalScope;
	}

	public Scope createChildScope() {
		return new Scope(environment,
				this, objectScope, globalScope,
				nameProvider,
				new FunctionProvider(functionProvider),
				new ClassProvider(classProvider),
				new VariableProvider(variableProvider),
				new FunctionProvider(operatorFunctionProvider));
	}

	public static Scope createObjectScope(String name, Scope parentScope) {
		Scope objectScope = new Scope(parentScope.getEnvironment(),
				parentScope,
				null,
				parentScope.getGlobalScope(),
				parentScope.nameProvider.extend(name, "."),
				new FunctionProvider(parentScope.functionProvider),
				new ClassProvider(parentScope.classProvider),
				new VariableProvider(parentScope.variableProvider),
				new FunctionProvider(parentScope.operatorFunctionProvider));
		objectScope.setObjectScope(objectScope);
		return objectScope;
	}

	public Scope createObjectScope(String name) {
		return createObjectScope(name, globalScope);
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
		return "<Scope \n\tvar=" + variableProvider + ".\n\tfunc=" + functionProvider.toString() + ".\n\topFunc=" + operatorFunctionProvider.toString() + ".\n\tclass=" + classProvider.toString() + ">";
	}
}
