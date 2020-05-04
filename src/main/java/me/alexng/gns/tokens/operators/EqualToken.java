package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.value.BooleanValue;
import me.alexng.gns.tokens.value.Value;

public class EqualToken extends BinaryOperatorToken<Token, Token> {

	public static final String OPERATOR_STRING = "==";

	public EqualToken(FileIndex fileIndex) {
		super(OPERATOR_STRING, fileIndex);
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
				return BooleanValue.valueOf(leftValue == rightValue);
			default:
				return BooleanValue.valueOf(leftValue.getJavaValue().equals(rightValue.getJavaValue()));
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