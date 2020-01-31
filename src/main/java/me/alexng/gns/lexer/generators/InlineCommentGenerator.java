package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.Token;

public class InlineCommentGenerator implements TokenGenerator {

	private static final EOLGenerator eolGenerator = new EOLGenerator();

	@Override
	public int accepts(String input, int index) {
		if (input.substring(index).startsWith("//")) {
			index += 2;
			while (index < input.length() && eolGenerator.accepts(input, index) == index) {
				index++;
			}
			return index;
		}
		return index;
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return null;
	}
}
