package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.operators.AdditionToken;

public class AdditionGenerator implements TokenGenerator {

	// TODO: All the BinaryOperatorGenerators are the exact same. Look into combining them again.

	@Override
	public int accepts(String input, int index) {
		return index + (input.substring(index).startsWith(AdditionToken.OPERATOR_STRING) ? AdditionToken.OPERATOR_STRING.length() : 0);
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return new AdditionToken(fileIndex);
	}
}