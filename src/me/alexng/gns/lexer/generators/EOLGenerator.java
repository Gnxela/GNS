package me.alexng.gns.lexer.generators;

import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.EOLToken;
import me.alexng.gns.tokens.Token;

public class EOLGenerator implements TokenGenerator {

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
