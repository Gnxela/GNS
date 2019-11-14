package me.alexng.gns.lexer.tokens;

import me.alexng.gns.AmbiguousParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class NumberToken extends Token {

	// TODO: I'm storing the value as a string here. Eventually we should change this (or keep it and just deal with it when we transform to Symbols)
	private String value;

	NumberToken(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "<Number " + value + ">";
	}

	public static class Generator implements TokenGenerator {
		@Override
		public int accepts(String input) {
			int endIndex = 0;
			while (endIndex < input.length() && Character.isDigit(input.charAt(endIndex))) {
				endIndex++;
			}
			return endIndex;
		}

		@Override
		public Token generate(String input) throws AmbiguousParsingException {
			return new NumberToken(input);
		}
	}
}