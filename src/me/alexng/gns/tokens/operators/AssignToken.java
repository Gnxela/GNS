package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.tokens.BinaryOperationToken;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.util.ExceptionUtil;

public class AssignToken extends BinaryOperationToken<IdentifierToken, Token> {

	public AssignToken(FileIndex fileIndex) {
		super(fileIndex);
	}

	@Override
	public void checkOperands(Token left, Token right) throws ParsingException {
		if (!(left instanceof IdentifierToken)) {
			throw ExceptionUtil.createParsingExpected("Invalid type", IdentifierToken.class, left);
		}
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value returnedValue = getRight().execute(scope);
		scope.setVariable(getLeft(), returnedValue);
		return returnedValue;
	}

	@Override
	public String toString() {
		return isBound() ? "<Assign " + getLeft().toString() + " = " + getRight().toString() + ">" : "<Assign UNBOUND>";
	}
}