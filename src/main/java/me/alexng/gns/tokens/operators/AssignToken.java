package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.value.Value;
import me.alexng.gns.util.ExceptionUtil;

public class AssignToken extends BinaryOperatorToken<Token, Token> {

	public static final String OPERATOR_STRING = "=";

	public AssignToken(FileIndex fileIndex) {
		super(OPERATOR_STRING, fileIndex);
	}

	public AssignToken(Token left, Token right) throws ParsingException {
		super(OPERATOR_STRING, null);
		bind(left, right);
	}

	@Override
	public void checkOperands(Token left, Token right) throws ParsingException {
		if (!(left instanceof IdentifierToken || left instanceof AccessToken)) {
			throw ExceptionUtil.createParsingExpected("Invalid type", IdentifierToken.class, left);
		}
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value returnedValue;
		if (getLeft() instanceof AccessToken) {
			returnedValue = getRight().execute(scope);
			((AccessToken) getLeft()).setValue(scope, returnedValue);
		} else {
			returnedValue = getRight().execute(scope);
			scope.set((IdentifierToken) getLeft(), returnedValue.wrap());
		}
		return returnedValue;
	}

	@Override
	public String toString() {
		return isBound() ? "<Assign " + getLeft().toString() + " = " + getRight().toString() + ">" : "<Assign UNBOUND>";
	}
}