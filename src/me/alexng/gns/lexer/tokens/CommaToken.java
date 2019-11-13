package me.alexng.gns.lexer.tokens;

import me.alexng.gns.AmbiguousParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class CommaToken extends Token {

	@Override
	public String toString() {
		return "<Comma >";
	}

	public static class CommaGenerator implements TokenGenerator {
		@Override
		public int accepts(String input) {
			return input.length() > 0 && input.charAt(0) == ',' ? 1 : 0;
		}

		@Override
		public Token generate(String input) throws AmbiguousParsingException {
			return new CommaToken();
		}
	}
}