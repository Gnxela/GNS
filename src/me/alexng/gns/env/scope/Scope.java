package me.alexng.gns.env.scope;

import me.alexng.gns.env.Environment;

public class Scope {

	public VariableProvider variableProvider;
	public ClassProvider classProvider;
	public FunctionProvider functionProvider;
	public OperatorFunctionProvider operatorFunctionProvider;
	private Environment environment;
	private Scope parentScope;
	private Scope objectScope;
	private Scope globalScope;

	private Scope(Environment environment,
				  Scope parentScope,
				  Scope objectScope,
				  Scope globalScope,
				  FunctionProvider functionProvider,
				  ClassProvider classProvider,
				  VariableProvider variableProvider,
				  OperatorFunctionProvider operatorFunctionProvider) {
		this.environment = environment;
		this.parentScope = parentScope;
		this.objectScope = objectScope;
		this.globalScope = globalScope;
		this.variableProvider = variableProvider;
		this.classProvider = classProvider;
		this.functionProvider = functionProvider;
		this.operatorFunctionProvider = operatorFunctionProvider;
	}

	public static Scope createGlobalScope(Environment environment) {
		Scope globalScope = new Scope(environment,
				null, null, null,
				new FunctionProvider(null),
				new ClassProvider(null),
				new VariableProvider(null),
				new OperatorFunctionProvider(null));
		globalScope.setGlobalScope(globalScope);
		return globalScope;
	}

	public Scope createChildScope() {
		return new Scope(environment,
				this, objectScope, globalScope,
				new FunctionProvider(functionProvider),
				new ClassProvider(classProvider),
				new VariableProvider(variableProvider),
				new OperatorFunctionProvider(operatorFunctionProvider));
	}

	private Scope createObjectScope(Scope parentScope) {
		Scope objectScope = new Scope(parentScope.getEnvironment(),
				parentScope,
				null,
				parentScope.getGlobalScope(),
				new FunctionProvider(parentScope.functionProvider),
				new ClassProvider(parentScope.classProvider),
				new VariableProvider(parentScope.variableProvider),
				new OperatorFunctionProvider(parentScope.operatorFunctionProvider));
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
		return "<Scope \n\tvar=" + variableProvider + ".\n\tfunc=" + functionProvider.toString() + ".\n\topFunc=" + operatorFunctionProvider.toString() + ".\n\tclass=" + classProvider.toString() + ">";
	}
}
