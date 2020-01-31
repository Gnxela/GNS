package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.operators.EqualToken;

public class EqualGenerator implements TokenGenerator {

	@Override
	public int accepts(String input, int index) {
		return index + (input.substring(index).startsWith(EqualToken.OPERATOR_STRING) ? EqualToken.OPERATOR_STRING.length() : 0);
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return new EqualToken(fileIndex);
	}
}