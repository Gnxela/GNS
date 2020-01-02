package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.BooleanValue;
import me.alexng.gns.env.Value;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.tokens.Token;

public class EqualToken extends BinaryOperationToken<Token, Token> {

	public EqualToken(FileIndex fileIndex) {
		super(fileIndex);
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
				return BooleanValue.valueOf(leftValue.getJavaValue() == rightValue.getJavaValue());
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