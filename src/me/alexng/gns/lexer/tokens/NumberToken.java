package me.alexng.gns.lexer.tokens;

import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class NumberToken extends Token {

	// TODO: I'm storing the value as a string here. Eventually we should change this (or keep it and just deal with it when we transform to Symbols)
	private String value;

	NumberToken(String value, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.value = value;
	}

	@Override
	public Value execute(Scope scope) {
		// TODO: Input data
		return new Value();
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
		public Token generate(String input, int startIndex, int endIndex) {
			return new NumberToken(input.substring(startIndex, endIndex), startIndex, endIndex);
		}
	}
}