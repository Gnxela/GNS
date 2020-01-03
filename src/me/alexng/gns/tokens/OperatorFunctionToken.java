package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Value;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.tokens.operators.OperatorToken;

public class OperatorFunctionToken extends FunctionToken {

	private OperatorToken operatorToken;

	public OperatorFunctionToken(OperatorToken operatorToken, ParametersToken parametersToken, BlockToken block, FileIndex fileIndex) {
		// TODO: Replace NULL with operator string
		super(new IdentifierToken("NULL", operatorToken.getFileIndex()), parametersToken, block, fileIndex);
		this.operatorToken = operatorToken;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		// TODO: Need a special place for these
		// scope.addFunction(this);
		return Value.NULL;
	}
}
