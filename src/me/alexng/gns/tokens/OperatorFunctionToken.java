package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Value;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.tokens.operators.OperatorToken;

public class OperatorFunctionToken extends FunctionToken {

	public OperatorFunctionToken(OperatorToken operatorToken, ParametersToken parametersToken, BlockToken block, FileIndex fileIndex) {
		super(operatorToken.getIdentifier(), parametersToken, block, fileIndex);
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		scope.operatorFunctionProvider.set(this);
		return Value.NULL;
	}
}
