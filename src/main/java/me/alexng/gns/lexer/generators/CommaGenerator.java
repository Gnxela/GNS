package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.CommaToken;
import me.alexng.gns.tokens.Token;

public class CommaGenerator implements TokenGenerator {

	private static final char CHAR = ',';

	@Override
	public int accepts(String input, int index) {
		return index + (input.charAt(index) == CHAR ? 1 : 0);
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return new CommaToken(fileIndex);
	}
}