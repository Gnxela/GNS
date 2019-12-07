package me.alexng.gns.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.BooleanValue;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;

public class EqualToken extends BinaryOperationToken<Token, Token> {

	public EqualToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value leftValue = getLeft().execute(scope);
		Value rightValue = getRight().execute(scope);
		if (leftValue.getType() != rightValue.getType()) {
			return BooleanValue.FALSE;
		}

		switch (leftValue.getType()) {
			case NULL:
				return BooleanValue.TRUE;
			case OBJECT:
				// TODO: Check references
			default:
				return BooleanValue.valueOf(leftValue.getValue() == rightValue.getValue());
		}
	}

	@Override
	public void checkOperands(Token left, Token right) {
		// TODO: Implement this
	}

	@Override
	public String toString() {
		return isBound() ? "<Equal " + getLeft().toString() + " == " + getRight().toString() + ">" : "<Equal UNBOUND>";
	}
}