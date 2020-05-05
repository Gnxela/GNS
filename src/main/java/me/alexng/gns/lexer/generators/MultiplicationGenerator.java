package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.operators.MultiplicationToken;

public class MultiplicationGenerator implements TokenGenerator {

	// TODO: All the BinaryOperatorGenerators are the exact same. Look into combining them again.

	@Override
	public int accepts(String input, int index) {
		return index + (input.substring(index).startsWith(MultiplicationToken.OPERATOR_STRING) ? MultiplicationToken.OPERATOR_STRING.length() : 0);
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return new MultiplicationToken(fileIndex);
	}
}