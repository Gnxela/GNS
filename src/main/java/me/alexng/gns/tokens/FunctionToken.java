package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.NullValue;
import me.alexng.gns.tokens.value.ReturnedValue;
import me.alexng.gns.tokens.value.Value;
import me.alexng.gns.util.StringUtil;

public class FunctionToken extends IdentifiedToken {

	public static final IdentifierToken ANONYMOUS_IDENTIFIER = new IdentifierToken("<ANONYMOUS>", FileIndex.INTERNAL_INDEX);

	private ParametersToken parameters;
	private BlockToken block;

	public FunctionToken(IdentifierToken identifier, ParametersToken parametersToken, BlockToken block, FileIndex fileIndex) {
		super(identifier, fileIndex);
		this.parameters = parametersToken;
		this.block = block;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		scope.set(this, this);
		return NullValue.INTERNAL;
	}

	public Value unwrapAndExecuteFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
		Value returnedValue = executeFunction(caller, parentScope, values);
		if (returnedValue instanceof ReturnedValue) {
			return ((ReturnedValue) returnedValue).getJavaValue();
		}
		return NullValue.INTERNAL;
	}

	public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
		IdentifierToken[] identifiers = parameters.getParameters();
		if (identifiers.length != values.length) {
			throw new RuntimeException(caller, "Invalid number of arguments. Expected: " + identifiers.length + ". Got: " + values.length);
		}

		Scope functionScope = parentScope.createChildScope();
		for (int i = 0; i < identifiers.length; i++) {
			// TODO: This will create new object for EVERY scope, the values in the objects will never change and so we should just create one and reuse it to save memory.
			functionScope.set(identifiers[i], values[i]);
		}

		return block.executeBlock(functionScope);
	}

	public boolean isAnonymous() {
		return getIdentifier().equals(ANONYMOUS_IDENTIFIER);
	}

	public String getName() {
		return getIdentifier().getName();
	}

	@Override
	public String toString() {
		return "<Function " + getIdentifier().getName()
				+ StringUtil.indent(block.toString())
				+ ">" + isAnonymous();
	}
}
