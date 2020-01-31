package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.Token;

public class AdditionToken extends BinaryOperatorToken<Token, Token> {

	public static final String OPERATOR_STRING = "+";

	public AdditionToken(FileIndex fileIndex) {
		super(OPERATOR_STRING, fileIndex);
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value objectValue = tryObjectOperation(scope);
		if (objectValue != null) {
			return objectValue;
		}

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
		return new NumberValue(left + right);
	}

	@Override
	public void checkOperands(Token left, Token right) throws ParsingException {
		// TODO: Implement
	}

	@Override
	public String toString() {
		return isBound() ? "<Addition " + getLeft().toString() + " + " + getRight().toString() + ">" : "<Addition UNBOUND>";
	}
}
