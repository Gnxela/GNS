package me.alexng.gns.lexer.tokens;

import me.alexng.gns.AmbiguousParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class IdentifierToken extends Token {

	private String identifier;

	IdentifierToken(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return "<Identifier " + identifier + ">";
	}

	public static class IdentifierGenerator implements TokenGenerator {
		@Override
		public int accepts(String input) {
			int endIndex = 0;
			while (Character.isLetterOrDigit(input.charAt(endIndex))) {
				endIndex++;
			}
			return endIndex;
		}

		@Override
		public Token generate(String input) throws AmbiguousParsingException {
			return new IdentifierToken(input);
		}
	}
}