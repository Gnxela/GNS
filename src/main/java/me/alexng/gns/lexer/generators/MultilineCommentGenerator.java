package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.Token;

public class MultilineCommentGenerator implements TokenGenerator {

	private static final EOLGenerator eolGenerator = new EOLGenerator();

	@Override
	public int accepts(String input, int index) {
		// TODO: make string literals final static variables.
		if (input.substring(index).startsWith("/*")) {
			index += 3;
			while (index < input.length() && !(input.charAt(index - 1) == '*' && input.charAt(index) == '/')) {
				index++;
			}
			return index + 1;
		}
		return index;
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return null;
	}
}
