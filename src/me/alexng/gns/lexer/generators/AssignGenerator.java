package me.alexng.gns.lexer.generators;

import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.AssignToken;
import me.alexng.gns.tokens.Token;

public class AssignGenerator implements TokenGenerator {

	private final char CHAR = '=';

	@Override
	public int accepts(String input, int index) {
		return index + (input.charAt(index) == CHAR ? 1 : 0);
	}

	@Override
	public Token generate(String input, int startIndex, int endIndex) {
		return new AssignToken(startIndex, endIndex);
	}
}