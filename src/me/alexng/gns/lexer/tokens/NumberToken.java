package me.alexng.gns.lexer.tokens;

import me.alexng.gns.ParsingException;
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
		public int accepts(String input, int index) {
			// TODO: Floats/doubles
			int endIndex = index;
			while (endIndex < input.length() && Character.isDigit(input.charAt(endIndex))) {
				endIndex++;
			}
			return endIndex;
		}

		@Override
		public Token generate(String input, int startIndex, int endIndex) throws ParsingException {
			return new NumberToken(input.substring(startIndex, endIndex));
		}
	}
}