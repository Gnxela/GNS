package me.alexng.gns.lexer.tokens;

import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class EqualToken extends Token {

	public EqualToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}
	
	@Override
	public String toString() {
		return "<Equal >";
	}

	public static class Generator implements TokenGenerator {

		private final char CHAR = '=';

		@Override
		public int accepts(String input, int index) {
			return index + (input.length() > index + 1 && input.charAt(index) == CHAR && input.charAt(index + 1) == CHAR ? 2 : 0);
		}

		@Override
		public Token generate(String input, int startIndex, int endIndex) throws ParsingException {
			return new EqualToken(startIndex, endIndex);
		}
	}
}