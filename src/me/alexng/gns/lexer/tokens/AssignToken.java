package me.alexng.gns.lexer.tokens;

import me.alexng.gns.GNSException;
import me.alexng.gns.ParsingException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.BinaryOperationToken;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class AssignToken extends BinaryOperationToken<IdentifierToken, Token> {

	private AssignToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}

	@Override
	public void checkOperands(Token left, Token right) throws ParsingException {
		if (!(left instanceof IdentifierToken)) {
			throw new ParsingException(left, "Invalid type");
		}
	}

	@Override
	public Value execute(Scope scope) throws GNSException {
		Value returnedValue = getRight().execute(scope);
		scope.setVariable(getLeft(), returnedValue);
		return returnedValue;
	}

	@Override
	public String toString() {
		return isBound() ? "<Assign " + getLeft().toString() + " = " + getRight().toString() + ">" : "<Assign UNBOUND>";
	}

	public static class Generator implements TokenGenerator {

		private final char CHAR = '=';

		@Override
		public int accepts(String input, int index) {
			return index + (input.charAt(index) == CHAR ? 1 : 0);
		}

		@Override
		public Token generate(String input, int startIndex, int endIndex) {
			return new AssignToken(startIndex, endIndex);
		}
	}
}