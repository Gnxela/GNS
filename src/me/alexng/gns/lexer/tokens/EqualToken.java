package me.alexng.gns.lexer.tokens;

import me.alexng.gns.AmbiguousParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class EqualToken extends Token {

	@Override
	public String toString() {
		return "<Assign >";
	}

	public static class EqualGenerator implements TokenGenerator {

		private final char CHAR = '=';

		@Override
		public int accepts(String input) {
			return input.length() > 1 && input.charAt(0) == CHAR && input.charAt(1) == CHAR ? 2 : 0;
		}

		@Override
		public Token generate(String input) throws AmbiguousParsingException {
			return new EqualToken();
		}
	}
}