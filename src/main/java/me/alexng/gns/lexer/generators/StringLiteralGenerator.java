package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.StringLiteralToken;
import me.alexng.gns.tokens.Token;

public class StringLiteralGenerator implements TokenGenerator {

	private static final char CHAR = '"';

	@Override
	public int accepts(String input, int index) {
		if (input.charAt(index) == CHAR) {
			// TODO: Escaped chars / quotes
			return input.indexOf(CHAR, index + 1) + 1;
		}
		return index;
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return new StringLiteralToken(fileIndex.morph(1, -1).substring(input), fileIndex);
	}
}
