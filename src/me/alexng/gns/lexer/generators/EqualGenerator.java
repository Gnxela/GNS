package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.operators.EqualToken;

public class EqualGenerator implements TokenGenerator {

	private final char CHAR = '=';

	@Override
	public int accepts(String input, int index) {
		return index + (input.length() > index + 1 && input.charAt(index) == CHAR && input.charAt(index + 1) == CHAR ? 2 : 0);
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return new EqualToken(fileIndex);
	}
}