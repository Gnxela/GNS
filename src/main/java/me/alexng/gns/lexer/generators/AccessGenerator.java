package me.alexng.gns.lexer.generators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.lexer.TokenGenerator;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.operators.AccessToken;

public class AccessGenerator implements TokenGenerator {

	@Override
	public int accepts(String input, int index) {
		return index + (input.substring(index).startsWith(AccessToken.OPERATOR_STRING) ? AccessToken.OPERATOR_STRING.length() : 0);
	}

	@Override
	public Token generate(String input, FileIndex fileIndex) {
		return new AccessToken(fileIndex);
	}
}