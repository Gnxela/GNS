package me.alexng.gns.lexer.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.BooleanValue;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.BinaryOperationToken;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class EqualToken extends BinaryOperationToken {

	private EqualToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}

    @Override
    public Value execute(Scope scope) throws RuntimeException {
        Value leftValue = getValue(getLeft(), scope);
        Value rightValue = getValue(getRight(), scope);
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

    private Value getValue(Token token, Scope scope) throws RuntimeException {
        if (token instanceof IdentifierToken) {
            return scope.getVariable(((IdentifierToken) token));
        }
        return token.execute(scope);
    }

    @Override
    public void checkOperands(Token left, Token right) {
        // TODO: Implement this
    }

	@Override
	public String toString() {
		return isBound() ? "<Equal " + getLeft().toString() + " == " + getRight().toString() + ">" : "<Equal UNBOUND>";
	}

	public static class Generator implements TokenGenerator {

		private final char CHAR = '=';

		@Override
		public int accepts(String input, int index) {
			return index + (input.length() > index + 1 && input.charAt(index) == CHAR && input.charAt(index + 1) == CHAR ? 2 : 0);
		}

		@Override
		public Token generate(String input, int startIndex, int endIndex) {
			return new EqualToken(startIndex, endIndex);
		}
	}
}