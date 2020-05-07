package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.value.NumberValue;

public class NumberGenerator implements TokenGenerator {

	private static final char NEGATION = '-';
	private static final char DECIMAL = '.';

	@Override
	public int accepts(String input, int index) {
		int endIndex = index;
		if (input.charAt(endIndex) == NEGATION) {
			endIndex++;
		}
		while (endIndex < input.length() && Character.isDigit(input.charAt(endIndex))) {
			endIndex++;
		}
		if (endIndex + 1 < input.length() && input.charAt(endIndex) == DECIMAL && Character.isDigit(input.charAt(endIndex + 1))) {
			endIndex += 2;
			while (endIndex < input.length() && Character.isDigit(input.charAt(endIndex))) {
				endIndex++;
			}
		}
		return endIndex;
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return new NumberValue(fileIndex.substring(input), fileIndex);
	}
}