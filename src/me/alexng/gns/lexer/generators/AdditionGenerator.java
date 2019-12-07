package me.alexng.gns.lexer.generators;

import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.AdditionToken;
import me.alexng.gns.tokens.Token;

public class AdditionGenerator implements TokenGenerator {

	private final char CHAR = '+';

	@Override
	public int accepts(String input, int index) {
		return index + (input.charAt(index) == CHAR ? 1 : 0);
	}

	@Override
	public Token generate(String input, int startIndex, int endIndex) {
		return new AdditionToken(startIndex, endIndex);
	}
}