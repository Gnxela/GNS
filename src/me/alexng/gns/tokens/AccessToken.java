package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Value;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.ObjectValue;
import me.alexng.gns.tokens.operators.BinaryOperatorToken;
import me.alexng.gns.util.ExceptionUtil;

public class AccessToken extends BinaryOperatorToken<Token, IdentifierToken> {

	public AccessToken(FileIndex fileIndex) {
		super(fileIndex);
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value leftValue = getLeft().execute(scope);
		if (leftValue instanceof ObjectValue) {
			ObjectValue objectValue = (ObjectValue) leftValue;
			return objectValue.getObjectScope().getLocalVariable(getRight());
		}
		throw new RuntimeException(getLeft(), "Invalid access. Must be of type OBJECT");
	}

	@Override
	public void checkOperands(Token left, Token right) throws ParsingException {
		// TODO: Check left types? It'll throw at runtime anyway
		if (!(right instanceof IdentifierToken)) {
			throw ExceptionUtil.createParsingExpected("Invalid access operand", IdentifierToken.class, right);
		}
	}

	@Override
	public String toString() {
		return isBound() ? "<Access " + getLeft().toString() + "." + getRight().toString() + ">" : "<Access UNBOUND>";
	}
}
