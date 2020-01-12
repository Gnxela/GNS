package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.RawObjectValue;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.IdentifiedToken;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.util.ExceptionUtil;

public class AccessToken extends BinaryOperatorToken<Token, IdentifiedToken> {

	public static final String OPERATOR_STRING = ".";

	public AccessToken(FileIndex fileIndex) {
		super(OPERATOR_STRING, fileIndex);
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value leftValue = getLeft().execute(scope);
		if (leftValue instanceof RawObjectValue) {
			RawObjectValue objectValue = (RawObjectValue) leftValue;
			return getRight().execute(objectValue.getObjectScope());
		}
		throw ExceptionUtil.createRuntimeExpected("Invalid access", getLeft(), Value.Type.OBJECT, leftValue.getType());
	}

	@Override
	public void checkOperands(Token left, Token right) throws ParsingException {
		if (!(right instanceof IdentifiedToken)) {
			throw ExceptionUtil.createParsingExpected("Invalid access operand", IdentifierToken.class, right);
		}
	}

	@Override
	public String toString() {
		return isBound() ? "<Access " + getLeft().toString() + "." + getRight().toString() + ">" : "<Access UNBOUND>";
	}
}
