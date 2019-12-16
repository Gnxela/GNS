package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.NumberToken;
import me.alexng.gns.tokens.Token;

public class NumberGenerator implements TokenGenerator {
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
	public Token generate(String input, FileIndex fileIndex) {
		return new NumberToken(fileIndex.substring(input), fileIndex);
	}
}