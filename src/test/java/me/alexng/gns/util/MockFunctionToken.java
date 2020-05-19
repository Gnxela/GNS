package me.alexng.gns.util;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.*;
import me.alexng.gns.tokens.value.NullValue;
import me.alexng.gns.tokens.value.Value;

public class MockFunctionToken extends FunctionToken {

	public Value[] values;

	public MockFunctionToken(IdentifierToken identifier, ParametersToken parametersToken, BlockToken block, FileIndex fileIndex) {
		super(identifier, parametersToken, block, fileIndex);
	}

	@Override
	public Value executeFunction(Token caller, Scope parentScope, Value[] values) throws RuntimeException {
		this.values = values;
		return NullValue.INTERNAL;
	}
}
