package me.alexng.gns.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.util.StringUtil;

public class FunctionToken extends IdentifiedToken {

	private ParametersToken parameters;
	private BlockToken block;

	public FunctionToken(IdentifierToken identifier, ParametersToken parametersToken, BlockToken block, int startIndex, int endIndex) {
		super(identifier, startIndex, endIndex);
		this.parameters = parametersToken;
		this.block = block;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		scope.addFunction(this);
		return Value.NULL;
	}

	public Value executeFunction(FunctionCallToken caller, Scope callerScope, Value[] values) throws RuntimeException {
		IdentifierToken[] identifiers = parameters.getParameters();
		if (identifiers.length != values.length) {
			throw new RuntimeException(caller, "Invalid number of arguments. Expected: " + identifiers.length + ". Got: " + values.length);
		}

		// TODO: We need to pass the object scope here if created inside a class (which has a parent global scope).
		Scope functionScope = new Scope(callerScope.getGlobalScope());
		functionScope.addFunction(this);
		for (int i = 0; i < identifiers.length; i++) {
			functionScope.setLocalVariable(identifiers[i], values[i]);
		}

		block.executeBlock(functionScope);
		// TODO: Function return values
		return Value.NULL;
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
