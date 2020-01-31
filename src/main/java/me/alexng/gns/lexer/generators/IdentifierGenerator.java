package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.Token;

public class IdentifierGenerator implements TokenGenerator {
	@Override
	public int accepts(String input, int index) {
		if (!Character.isLetter(input.charAt(index))) {
			return 0;
		}
		int endIndex = index + 1;
		while (endIndex < input.length() && Character.isLetterOrDigit(input.charAt(endIndex))) {
			endIndex++;
		}
		return endIndex;
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return new IdentifierToken(fileIndex.substring(input), fileIndex);
	}
}
