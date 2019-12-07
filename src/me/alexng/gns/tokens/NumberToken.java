package me.alexng.gns.tokens;

import me.alexng.gns.env.NumberValue;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class NumberToken extends Token {

	private NumberValue value;

	private NumberToken(String value, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.value = new NumberValue(value);
	}

	@Override
	public Value execute(Scope scope) {
		return value;
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