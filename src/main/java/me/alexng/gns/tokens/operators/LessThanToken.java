package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.value.BooleanValue;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.tokens.value.Value;

public class LessThanToken extends BinaryOperatorToken<Token, Token> {

	public static final String OPERATOR_STRING = "<";

	public LessThanToken(FileIndex fileIndex) {
		super(OPERATOR_STRING, fileIndex);
	}

	public LessThanToken(Token left, Token right) throws ParsingException {
		super(OPERATOR_STRING, null);
		bind(left, right);
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value leftValue = getLeft().execute(scope);
		Value rightValue = getRight().execute(scope);
		if (leftValue.getType() != Value.Type.NUMBER) {
			throw new RuntimeException(getLeft(), "Invalid type. Expected: NUMBER. Received: " + leftValue.getType());
		}
		if (rightValue.getType() != Value.Type.NUMBER) {
			throw new RuntimeException(getRight(), "Invalid type. Expected: NUMBER. Received: " + rightValue.getType());
		}
		// TODO: Do properly, check if we need a double or not.
		int left = (int) ((NumberValue) leftValue).getJavaValue();
		int right = (int) ((NumberValue) rightValue).getJavaValue();
		return BooleanValue.valueOf(left < right);
	}

	@Override
	public void checkOperands(Token left, Token right) throws ParsingException {
		// TODO: Implement
	}

	@Override
	public String toString() {
		return isBound() ? "<LessThan " + getLeft().toString() + " + " + getRight().toString() + ">" : "<LessThan UNBOUND>";
	}
}
