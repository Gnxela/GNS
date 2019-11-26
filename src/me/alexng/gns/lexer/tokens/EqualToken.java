package me.alexng.gns.lexer.tokens;

import me.alexng.gns.lexer.BindableToken;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class EqualToken extends BindableToken {

	private Token left, right;

	EqualToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}

	public void bind(Token left, Token right) {
		super.bind();
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return isBound() ? "<Equal " + left.toString() + " == " + right.toString() + ">" : "<Equal UNBOUND>";
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