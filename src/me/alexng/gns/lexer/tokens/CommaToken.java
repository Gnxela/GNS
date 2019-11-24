package me.alexng.gns.lexer.tokens;

import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class CommaToken extends Token {

	public CommaToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}
	
	@Override
	public String toString() {
		return "<Comma >";
	}

	public static class Generator implements TokenGenerator {

		private final char CHAR = ',';

		@Override
		public int accepts(String input, int index) {
			return index + (input.charAt(index) == CHAR ? 1 : 0);
		}

		@Override
		public Token generate(String input, int startIndex, int endIndex) throws ParsingException {
			return new CommaToken(startIndex, endIndex);
		}
	}
}