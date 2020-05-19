package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.InvalidTypeException;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.tokens.value.Value;

public class MultiplicationToken extends BinaryOperatorToken<Token, Token> {

	public static final String OPERATOR_STRING = "*";

	public MultiplicationToken(FileIndex fileIndex) {
		super(OPERATOR_STRING, fileIndex);
	}

	public MultiplicationToken(Token left, Token right) throws ParsingException {
		super(OPERATOR_STRING, null);
		bind(left, right);
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value leftValue = getLeft().execute(scope);
		Value rightValue = getRight().execute(scope);
		if (leftValue.getType() != Value.Type.NUMBER) {
			throw new InvalidTypeException(getLeft(), Value.Type.NUMBER, leftValue.getType());
		}
		if (rightValue.getType() != Value.Type.NUMBER) {
			throw new InvalidTypeException(getRight(), Value.Type.NUMBER, rightValue.getType());
		}
		// TODO: Do properly, check if we need a double or not.
		int left = (int) ((NumberValue) leftValue).getJavaValue();
		int right = (int) ((NumberValue) rightValue).getJavaValue();
		return new NumberValue(left * right, getFileIndex());
	}

	@Override
	public void checkOperands(Token left, Token right) throws ParsingException {
		// TODO: Implement
	}

	@Override
	public String toString() {
		return isBound() ? "<Multiplication " + getLeft().toString() + " + " + getRight().toString() + ">" : "<Multiplication UNBOUND>";
	}
}
