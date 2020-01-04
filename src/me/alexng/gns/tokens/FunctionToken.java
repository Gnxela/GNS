package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Value;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.ReturnedValue;
import me.alexng.gns.util.StringUtil;

public class FunctionToken extends IdentifiedToken {

	private ParametersToken parameters;
	private BlockToken block;

	public FunctionToken(IdentifierToken identifier, ParametersToken parametersToken, BlockToken block, FileIndex fileIndex) {
		super(identifier, fileIndex);
		this.parameters = parametersToken;
		this.block = block;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		scope.functionProvider.set(this);
		return Value.NULL;
	}

	public Value unwrapAndExecuteFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
		Value returnedValue = executeFunction(this, parentScope, values);
		if (returnedValue instanceof ReturnedValue) {
			return ((ReturnedValue) returnedValue).getJavaValue();
		}
		return Value.NULL;
	}

	public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
		IdentifierToken[] identifiers = parameters.getParameters();
		if (identifiers.length != values.length) {
			throw new RuntimeException(caller, "Invalid number of arguments. Expected: " + identifiers.length + ". Got: " + values.length);
		}

		Scope functionScope = parentScope.createChildScope();
		for (int i = 0; i < identifiers.length; i++) {
			functionScope.variableProvider.setLocal(identifiers[i], values[i]);
		}

		return block.executeBlock(functionScope);
	}

	public String getName() {
		return getIdentifier().getName();
	}

	@Override
	public String toString() {
		return "<Function " + getIdentifier().getName()
				+ StringUtil.indent(block.toString())
				+ ">";
	}
}
