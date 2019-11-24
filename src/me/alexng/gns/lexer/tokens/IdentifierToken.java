package me.alexng.gns.lexer.tokens;

import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class IdentifierToken extends Token {

	private String identifier;

	IdentifierToken(String identifier, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return "<Identifier " + identifier + ">";
	}

	public static class Generator implements TokenGenerator {
		@Override
		public int accepts(String input, int index) {
			if (!Character.isLetter(input.charAt(index))) {
				return 0;
			}
			int endIndex = index + 1;
			while (endIndex < input.length() && Character.isLetterOrDigit(input.charAt(endIndex))) {
				endIndex++;
			}
			return endIndex;
		}

		@Override
		public Token generate(String input, int startIndex, int endIndex) throws ParsingException {
			return new IdentifierToken(input.substring(startIndex, endIndex), startIndex, endIndex);
		}
	}
}