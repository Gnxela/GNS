package me.alexng.gns.lexer.tokens;

import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class EOLToken extends Token {

	EOLToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}

	@Override
	public String toString() {
		return "<EOL >";
	}

	public static class Generator implements TokenGenerator {

		// TODO: Extend this to accept different EOL types ["\r\n", "\n\r", etc.].
		private final char CHAR = '\n';

		@Override
		public int accepts(String input, int index) {
			return index + (input.charAt(index) == CHAR ? 1 : 0);
		}

		@Override
		public Token generate(String input, int startIndex, int endIndex) {
			return new EOLToken(startIndex, endIndex);
		}
	}
}
